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

public class AddTicketActivity extends BaseActivity implements View.OnClickListener {

    String userProductName,userProductPrice,userproductShipping,userProductDescription,userAbsorbFee,userProductButton;
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
    @BindView(R.id.radio_button_02_03)
    RadioButton rb_02_03;
    @BindView(R.id.radio_button_02_04)
    RadioButton rb_02_04;
    @BindView(R.id.radio_button_02_05)
    RadioButton rb_02_05;
    @BindView(R.id.radio_button_02_06)
    RadioButton rb_02_06;
    @BindView(R.id.radio_button_02_07)
    RadioButton rb_02_07;
    @BindView(R.id.radio_button_02_08)
    RadioButton rb_02_08;

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
    @BindView(R.id.radio_button_03_07)
    RadioButton rb_03_07;
    @BindView(R.id.radio_button_03_08)
    RadioButton rb_03_08;

    @BindView(R.id.add_product_name)
    EditText etAddProductName;

    @BindView(R.id.add_product_price)
    EditText etAddProductPrice;

    @BindView(R.id.add_product_shipping)
    EditText etAddProductShipping;

    @BindView(R.id.add_description)
    EditText etAddProductDescription;

    @OnClick(R.id.add_product_button)
    public void addProduct()
    {
        userProductName = etAddProductName.getText().toString();
        userProductPrice = etAddProductPrice.getText().toString();
        userproductShipping =etAddProductShipping.getText().toString();
        userProductDescription = etAddProductDescription.getText().toString();

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
           userProductButton = "/02/01.png";
           addProductDetails();
        }
        else if (rb_02_02.isChecked())
        {
            userProductButton = "/02/02.png";
            addProductDetails();
        }
        else if (rb_02_03.isChecked())
        {
            userProductButton = "/02/03.png";
            addProductDetails();
        }
        else if (rb_02_04.isChecked())
        {
            userProductButton = "/02/04.png";
            addProductDetails();
        }
        else if (rb_02_05.isChecked())
        {
            userProductButton = "/02/05.png";
            addProductDetails();
        }
        else if (rb_02_06.isChecked())
        {
            userProductButton = "/02/06.png";
            addProductDetails();
        }
        else if (rb_02_07.isChecked())
        {
            userProductButton = "/02/07.png";
            addProductDetails();
        }
        else if (rb_02_08.isChecked())
        {
            userProductButton = "/02/08.png";
            addProductDetails();
        }
        else if (rb_03_01.isChecked())
        {
            userProductButton = "/03/01.png";
            addProductDetails();
        }
        else if (rb_03_02.isChecked())
        {
            userProductButton = "/03/02.png";
            addProductDetails();
        }
        else if (rb_03_03.isChecked())
        {
            userProductButton = "/03/03.png";
            addProductDetails();
        }
        else if (rb_03_04.isChecked())
        {
            userProductButton = "/03/04.png";
            addProductDetails();
        }
        else if (rb_03_05.isChecked())
        {
            userProductButton = "/03/05.png";
            addProductDetails();
        }
        else if (rb_03_06.isChecked())
        {
            userProductButton = "/03/06.png";
            addProductDetails();
        }
        else if (rb_03_07.isChecked())
        {
            userProductButton = "/03/07.png";
            addProductDetails();
        }
        else if (rb_03_08.isChecked())
        {
            userProductButton = "/03/08.png";
            addProductDetails();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Choose a button",Toast.LENGTH_SHORT).show();
        }
    }

    private void addProductDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<AddProductResponse> call = AddProductAdapter.addProductData(new AddProduct("addproduct", PrefUtils.getUserId(this),"83Ide@$321!",userAbsorbFee,userProductName,userProductPrice,userproductShipping,userProductDescription,userProductButton));
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

    }






    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_product;
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
        tvAppTitle.setText(R.string.add_tickets);
        notificationIcon.setVisibility(View.GONE);
        rbYes.setOnClickListener(this);
        rbNo.setOnClickListener(this);

        rb_02_01.setOnClickListener(this);
        rb_02_02.setOnClickListener(this);
        rb_02_03.setOnClickListener(this);
        rb_02_04.setOnClickListener(this);
        rb_02_05.setOnClickListener(this);
        rb_02_06.setOnClickListener(this);
        rb_02_07.setOnClickListener(this);
        rb_02_08.setOnClickListener(this);

        rb_03_01.setOnClickListener(this);
        rb_03_02.setOnClickListener(this);
        rb_03_03.setOnClickListener(this);
        rb_03_04.setOnClickListener(this);
        rb_03_05.setOnClickListener(this);
        rb_03_06.setOnClickListener(this);
        rb_03_07.setOnClickListener(this);
        rb_03_08.setOnClickListener(this);


         setUpRestAdapter();

    }

    private boolean isRegistrationValid() {
        if (userProductName == null || userProductName.equals("")||userProductPrice == null || userProductPrice.equals("")
                ||userproductShipping == null || userproductShipping.equals(""))

        {

            if (userProductName == null || userProductName.equals("") ) {
                etAddProductName.setError(getString(R.string.error_compulsory_field));
            }

            if (userProductPrice == null || userProductPrice.equals("") ) {
                etAddProductPrice.setError(getString(R.string.error_compulsory_field));
            }

            if (userproductShipping == null || userproductShipping.equals("") ) {
                etAddProductShipping.setError(getString(R.string.error_compulsory_field));
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
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_02:
                rb_02_01.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_03:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_04:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;
            case R.id.radio_button_02_05:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_06:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_07:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_08:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_01:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_02:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_03:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_04:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;
            case R.id.radio_button_03_05:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_06:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;
            case R.id.radio_button_03_07:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_08:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                break;
        }

    }


    private void setUpRestAdapter() {
        AddProductAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.addProductClient.class, BASE_URL, this);

    }

}
