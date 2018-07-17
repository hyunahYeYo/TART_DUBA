package sjsu.tart.duba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetUserNameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getname);

        final EditText userName = (EditText)findViewById(R.id.userName);
        Button getName = (Button)findViewById(R.id.getName);

        getName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPage = new Intent(
                                            getApplication(), //현재 화면의 제어권자
                                            MainActivity.class); //넘어 갈 클래스 지정
                //startActivity(mainPage);
            }
        });
    }
}
