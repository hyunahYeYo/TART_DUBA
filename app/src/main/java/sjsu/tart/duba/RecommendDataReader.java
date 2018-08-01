package sjsu.tart.duba;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lion7 on 2018-07-31.
 */

public class RecommendDataReader {

    private String path;
    private String encoding = "euc-kr";
    private LoadingActivity loadingActivity;

    private static String[][] pathTitleField = new String[5][];
    private static String[][] pathAddressField = new String[5][];
    private static int pathNum = 0;
    private static int currentPath = 0;

    public RecommendDataReader(String path, LoadingActivity loadingActivity){
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
                Log.d("dataReader", Integer.toString(pathNum) + ":"+line);
                String[] field = line.split(cvsSplitBy);
                pathTitleField[pathNum] = field;
                pathAddressField[pathNum++] = br.readLine().split(cvsSplitBy);
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

    public static String[] getRecommendPathTitle(){
        currentPath++;
        if(currentPath > 2)
            currentPath = 0;

        Log.d("currentPath", Integer.toString(currentPath));
        return pathTitleField[currentPath];
    }

    public static String[] getRecommendPathAddress(){
        return pathAddressField[currentPath];
    }
}
