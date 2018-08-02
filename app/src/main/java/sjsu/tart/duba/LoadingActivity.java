package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by RosieHyunahPark on 2018-07-14.
 */

public class LoadingActivity extends Activity {

    private TextView loadingText;
    public static DbOpenHelper mDbOpenHelper;
    public static RecommendDataReader mRecommendDataReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("conflict", "HEllo2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingText = (TextView) findViewById(R.id.loadingText);

        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        mDbOpenHelper.close();

        mRecommendDataReader = new RecommendDataReader("recommend_sanfrancisco.tsv", this);
        mRecommendDataReader.run();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("isFirstRun", Activity.MODE_PRIVATE);
                boolean first = pref.getBoolean("isFirstRun", true);
                if(first){

                    loadingText.setText("...Downloading files...");
                    setDataBase();

                    Intent termOfUsePage = new Intent(LoadingActivity.this, TermOfUseActivity.class);
                    startActivity(termOfUsePage);

                }else{

                    loadingText.setText("...Loading...");
                    //앱 최초 실행이 아닐시, main activity로 이동
                    Intent mainPage = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(mainPage);

                }

                finish();
            }
        }, 2000);


    }

    private void setDataBase(){
        DataReader dataReader = new DataReader("data.tsv", this);
        dataReader.run();

        mDbOpenHelper.open();
        MarkerData[] markerDatas = dataReader.getMarkerData();
        Log.d("dataReader.getMarkerNum", Integer.toString(dataReader.getMarkerNum()));

        for(int i = 0; i < dataReader.getMarkerNum(); i++){
            mDbOpenHelper.insertColumn(Integer.toString(i),
                    markerDatas[i].title, markerDatas[i].tag, markerDatas[i].lan, markerDatas[i].lon, markerDatas[i].color);
            Log.d("insultColumn", markerDatas[i].title);
        }
        mDbOpenHelper.close();
    }

}