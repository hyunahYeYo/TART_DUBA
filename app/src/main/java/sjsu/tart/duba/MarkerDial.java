package sjsu.tart.duba;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import static sjsu.tart.duba.RouteList.addList;
import static sjsu.tart.duba.RouteList.checkList;
import static sjsu.tart.duba.LoadingActivity.mDbOpenHelper;
import static sjsu.tart.duba.RouteList.printList;

/**
 * Created by Hak on 2018-07-28.
 */

public class MarkerDial extends DialogFragment {

    private Button addBtn;
    private Button closeBtn;
    private String markerTitle, markerAddr;
    private Marker marker;

    public MarkerDial(Marker marker){
        this.marker = marker;
    }
    private ImageView image;

    private TextView locationTitle,locationTheme;
    private String theme;
    private String b,s;
    public MarkerDial(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view=inflater.inflate(R.layout.marker_dial,null);
        image=(ImageView)view.findViewById(R.id.imageView);
        locationTitle=(TextView)view.findViewById(R.id.locationTitleview);
        locationTheme=(TextView)view.findViewById(R.id.locationThemeview);
        Bundle mArgs=getArguments();
        markerTitle=mArgs.getString("title");
        markerAddr=mArgs.getString("addr");
        theme=checkTheme(markerTitle);
        b=markerTitle.replaceAll(" ","");
        b=b.replaceAll("&","");
        b=b.replaceAll("'","");
        s=b.toLowerCase();
        Log.e("markertitle",s);
        int lid=this.getResources().getIdentifier(s,"drawable",getActivity().getPackageName());
        locationTheme.setText(theme);
        locationTitle.setText(markerTitle);
        image.setImageResource(lid);
        closeBtn=(Button)view.findViewById(R.id.closeBtn);
        addBtn=(Button)view.findViewById(R.id.addBtn);

        if(checkList(markerTitle))
            addBtn.setEnabled(false);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarkerDial.this.dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Log.e("marker add click","click");

                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                RouteList.addList(markerTitle, markerAddr, marker, getContext());
                MainActivity.modifyRightlist();
                MarkerDial.this.dismiss();
                printList();
            }
        });

        Log.e("","");
        return view;
}

public String checkTheme(String markerTitle){

    Log.e("ret","0");
    mDbOpenHelper.open();
    Log.e("ret","1");
    String[] ret = LoadingActivity.mDbOpenHelper.selectColumn("title",markerTitle);
    Log.e("ret","2");
    System.out.println("123456");
    System.out.println(markerTitle);
    System.out.println(ret[0]);
    String[] slices = ret[0].split("\t");
    LoadingActivity.mDbOpenHelper.close();
    String tag = slices[2];

    return tag;
}
}