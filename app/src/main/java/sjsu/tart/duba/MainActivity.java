package sjsu.tart.duba;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener{

    public static final String TAG = "DUBA_Project";

    FloatingActionButton camerafab, gpsfab;
    MapView mapView;
    ImageButton fabDrawer;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    private FloatingActionButton slideBar;
    private ViewGroup backLayout;

    private int xDelta;
    private int yDelta;

    private DrawerLayout rightLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //floatingButton으로 drawer 열기
        camerafab = (FloatingActionButton) findViewById(R.id.cameraFab);
        gpsfab = (FloatingActionButton) findViewById(R.id.gpsFab);
        fabDrawer=(ImageButton)findViewById(R.id.fabHam);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

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

        camerafab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rightLayout = (DrawerLayout)findViewById(R.id.rightSideDrawer);
        gpsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Afction", null).show();
            }
        });

        backLayout = (CoordinatorLayout)findViewById(R.id.bg);
        slideBar = (FloatingActionButton)findViewById(R.id.sideBar);
        slideBar.setOnTouchListener(this);


    }
    public boolean onTouch(View view, MotionEvent event) {
        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                CoordinatorLayout.LayoutParams lParams = (CoordinatorLayout.LayoutParams)view.getLayoutParams();

                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                Log.d("SlideBarTest", "DownPoints:"+x+"/"+y);
                Log.d("SlideBarTest", "DownParams:"+lParams.leftMargin+"/"+lParams.topMargin);
                break;
            case MotionEvent.ACTION_MOVE:
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
                Log.d("SlideBarTest", "MoveParams1:"+layoutParams.leftMargin+"/"+layoutParams.topMargin);

                layoutParams.leftMargin = x - xDelta;
                layoutParams.topMargin = y - yDelta;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                Log.d("SlideBarTest", "MovePoints:"+x+"/"+y);
                Log.d("SlideBarTest", "MoveParams2:"+layoutParams.leftMargin+"/"+layoutParams.topMargin);

                view.setLayoutParams(layoutParams);

                break;
            case MotionEvent.ACTION_UP:

                //Toast.makeText(MainActivity.this, "앗싸", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
        backLayout.invalidate();
        return true;
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
    public boolean onNavigationItemSelected(MenuItem item) {
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
