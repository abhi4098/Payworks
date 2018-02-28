package com.payworks.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Country;
import com.payworks.generated.model.CountryList;
import com.payworks.generated.model.CountryListResponse;
import com.payworks.generated.model.EditProfile;
import com.payworks.generated.model.EditProfileResponse;
import com.payworks.generated.model.State;
import com.payworks.generated.model.StateList;
import com.payworks.generated.model.StateListResponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;

public class AddBankActivity extends BaseActivity {

    /*private RetrofitInterface.editProfileClient EditProfileAdapter;
    String userTitle,userFirstName,userLastName,userAddress,userPhone,userEmail,userBio,userTinnumber,usernibpassport,userZip,userCity,userCountry,userState;*/

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;

   /* @BindView(R.id.user_first_name)
    EditText etUserFirstName;

    @BindView(R.id.user_last_name)
    EditText etUserLastName;

    @BindView(R.id.user_email)
    EditText etUserEmailId;

    @BindView(R.id.state_spinner)
    Spinner spStateDropdown;

    @BindView(R.id.country_spinner)
    Spinner spCountryDropdown;


    @BindView(R.id.user_phone_num)
    EditText etUserPhoneNumber;

    @BindView(R.id.user_address)
    EditText etUserAddress;

    @BindView(R.id.user_city)
    EditText etUserCity;


    @BindView(R.id.user_zip)
    EditText etUserZip;

    @BindView(R.id.user_passport)
    EditText etUserPassport;

    @BindView(R.id.user_tin_number)
    EditText etUserTinNumber;

    @BindView(R.id.user_title)
    EditText etUserTitle;

    @BindView(R.id.user_bio)
    EditText etUserBio;


    ArrayList<Country> countryList = null;
    ArrayList<String> showCountryList = null;
    String spCountrySelectedItem ;

    ArrayList<State> stateList = null;
    ArrayList<String> showStateList = null;
    String spStateSelectedItem ;

    private RetrofitInterface.getCountryListClient countryListAdapter;
    private RetrofitInterface.getStateListClient stateListAdapter;

    @OnClick(R.id.edit_profile_button)
    public void editUserProfile()
    {
        userTitle = etUserTitle.getText().toString();
        userFirstName = etUserFirstName.getText().toString();
        userLastName = etUserLastName.getText().toString();
        userAddress = etUserAddress.getText().toString();
        userPhone = etUserPhoneNumber.getText().toString();
        userEmail = etUserEmailId.getText().toString();
        userBio = etUserBio.getText().toString();
        userTinnumber = etUserTinNumber.getText().toString();
        usernibpassport = etUserPassport.getText().toString();
        userZip = etUserZip.getText().toString();
        userCity = etUserCity.getText().toString();

        for (int j=0;j<countryList.size();j++)
        {
            if ((countryList.get(j).getName()).equals(spCountrySelectedItem))
            {
                userCountry = countryList.get(j).getId();
            }
        }

        for (int j=0;j<stateList.size();j++)
        {
            if ((stateList.get(j).getName()).equals(spStateSelectedItem))
            {
                userState = stateList.get(j).getId();
            }
        }


        editProfileDetails();
    }
*/
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_bank;
    }

    @Override
    public int getNavigationIconId() {
        return R.drawable.ic_keyboard_arrow_left_white_24dp;
    }

    @Override
    public void onNavigationIconClick(View v) {
        super.onBackPressed();
    }

    @Override
    public String getActivityTitle() {
         return getString(R.string.title_edit_profile);
    }

    @Override
    public boolean focusAtLaunch() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvAppTitle.setText(R.string.add_bank_account);
        notificationIcon.setVisibility(View.GONE);

      /*  etUserEmailId.setText(PrefUtils.getEmail(AddBankActivity.this));
        etUserFirstName.setText(PrefUtils.getFirstName(AddBankActivity.this));
        etUserLastName.setText(PrefUtils.getLastName(AddBankActivity.this));
        etUserPhoneNumber.setText(PrefUtils.getPhone(AddBankActivity.this));
        etUserTitle.setText(PrefUtils.getUserTitle(AddBankActivity.this));
        etUserAddress.setText(PrefUtils.getUserAdd(AddBankActivity.this));
        etUserCity.setText(PrefUtils.getUserCity(AddBankActivity.this));
        etUserZip.setText(PrefUtils.getUserZip(AddBankActivity.this));
        etUserPassport.setText(PrefUtils.getUserPassport(AddBankActivity.this));
        etUserTinNumber.setText(PrefUtils.getUserTinNumber(AddBankActivity.this));
        etUserBio.setText(PrefUtils.getUserBio(AddBankActivity.this));
       // spCountryDropdown;
        if (PrefUtils.getCountry(AddBankActivity.this) == null)
        {
            spCountrySelectedItem = "Select Country";
        }
        else {
            spCountrySelectedItem = PrefUtils.getCountry(AddBankActivity.this);
        }

        if (PrefUtils.getUserState(AddBankActivity.this) == null)
        {
            spStateSelectedItem = "Select State";
        }
        else {
            spStateSelectedItem = PrefUtils.getUserState(AddBankActivity.this);
        }

        setUpRestAdapter();
        getCountryDropDownList();*/
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

   /* private void getCountryDropDownList() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<CountryListResponse> call = countryListAdapter.countryListData(new CountryList("countryList","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(AddBankActivity.this)) {
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
            SnakBarUtils.networkConnected(AddBankActivity.this);
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

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_add_card, showCountryList);
        spCountryDropdown.setAdapter(categoryAdapter);

        spCountryDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spCountrySelectedItem = spCountryDropdown.getSelectedItem().toString();
                if (!spCountrySelectedItem.equals("Select Country")) {
                    for (int j=0;j<countryList.size();j++)
                    {
                        if ((countryList.get(j).getName()).equals(spCountrySelectedItem))
                        {
                            userCountry = countryList.get(j).getId();
                        }
                    }
                    getStateDropDownList(userCountry);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void getStateDropDownList(String userCountry) {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<StateListResponse> call = stateListAdapter.stateListData(new StateList("countryList","83Ide@$321!",userCountry));
        if (NetworkUtils.isNetworkConnected(AddBankActivity.this)) {
            call.enqueue(new Callback<StateListResponse>() {

                @Override
                public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() ==1)
                        {
                            setStateListDropDown(response);
                        }
                        LoadingDialog.cancelLoading();
                    }
                }

                @Override
                public void onFailure(Call<StateListResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(AddBankActivity.this);
        }
    }


    private void setStateListDropDown(Response<StateListResponse> response) {
        showStateList = new ArrayList<>();
        showStateList.add(spStateSelectedItem);
        stateList = new ArrayList<>();
        for (int i = 0; i < response.body().getStates().size(); i++) {
            State state = new State();

            state.setId(response.body().getStates().get(i).getId());
            state.setName(response.body().getStates().get(i).getName());
            stateList.add(state);
            showStateList.add(response.body().getStates().get(i).getName());
            Log.e("abhi", "setCountryListDropDown: "   +stateList.get(i).getName() );
        }

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_add_card, showStateList);
        spStateDropdown.setAdapter(categoryAdapter);

        spStateDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spStateSelectedItem = spStateDropdown.getSelectedItem().toString();
                if (!spStateSelectedItem.equals("Select State")) {
                    for (int j=0;j<stateList.size();j++)
                    {
                        if ((stateList.get(j).getName()).equals(spStateSelectedItem))
                        {
                            userState = stateList.get(j).getId();
                        }
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUpRestAdapter() {
        EditProfileAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.editProfileClient.class, BASE_URL, this);
        countryListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getCountryListClient.class, BASE_URL, this);
        stateListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getStateListClient.class, BASE_URL, this);
    }


    private void editProfileDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<EditProfileResponse> call = EditProfileAdapter.editProfileData(new EditProfile("updatemyprofile", PrefUtils.getUserId(this),"83Ide@$321!",userTitle,userFirstName,userLastName,userAddress,userPhone,userEmail,userBio,userTinnumber,usernibpassport,userZip,userCity,userCountry,userState));
        if (NetworkUtils.isNetworkConnected(AddBankActivity.this)) {
            call.enqueue(new Callback<EditProfileResponse>() {

                @Override
                public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {

                    if (response.isSuccessful()) {


                        if (response.body().getTokenid() !=null) {

                            if (response.body().getType() == 1) {
                                Log.e("abhi", "onResponse: "+response.body().getMsg() );
                                Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                                LoadingDialog.cancelLoading();
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(AddBankActivity.this);
            LoadingDialog.cancelLoading();
        }
    }*/
}
