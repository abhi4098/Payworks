package com.payworks.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.AddDonation;
import com.payworks.generated.model.AddDonationResponse;
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

public class AddDonationActivity extends BaseActivity implements View.OnClickListener {

    String userDonationName,userDonationPrice,userAbsorbFee,userDonationButton;
    private RetrofitInterface.addDonationClient AddDonationAdapter;
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
    @BindView(R.id.radio_button_02_03)
    RadioButton rb_02_03;
    @BindView(R.id.radio_button_02_04)
    RadioButton rb_02_04;
    @BindView(R.id.radio_button_02_05)
    RadioButton rb_02_05;
    @BindView(R.id.radio_button_02_06)
    RadioButton rb_02_06;


    @BindView(R.id.radio_button_03_01)
    RadioButton rb_03_01;
    @BindView(R.id.radio_button_03_02)
    RadioButton rb_03_02;
    @BindView(R.id.radio_button_03_03)
    RadioButton rb_03_03;
    @BindView(R.id.radio_button_03_04)
    RadioButton rb_03_04;
    @BindView(R.id.radio_button_03_05)
    RadioButton rb_03_05;
    @BindView(R.id.radio_button_03_06)
    RadioButton rb_03_06;


    @BindView(R.id.add_donation_name)
    EditText etAddDonationName;

    @BindView(R.id.add_donation_price)
    EditText etAddDonationPrice;



    @OnClick(R.id.add_donation_button)
    public void addDonation()
    {
        userDonationName = etAddDonationName.getText().toString();
        userDonationPrice = etAddDonationPrice.getText().toString();


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
           userDonationButton = "/donate/01.png";
           addDonationDetails();
        }
        else if (rb_02_02.isChecked())
        {
            userDonationButton = "/donate/02.png";
            addDonationDetails();
        }
        else if (rb_02_03.isChecked())
        {
            userDonationButton = "/donate/03.png";
            addDonationDetails();
        }
        else if (rb_02_04.isChecked())
        {
            userDonationButton = "/donate/04.png";
            addDonationDetails();
        }
        else if (rb_02_05.isChecked())
        {
            userDonationButton = "/donate/05.png";
            addDonationDetails();
        }
        else if (rb_02_06.isChecked())
        {
            userDonationButton = "/donate/07.png";
            addDonationDetails();
        }

        else if (rb_03_01.isChecked())
        {
            userDonationButton = "/01/gray-color/01.png";
            addDonationDetails();
        }
        else if (rb_03_02.isChecked())
        {
            userDonationButton = "/01/gray-color/02.png";
            addDonationDetails();
        }
        else if (rb_03_03.isChecked())
        {
            userDonationButton = "/01/gray-color/03.png";
            addDonationDetails();
        }
        else if (rb_03_04.isChecked())
        {
            userDonationButton = "/01/gray-color/04.png";
            addDonationDetails();
        }
        else if (rb_03_05.isChecked())
        {
            userDonationButton = "/01/gray-color/05.png";
            addDonationDetails();
        }
        else if (rb_03_06.isChecked())
        {
            userDonationButton = "/01/gray-color/06.png";
            addDonationDetails();
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Choose a button",Toast.LENGTH_SHORT).show();
        }
    }

    private void addDonationDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<AddDonationResponse> call = AddDonationAdapter.addDonationData(new AddDonation("adddonation", PrefUtils.getUserId(this),"83Ide@$321!",userAbsorbFee,userDonationName,userDonationPrice,userDonationButton));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<AddDonationResponse>() {

                @Override
                public void onResponse(Call<AddDonationResponse> call, Response<AddDonationResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() == 1)
                        {
                            Toast.makeText(getApplicationContext(),"Donation added successfully.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error Adding Donation.",Toast.LENGTH_SHORT).show();
                        }
                        LoadingDialog.cancelLoading();


                    }
                }

                @Override
                public void onFailure(Call<AddDonationResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }






    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_donation;
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
        tvAppTitle.setText(R.string.add_donation);
        notificationIcon.setVisibility(View.GONE);
        rbYes.setOnClickListener(this);
        rbNo.setOnClickListener(this);

        rb_02_01.setOnClickListener(this);
        rb_02_02.setOnClickListener(this);
        rb_02_03.setOnClickListener(this);
        rb_02_04.setOnClickListener(this);
        rb_02_05.setOnClickListener(this);
        rb_02_06.setOnClickListener(this);

        rb_03_01.setOnClickListener(this);
        rb_03_02.setOnClickListener(this);
        rb_03_03.setOnClickListener(this);
        rb_03_04.setOnClickListener(this);
        rb_03_05.setOnClickListener(this);
        rb_03_06.setOnClickListener(this);


         setUpRestAdapter();

    }

    private boolean isRegistrationValid() {
        if (userDonationName == null || userDonationName.equals("")||userDonationPrice == null || userDonationPrice.equals(""))

        {

            if (userDonationName == null || userDonationName.equals("") ) {
                etAddDonationName.setError(getString(R.string.error_compulsory_field));
            }

            if (userDonationPrice == null || userDonationPrice.equals("") ) {
                etAddDonationPrice.setError(getString(R.string.error_compulsory_field));
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
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_02_02:
                rb_02_01.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_02_03:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_02_04:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                break;
            case R.id.radio_button_02_05:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_02_06:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;


            case R.id.radio_button_03_01:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);

                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_03_02:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_03_03:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_03_04:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);

                break;
            case R.id.radio_button_03_05:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_06.setChecked(false);

                break;

            case R.id.radio_button_03_06:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);


                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);

                break;

        }

    }


    private void setUpRestAdapter() {
        AddDonationAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.addDonationClient.class, BASE_URL, this);

    }

}
