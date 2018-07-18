package sjsu.tart.duba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by RosieHyunahPark on 2018-07-18.
 */

public class Test extends Activity implements View.OnTouchListener{
    private ViewGroup mainLayout;
    private TextView text;

    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_sidebar);

        mainLayout = (RelativeLayout)findViewById(R.id.main);
        text = (TextView)findViewById(R.id.text);
        mainLayout.setOnTouchListener(this);
        //text.setOnTouchListener(this);
    }
    public boolean onTouch(View view, MotionEvent event) {

        Log.d("SideBarTest", "DOWN : "+event.getX()+"/"+event.getY());

        return true;
    }
    /*
    public boolean onTouch(View view, MotionEvent event) {
        final int x = (int)event.getRawX();
        final int y = (int)event.getRawY();
        Log.d("SideBarTest", "DOWN : "+event.getX()+"/"+event.getY());

        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)view.getLayoutParams();

                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;

                break;
            case MotionEvent.ACTION_MOVE :
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
                layoutParams.leftMargin = x - xDelta;
                layoutParams.topMargin = y - yDelta;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                view.setLayoutParams(layoutParams);

                break;
            case MotionEvent.ACTION_UP :

                Toast.makeText(Test.this, "앗싸", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
        mainLayout.invalidate();
        return true;
    }
    */
}
