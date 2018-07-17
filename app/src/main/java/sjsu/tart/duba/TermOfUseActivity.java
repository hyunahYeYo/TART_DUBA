package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TermOfUseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TermOfUse", "Here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termofuse);
    }
}
