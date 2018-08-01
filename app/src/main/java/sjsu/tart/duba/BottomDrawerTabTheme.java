package sjsu.tart.duba;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by lion7 on 2018-07-24.
 */

public class BottomDrawerTabTheme extends Fragment {
    public static String[] buttonsKeys = {"Single", "Family", "Couple", "Friend"};
    public static Boolean[] buttonsValues = {false, false, false, false};

    private Button[] buttons = new Button[4];
    private int[] buttonsId = {R.id.themeButton0, R.id.themeButton1, R.id.themeButton2, R.id.themeButton3};
    private static int buttonsNum = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_theme, container, false);
        findViewByButtons(view);
        setOnClickListeners();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViewByButtons(View view){
        for(int i = 0; i < buttonsNum; i++){
            buttons[i] = view.findViewById(buttonsId[i]);
            if(buttonsValues[i]){
                buttons[i].setAlpha(MainActivity.NOT_TRANSPARENT);
                buttons[i].setText("V");
            }
        }
    }

    private void setOnClickListeners(){
        for(int i = 0; i < buttonsNum; i++){
            buttons[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    for(int j = 0; j < buttonsNum; j++){
                        if(view.getId() == buttonsId[j]){
                            if(view.getAlpha() == MainActivity.TRANSPARENT){
                                buttons[j].setAlpha(MainActivity.NOT_TRANSPARENT);
                                buttons[j].setText("V");
                                buttonsValues[j] = true;
                            }
                            else{
                                buttons[j].setAlpha(MainActivity.TRANSPARENT);
                                buttons[j].setText("");
                                buttonsValues[j] = false;
                            }
                        }
                    }

                    LoadingActivity.mDbOpenHelper.open();
                    LoadingActivity.mDbOpenHelper.showDatabaseByLog("markerid");
                    String[] tags = getSelectedTotalOptions();
                    MarkerData[] markers = LoadingActivity.mDbOpenHelper.getMarkerData(tags);
                    addMarkerstoMap(markers);
                    LoadingActivity.mDbOpenHelper.close();
                }
            });
        }
    }

    public static String getSelectedOptions(){
        String ret = "";
        for(int i = 0; i < buttonsNum; i++){
            if(buttonsValues[i]){
                ret += ( buttonsKeys[i] + "," );
            }
        }
        return ret;
    }


    public String[] getSelectedTotalOptions(){
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
        MainActivity.removeRecommendedMarker();

        for(int i = 0; i < 5; i++){
            LatLng currentLocation = new LatLng( Double.parseDouble(markers[i].lan), Double.parseDouble(markers[i].lon));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markers[i].title);

            Marker currentMarker = googleMap.addMarker(markerOptions);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.0f));
            MainActivity.recommendedMarker[i] = currentMarker;
        }
    }
}