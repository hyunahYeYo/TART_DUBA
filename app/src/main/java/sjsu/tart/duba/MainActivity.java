package sjsu.tart.duba;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DirectionFinderListener{

    public static final String TAG = "DUBA_Project";
    public static final float TRANSPARENT = 0.3F;
    public static final float NOT_TRANSPARENT = 1.0F;
    public static final int RECOMMENDED_MARKER_NUM = 5;

    static final String[] DIALOG_LIST_MENU={ "YeonJae- So So","Hyuna - No Fun", "Sang il - Neclear No Fun",
            "JangHak- Good place","Soo yeon- Beautilful Place!","Kong - Nothing is worse than this place"};
    public static Marker[] recommendedMarker = new Marker[RECOMMENDED_MARKER_NUM];
    public static int recommendedStartMarkerIdx = 0;

    private ImageButton fabDrawer;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private static Button bottomDrawerButton;
    private static SlidingDrawer slidingDrawer;

    private ImageButton rightSlideBtn; //right slide button
    private LinearLayout rightNavigationView;
    private ListView rightSlideListView;
    private Button rightSlideEditBtn;
    private static RightBarListViewAdapter rightBarAdapter;
    private Button confirmBtn;
    private Button searchBtn;
    private ImageButton upBtn;
    private ImageButton downBtn;
    private ImageButton delBtn;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private int index = 0;
    private GoogleMap googleMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MemoryError","MainAcitivity");

        //floatingButton으로 drawer 열기
        fabDrawer=(ImageButton)findViewById(R.id.fabHam);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);
        bottomDrawerButton = (Button) findViewById(R.id.bottomDrawer);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);

        upBtn = (ImageButton)findViewById(R.id.upBtn);
        downBtn = (ImageButton)findViewById(R.id.downBtn);
        delBtn = (ImageButton)findViewById(R.id.delBtn);

        rightSlideBtn = (ImageButton)findViewById(R.id.rightSlideBtn);
        rightNavigationView=(LinearLayout)findViewById(R.id.rightDrawer);
        rightSlideListView = (ListView)findViewById(R.id.rightBarList);
        rightSlideEditBtn = (Button)findViewById(R.id.editBtn);
        confirmBtn = (Button)findViewById(R.id.confirmBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);

        rightBarAdapter = new RightBarListViewAdapter();
        rightSlideListView.setAdapter(rightBarAdapter);


        fabDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);

                SharedPreferences userPref = getSharedPreferences("userName", Activity.MODE_PRIVATE);
                SharedPreferences pointPref = getSharedPreferences("point", Activity.MODE_PRIVATE);
                SharedPreferences stepPref = getSharedPreferences("step", Activity.MODE_PRIVATE);

                String userName = userPref.getString("userName", "");
                int point = pointPref.getInt("userName", 10000);
                int step = stepPref.getInt("userName", 16384);
                String pointAndStepStr = String.format("%,d", point)+" points    "+String.format("%,d", step)+" steps";

                TextView userNameTextView = (TextView)findViewById(R.id.userName);
                TextView pointAndStepTextView = (TextView)findViewById(R.id.pointAndStep);
                userNameTextView.setText(userName);
                pointAndStepTextView.setText(pointAndStepStr);

            }
        });

        // Listeners for sliding drawer
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {

            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {

            }

        });

        // bottom drawer 생성 및 세팅
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("THEME"));
        tabLayout.addTab(tabLayout.newTab().setText("PLACE"));
        tabLayout.addTab(tabLayout.newTab().setText("INFO"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        rightSlideBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Right Slide Button Clicked");

                drawerLayout.openDrawer(rightNavigationView);
            }
        });
        rightSlideEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SEARCH BUTTON CLICKED");

                upBtn.setVisibility(View.VISIBLE);
                downBtn.setVisibility(View.VISIBLE);
                delBtn.setVisibility(View.VISIBLE);
                confirmBtn.setVisibility(View.VISIBLE);
                rightSlideEditBtn.setVisibility(View.GONE);
                searchBtn.setVisibility(View.GONE);

                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        upBtn.setVisibility(View.GONE);
                        downBtn.setVisibility(View.GONE);
                        delBtn.setVisibility(View.GONE);
                        confirmBtn.setVisibility(View.GONE);
                        rightSlideEditBtn.setVisibility(View.VISIBLE);
                        searchBtn.setVisibility(View.VISIBLE);
                    }
                });
                delBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DELETE", "DELETE CLICKED");
                        int count, checked ;
                        count = rightBarAdapter.getCount() ;

                        if (count > 0) {
                            // 현재 선택된 아이템의 position 획득.
                            checked = rightSlideListView.getCheckedItemPosition();
                            Log.d("DELETE", "DELETE : " + count + "/" + checked);
                            if (checked > -1 && checked < count) {
                                RouteList.deleteItem(checked);
                                RouteList.printList();

                                modifyRightlist();
                            }
                        }
                    }
                });
                upBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DELETE", "DELETE CLICKED");
                        int count, checked ;
                        count = rightBarAdapter.getCount() ;
                        if (count > 0) {
                            // 현재 선택된 아이템의 position 획득.
                            checked = rightSlideListView.getCheckedItemPosition();
                            Log.d("UP", "DELETE : " + count + "/" + checked);
                            if (checked > -1 && checked < count) {
                                RouteList.changeOrder(checked, 0);
                                RouteList.printList();

                                modifyRightlist();
                            }
                        }
                    }
                });
                downBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DOWN", "DELETE CLICKED");
                        int count, checked ;
                        count = rightBarAdapter.getCount() ;
                        if (count > 0) {
                            // 현재 선택된 아이템의 position 획득.
                            checked = rightSlideListView.getCheckedItemPosition();
                            Log.d("DOWN", "DELETE : " + count + "/" + checked);
                            if (checked > -1 && checked < count) {
                                RouteList.changeOrder(checked, 1);
                                RouteList.printList();

                                modifyRightlist();
                            }
                        }
                    }
                });
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_Path(RouteList.getList());
            }
        });
    }

    public void search_Path(Route routeList_Header) {
        for (Route r = routeList_Header; r.getNext() != null; r = r.getNext()) {
            try {
                String origin = r.getAddress();
                String destination = r.getNext().getAddress();
                new DirectionFinder(this, origin, destination).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRequest_Food() {
        /*String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();*/

        try {
            //new Direction(this, "San Jose State University", "San Jose Diridon Station").execute();
            onDirectionFinderStart();
            new DirectionFinder(this, "San Jose State University", "404 S 6th St, San Jose, CA 95112").execute();
            new DirectionFinder(this, "404 S 6th St, San Jose, CA 95112", "374 S 3rd St, San Jose, CA 95112").execute();
            new DirectionFinder(this, "374 S 3rd St, San Jose, CA 95112", "Robert F. Peckham Federal Building, 280 S 1st St, San Jose, CA 95113").execute();
            new DirectionFinder(this, "Robert F. Peckham Federal Building, 280 S 1st St, San Jose, CA 95113", "201 S Market St, San Jose, CA 95113").execute();
            new DirectionFinder(this, "201 S Market St, San Jose, CA 95113", "San Jose Diridon Station").execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /*public void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        index = 1;
        if (origin.isEmpty()) {
            return;
        }
        if (destination.isEmpty()) {
            return;
        }

        try {
            new Direction(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    } //단순히 길을 찾는 기능*/
/*
    public void sendRequest_Shopping() {
        try {
            //new Direction(this, "San Jose State University", "San Jose Diridon Station").execute();
            onDirectionFinderStart();
            new DirectionFinder(this, "770 North Point St, San Francisco, CA 94109", "San Francisco, CA 94111").execute();
            new DirectionFinder(this, "San Francisco, CA 94111", "865 Market St, San Francisco, CA 94103").execute();
            new DirectionFinder(this, "865 Market St, San Francisco, CA 94103", "900 North Point St, San Francisco, CA 94109").execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void sendRequest_Activity() {
        try {
            //new Direction(this, "San Jose State University", "San Jose Diridon Station").execute();
            onDirectionFinderStart();
            new DirectionFinder(this, "San Jose State University", "404 S 6th St, San Jose, CA 95112").execute();
            new DirectionFinder(this, "404 S 6th St, San Jose, CA 95112", "374 S 3rd St, San Jose, CA 95112").execute();
            new DirectionFinder(this, "374 S 3rd St, San Jose, CA 95112", "Robert F. Peckham Federal Building, 280 S 1st St, San Jose, CA 95113").execute();
            new DirectionFinder(this, "Robert F. Peckham Federal Building, 280 S 1st St, San Jose, CA 95113", "201 S Market St, San Jose, CA 95113").execute();
            new DirectionFinder(this, "201 S Market St, San Jose, CA 95113", "San Jose Diridon Station").execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
*/
    public void onDirectionFinderStart() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    public void onDirectionFinderSuccess(List<Line> routes) {
        /*polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();*/
        googleMap = FragmentMap.getGoogleMap();
        for (Line route : routes) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 14));
            if(index == 0)
            {
/*
                originMarkers.add(googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title(route.startAddress)
                        .position(route.startLocation)));
                destinationMarkers.add(googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title(route.endAddress)
                        .position(route.endLocation)));
*/
            }
            else    //단순한 길 찾기
            {
                originMarkers.add(googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(route.startAddress)
                        .position(route.startLocation)));
                destinationMarkers.add(googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(route.endAddress)
                        .position(route.endLocation)));
            }
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.GREEN).
                    width(10);
            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(googleMap.addPolyline(polylineOptions));
        }
    }

    public static void modifyRightlist() {
        Log.d("LENGTH", "check1");
        rightBarAdapter.clearAllItems();
        Route mover = RouteList.HeadRoute;
        Log.d("LENGTH", "check2");
        if(mover!=null) {
            while(mover!=RouteList.TailRoute) {
                rightBarAdapter.addItem(mover.getLocation());
                mover = mover.getNext();
            }
            rightBarAdapter.addItem(mover.getLocation());
        }
        Log.d("LENGTH", "check3");
        // listview 갱신.
        rightBarAdapter.notifyDataSetChanged();
        Log.d("LENGTH", "check4");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem  item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {

        } else if (id == R.id.wishlist) {

        } else if (id == R.id.setting) {

        } else if(id== R.id.emergency) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void removeRecommendedMarker(){
        for(int i = 0; i < RECOMMENDED_MARKER_NUM; i++){
            if(recommendedMarker[i] != null){
                recommendedMarker[i].remove();
            }
        }
    }
}