package congthuc.com.a4t;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TungActivity extends ActionBarActivity {

    TelephonyManager telephonyManager;
    TextView deviceIdTV;
    Button sendData;

    PackageManager pm;

    String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tung);


        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        pm = getBaseContext().getPackageManager();

        imei = telephonyManager.getDeviceId();
        deviceIdTV = (TextView)findViewById(R.id.tung_device_id_tv);
        sendData = (Button)findViewById(R.id.btn_tung_send_data);
        deviceIdTV.setText(imei);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "phan cong thuc :) hahaha");
                sendIntent.putExtra(Intent.EXTRA_TEXT, imei);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                */
                if(pm == null)
                    Log.w("Package Manager PCT" , " Package Manager is null!");
                else
                    Log.w("Package Manager PCT" , " Package Manager has value!");
                try{
                    Intent appStartIntent = pm.getLaunchIntentForPackage("com.uit.danc.secondapp");
                    if (appStartIntent != null)
                    {
                        appStartIntent.putExtra("data", "Phan Cong Thuc :) hello there!");
                        appStartIntent.putExtra("My_IMEI", imei);
                        startActivity(appStartIntent);
                    }
                    else{
                        Log.w("Log Intent PCT", "Log get launch intent for package return null");
                    }
                }catch(Exception e){
                    Log.w("Log Intent PCT", "Log get launch intent for package get error");
                }
            }
        });
    }
}
