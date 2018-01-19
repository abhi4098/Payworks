package com.payworks.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.ApiEndPoints;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.GetOTP;
import com.payworks.generated.model.GetOTPResponse;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyWalletResponse;
import com.payworks.generated.model.SendMoneyVerified;
import com.payworks.generated.model.SendMoneyVerifiedReponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SmsReceiver;
import com.payworks.utils.SnakBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationScreenActivity extends BaseActivity {



    private RetrofitInterface.sendMoneyVerifiedClient PayMoneyAdapter;
    private RetrofitInterface.getOTPClient OTPAdapter;
    SmsReceiver BR_smsreceiver = null;
    String userPayId;
    private boolean otpSend = false;
    private MyCountDownTimer countDownTimer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;


    @BindView(R.id.inputOtp)
    EditText inputOTP;

    @BindView(R.id.txt_seconds)
    TextView textSeconds;



    @BindView(R.id.btn_verify_otp)
    Button btnSendMoney;

    @BindView(R.id.txt_timer)
    TextView textTimer;

    @BindView(R.id.timer_image)
    ImageView timerImage;




    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @BindView(R.id.OTP_resend_button)
    Button btnResend;




    @OnClick(R.id.btn_verify_otp)
    public void sendMoney()
    {
        if (PrefUtils.getSavedOTP(this).equals(inputOTP.getText().toString()))
           payMoney();
        else
            Toast.makeText(getApplicationContext(),"Please Enter Valid OTP ",Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.OTP_resend_button)
    public void resendOTP() {
        if (NetworkUtils.isNetworkConnected(this)) {
            if (otpSend) {
                btnResend.setVisibility(View.GONE);
              // tvotp.setVisibility(View.GONE);
                otpSend = false;
                progressBar.setVisibility(View.GONE);
                inputOTP.setText("");
                //final String mobileNum = inputMobileNum.getText().toString().trim();
                getOTP();

            }
        }
    }




    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_otp_verification;
    }

    @Override
    public int getNavigationIconId() {
        return R.drawable.ic_keyboard_arrow_left_white_24dp;
    }

    @Override
    public void onNavigationIconClick(View v) {
    onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onBackPressed();
    }



    @Override
    public String getActivityTitle() {
        return null;
    }

    @Override
    public boolean focusAtLaunch() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvAppTitle.setText(R.string.otp_verification_title);
        notificationIcon.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        if (checkPermission()) {
            requestPermission();
        }
        userPayId =getIntent().getStringExtra("PAYID");
        setUpRestAdapter();
        getOTP();

    }

    public void receivedSms(String message) {
        try {
            Log.e("abhi", "receivedSms: "+message );
            inputOTP.setText(message);
        } catch (Exception e) {
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS},
                1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }
        }
    }



    private void callReceiver() {
        Log.e("abhi", "callReceiver: ----------" );
        BR_smsreceiver = new SmsReceiver();
        BR_smsreceiver.setMainActivityHandler(OtpVerificationScreenActivity.this);
        IntentFilter fltr_smsreceived = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(BR_smsreceiver, fltr_smsreceived);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        callReceiver();
        /*setUpRestAdapter();
        getOTP();*/
    }

    private void getOTP() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<GetOTPResponse> call = OTPAdapter.getOTPData(new GetOTP("generateSendOTP", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<GetOTPResponse>() {

                @Override
                public void onResponse(Call<GetOTPResponse> call, Response<GetOTPResponse> response) {

                    if (response.isSuccessful()) {
                       if (response.body().getType() == 1)
                       {

                           PrefUtils.storeOTP(response.body().getOtp(),OtpVerificationScreenActivity.this);
                           startCountDownTimer();
                       }
                        Log.e("abhi", "onResponse:otp generated--------- " +PrefUtils.getSavedOTP(OtpVerificationScreenActivity.this) + " phone"+response.body().getMsg() );
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<GetOTPResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
            LoadingDialog.cancelLoading();
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            String time = "" + millisUntilFinished / 1000;
            textTimer.setText(time);
            progressBar.setProgress((int) (millisUntilFinished / 1000));

        }

        @Override
        public void onFinish() {

            textTimer.setVisibility(View.GONE);
            textSeconds.setVisibility(View.GONE);
            btnResend.setVisibility(View.VISIBLE);
            //timerImage.setVisibility(View.GONE);
              otpSend = true;
            progressBar.setProgress(0);
            progressBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.progressbar_resend));
        }
    }

    private void startCountDownTimer() {

        countDownTimer = new MyCountDownTimer(90 * 1000, 1000);
        textTimer.setVisibility(View.VISIBLE);
        textSeconds.setVisibility(View.VISIBLE);
        timerImage.setVisibility(View.VISIBLE);
        inputOTP.setVisibility(View.VISIBLE);
        btnSendMoney.setVisibility(View.VISIBLE);
        countDownTimer.start();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(90);
        progressBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.progressbar_normal));

    }

    private void payMoney() {
        Log.e("abhi", "payMoney: pay id......."+userPayId );
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SendMoneyVerifiedReponse> call = PayMoneyAdapter.sendMoneyVerifiedData(new SendMoneyVerified("paymoneyrequest", PrefUtils.getUserId(this),"83Ide@$321!",userPayId));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<SendMoneyVerifiedReponse>() {

                @Override
                public void onResponse(Call<SendMoneyVerifiedReponse> call, Response<SendMoneyVerifiedReponse> response) {

                    if (response.isSuccessful()) {
                        Log.e("abhi", "onResponse: send money type.........."+response.body().getMsg() );
                        if (response.body().getType() == 1)
                        {
                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                            finish();
                            Log.e("abhi", "onResponse: send money message.........."+response.body().getMsg() );

                            View popupView = LayoutInflater.from(OtpVerificationScreenActivity.this).inflate(R.layout.layout_send_successfull_popup, null);
                            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            Button btnDismiss = (Button) popupView.findViewById(R.id.btn_close_popup);
                            // Button btnAddMoney = (Button) popupView.findViewById(R.id.add_money_button);
                            btnDismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popupWindow.dismiss();
                                }
                            });

                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                            finish();
                            Log.e("abhi", "onResponse: send money message.........."+response.body().getMsg() );
                        }
                       // Log.e("abhi", "onResponse:otp generated--------- " +PrefUtils.getSavedOTP(OtpVerificationScreenActivity.this) + " phone"+response.body().getMsg() );
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<SendMoneyVerifiedReponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
            LoadingDialog.cancelLoading();
        }
    }

    private void setUpRestAdapter() {
        OTPAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getOTPClient.class, ApiEndPoints.BASE_URL, this);
        PayMoneyAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.sendMoneyVerifiedClient.class, ApiEndPoints.BASE_URL, this);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }



}

