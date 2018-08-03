package sjsu.tart.duba;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by lion7 on 2018-07-24.
 */

public class BottomDrawerTabTheme extends Fragment {
    public static String[] buttonsKeys = {"Single", "Family", "Couple", "Friend"};
    public static Boolean[] buttonsValues = {false, false, false, false};

    private Button[] buttons = new Button[4];
    private Button suggest1, suggest2;
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
        suggest1 = (Button) view.findViewById(R.id.suggestOtherPlaceButton);
        suggest2 = (Button) view.findViewById(R.id.suggestPathButton);
    }

    private void setOnClickListeners(){
        for(int i = 0; i < buttonsNum; i++){
            buttons[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    for(int j = 0; j < buttonsNum; j++){
                        if(view.getId() == buttonsId[j]){
                            if(view.getAlpha() == MainActivity.TRANSPARENT){
                                for(int k = 0; k < buttonsNum; k++){
                                    buttons[j].setAlpha(MainActivity.TRANSPARENT);
                                    buttons[j].setText("");
                                    buttonsValues[j] = false;
                                }
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

                    MainActivity.recommendedStartMarkerIdx = 0;
                    LoadingActivity.mDbOpenHelper.open();
                    LoadingActivity.mDbOpenHelper.showDatabaseByLog("markerid");
                    String[] tags = getSelectedTotalOptions();
                    MarkerData[] markers = LoadingActivity.mDbOpenHelper.getMarkerData(tags);
                    LoadingActivity.mDbOpenHelper.close();
                    if(!tags[0].equals(""))
                        addMarkerstoMap(markers, BitmapDescriptorFactory.HUE_RED, 1);
                    else {
                        MainActivity.removeRecommendedMarker();
                    }
                }
            });

            suggest1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d("mainActivityStartidx", Integer.toString(MainActivity.recommendedStartMarkerIdx));
                    if(MainActivity.recommendedStartMarkerIdx > 25){
                        MainActivity.recommendedStartMarkerIdx = 0;
                    }
                    LoadingActivity.mDbOpenHelper.open();
                    LoadingActivity.mDbOpenHelper.showDatabaseByLog("markerid");
                    String[] tags = getSelectedTotalOptions();
                    Log.d("mainActivityStartidx", Integer.toString(MainActivity.recommendedStartMarkerIdx));
                    MarkerData[] markers = LoadingActivity.mDbOpenHelper.getMarkerData(tags);
                    addMarkerstoMap(markers, BitmapDescriptorFactory.HUE_RED, 1);
                    LoadingActivity.mDbOpenHelper.close();

                }
            });

            suggest2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String[] titles = RecommendDataReader.getRecommendPathTitle();
                    String[] address = RecommendDataReader.getPathAddrField();
                    String[] lan = RecommendDataReader.getPathLanField();
                    String[] lon = RecommendDataReader.getPathLonField();
                    GoogleMap googleMap = FragmentMap.getGoogleMap();
                    Marker marker;

                    RouteList.deleteAll();
                    for(int i = 0; i < titles.length; i++){
                        if(!RouteList.checkList(titles[i])) {
                            LatLng currentLocation = new LatLng(Double.parseDouble(lan[i]), Double.parseDouble(lon[i]));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(currentLocation);
                            markerOptions.title(titles[i]);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                            marker = googleMap.addMarker(markerOptions);
                            googleMap.getUiSettings().setMapToolbarEnabled(false);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.0f));
                        }
                        else{
                            continue;
                        }
                        RouteList.addList(titles[i], address[i], marker, getContext());
                    }
                    RouteList.printList();
                    MainActivity.modifyRightlist();
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

    public Marker[] addMarkerstoMap(MarkerData[] markersData, float color, int type){
        GoogleMap googleMap = FragmentMap.getGoogleMap();
        if(type == 1)
            MainActivity.removeRecommendedMarker();
        Marker[] markers = new Marker[5];

        int i = 0;
        while( i < 5 ){
            int index = i + MainActivity.recommendedStartMarkerIdx;
            if(!RouteList.checkList(markersData[index].title)) {
                LatLng currentLocation = new LatLng(Double.parseDouble(markersData[index].lan), Double.parseDouble(markersData[index].lon));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(currentLocation);
                markerOptions.title(markersData[index].title);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));

                markers[i] = googleMap.addMarker(markerOptions);
                googleMap.getUiSettings().setMapToolbarEnabled(false);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.0f));
                if(type == 1)
                    MainActivity.recommendedMarker[i] =markers[i];
                i += 1;
            }
            else{
                MainActivity.recommendedStartMarkerIdx += 1;
            }
        }
        MainActivity.recommendedStartMarkerIdx += 5;
        return markers;
    }

}