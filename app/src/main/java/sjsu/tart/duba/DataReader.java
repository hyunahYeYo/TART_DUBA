package sjsu.tart.duba;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lion7 on 2018-07-31.
 */

public class DataReader {

    private String path;
    private String encoding = "euc-kr";
    private LoadingActivity loadingActivity;

    private static MarkerData[] markerField = new MarkerData[50];
    private static int markerNum = 0;

    public DataReader(String path, LoadingActivity loadingActivity){
        this.path = path;
        this.loadingActivity = loadingActivity;
    }

    public void run() {
        BufferedReader br = null;
        String line;
        String cvsSplitBy = "\t";

        Log.d("dataReader", "run");

        try {
            br = new BufferedReader(new InputStreamReader(
                    loadingActivity.getAssets().open(path)));

            while ((line = br.readLine()) != null && !line.equals("")) {
                Log.d("dataReader", Integer.toString(markerNum) + ":"+line);
                String[] field = line.split(cvsSplitBy);
                markerField[markerNum++] = new MarkerData(field[0], field[1], field[2], field[3], field[4]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("dataReader", "FileNotFoundException");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dataReader", "IOException");
        } finally {
            if (br != null) {
                try {
                    br.close();
                    Log.d("dataReader", "close");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public MarkerData[] getMarkerData(){   return markerField; }
    public int getMarkerNum(){ return markerNum; }

}
