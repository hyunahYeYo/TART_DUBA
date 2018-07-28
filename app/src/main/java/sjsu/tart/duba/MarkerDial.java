package sjsu.tart.duba;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hak on 2018-07-28.
 */

public class MarkerDial extends DialogFragment {

    public MarkerDial(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view=inflater.inflate(R.layout.marker_dial,null);


        return view;
}
}
