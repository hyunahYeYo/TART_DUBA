package sjsu.tart.duba;

import android.app.Activity;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "DUBA_Project";
    public static final float TRANSPARENT = 0.3F;
    public static final float NOT_TRANSPARENT = 1.0F;
    public static final int RECOMMENDED_MARKER_NUM = 5;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        /*******************************************************/
        Route mover = RouteList.HeadRoute;

        if(mover!=null) {
            while(mover!=RouteList.TailRoute) {
                rightBarAdapter.addItem(mover.getLocation());
                mover = mover.getNext();
            }
            rightBarAdapter.addItem(mover.getLocation());
        }

        rightBarAdapter.notifyDataSetChanged();
        /*******************************************************/

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

                                // 아이템 삭제
                                //rightBarAdapter.deleteItem(checked);

                                rightBarAdapter.clearAllItems();

                                Route mover = RouteList.HeadRoute;
                                while(mover!=RouteList.TailRoute) {
                                    Log.d("DELETE","while : " + mover.getLocation());
                                    rightBarAdapter.addItem(mover.getLocation());
                                    mover = mover.getNext();
                                }
                                rightBarAdapter.addItem(mover.getLocation());
                                // listview 선택 초기화.
                                rightSlideListView.clearChoices();
                                // listview 갱신.
                                rightBarAdapter.notifyDataSetChanged();
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

                                // 아이템 삭제
                                //rightBarAdapter.deleteItem(checked);

                                rightBarAdapter.clearAllItems();

                                Route mover = RouteList.HeadRoute;
                                while(mover!=RouteList.TailRoute) {
                                    Log.d("UP","while : " + mover.getLocation());
                                    rightBarAdapter.addItem(mover.getLocation());
                                    mover = mover.getNext();
                                }
                                rightBarAdapter.addItem(mover.getLocation());
                                // listview 선택 초기화.
                                rightSlideListView.clearChoices();
                                // listview 갱신.
                                rightBarAdapter.notifyDataSetChanged();
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

                                // 아이템 삭제
                                //rightBarAdapter.deleteItem(checked);

                                rightBarAdapter.clearAllItems();

                                Route mover = RouteList.HeadRoute;
                                while(mover!=RouteList.TailRoute) {
                                    Log.d("DOWN","while : " + mover.getLocation());
                                    rightBarAdapter.addItem(mover.getLocation());
                                    mover = mover.getNext();
                                }
                                rightBarAdapter.addItem(mover.getLocation());
                                // listview 선택 초기화.
                                rightSlideListView.clearChoices();
                                // listview 갱신.
                                rightBarAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                rightSlideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    }
                });

                /*
                rightBarAdapter.addItem("b");

                rightBarAdapter.notifyDataSetChanged();*/
            }
        });

    }

    public static void modifyRightlist() {
        rightBarAdapter.clearAllItems();
        Route mover = RouteList.HeadRoute;

        if(mover!=null) {
            while(mover!=RouteList.TailRoute) {
                rightBarAdapter.addItem(mover.getLocation());
                mover = mover.getNext();
            }
            rightBarAdapter.addItem(mover.getLocation());
        }

        // listview 갱신.
        rightBarAdapter.notifyDataSetChanged();
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
            // Handle the camera action
        } else if (id == R.id.shop) {

        } else if (id == R.id.setting) {

        } else if(id== R.id.help) {

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