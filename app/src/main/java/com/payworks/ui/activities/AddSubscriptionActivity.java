package com.payworks.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.AddProduct;
import com.payworks.generated.model.AddProductResponse;
import com.payworks.generated.model.AddSubscription;
import com.payworks.generated.model.AddSubscriptionResponse;
import com.payworks.generated.model.EditSubscription;
import com.payworks.generated.model.EditSubscriptionResponse;
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

public class AddSubscriptionActivity extends BaseActivity implements View.OnClickListener {

    String userSubsName,userSubsPrice,userSubsShipping,userSubsDescription,userAbsorbFee,userSubsButton,userTrailPeriod,userSetUpFee,userDuration;
    String subsNameViaIntent ,subsPriceViaIntent,subsShippingViaIntent,subsDescriptionViaIntent,subsButtonViaIntent,subsFeeViaIntent,subsId;
    String subsSetUpFeeViaIntent,subsTrailPeriodViaIntent,subsDurationViaIntent;
    private RetrofitInterface.addSubscriptionClient AddSubscriptionAdapter;
    private RetrofitInterface.editSubsClient EditSubscriptionAdapter;
    String intentFrom ;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;

    @BindView(R.id.radio_button_yes)
    RadioButton rbYes;
    @BindView(R.id.radio_button_no)
    RadioButton rbNo;

    @BindView(R.id.radio_button_02_01)
    RadioButton rb_02_01;
    @BindView(R.id.radio_button_02_02)
    RadioButton rb_02_02;

    @BindView(R.id.radio_button_03_01)
    RadioButton rb_03_01;
    @BindView(R.id.radio_button_03_02)
    RadioButton rb_03_02;

    @BindView(R.id.add_subscription_name)
    EditText etAddSubsName;

    @BindView(R.id.add_subscription_price)
    EditText etAddSubsPrice;

    @BindView(R.id.add_subs_trail_period)
    EditText etAddSubsTrailPeriod;

    @BindView(R.id.add_subscription_duration)
    EditText etAddSubsDuration;

    @BindView(R.id.add_subscription_set_up_fee)
    EditText etAddSubsSetUpFee;

    @BindView(R.id.add_subs_shipping)
    EditText etAddSubsShipping;

    @BindView(R.id.add_description)
    EditText etAddSubsDescription;

    @BindView(R.id.add_subs_button)
    Button addSubsBtn;



    @OnClick(R.id.add_subs_button)
    public void addSubscription()
    {
        userSubsName = etAddSubsName.getText().toString();
        userSubsPrice = etAddSubsPrice.getText().toString();
        userSubsShipping =etAddSubsShipping.getText().toString();
        userSubsDescription = etAddSubsDescription.getText().toString();
        userDuration = etAddSubsDuration.getText().toString();
        userTrailPeriod = etAddSubsTrailPeriod.getText().toString();
        userSetUpFee = etAddSubsSetUpFee.getText().toString();

        if (isRegistrationValid()) {

            if (rbYes.isChecked())
            {
                userAbsorbFee = "1";
                setButtonSelection();
            }
            else if (rbNo.isChecked())
            {
                userAbsorbFee = "2";
                setButtonSelection();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Select Yes or No",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setButtonSelection() {

        if(rb_02_01.isChecked())
        {
           userSubsButton = "/subscribe/02.png";
           addSubsDetails();
        }
        else if (rb_02_02.isChecked())
        {
            userSubsButton = "/subscribe/04.png";
            addSubsDetails();
        }

        else if (rb_03_01.isChecked())
        {
            userSubsButton = "/subscribe/03.png";
            addSubsDetails();
        }
        else if (rb_03_02.isChecked())
        {
            userSubsButton = "/subscribe/05.png";
            addSubsDetails();
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Choose a button",Toast.LENGTH_SHORT).show();
        }
    }

    private void addSubsDetails() {
        if (intentFrom.equals("AddSubscription")) {
            Log.e("abhi", "addSubsDetails: ........" + userSubsButton + " " + userAbsorbFee);
            LoadingDialog.showLoadingDialog(this, "Loading...");
            Call<AddSubscriptionResponse> call = AddSubscriptionAdapter.addSubscriptionData(new AddSubscription("addsubscription", PrefUtils.getUserId(this), "83Ide@$321!", userAbsorbFee, userSubsName, userSubsPrice, userSubsShipping, userSubsDescription, userSubsButton, userSetUpFee, userTrailPeriod, userDuration));
            if (NetworkUtils.isNetworkConnected(this)) {
                call.enqueue(new Callback<AddSubscriptionResponse>() {

                    @Override
                    public void onResponse(Call<AddSubscriptionResponse> call, Response<AddSubscriptionResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getType() == 1) {
                                Toast.makeText(getApplicationContext(), "Subscription added successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Adding Subscription.", Toast.LENGTH_SHORT).show();
                            }
                            LoadingDialog.cancelLoading();


                        }
                    }

                    @Override
                    public void onFailure(Call<AddSubscriptionResponse> call, Throwable t) {
                        LoadingDialog.cancelLoading();
                    }


                });

            } else {
                SnakBarUtils.networkConnected(this);
            }
        }
        else
        {
            LoadingDialog.showLoadingDialog(this, "Loading...");
            Call<EditSubscriptionResponse> call = EditSubscriptionAdapter.editSubsData(new EditSubscription("editsubscription", PrefUtils.getUserId(this),subsId, "83Ide@$321!", userAbsorbFee, userSubsName, userSubsPrice, userSubsShipping, userSubsDescription, userSubsButton, userSetUpFee, userTrailPeriod, userDuration));
            if (NetworkUtils.isNetworkConnected(this)) {
                call.enqueue(new Callback<EditSubscriptionResponse>() {

                    @Override
                    public void onResponse(Call<EditSubscriptionResponse> call, Response<EditSubscriptionResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getType() == 1) {
                                Toast.makeText(getApplicationContext(), "Subscription edited successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error editing Subscription.", Toast.LENGTH_SHORT).show();
                            }
                            LoadingDialog.cancelLoading();


                        }
                    }

                    @Override
                    public void onFailure(Call<EditSubscriptionResponse> call, Throwable t) {
                        LoadingDialog.cancelLoading();
                    }


                });

            } else {
                SnakBarUtils.networkConnected(this);
            }

        }
    }






    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_subscription;
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
        intentFrom= getIntent().getExtras().getString("INTENT_FROM");

        if (intentFrom !=null) {
            if (intentFrom.equals("AddSubscription")) {
                tvAppTitle.setText(R.string.add_subscriptions);
            } else {
                subsNameViaIntent = getIntent().getExtras().getString("SUBS_NAME");
                subsPriceViaIntent = getIntent().getExtras().getString("SUBS_PRICE");
                subsShippingViaIntent = getIntent().getExtras().getString("SUBS_SHIPPING");
                subsDescriptionViaIntent = getIntent().getExtras().getString("SUBS_DESCRIPTION");
                subsButtonViaIntent = getIntent().getExtras().getString("SUBS_BUTTON");
                subsFeeViaIntent = getIntent().getExtras().getString("SUBS_FEE");
                subsSetUpFeeViaIntent = getIntent().getExtras().getString("SUBS_SET_UP_FEE");
                subsTrailPeriodViaIntent = getIntent().getExtras().getString("SUBS_TRAIL_PERIOD");
                subsDurationViaIntent = getIntent().getExtras().getString("SUBS_DURATION");
                subsId = getIntent().getExtras().getString("SUBS_ID");
                Log.e("abhi", "onCreate:..... " +subsFeeViaIntent  + " " + subsButtonViaIntent );

                tvAppTitle.setText(R.string.edit_subscriptions);
                addSubsBtn.setText("EDIT SUBSCRIPTION");
                etAddSubsName.setText(subsNameViaIntent);
                etAddSubsPrice.setText(subsPriceViaIntent);
                etAddSubsShipping.setText(subsShippingViaIntent);
                etAddSubsDescription.setText(subsDescriptionViaIntent);
                etAddSubsTrailPeriod.setText(subsTrailPeriodViaIntent);
                etAddSubsDuration.setText(subsDurationViaIntent);
                etAddSubsSetUpFee.setText(subsSetUpFeeViaIntent);

                if (subsFeeViaIntent !=null) {

                    if (subsFeeViaIntent.equals("1")) {
                        rbYes.setChecked(true);
                    } else {
                        rbNo.setChecked(true);
                    }
                }

                if (subsButtonViaIntent !=null)
                {
                    setSubsButton();
                }



            }
        }

        notificationIcon.setVisibility(View.GONE);
        rbYes.setOnClickListener(this);
        rbNo.setOnClickListener(this);

        rb_02_01.setOnClickListener(this);
        rb_02_02.setOnClickListener(this);


        rb_03_01.setOnClickListener(this);
        rb_03_02.setOnClickListener(this);



         setUpRestAdapter();

    }

    private void setSubsButton() {
        if(subsButtonViaIntent.equals("/subscribe/02.png"))
        {
            rb_02_01.setChecked(true);
        }
        else if (subsButtonViaIntent.equals("/subscribe/03.png"))
        {
            rb_03_01.setChecked(true);
        }
        else if (subsButtonViaIntent.equals("/subscribe/04.png"))
        {
            rb_02_02.setChecked(true);
        }
        else if (subsButtonViaIntent.equals("/subscribe/05.png"))
        {
            rb_03_02.setChecked(true);
        }

    }


    private boolean isRegistrationValid() {
        if (userSubsName == null || userSubsName.equals("")||userSubsPrice == null || userSubsPrice.equals("")
                ||userSubsShipping == null || userSubsShipping.equals("")||userSetUpFee == null || userSetUpFee.equals("")
                ||userTrailPeriod == null || userTrailPeriod.equals("")||userDuration == null || userDuration.equals(""))

        {

            if (userSubsName == null || userSubsName.equals("") ) {
                etAddSubsName.setError(getString(R.string.error_compulsory_field));
            }

            if (userSubsPrice == null || userSubsPrice.equals("") ) {
                etAddSubsPrice.setError(getString(R.string.error_compulsory_field));
            }

            if (userSubsShipping == null || userSubsShipping.equals("") ) {
                etAddSubsShipping.setError(getString(R.string.error_compulsory_field));
            }

            if (userSetUpFee == null || userSetUpFee.equals("") ) {
                etAddSubsSetUpFee.setError(getString(R.string.error_compulsory_field));
            }

            if (userTrailPeriod == null || userTrailPeriod.equals("") ) {
                etAddSubsTrailPeriod.setError(getString(R.string.error_compulsory_field));
            }

            if (userDuration == null || userDuration.equals("") ) {
                etAddSubsDuration.setError(getString(R.string.error_compulsory_field));
            }


            return false;
        } else
            return true;

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.radio_button_yes:
                rbNo.setChecked(false);
                break;

            case R.id.radio_button_no:
                rbYes.setChecked(false);
                break;

            case R.id.radio_button_02_01:

                rb_02_02.setChecked(false);
                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);

                break;

            case R.id.radio_button_02_02:
                rb_02_01.setChecked(false);
                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);

                break;

            case R.id.radio_button_03_01:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);

                rb_03_02.setChecked(false);

                break;

            case R.id.radio_button_03_02:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_03_01.setChecked(false);

                break;

        }

    }


    private void setUpRestAdapter() {
        AddSubscriptionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.addSubscriptionClient.class, BASE_URL, this);
        EditSubscriptionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.editSubsClient.class, BASE_URL, this);

    }

}
