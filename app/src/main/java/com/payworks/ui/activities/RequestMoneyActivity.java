package com.payworks.ui.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Registration;
import com.payworks.generated.model.RegistrationResponse;
import com.payworks.generated.model.RequestMoney;
import com.payworks.generated.model.RequestMoneyResponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;

public class RequestMoneyActivity extends BaseActivity {


    String userPhone,userEmail,userPriority,userDate,userAmount,userComment;
    private RetrofitInterface.UserRequestMoneyClient requestMoneyAdapter;
    Calendar myCalendar = Calendar.getInstance();
    String selectedPriority;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.user_Date)
    EditText etDueDate;

    @BindView(R.id.user_email_name)
    EditText etUserEmail;

    @BindView(R.id.user_phone_number)
    EditText etUserPhoneNum;


    @BindView(R.id.user_add_amount)
    EditText etAddAmount;

    @BindView(R.id.user_comment)
    EditText etUserComment;

    @BindView(R.id.user_priority)
    AutoCompleteTextView spPriority;

    @OnClick(R.id.user_priority)
    public void userPriority()
    {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(spPriority.getWindowToken(), 0);
        spPriority.showDropDown();

    }

    @OnClick(R.id.user_Date)
    public void dueDate()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etDueDate.getWindowToken(), 0);
        new DatePickerDialog(RequestMoneyActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @OnClick(R.id.request_money_button)
    public void requestMoney()
    {
        userAmount = etAddAmount.getText().toString();
        userDate = etDueDate.getText().toString();
        userEmail = etUserEmail.getText().toString();
        userPhone = etUserPhoneNum.getText().toString();
        userPriority = spPriority.getText().toString();
        userComment = etUserComment.getText().toString();

        Log.e("abhi", "user email ......... " +userEmail );
        Log.e("abhi", "user phone ......... " +userPhone );
        Log.e("abhi", "user priority ......... " +userPriority );
        Log.e("abhi", "user date ......... " +userDate );
      if ((userPhone != null && !userPhone.equals("")) || (userEmail != null && !userEmail.equals("")))
        {

            Log.e("abhi", "inside 1 if statement ............." );
            if ((userPhone == null) ||(userPhone.equals("")))
            {
                Log.e("abhi", "if phone number is null ............." );
                if (!isValidEmail(userEmail) ) {
                    etUserEmail.setError("Invalid Email");
                }
                else
                {
                    if (isRegistrationValid()) {
                        Log.e("abhi", "requestMoney: valid............" );
                         sendRequestMoney();
                    }
                }
            }
            else
            {
                if (isRegistrationValid()) {
                    Log.e("abhi", "requestMoney: valid............" );
                    sendRequestMoney();
                }
            }

        }
        else
      {
          etUserEmail.setError(getString(R.string.error_one_compulsory_field));
          etUserPhoneNum.setError(getString(R.string.error_compulsory_field));
      }


    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_request_money;
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
        tvAppTitle.setText(R.string.request_money);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);
        spPriority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPriority = (String)adapterView.getItemAtPosition(i);
                Log.e("abhi", "onCreate: ------------"+selectedPriority );
            }
        });

       setUpRestAdapter();

    }


    private void setUpRestAdapter() {
        requestMoneyAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserRequestMoneyClient.class, BASE_URL, this);

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDueDate.setText(sdf.format(myCalendar.getTime()));

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isRegistrationValid() {

        if (userPriority == null || userPriority.equals("") || userDate == null || userDate.equals("") || userAmount == null || userAmount.equals(""))

        {

            if (userPriority == null || userPriority.equals("") )
                Toast.makeText(getApplicationContext(),"Select Priority ",Toast.LENGTH_SHORT).show();

            if (userDate == null || userDate.equals(""))
                Toast.makeText(getApplicationContext(),"Select Due Date ",Toast.LENGTH_SHORT).show();

            if (userAmount == null || userAmount.equals(""))
                etAddAmount.setError(getString(R.string.error_compulsory_field));


            return false;
        } else
            return true;

    }


    private void sendRequestMoney() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<RequestMoneyResponse> call = requestMoneyAdapter.requestMoneyData(new RequestMoney("requestmoney", PrefUtils.getUserId(this),"83Ide@$321!",userComment,userDate,userPriority,userAmount,userPhone,userEmail));
        if (NetworkUtils.isNetworkConnected(RequestMoneyActivity.this)) {
            call.enqueue(new Callback<RequestMoneyResponse>() {

                @Override
                public void onResponse(Call<RequestMoneyResponse> call, Response<RequestMoneyResponse> response) {

                    if (response.isSuccessful()) {


                        if (response.body().getTokenid() !=null) {

                            if (response.body().getType() == 1) {
                                Log.e("abhi", "onResponse: "+response.body().getMsg() );
                                Toast.makeText(getApplicationContext(),"Request Successfully Sent ",Toast.LENGTH_SHORT).show();
                                LoadingDialog.cancelLoading();
                                finish();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RequestMoneyResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(RequestMoneyActivity.this);
            LoadingDialog.cancelLoading();
        }
    }
}