package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GetUserInfoActivity extends Activity{

    private Button cancelButton, confirmButton;
    private RadioGroup userAgeGroup, userGenderGroup, userBloodTypeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getinfo);

        cancelButton = (Button)findViewById(R.id.cancelButton);
        confirmButton = (Button)findViewById(R.id.confirmButton);

        userGenderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
        userBloodTypeGroup = (RadioGroup) findViewById(R.id.radioBloodTypeGroup);

        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                int radioGenderButtonID = userGenderGroup.getCheckedRadioButtonId();
                RadioButton radioGenderButton = (RadioButton) userGenderGroup.findViewById(radioGenderButtonID);
                String selectedGenderText = (String) radioGenderButton.getText();

                int radioBloodTypeButtonID = userGenderGroup.getCheckedRadioButtonId();
                RadioButton radioBloodTypeButton = (RadioButton) userGenderGroup.findViewById(radioBloodTypeButtonID);
                String selectedBloodTypeText = (String) radioBloodTypeButton.getText();

                if(radioGenderButtonID < 0){
                    Intent intent = new Intent(GetUserInfoActivity.this, PopupActivity.class);
                    intent.putExtra("message", "Enter your gender");
                    startActivity(intent);
                }
                else if(radioBloodTypeButtonID < 0){
                    Intent intent = new Intent(GetUserInfoActivity.this, PopupActivity.class);
                    intent.putExtra("message", "Enter your blood type");
                    startActivity(intent);
                }
                else{
                    saveUserInfo("gender", selectedGenderText);
                    saveUserInfo("bloodType", selectedGenderText);

                    Intent intent = new Intent(GetUserInfoActivity.this, GetUserTasteActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void saveUserInfo(String key, String value){
        SharedPreferences pref = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
