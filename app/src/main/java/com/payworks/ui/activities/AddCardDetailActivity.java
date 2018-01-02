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
import com.payworks.generated.model.RequestMoney;
import com.payworks.generated.model.RequestMoneyResponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

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

    @BindView(R.id.add_card_details)
    Button btnAddCard;

    @BindView(R.id.user_country)
    EditText etUserCountry;

    @BindView(R.id.user_state)
    EditText etUserState;

    @BindView(R.id.user_city)
    EditText etUserCity;

    @BindView(R.id.user_postal_code)
    EditText etUserPostalCode;

    @BindView(R.id.user_add)
    EditText etUserAddress;

    @BindView(R.id.card_type)
    AutoCompleteTextView actCardType;



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
        userCountry = etUserCountry.getText().toString();
        userPostalCode = etUserPostalCode.getText().toString();
        userState = etUserState.getText().toString();
        cardType =actCardType.getText().toString();

        String[] splited = expiry.split("/");
        expiryMonth = splited[0];
        expiryYear = splited[1];

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
        userAmount = getIntent().getStringExtra("AMOUNT");
        userCoupon = getIntent().getStringExtra("COUPON");
        tvPayAmount.setText(userAmount);
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
        populate();

    }

    private void setUpRestAdapter() {
        addMoneyAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserAddMoneyClient.class, BASE_URL, this);

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

        if (userAddress == null || userAddress.equals("") || userCity == null || userCity.equals("") ||userState == null || userState.equals("") || userPostalCode == null || userPostalCode.equals("") || userCountry == null || userCountry.equals("")||cardType == null || cardType.equals(""))

        {

            if (userAddress == null || userAddress.equals("") )
                etUserAddress.setError(getString(R.string.error_compulsory_field));

            if (userState == null || userState.equals(""))
                etUserState.setError(getString(R.string.error_compulsory_field));

            if (userCity == null || userCity.equals(""))
                etUserCity.setError(getString(R.string.error_compulsory_field));

            if (userPostalCode == null || userPostalCode.equals(""))
                etUserPostalCode.setError(getString(R.string.error_compulsory_field));

            if (userCountry == null || userCountry.equals(""))
                etUserCountry.setError(getString(R.string.error_compulsory_field));

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

