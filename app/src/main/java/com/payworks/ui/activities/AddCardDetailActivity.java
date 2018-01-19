package com.payworks.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.AddMoney;
import com.payworks.generated.model.AddMoneyResponse;
import com.payworks.generated.model.Country;
import com.payworks.generated.model.CountryList;
import com.payworks.generated.model.CountryListResponse;
import com.payworks.generated.model.RequestMoney;
import com.payworks.generated.model.RequestMoneyResponse;
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

public class AddCardDetailActivity extends BaseActivity {

    String cardHolderName;
    String cardNumber;
    String expiry;
    String cvv;
    String cardType;
    String expiryMonth ;
    String expiryYear;
    String selectedCardType;
    private final int CREATE_NEW_CARD = 0;
    private RetrofitInterface.UserAddMoneyClient addMoneyAdapter;
    private RetrofitInterface.getCountryListClient countryListAdapter;
    private RetrofitInterface.getStateListClient stateListAdapter;

   String userCountry,userState,userCity,userPostalCode,userAddress, userAmount,userCoupon,userCardType;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.card_container)
    LinearLayout llcardContainer;

    @BindView(R.id.payable_amount)
    TextView tvPayAmount;

    @BindView(R.id.additional_Details)
    LinearLayout llAditionalDetails;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;

    @BindView(R.id.add_card_details)
    Button btnAddCard;

    @BindView(R.id.state_spinner)
    Spinner spStateDropdown;

    /*@BindView(R.id.user_state)
    EditText etUserState;*/

    @BindView(R.id.user_city)
    EditText etUserCity;

    @BindView(R.id.user_postal_code)
    EditText etUserPostalCode;

    @BindView(R.id.user_add)
    EditText etUserAddress;

    @BindView(R.id.country_spinner)
    Spinner spCountryDropdown;

    @BindView(R.id.card_type)
    AutoCompleteTextView actCardType;

    ArrayList<Country> countryList = null;
    ArrayList<String> showCountryList = null;
    String spCountrySelectedItem ;

    ArrayList<State> stateList = null;
    ArrayList<String> showStateList = null;
    String spStateSelectedItem;

 /*   @BindView(R.id.credit_card_view)
    CreditCardView creditCardView;*/

    @OnClick(R.id.card_type)
    public void userCardType()
    {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(actCardType.getWindowToken(), 0);
        actCardType.showDropDown();

    }


  @OnClick(R.id.add_card_details)
    public void addCardDetails()
    {

        Intent intent = new Intent(AddCardDetailActivity.this, CardEditActivity.class);
        intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, "XXXXX XXXXX");
        startActivityForResult(intent,CREATE_NEW_CARD);

    }

    @OnClick(R.id.add_money_button)
    public void addMonneyNow()
    {

        userAddress = etUserAddress.getText().toString();
        userCity = etUserCity.getText().toString();
        userCountry = spCountrySelectedItem;
        userPostalCode = etUserPostalCode.getText().toString();
        userState = spStateSelectedItem;
        cardType =actCardType.getText().toString();
        if (expiry!=null) {
            String[] splited = expiry.split("/");
            expiryMonth = splited[0];
            expiryYear = splited[1];
        }

        if (isRegistrationValid()) {
            Log.e("abhi", "requestMoney: valid............" );
            sendAddMoney();
        }

    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_card_detail;
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
        tvAppTitle.setText(R.string.add_card_Details_title);
        notificationIcon.setVisibility(View.GONE);
        userAmount = getIntent().getStringExtra("AMOUNT");
        userCoupon = getIntent().getStringExtra("COUPON");
        tvPayAmount.setText(userAmount);


        etUserAddress.setText(PrefUtils.getUserAdd(AddCardDetailActivity.this));
        etUserCity.setText(PrefUtils.getUserCity(AddCardDetailActivity.this));
        etUserPostalCode.setText(PrefUtils.getUserZip(AddCardDetailActivity.this));

        // spCountryDropdown;
        if (PrefUtils.getCountry(AddCardDetailActivity.this) == null)
        {
            spCountrySelectedItem = "Select Country";
        }
        else {
            spCountrySelectedItem = PrefUtils.getCountry(AddCardDetailActivity.this);
        }

        if (PrefUtils.getUserState(AddCardDetailActivity.this) == null)
        {
            spStateSelectedItem = "Select State";
        }
        else {
            spStateSelectedItem = PrefUtils.getUserState(AddCardDetailActivity.this);
        }




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.card_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actCardType.setAdapter(adapter);
        actCardType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCardType = (String)adapterView.getItemAtPosition(i);
                Log.e("abhi", "onCreate: ------------"+selectedCardType );
            }
        });
        setUpRestAdapter();
        getCountryDropDownList();

        populate();

    }

    private void setUpRestAdapter() {
        addMoneyAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserAddMoneyClient.class, BASE_URL, this);
        countryListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getCountryListClient.class, BASE_URL, this);
        stateListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getStateListClient.class, BASE_URL, this);
    }

    private void getCountryDropDownList() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<CountryListResponse> call = countryListAdapter.countryListData(new CountryList("countryList","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(AddCardDetailActivity.this)) {
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
            SnakBarUtils.networkConnected(AddCardDetailActivity.this);
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
        if (NetworkUtils.isNetworkConnected(AddCardDetailActivity.this)) {
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
            SnakBarUtils.networkConnected(AddCardDetailActivity.this);
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

    private void populate() {
        CreditCardView sampleCreditCardView = new CreditCardView(this);

        String name = "XXXXXX XXXX";
        String cvv = "XXX";
        String expiry = "MM/YY";
        String cardNumber = "XXXX XXXX XXXX XXXX";

        sampleCreditCardView.setCVV(cvv);
        sampleCreditCardView.setCardHolderName(name);
        sampleCreditCardView.setCardExpiry(expiry);
        sampleCreditCardView.setCardNumber(cardNumber);

        llcardContainer.addView(sampleCreditCardView);

    }


    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            llcardContainer.removeAllViews();
            cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            Log.e("abhi", "onActivityResult: " +cardType );
            // Your processing goes here.


            if (reqCode == CREATE_NEW_CARD) {
                CreditCardView creditCardView = new CreditCardView(this);

                creditCardView.setCVV(cvv);
                creditCardView.setCardHolderName(cardHolderName);
                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);
                llcardContainer.addView(creditCardView);
                llAditionalDetails.setVisibility(View.VISIBLE);
                btnAddCard.setVisibility(View.GONE);

            }

        }
    }


    private boolean isRegistrationValid() {

        if (userAddress == null || userAddress.equals("") || userCity == null || userCity.equals("") ||userState == null || userState.equals("")|| userState.equals("Select State") || userPostalCode == null || userPostalCode.equals("") || userCountry == null || userCountry.equals("")|| userCountry.equals("Select Country")||cardType == null || cardType.equals(""))

        {

            if (userAddress == null || userAddress.equals("") )
                etUserAddress.setError(getString(R.string.error_compulsory_field));

            if (userState == null || userState.equals("")|| userState.equals("Select State"))
                Toast.makeText(getApplicationContext(),"Select State ",Toast.LENGTH_SHORT).show();

            if (userCity == null || userCity.equals(""))
                etUserCity.setError(getString(R.string.error_compulsory_field));

            if (userPostalCode == null || userPostalCode.equals(""))
                etUserPostalCode.setError(getString(R.string.error_compulsory_field));

            if (userCountry == null || userCountry.equals("")|| userCountry.equals("Select Country"))
                Toast.makeText(getApplicationContext(),"Select Country ",Toast.LENGTH_SHORT).show();


            if (cardType == null || cardType.equals(""))
                Toast.makeText(getApplicationContext(),"Select Card Type ",Toast.LENGTH_SHORT).show();





            return false;
        } else
            return true;

    }

    private void sendAddMoney() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<AddMoneyResponse> call = addMoneyAdapter.addMoneyData(new AddMoney("add_money","83Ide@$321!",userAmount,userState,userCity,userCountry,userPostalCode,cardHolderName,cardNumber,expiryMonth,expiryYear,cardType,cvv,userAddress,PrefUtils.getUserId(this)));
        if (NetworkUtils.isNetworkConnected(AddCardDetailActivity.this)) {
            call.enqueue(new Callback<AddMoneyResponse>() {

                @Override
                public void onResponse(Call<AddMoneyResponse> call, Response<AddMoneyResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getType() ==0) {
                                Toast.makeText(getApplicationContext(),"Fraud Detected ",Toast.LENGTH_SHORT).show();
                                LoadingDialog.cancelLoading();
                                finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Money Added Successfully ",Toast.LENGTH_SHORT).show();
                            LoadingDialog.cancelLoading();
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddMoneyResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(AddCardDetailActivity.this);
            LoadingDialog.cancelLoading();
        }
    }
}

