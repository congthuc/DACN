package congthuc.com.a4t;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {

    Button pct_btn, nvt_btn;
    ImageButton call_btn, message_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pct_btn = (Button)findViewById(R.id.thuc_btn);
        nvt_btn = (Button)findViewById(R.id.tung_btn);
        call_btn = (ImageButton)findViewById(R.id.call_btn);
        message_btn = (ImageButton)findViewById(R.id.message_btn);

        pct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ThucActivity.class));
            }
        });
        nvt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TungActivity.class));
            }
        });

    }
}
