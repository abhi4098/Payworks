package com.payworks.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.payworks.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCardDetailActivity extends BaseActivity {

    String cardHolderName;
    String cardNumber;
    String expiry;
    String cvv;
    private final int CREATE_NEW_CARD = 0;



    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.card_container)
    LinearLayout llcardContainer;

    @BindView(R.id.additional_Details)
    LinearLayout llAditionalDetails;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.add_card_details)
    Button btnAddCard;

 /*   @BindView(R.id.credit_card_view)
    CreditCardView creditCardView;*/



  @OnClick(R.id.add_card_details)
    public void addCardDetails()
    {

        Intent intent = new Intent(AddCardDetailActivity.this, CardEditActivity.class);
        intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, "XXXXX XXXXX");
        startActivityForResult(intent,CREATE_NEW_CARD);

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
        populate();



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
            Log.e("abhi", "onActivityResult: " +cardHolderName +cardNumber );
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
}

