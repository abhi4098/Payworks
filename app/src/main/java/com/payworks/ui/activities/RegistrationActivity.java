package com.payworks.ui.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Country;
import com.payworks.generated.model.CountryList;
import com.payworks.generated.model.CountryListResponse;
import com.payworks.generated.model.Invoice;
import com.payworks.generated.model.Registration;
import com.payworks.generated.model.RegistrationResponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;


public class RegistrationActivity extends BaseActivity implements View.OnClickListener{



    ImageView notificationIcon;

    EditText etUserFirstName;
    EditText etUserEmailId;
    EditText etUserLastName;
    //EditText etUserCountry;
    EditText etUserPhoneNumber;
    EditText etUserPassword;
    EditText etCompanyName;
    TextView tvAppTitle;
    CheckBox cbCompany;
    Spinner spCountryDropdown;
    String spCountrySelectedItem = "Select Country";
    android.support.v7.widget.Toolbar toolbar;
    LinearLayout btnRegisterAccount;
    boolean isPasswordValid =false;
    ArrayList<Country> countryList = null;
    ArrayList<String> showCountryList = null;
    String userCheckBox ="0";
    String userFirstname,userLastName,userPhone,userEmail,userCountry,userPassword,userFullName,userCompanyName;
    private RetrofitInterface.UserRegistrationClient registrationAdapter;
    private RetrofitInterface.getCountryListClient countryListAdapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_registration;
    }

    @Override
    public int getNavigationIconId() {
        return 0;
    }

    @Override
    public void onNavigationIconClick(View v) {

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
        etUserFirstName = (EditText) findViewById(R.id.user_first_name);
        etUserLastName = (EditText) findViewById(R.id.user_last_name);
        etUserPhoneNumber = (EditText) findViewById(R.id.user_phone_num);
        etUserEmailId = (EditText) findViewById(R.id.user_email);
        etUserPassword = (EditText) findViewById(R.id.user_password);
        etCompanyName = (EditText) findViewById(R.id.company_name);
        cbCompany = (CheckBox) findViewById(R.id.checkbox_agree);
        spCountryDropdown = (Spinner) findViewById(R.id.country_spinner);
        notificationIcon = (ImageView) findViewById(R.id.notification_icon);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        tvAppTitle = (TextView) findViewById(R.id.tv_app_title);
        btnRegisterAccount = (LinearLayout) findViewById(R.id.register_account);
        btnRegisterAccount.setOnClickListener(this);
        //setSupportActionBar(toolbar);
        tvAppTitle.setText("REGISTRATION");
        //notificationIcon.setVisibility(View.GONE);
        cbCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbCompany.isChecked())
                {
                    etCompanyName.setVisibility(View.VISIBLE);
                    userCheckBox = "1";
                }
                else
                {
                    etCompanyName.setVisibility(View.GONE);
                    userCheckBox ="0";
                }
            }
        });
        setUpRestAdapter();
        getCountryDropDownList();

    }



    private void setUpRestAdapter() {
        registrationAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserRegistrationClient.class, BASE_URL, this);
        countryListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getCountryListClient.class, BASE_URL, this);
    }

    @Override
    public void onClick(View view) {
        userCompanyName = etUserFirstName.getText().toString();
        userFirstname = etUserFirstName.getText().toString();
        userLastName = etUserLastName.getText().toString();
        userEmail = etUserEmailId.getText().toString();
        userPassword = etUserPassword.getText().toString();
        userCountry = spCountrySelectedItem;
        userPhone = etUserPhoneNumber.getText().toString();
        for (int i=0;i<countryList.size();i++)
        {
            if ((countryList.get(i).getName()).equals(spCountrySelectedItem))
            {
                userCountry = countryList.get(i).getId();
            }
        }
        PrefUtils.storePhone(userPhone, RegistrationActivity.this);
        PrefUtils.storeFirstName(userFirstname, RegistrationActivity.this);
        PrefUtils.storeLastName(userLastName, RegistrationActivity.this);
        PrefUtils.storeEmail(userEmail, RegistrationActivity.this);
        //String password = (etUserPassword.getText().toString());
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
                userPhone == null || userPhone.equals("") ||userCountry.equals("select Country")|| userCountry.equals("Select")|| userCountry == null || userPassword.equals("") ||
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

            if (userCountry == null || userCountry.equals("")||userCountry.equals("select Country"))
                Toast.makeText(getApplicationContext(),"Select Country",Toast.LENGTH_SHORT).show();


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
                                PrefUtils.storeUsernId(response.body().getTokenid().toString(),RegistrationActivity.this);

                                Intent intent = new Intent(RegistrationActivity.this, NavigationalActivity.class);
                                startActivity(intent);

                            }
                            else
                                Toast.makeText(getApplicationContext(),"You have already registered ",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                        }

                        LoadingDialog.cancelLoading();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(RegistrationActivity.this);
        }
    }






    private void getCountryDropDownList() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<CountryListResponse> call = countryListAdapter.countryListData(new CountryList("countryList","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(RegistrationActivity.this)) {
            call.enqueue(new Callback<CountryListResponse>() {

                @Override
                public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() ==1)
                        {
                            setCountryListDropDown(response);
                        }
                        LoadingDialog.cancelLoading();
                    }
                }

                @Override
                public void onFailure(Call<CountryListResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(RegistrationActivity.this);
        }
    }

    private void setCountryListDropDown(Response<CountryListResponse> response) {
        showCountryList = new ArrayList<>();
        showCountryList.add(spCountrySelectedItem);
        countryList = new ArrayList<>();
        for (int i = 0; i < response.body().getCountries().size(); i++) {
            Country country = new Country();

            country.setId(response.body().getCountries().get(i).getId());
            country.setName(response.body().getCountries().get(i).getName());
            countryList.add(country);
            showCountryList.add(response.body().getCountries().get(i).getName());
            Log.e("abhi", "setCountryListDropDown: "   +countryList.get(i).getName() );
        }

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, showCountryList);
        spCountryDropdown.setAdapter(categoryAdapter);

        spCountryDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spCountrySelectedItem = spCountryDropdown.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
