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

    private EditText inputUserDateOfBirth;
    private Button cancelButton, confirmButton;
    private RadioGroup userAgeGroup, userGenderGroup;
    private RadioButton[] ageButton = new RadioButton[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getinfo);

        cancelButton = (Button)findViewById(R.id.cancelButton);
        confirmButton = (Button)findViewById(R.id.confirmButton);
        inputUserDateOfBirth = (EditText)findViewById(R.id.inputUserDateOfBirth);
        userGenderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);

        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String userDateOfBirth = inputUserDateOfBirth.getText().toString();
                int[] userDateOfBirthInt = new int[3];

                int radioButtonID = userGenderGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) userGenderGroup.findViewById(radioButtonID);
                String selectedGenderText = (String) radioButton.getText();

                if(userDateOfBirth.equals("")){
                    Intent intent = new Intent(GetUserInfoActivity.this, PopupActivity.class);
                    intent.putExtra("message", "Enter your date of birth");
                    startActivity(intent);
                }
                else if(radioButtonID < 0){
                    Intent intent = new Intent(GetUserInfoActivity.this, PopupActivity.class);
                    intent.putExtra("message", "Enter your gender");
                    startActivity(intent);
                }
                else{
                    userDateOfBirthInt = getUserDateInfo(userDateOfBirth);
                    saveUserDateInfo(userDateOfBirthInt);
                    saveUserInfo("GENDER", selectedGenderText);
                    saveUserInfo("ISFIRSTRUN", false);

                    Intent intent = new Intent(GetUserInfoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private int[] getUserDateInfo(String selectedBirthText){
        String[] userDateOfBirthStr = new String[3];
        int[] userDateOfBirthInt = new int[3];
        userDateOfBirthStr = selectedBirthText.split("-");

        for(int i = 0; i < 3; i++){
            userDateOfBirthInt[i] = Integer.parseInt(userDateOfBirthStr[i]);
        }

        return userDateOfBirthInt;
    }

    private void saveUserDateInfo(int[] userDateBirthInt){
        saveUserInfo("MONTH", userDateBirthInt[0]);
        saveUserInfo("DAY", userDateBirthInt[1]);
        saveUserInfo("YEAR", userDateBirthInt[2]);
    }

    private void saveUserInfo(String key, int value){
        SharedPreferences pref = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    private void saveUserInfo(String key, String value){
        SharedPreferences pref = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void saveUserInfo(String key, Boolean value){
        SharedPreferences pref = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

}
