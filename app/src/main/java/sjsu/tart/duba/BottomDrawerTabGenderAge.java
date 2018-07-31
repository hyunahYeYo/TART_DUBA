package sjsu.tart.duba;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by lion7 on 2018-07-24.
 */

public class BottomDrawerTabGenderAge extends Fragment {
    public static String[] buttonsKeys = {"Child", "Adult", "Elderly"};
    public static Boolean[] buttonsValues = {false, false, false};

    private Button[] buttons = new Button[3];
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

}