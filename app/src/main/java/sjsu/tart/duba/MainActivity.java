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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "DUBA_Project";
    public static final float TRANSPARENT = 0.3F;
    public static final float NOT_TRANSPARENT = 1.0F;

    private ImageButton fabDrawer;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private static Button bottomDrawerButton;
    private static SlidingDrawer slidingDrawer;

    private ImageButton rightSlideBtn; //right slide button
    //private NavigationView rightNavigationView;
    private LinearLayout rightNavigationView;
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

        rightSlideBtn = (ImageButton)findViewById(R.id.rightSlideBtn);
        rightNavigationView=(LinearLayout)findViewById(R.id.rightDrawer);

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
                LoadingActivity.mDbOpenHelper.open();
                LoadingActivity.mDbOpenHelper.showDatabaseByLog("markerid");
                String[] tags = getSelectedOptions();
                MarkerData[] markers = LoadingActivity.mDbOpenHelper.getMarkerData(tags);
                addMarkerstoMap(markers);
                LoadingActivity.mDbOpenHelper.close();
            }

            public String[] getSelectedOptions(){
                String options = "";
                options += BottomDrawerTabTheme.getSelectedOptions();
                options += BottomDrawerTabNationality.getSelectedOptions();
                options += BottomDrawerTabGenderAge.getSelectedOptions();
                Log.d("getSelectedOptions()", options);

                String[] ret = options.split(",");
                return ret;
            }

            public void addMarkerstoMap(MarkerData[] markers){
                GoogleMap googleMap = FragmentMap.getGoogleMap();
                for(int i = 0; i < 5; i++){
                    LatLng currentMarker = new LatLng( Double.parseDouble(markers[i].lan), Double.parseDouble(markers[i].lon));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(currentMarker);
                    googleMap.addMarker(markerOptions);
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentMarker));
                }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
