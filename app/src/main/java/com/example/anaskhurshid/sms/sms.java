package com.example.anaskhurshid.sms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;
import android.app.PendingIntent;

import org.w3c.dom.Text;

public class sms extends AppCompatActivity {
    SmsManager smsManager = SmsManager.getDefault();
    EditText txt_num;
    Button send_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        send_btn= (Button) findViewById(R.id.btnSendSMS);
        txt_num=(EditText)findViewById(R.id.editText);

        send_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
    }
    protected void sendSMSMessage(){
        Log.i("Send SMS", "");
        String cnic_No="hello";
        String phone_no="03002416068";

        String sent = "android.telephony.SmsManager.STATUS_ON_ICC_SENT";

        PendingIntent piSent = PendingIntent.getBroadcast(sms.this, 0,new Intent(sent), 0);

        smsManager.sendTextMessage(phone_no, null, cnic_No, piSent, null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
