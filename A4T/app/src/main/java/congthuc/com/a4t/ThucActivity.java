package congthuc.com.a4t;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class ThucActivity extends ActionBarActivity {

    TelephonyManager telephonyManager;
    TextView deviceIdTextView, deviceLocationTextView;
    int mcc, mnc;
    Button send_Data_btn;

    public final static String EXTRA_MESSAGE_DEVICE_ID = "device_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc);


        send_Data_btn = (Button) findViewById(R.id.send_Data_btn);


        deviceIdTextView = (TextView) findViewById(R.id.device_id_textView);
        deviceLocationTextView = (TextView) findViewById(R.id.device_location_textView);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String country = Locale.getDefault().getCountry();

        String networkOperator = telephonyManager.getNetworkOperator();

        if (!networkOperator.isEmpty()) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
        }
        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            final GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
            if (location != null) {
                deviceLocationTextView.setText("LAC: " + location.getLac() + " CID: " + location.getCid() +
                        " MCC: " + location.getPsc() + " Country: " + country + " MCC: " + mcc + " MNC: " + mnc);
            }
        }

        String imei = telephonyManager.getDeviceId();
        deviceIdTextView.setText(imei);

        send_Data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DoJobs.class);
                String message = deviceIdTextView.getText().toString();
                intent.putExtra(EXTRA_MESSAGE_DEVICE_ID, message);
                startActivity(intent);
                finish();
            }
        });

    }
}
