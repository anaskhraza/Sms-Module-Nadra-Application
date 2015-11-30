package com.example.anaskhurshid.sms;

import android.content.BroadcastReceiver;
import android.content.Context;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
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
import android.telephony.PhoneNumberUtils;
import android.os.Build;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by anaskhurshid on 27/11/2015.
 */
public class SmsReciever extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    SmsMessage SMessage;
    String sender;
    String body;
    String phoneNumber="+923002416068";
    String [] name_break;
   int verify=0;
    int id=0;
      @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                //SmsMessage[] messages=null;
                // large message might be broken into many
                // messages = new SmsMessage[pdus.length];
                abortBroadcast();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {

                    //  messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    //sb.append(messages[i].getMessageBody());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = bundle.getString("format");
                        SMessage = SmsMessage.createFromPdu((byte[]) pdus[i],format);
                    }
                    else
                    {SMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);}
                    sender = SMessage.getOriginatingAddress();
                    body = SMessage.getMessageBody().toString();
                }


                if (PhoneNumberUtils.compare(sender, phoneNumber)) {
                    name_break = body.split("ولد");
                    String FirstName = name_break[0];
                    String LastName = name_break[1];
                    // byte[] b=FirstName.getBytes(UTF-8);
                    // byte[] c=LastName.getBytes(UTF-8);
                    Toast.makeText(context, FirstName, Toast.LENGTH_SHORT).show();
                    LocationDatabaseHandler create_db = new LocationDatabaseHandler(context);
                    Cursor result = create_db.getData(FirstName, LastName);
                    int i = 0;
                    if (result.getCount() > 0) {
                        result.moveToFirst();
                        do {
                            //verify= result.getString(result.getColumnIndex("VERIFY"));
                            verify = result.getInt(result.getColumnIndex("Verify"));

                            id = result.getInt(result.getColumnIndex("id"));
                            i++;
                        } while (result.moveToNext());
                        result.close();
                    }

                    if (verify ==1) {
                        Log.d("test", "Already verified");
                        Toast.makeText(context, "GoodTo Know! This ID is already been verified", Toast.LENGTH_SHORT).show();
                    } else {
                        verify = 1;
                        boolean check = create_db.updateVerify(verify, id);
                        Toast.makeText(context, "verified", Toast.LENGTH_SHORT).show();
                    }

                }

                // prevent any other broadcast receivers from receiving broadcast
            }  // abortBroadcast()

        }
    }

}

