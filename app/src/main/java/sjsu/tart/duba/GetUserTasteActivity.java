package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GetUserTasteActivity extends Activity implements View.OnClickListener{

    private ImageView mImageView;
    private TextView mTextView;
    private Button mButton1, mButton2;
    private int[] imageId = {
            R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4,
            R.drawable.pic5, R.drawable.pic6, R.drawable.pic7, R.drawable.pic8
    };
    private int[] textId = {
            R.string.pic_text, R.string.pic_text2, R.string.pic_text3, R.string.pic_text3,
            R.string.pic_text5, R.string.pic_text6, R.string.pic_text7, R.string.pic_text8
    };
    private int numPic = 8;
    private int idx = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettaste);

        mButton1 = (Button)findViewById(R.id.noButton);
        mButton2 = (Button)findViewById(R.id.yesButton);
        mTextView = (TextView) findViewById(R.id.placeText);
        mImageView = (ImageView) findViewById(R.id.placeImage);
        mImageView.setImageResource(R.drawable.pic1);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }


    private void saveUserInfo(String key, Boolean value){
        SharedPreferences pref = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        if(idx == numPic - 1){
            mImageView.setImageResource(imageId[idx]);
            mTextView.setText(textId[idx]);
            idx += 1;
        }
        else if(idx == numPic){
            saveUserInfo("isFirstRun", false);

            Intent intent = new Intent(GetUserTasteActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Welcome :-)", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            mImageView.setImageResource(imageId[idx]);
            mTextView.setText(textId[idx]);
            idx += 1;
        }
    }
}
