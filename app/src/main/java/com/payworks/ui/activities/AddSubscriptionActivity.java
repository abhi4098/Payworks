package com.payworks.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
    private RetrofitInterface.addProductClient AddProductAdapter;
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
    EditText etAddProductDescription;

    @OnClick(R.id.add_subs_button)
    public void addSubscription()
    {
        userSubsName = etAddSubsName.getText().toString();
        userSubsPrice = etAddSubsPrice.getText().toString();
        userSubsShipping =etAddSubsShipping.getText().toString();
        userSubsDescription = etAddProductDescription.getText().toString();
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
        Log.e("abhi", "addSubsDetails: ........"+userSubsButton + " " +userAbsorbFee );
     /*   LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<AddProductResponse> call = AddProductAdapter.addProductData(new AddProduct("addproduct", PrefUtils.getUserId(this),"83Ide@$321!",userAbsorbFee,userSubsName,userSubsPrice,userSubsShipping,userSubsDescription,userSubsButton));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<AddProductResponse>() {

                @Override
                public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() == 1)
                        {
                            Toast.makeText(getApplicationContext(),"Product added successfully.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error Adding Product.",Toast.LENGTH_SHORT).show();
                        }
                        LoadingDialog.cancelLoading();


                    }
                }

                @Override
                public void onFailure(Call<AddProductResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
*/
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
        tvAppTitle.setText(R.string.add_subscriptions);
        notificationIcon.setVisibility(View.GONE);
        rbYes.setOnClickListener(this);
        rbNo.setOnClickListener(this);

        rb_02_01.setOnClickListener(this);
        rb_02_02.setOnClickListener(this);


        rb_03_01.setOnClickListener(this);
        rb_03_02.setOnClickListener(this);



         setUpRestAdapter();

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
        AddProductAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.addProductClient.class, BASE_URL, this);

    }

}
