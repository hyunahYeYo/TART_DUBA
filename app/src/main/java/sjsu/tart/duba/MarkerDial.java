package sjsu.tart.duba;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static sjsu.tart.duba.RouteList.addList;

/**
 * Created by Hak on 2018-07-28.
 */

public class MarkerDial extends DialogFragment {

    private Button addBtn;
    private String markerTitle, markerAddr;
    public MarkerDial(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view=inflater.inflate(R.layout.marker_dial,null);

        Bundle mArgs=getArguments();
        markerTitle=mArgs.getString("title");
        markerAddr=mArgs.getString("addr");

        addBtn=(Button)view.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Log.e("marker add click","click");
               RouteList.addList(markerTitle, markerAddr, getContext());
             //   Toast.makeText(getContext(),markerTitle,Toast.LENGTH_LONG).show();
            }
        });

        return view;
}
}
