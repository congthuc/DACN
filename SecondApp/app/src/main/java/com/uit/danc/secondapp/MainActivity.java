package com.uit.danc.secondapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends ActionBarActivity {

    // Instantiate the RequestQueue.
    RequestQueue queue;
    String url_destination;
    String data_send;

    TextView show_data_received;
    EditText data_send_editText, url_destination_editText;
    Button send_data_btn;
    WebView show_response_ww;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data_send_editText = (EditText)findViewById(R.id.data_send_editText);
        url_destination_editText = (EditText)findViewById(R.id.destination_url_editText);
        send_data_btn = (Button)findViewById(R.id.send_data_btn);
        show_response_ww = (WebView)findViewById(R.id.response_show_webview);
        show_data_received = (TextView)findViewById(R.id.data_receive_tv);

        // Received data from sentApp

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        String message = intent.getStringExtra("data");
        String Victim_IMEI = intent.getStringExtra("My_IMEI");
        if(message != null && Victim_IMEI != null){
            show_data_received.setText("Message: "+ message + ", Victim_IMEI: " + Victim_IMEI);
        }else
            show_data_received.setText("Cannot received data from app 1");


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }

        queue = Volley.newRequestQueue(this);

        send_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(data_send_editText) || isEmpty(url_destination_editText)){
                    Toast.makeText(getBaseContext(), "Insert data to edit text", Toast.LENGTH_LONG).show();
                }else{
                    data_send = data_send_editText.getText().toString();
                    url_destination = url_destination_editText.getText().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url_destination,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    show_response_ww.loadData(response,"text/html","utf-8");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getBaseContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                        }
                    });
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }
        });
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String Victim_IMEI = intent.getStringExtra("My_IEMI");
        if (sharedText != null && Victim_IMEI != null) {
            // Update UI to reflect text being shared
            show_data_received.setText("Victim_IMEI" + Victim_IMEI + " Message from victim: " +sharedText);
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
