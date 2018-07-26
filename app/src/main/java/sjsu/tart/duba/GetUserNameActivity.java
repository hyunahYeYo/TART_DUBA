package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetUserNameActivity extends Activity {

    private EditText inputUserName;
    private Button cancelButton, confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getname);

        inputUserName = (EditText)findViewById(R.id.inputUserName);
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

                if(userName.equals("")){
                    Intent intent = new Intent(GetUserNameActivity.this, PopupActivity.class);
                    intent.putExtra("message", "Enter your name");
                    startActivity(intent);
                }
                else{
                    SharedPreferences userNamePref = getSharedPreferences("userName", MODE_PRIVATE);
                    SharedPreferences.Editor userNameEditor = userNamePref.edit();
                    userNameEditor.putString("userName", userName);
                    userNameEditor.apply();

                    Intent intent = new Intent(GetUserNameActivity.this, GetUserInfoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
