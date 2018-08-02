package sjsu.tart.duba;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class BottomDrawerTabGenderAge extends Fragment {
    public static String[] buttonsKeys = {"Child", "Adult", "Elderly"};
    public static Boolean[] buttonsValues = {false, false, false};

    private Button[] buttons = new Button[3];
    private Button suggest1, suggest2;
    private int[] buttonsId = {R.id.infoButton0, R.id.infoButton1, R.id.infoButton2};
    private static int buttonsNum = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_gender_age, container, false);
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
                    addMarkerstoMap(markers);
                    LoadingActivity.mDbOpenHelper.close();
                }
            });
        }
        suggest1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity.recommendedStartMarkerIdx += MainActivity.RECOMMENDED_MARKER_NUM;
                if(MainActivity.recommendedStartMarkerIdx > 25){
                    MainActivity.recommendedStartMarkerIdx = 0;
                }
                LoadingActivity.mDbOpenHelper.open();
                LoadingActivity.mDbOpenHelper.showDatabaseByLog("markerid");
                String[] tags = getSelectedTotalOptions();
                MarkerData[] markers = LoadingActivity.mDbOpenHelper.getMarkerData(tags);
                addMarkerstoMap(markers);
                LoadingActivity.mDbOpenHelper.close();
            }
        });

        suggest2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String[] titles = RecommendDataReader.getRecommendPathTitle();
                String[] address = RecommendDataReader.getRecommendPathAddress();

                RouteList.setSizeZero();
                for(int i = 0; i < titles.length; i++){
                    RouteList.addList(titles[i], address[i], getContext());
                }
                RouteList.printList();
                RouteList.reviseSelectedMarkerToMap(getContext());
            }
        });

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
            int index = i + MainActivity.recommendedStartMarkerIdx;
            LatLng currentLocation = new LatLng( Double.parseDouble(markers[index].lan), Double.parseDouble(markers[index].lon));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markers[index].title);

            Marker currentMarker = googleMap.addMarker(markerOptions);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.0f));
            MainActivity.recommendedMarker[i] = currentMarker;
        }
    }
}