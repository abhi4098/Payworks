package com.payworks.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Registration;
import com.payworks.generated.model.RegistrationResponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etUserFirstName;
    EditText etUserEmailId;
    EditText etUserLastName;
    EditText etUserCountry;
    EditText etUserPhoneNumber;
    EditText etUserPassword;
    Button btnRegisterAccount;
    boolean isPasswordValid =false;
    String userFirstname,userLastName,userPhone,userEmail,userCountry,userPassword;
    private RetrofitInterface.UserRegistrationClient registrationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etUserFirstName = (EditText) findViewById(R.id.user_first_name);
        etUserLastName = (EditText) findViewById(R.id.user_last_name);
        etUserPhoneNumber = (EditText) findViewById(R.id.user_phone_num);
        etUserEmailId = (EditText) findViewById(R.id.user_email);
        etUserPassword = (EditText) findViewById(R.id.user_password);
        etUserCountry = (EditText) findViewById(R.id.user_country);
        btnRegisterAccount = (Button) findViewById(R.id.register_account);
        btnRegisterAccount.setOnClickListener(this);
        setUpRestAdapter();

    }


    private void setUpRestAdapter() {
        registrationAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserRegistrationClient.class, BASE_URL, this);

    }

    @Override
    public void onClick(View view) {

        userFirstname = etUserFirstName.getText().toString();
        userLastName = etUserLastName.getText().toString();
        userEmail = etUserEmailId.getText().toString();
        userPassword = etUserPassword.getText().toString();
        userCountry = etUserCountry.getText().toString();
        userPhone = etUserPhoneNumber.getText().toString();
        userPhone = etUserPhoneNumber.getText().toString();
       /* PrefUtils.storeUserName(userFullName, RegistrationActivity.this);
        PrefUtils.storeStateId(userState, RegistrationActivity.this);
        PrefUtils.storeEmail(userEmail, RegistrationActivity.this);
        PrefUtils.storeCloverId(userCloverId, RegistrationActivity.this);
        PrefUtils.storeCloverToken(userCloverApiKey, RegistrationActivity.this);
        String password = (etUserPassword.getText().toString());*/

     /*   if(password.length()>5 && !password.equals(password.toLowerCase()) &&
                !password.equals(password.toUpperCase()) &&
                password.matches(".*\\d+.*")  ){
            isPasswordValid = true;

        }else{

            isPasswordValid = false;
            etUserPassword.setError(getString(R.string.error_password_field));
        }*/

        if (isRegistrationValid()) {
            getRegistrationDetails();
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isRegistrationValid() {

        if (userFirstname == null || userFirstname.equals("") || userLastName == null || userLastName.equals("") ||
                userPhone == null || userPhone.equals("") || userCountry.equals("Select")|| userCountry == null || userPassword.equals("") ||
                userPassword == null || userEmail.equals("") || userEmail == null   || !isValidEmail(userEmail))

        {

            if (userFirstname == null || userFirstname.equals("") )
                etUserFirstName.setError(getString(R.string.error_compulsory_field));

            if (userLastName == null || userLastName.equals(""))
                etUserLastName.setError(getString(R.string.error_compulsory_field));

            if ( userPhone == null || userPhone.equals(""))
                etUserPhoneNumber.setError(getString(R.string.error_compulsory_field));

            if ( userPassword == null || userPassword.equals(""))
                etUserPassword.setError(getString(R.string.error_compulsory_field));

            if ( userEmail == null || userEmail.equals(""))
                etUserEmailId.setError(getString(R.string.error_compulsory_field));

            if (userCountry == null || userCountry.equals(""))
                etUserCountry.setError(getString(R.string.error_compulsory_field));


            if (!isValidEmail(userEmail) )
                etUserEmailId.setError("Invalid Email");

            return false;
        } else
            return true;

    }

    private void getRegistrationDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<RegistrationResponse> call = registrationAdapter.userRegistration(new Registration(userEmail, userFirstname, userLastName, userPassword, userCountry,"registration","83Ide@$321!",userPhone));
        if (NetworkUtils.isNetworkConnected(RegistrationActivity.this)) {
            call.enqueue(new Callback<RegistrationResponse>() {

                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                    if (response.isSuccessful()) {


                        if (response.body().getTokenid() !=null) {

                            if (response.body().getType() == 1) {

                                Intent intent = new Intent(RegistrationActivity.this, NavigationalActivity.class);
                                startActivity(intent);
                                LoadingDialog.cancelLoading();
                            }
                            else
                                Toast.makeText(getApplicationContext(),"You have already registered ",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {

                }


            });

        } else {
            SnakBarUtils.networkConnected(RegistrationActivity.this);
        }
    }
}
