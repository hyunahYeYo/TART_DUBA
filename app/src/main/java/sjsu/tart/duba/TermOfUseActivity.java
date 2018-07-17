package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TermOfUseActivity extends Activity implements View.OnClickListener {

    private CheckBox[] agrees = new CheckBox[4];
    private int[] agreesId = {R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox4};
    private final int numAgrees = 4;
    private int numUserAgrees = 0;
    private  Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TermOfUse", "Here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termofuse);

        for(int i = 0; i < numAgrees; i++){
            agrees[i] = (CheckBox) findViewById(agreesId[i]);
            agrees[i].setOnClickListener(this);
        }
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setEnabled(false);
        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(TermOfUseActivity.this, GetUserNameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        numUserAgrees = 0;

        for(int i = 0; i < numAgrees; i++){
            if(agrees[i].isChecked()){
                numUserAgrees++;
            }
        }

        if(numUserAgrees == numAgrees){
            nextButton.setEnabled(true);
        }
        else{
            nextButton.setEnabled(false);
        }

    }
}
