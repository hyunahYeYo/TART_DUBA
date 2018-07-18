package sjsu.tart.duba;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
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

import com.google.android.gms.maps.MapView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener{

    public static final String TAG = "DUBA_Project";

    FloatingActionButton fab;
    MapView mapView;
    ImageButton fabDrawer;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    private FloatingActionButton slideBar;
    private ViewGroup backLayout;

    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("asdfsd", "asdfs");

        //floatingButton으로 drawer 열기
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabDrawer = (ImageButton) findViewById(R.id.fabHam);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        fabDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

/*
    public boolean onTouch(View view, MotionEvent event) {
        final int x = (int)event.getRawX();
        final int y = (int)event.getRawY();

        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                LinearLayout.LayoutParams
                break;
            case MotionEvent.ACTION_MOVE :
                break;
            case MotionEvent.ACTION_UP :
                break;
            default :
                break;
        }
        return false;
    }
    */
/*
    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG, "***onLongClick***");
        String[] descriptions = {
                ClipDescription.MIMETYPE_TEXT_PLAIN
        };
        ClipData.Item item = new ClipData.Item(String.valueOf((int)v.getTag()));
        ClipData clipData = new ClipData("drag and drop",descriptions, item);

        View.DragShadowBuilder shadow = new View.DragShadowBuilder(sideBar);
        v.startDrag(clipData, shadow, null, 0);

        return true;
    }
    @Override
    public boolean onDrag(View v, DragEvent event) {
        final int action = event.getAction();
        String label;
        switch(action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG, "***ACTION_DRAG_STARTED***");
                label = (String)event.getClipDescription().getLabel();
                return label.equals("Data_from_fb");
            case DragEvent.ACTION_DROP:
                Log.d(TAG, "***ACTION_DROP***");
                return true;
        }
        return false;
    }
    */
    //Detect DRAG Motion
    /*
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                downX = motionEvent.getX();
                downY = motionEvent.getY();

                Log.d(TAG, "****ACTION DOWN****"+downX+"/"+downY);
                break;
            case MotionEvent.ACTION_MOVE :
                Log.d(TAG, "****ACTION MOVE****");

                break;
            case MotionEvent.ACTION_UP :


                upX = motionEvent.getX();
                upY = motionEvent.getY();

                Log.d(TAG, "****ACTION UP****"+upX+"/"+upY);
                calTouchPoints();
                break;
            default :
                break;
        }
        return false;
    }
*/
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
