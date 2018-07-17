package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by RosieHyunahPark on 2018-07-14.
 */

public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("isFirstRun", Activity.MODE_PRIVATE);
                boolean first = pref.getBoolean("isFirstRun", false);
                if(!first){
                    Log.d("isFirstRun", "true");
                    Intent termOfUsePage = new Intent(LoadingActivity.this, TermOfUseActivity.class);
                    startActivity(termOfUsePage);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putBoolean("isFirstRun",true);
                    editor.commit();
                }else{
                    Log.d("isFirstRun", "false");

                    //앱 최초 실행이 아닐시, main activity로 이동
                    Intent mainPage = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(mainPage);

                }

                finish();
            }
        }, 2000);


    }
}