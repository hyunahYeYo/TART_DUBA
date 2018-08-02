package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetUserNameActivity extends Activity {

    private EditText inputUserName, inputUserDateOfBirth;
    private Button cancelButton, confirmButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getname);

        inputUserName = (EditText)findViewById(R.id.inputUserName);
        inputUserDateOfBirth = (EditText)findViewById(R.id.inputUserDateOfBirth);

        cancelButton = (Button)findViewById(R.id.cancelButton);
        confirmButton = (Button)findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String userName = inputUserName.getText().toString();
                String userDateOfBirth = inputUserDateOfBirth.getText().toString();

                if(userName.equals("")){
                    Intent intent = new Intent(GetUserNameActivity.this, PopupActivity.class);
                    intent.putExtra("message", "Enter your name");
                    startActivity(intent);
                }
                if(userDateOfBirth.equals("")){
                    Intent intent = new Intent(GetUserNameActivity.this, PopupActivity.class);
                    intent.putExtra("message", "Enter your date of birth");
                    startActivity(intent);
                }
                else{
                    saveUserInfo("userName", userName);
                    saveUserInfo("userDateOfBirth", userDateOfBirth);

                    Intent intent = new Intent(GetUserNameActivity.this, GetUserInfoActivity.class);
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
