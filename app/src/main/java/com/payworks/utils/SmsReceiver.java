package com.payworks.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.android.gms.clearcut.LogEventParcelable;
import com.payworks.ui.activities.OtpVerificationScreenActivity;


/**
 * Created by Abhinandan Sharma on 03/01/18.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = LogUtils.makeLogTag(SmsReceiver.class);

    final SmsManager sms = SmsManager.getDefault();
    OtpVerificationScreenActivity main = null;

    @Override
    public void onReceive(Context context, Intent intent)
    {


        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj .length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])                                                                                                    pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber ;
                    String message = currentMessage .getDisplayMessageBody();

                    try
                    {

                        senderNum = senderNum.substring(3,6);
                        Log.e(TAG, "onReceive: phone num- ---"+senderNum);

                        if (senderNum .equals("044"))
                        {
                            Log.e(TAG, "onReceive: receiver,,,,,,,,,,,,,,,,," );
                            //String numberOnly= message.replaceAll("[^0-9]", "");
                            String OTP = message.substring(4,10);
                            main.receivedSms(OTP);
                        }
                    }
                    catch(Exception e){}

                }
            }

        } catch (Exception e)
        {
        }
    }

    public void setMainActivityHandler(OtpVerificationScreenActivity main){
        this.main=main;
    }
}
