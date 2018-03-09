package com.payworks.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.LocalBankBranch;
import com.payworks.generated.model.Branch;
import com.payworks.generated.model.Country;
import com.payworks.generated.model.CountryList;
import com.payworks.generated.model.CountryListResponse;
import com.payworks.generated.model.EditProfile;
import com.payworks.generated.model.EditProfileResponse;
import com.payworks.generated.model.InterBankAddition;
import com.payworks.generated.model.InterBankAdditionResponse;
import com.payworks.generated.model.LocalBankAccount;
import com.payworks.generated.model.LocalBankAccountResponse;
import com.payworks.generated.model.LocalBankAddition;
import com.payworks.generated.model.LocalBankAdditionResponse;
import com.payworks.generated.model.LocalBankBranchResponse;
import com.payworks.generated.model.LocalBankList;
import com.payworks.generated.model.Localbank;
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
    private RetrofitInterface.getLocalBanksListClient MyLocalBankAdapter;
    private RetrofitInterface.getLocalBanksBranchClient MyLocalBankBranchAdapter;
    ArrayList<Branch> branchList = null;
    ArrayList<String> showBranchList = null;
    Boolean isBankSelected =false;
    Boolean isInternationalSelected =false;
    String spBranchSelectedItem,selectedAccountType,userAccountName,userAccountNumber,selectedInterAccountType ;
    private RetrofitInterface.getCountryListClient countryListAdapter;
    private RetrofitInterface.getStateListClient stateListAdapter;

    private RetrofitInterface.myLocalBankAdditionClient myLocalAdditionAdapter;
    private RetrofitInterface.myInterBankAdditionClient myInterAdditonAdapter;

    ArrayList<Country> countryList = null;
    ArrayList<String> showCountryList = null;
    String spCountrySelectedItem ;

    ArrayList<State> stateList = null;
    ArrayList<String> showStateList = null;
    String spStateSelectedItem;

    String userCountry,userState,userInterBankName,userInterBankAdd,userInterBankCity, userInterBankZip,userInterBankPhone
            ,userInterBankAccNum,userInterBankRoutingNumber,userInterBankSwift,userInterBankAccHolder;


    ArrayList<Localbank> myLocalBankAccountList = null;
    ArrayList<String> myLocalBanknameList = null;
    String spLocalBank,userLocalBankId,userBranch,userAccountType,userInterAccountType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;

    @BindView(R.id.ll_local_bank)
    LinearLayout llLocalBank;

    @BindView(R.id.rl_international_bank)
    RelativeLayout rlInternationalBank;

    @BindView(R.id.local_bank_account_holder)
    EditText etLocalBankAccountHolder;

    @BindView(R.id.local_bank_account_number)
    EditText etLocalBankAccountNumber;

    @BindView(R.id.local_acctype_spinner)
    AutoCompleteTextView actLocalAccType;

    @BindView(R.id.local_bank_name_spinner)
    Spinner spLocalBankNameDropdown;

    @BindView(R.id.local_bank_branch_spinner)
    Spinner spLocalBranchDropdown;




    @BindView(R.id.bank_country_spinner)
    Spinner spCountryDropdown;
    @BindView(R.id.state_spinner)
    Spinner spStateDropdown;


    @BindView(R.id.bank_name)
    EditText etInterBankName;

    @BindView(R.id.bank_address)
    EditText etInterBankAdd;

    @BindView(R.id.bank_city)
    EditText etInterBankCity;

    @BindView(R.id.bank_zip)
    EditText etInterBankZip;

    @BindView(R.id.bank_phone)
    EditText etInterBankPhone;

    @BindView(R.id.bank_account_holder)
    EditText etInterBankAccountHolder;

    @BindView(R.id.bank_account_number)
    EditText etInterBankAccNum;

    @BindView(R.id.bank_routing_number)
    EditText etInterBankRoutingNum;

    @BindView(R.id.bank_swiftcode)
    EditText etInterBankSwiftCode;

    @BindView(R.id.inter_acctype_spinner)
    AutoCompleteTextView actInterAccType;


    @OnClick(R.id.add_international_button)
    public void addInternational()
    {

        if (isInternationalSelected) {
            userInterBankName = etInterBankName.getText().toString();
            userInterBankAdd = etInterBankAdd.getText().toString();
            userInterBankCity = etInterBankCity.getText().toString();
            userInterBankZip = etInterBankZip.getText().toString();
            userInterBankPhone = etInterBankPhone.getText().toString();
            userInterBankAccNum = etInterBankAccNum.getText().toString();
            userInterBankAccHolder = PrefUtils.getFirstName(this).concat(PrefUtils.getLastName(this));
            userInterBankRoutingNumber = etInterBankRoutingNum.getText().toString();
            userInterBankSwift = etInterBankSwiftCode.getText().toString();
            userCountry =spCountrySelectedItem;
            userState =spStateSelectedItem;
            userInterAccountType =selectedInterAccountType;


            if (isInterRegistrationValid()) {
                Log.e("abhi", "requestMoney: valid............");
                addInterBankDetails();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Add Bank Details ",Toast.LENGTH_SHORT).show();
        }
    }

    private void addInterBankDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<InterBankAdditionResponse> call = myInterAdditonAdapter.myinterAdditionData(new InterBankAddition("addbankaccount",PrefUtils.getUserId(this),"83Ide@$321!","international",userInterBankName,userInterBankAdd,userInterBankAccHolder,userInterBankAccNum,userInterAccountType,userCountry,userState,userInterBankPhone,userInterBankRoutingNumber,userInterBankSwift));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<InterBankAdditionResponse>() {

                @Override
                public void onResponse(Call<InterBankAdditionResponse> call, Response<InterBankAdditionResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() ==1) {
                            Log.e("abhi", "onResponse: "+response.body().getMsg());
                            finish();
                        }
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<InterBankAdditionResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }


    @OnClick(R.id.select_international_btn)
    public void selectInternational()
    {
        rlInternationalBank.setVisibility(View.VISIBLE);
        llLocalBank.setVisibility(View.GONE);

        etInterBankAccountHolder.setText(PrefUtils.getFirstName(this).concat(PrefUtils.getLastName(this)));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actInterAccType.setAdapter(adapter);
        actInterAccType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedInterAccountType = (String)adapterView.getItemAtPosition(i);
                Log.e("abhi", "onCreate: ------------"+selectedInterAccountType );
            }
        });
        getCountryDropDownList();
    }

    @OnClick(R.id.select_Local)
    public void selectLocal()
    {
     rlInternationalBank.setVisibility(View.GONE);
     llLocalBank.setVisibility(View.VISIBLE);
        etLocalBankAccountHolder.setText(PrefUtils.getFirstName(this).concat(PrefUtils.getLastName(this)));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actLocalAccType.setAdapter(adapter);
        actLocalAccType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAccountType = (String)adapterView.getItemAtPosition(i);
                Log.e("abhi", "onCreate: ------------"+selectedAccountType );
            }
        });
        setUpRestAdapter();
        getMyLocalBanks();
    }

    @OnClick(R.id.add_local_bank_button)
    public void addLocalBank()
    {
        if (isBankSelected) {
            userAccountName = PrefUtils.getFirstName(this).concat(PrefUtils.getLastName(this));
            userAccountNumber = etLocalBankAccountNumber.getText().toString();
            userAccountType = selectedAccountType;
            userBranch = spBranchSelectedItem;
            userLocalBankId =spLocalBank;

            if (isRegistrationValid()) {
                Log.e("abhi", "requestMoney: valid............");
                addLocalBankDetails();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Add Bank Details ",Toast.LENGTH_SHORT).show();
        }
    }

    private void addLocalBankDetails() {

        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<LocalBankAdditionResponse> call = myLocalAdditionAdapter.myLocalAdditionData(new LocalBankAddition("addbankaccount",PrefUtils.getUserId(this),"83Ide@$321!","local",userLocalBankId,userBranch,userAccountName,userAccountNumber,userAccountType));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<LocalBankAdditionResponse>() {

                @Override
                public void onResponse(Call<LocalBankAdditionResponse> call, Response<LocalBankAdditionResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() ==1) {
                            Log.e("abhi", "onResponse: "+response.body().getMsg());
                            finish();
                        }
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<LocalBankAdditionResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }


    private boolean isRegistrationValid() {

        if (userAccountNumber == null || userAccountNumber.equals("")  || userBranch.equals("")|| userBranch.equals("Select Branch")
                || userLocalBankId.equals("")|| userLocalBankId.equals("Select Local Bank")||selectedAccountType.equals("")|| selectedAccountType.equals("Select Local Bank"))

        {

            if (userAccountNumber == null || userAccountNumber.equals("") )
                etLocalBankAccountNumber.setError(getString(R.string.error_compulsory_field));

            if (userLocalBankId == null || userLocalBankId.equals("")|| userLocalBankId.equals("Select Local Bank"))
                Toast.makeText(getApplicationContext(),"Select Local Bank",Toast.LENGTH_SHORT).show();

            if (userBranch == null || userBranch.equals("")|| userBranch.equals("Select Branch"))
                Toast.makeText(getApplicationContext(),"Select Branch ",Toast.LENGTH_SHORT).show();


            if (selectedAccountType == null || selectedAccountType.equals(""))
                Toast.makeText(getApplicationContext(),"Select Account Type ",Toast.LENGTH_SHORT).show();





            return false;
        } else
            return true;

    }

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
        etLocalBankAccountHolder.setText(PrefUtils.getFirstName(this).concat(PrefUtils.getLastName(this)));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actLocalAccType.setAdapter(adapter);
        actLocalAccType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAccountType = (String)adapterView.getItemAtPosition(i);
                Log.e("abhi", "onCreate: ------------"+selectedAccountType );
            }
        });
        setUpRestAdapter();
        getMyLocalBanks();
    }

    @OnClick(R.id.local_acctype_spinner)
    public void userAccountType()
    {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(actLocalAccType.getWindowToken(), 0);
        actLocalAccType.showDropDown();

    }

    @OnClick(R.id.inter_acctype_spinner)
    public void userInterAccountType()
    {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(actInterAccType.getWindowToken(), 0);
        actInterAccType.showDropDown();

    }



    private void getMyLocalBanks() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<LocalBankAccountResponse> call = MyLocalBankAdapter.localBankListData(new LocalBankList("localbanklist","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<LocalBankAccountResponse>() {

                @Override
                public void onResponse(Call<LocalBankAccountResponse> call, Response<LocalBankAccountResponse> response) {

                    if (response.isSuccessful()) {
                        setLocalBankDetails(response);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<LocalBankAccountResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }



    private void setLocalBankDetails(Response<LocalBankAccountResponse> response) {
        myLocalBankAccountList = new ArrayList<>();
        myLocalBanknameList = new ArrayList<>();
        myLocalBanknameList.add("Select Local Bank");
        for (int i = 0; i < response.body().getLocalbanks().size(); i++) {
            Localbank localbank = new Localbank();
            localbank.setId(response.body().getLocalbanks().get(i).getId());
            localbank.setBankname(response.body().getLocalbanks().get(i).getBankname());
            /*localbank.setLocalbankname(response.body().getLocalbanks().get(i).getLocalbankname());
            localbank.setBranchname(response.body().getLocalbanks().get(i).getBranchname());
            localbank.setTransit(response.body().getLocalbanks().get(i).getTransit());
            localbank.setIsdefault(response.body().getLocalbanks().get(i).getIsdefault());*/
            myLocalBankAccountList.add(localbank);
            myLocalBanknameList.add(response.body().getLocalbanks().get(i).getBankname());

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_layout, myLocalBanknameList);
        spLocalBankNameDropdown.setAdapter(dataAdapter);

        spLocalBankNameDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spLocalBank = spLocalBankNameDropdown.getSelectedItem().toString();



                if (!spLocalBank.equals("Select Local Bank")) {
                    for (int j=0;j<myLocalBankAccountList.size();j++)
                    {
                        if ((myLocalBankAccountList.get(j).getBankname()).equals(spLocalBank))
                        {
                            userLocalBankId = myLocalBankAccountList.get(j).getId();
                        }
                    }
                    getBranchNameDropdown(userLocalBankId);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getBranchNameDropdown(String userLocalBankId) {

        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<LocalBankBranchResponse> call = MyLocalBankBranchAdapter.localBankBranchData(new LocalBankBranch("getbankbranches","83Ide@$321!",userLocalBankId));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<LocalBankBranchResponse>() {

                @Override
                public void onResponse(Call<LocalBankBranchResponse> call, Response<LocalBankBranchResponse> response) {

                    if (response.isSuccessful()) {

                            Log.e("abhi", "onResponse: ..........."+response.body().getBranches().size() );
                           setBranchListDropDown(response);

                        LoadingDialog.cancelLoading();
                    }
                }

                @Override
                public void onFailure(Call<LocalBankBranchResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setBranchListDropDown(Response<LocalBankBranchResponse> response) {
        branchList = new ArrayList<>();
        showBranchList = new ArrayList<>();
        showBranchList.add("Select Branch");

        for (int i = 0; i < response.body().getBranches().size(); i++) {
            Branch branch = new Branch();

            branch.setId(response.body().getBranches().get(i).getId());
            branch.setBranchname(response.body().getBranches().get(i).getBranchname());
            branch.setTransit(response.body().getBranches().get(i).getTransit());
            branchList.add(branch);
            showBranchList.add(response.body().getBranches().get(i).getBranchname().concat("(").concat(response.body().getBranches().get(i).getTransit()).concat(")"));

        }

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_layout, showBranchList);
        spLocalBranchDropdown.setAdapter(categoryAdapter);

        spLocalBranchDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spBranchSelectedItem = spLocalBranchDropdown.getSelectedItem().toString();
                isBankSelected =true;
                Log.e("abhi", "onItemSelected:.................branch "+spBranchSelectedItem );
                if (!spBranchSelectedItem.equals("Select Branch")) {
                    for (int j=0;j<branchList.size();j++)
                    {
                        if ((branchList.get(j).getBranchname()).equals(spBranchSelectedItem))
                        {
                            userBranch = branchList.get(j).getId();
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
        MyLocalBankBranchAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getLocalBanksBranchClient.class, BASE_URL, this);
        MyLocalBankAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getLocalBanksListClient.class, BASE_URL, this);
        countryListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getCountryListClient.class, BASE_URL, this);
        stateListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getStateListClient.class, BASE_URL, this);
        myLocalAdditionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.myLocalBankAdditionClient.class, BASE_URL, this);
        myInterAdditonAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.myInterBankAdditionClient.class, BASE_URL, this);
    }


    private void getCountryDropDownList() {
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
        showCountryList.add("Select Country");
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
        showStateList.add("Select State");
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
                isInternationalSelected =true;
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

    private boolean isInterRegistrationValid() {

        if (userInterBankAdd == null || userInterBankAdd.equals("") || userInterBankCity == null || userInterBankCity.equals("") ||userState == null || userState.equals("")|| userState.equals("Select State") || userInterBankName == null || userInterBankName.equals("") || userCountry == null
                || userCountry.equals("")|| userCountry.equals("Select Country")||userInterAccountType == null || userInterAccountType.equals("")||
                userInterBankZip == null || userInterBankZip.equals("") ||userInterAccountType.equals("")|| userInterAccountType.equals("Select Local Bank")|| userInterBankPhone == null || userInterBankPhone.equals("")
                || userInterBankAccNum == null || userInterBankAccNum.equals("")|| userInterBankRoutingNumber == null || userInterBankRoutingNumber.equals("")|| userInterBankSwift == null || userInterBankSwift.equals(""))

        {

            if (userInterBankName == null || userInterBankName.equals("") )
                etInterBankName.setError(getString(R.string.error_compulsory_field));

            if (userState == null || userState.equals("")|| userState.equals("Select State"))
                Toast.makeText(getApplicationContext(),"Select State ",Toast.LENGTH_SHORT).show();


            if (userCountry == null || userCountry.equals("")|| userCountry.equals("Select Country"))
                Toast.makeText(getApplicationContext(),"Select Country ",Toast.LENGTH_SHORT).show();




            if (userInterAccountType == null || userInterAccountType.equals(""))
                Toast.makeText(getApplicationContext(),"Select Account Type ",Toast.LENGTH_SHORT).show();

            if (userInterBankAdd == null || userInterBankAdd.equals("") )
                etInterBankAdd.setError(getString(R.string.error_compulsory_field));

            if (userInterBankCity == null || userInterBankCity.equals("") )
                etInterBankCity.setError(getString(R.string.error_compulsory_field));

            if (userInterBankZip == null || userInterBankZip.equals("") )
                etInterBankZip.setError(getString(R.string.error_compulsory_field));

            if (userInterBankPhone == null || userInterBankPhone.equals("") )
                etInterBankPhone.setError(getString(R.string.error_compulsory_field));

            if (userInterBankAccNum == null || userInterBankAccNum.equals("") )
                etInterBankAccNum.setError(getString(R.string.error_compulsory_field));

            if (userInterBankRoutingNumber == null || userInterBankRoutingNumber.equals("") )
                etInterBankRoutingNum.setError(getString(R.string.error_compulsory_field));

            if (userInterBankSwift == null || userInterBankSwift.equals("") )
                etInterBankSwiftCode.setError(getString(R.string.error_compulsory_field));







            return false;
        } else
            return true;

    }

}
