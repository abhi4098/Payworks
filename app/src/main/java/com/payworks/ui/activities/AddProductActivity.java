package com.payworks.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Activity;
import com.payworks.generated.model.AddProduct;
import com.payworks.generated.model.AddProductResponse;
import com.payworks.generated.model.EditProduct;
import com.payworks.generated.model.EditProductResponse;
import com.payworks.generated.model.MerchantData;
import com.payworks.generated.model.MerchantProductResponse;
import com.payworks.generated.model.Product;
import com.payworks.ui.adapters.MyProductAdapter;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.payworks.api.ApiEndPoints.BASE_URL;

public class AddProductActivity extends BaseActivity implements View.OnClickListener {

    String userProductName,userProductPrice,userproductShipping,userProductDescription,userAbsorbFee,userProductButton;
    private RetrofitInterface.addProductClient AddProductAdapter;
    private RetrofitInterface.editProductClient EditProductAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Context context;

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

    @BindView(R.id.add_product_button)
    Button addProductButton;

    @BindView(R.id.add_description)
    EditText etAddProductDescription;
    String intentFrom ;
    String productNameViaIntent ,productPriceViaIntent,productShippingViaIntent,productDescriptionViaIntent,productButtonViaIntent,productFeeViaIntent,productId;
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
        if (intentFrom.equals("AddProduct")) {
            LoadingDialog.showLoadingDialog(this, "Loading...");
            Call<AddProductResponse> call = AddProductAdapter.addProductData(new AddProduct("addproduct", PrefUtils.getUserId(this), "83Ide@$321!", userAbsorbFee, userProductName, userProductPrice, userproductShipping, userProductDescription, userProductButton));
            if (NetworkUtils.isNetworkConnected(this)) {
                call.enqueue(new Callback<AddProductResponse>() {

                    @Override
                    public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getType() == 1) {
                                Toast.makeText(getApplicationContext(), "Product added successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Adding Product.", Toast.LENGTH_SHORT).show();
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

        else
        {
            LoadingDialog.showLoadingDialog(this, "Loading...");
            Call<EditProductResponse> call = EditProductAdapter.editProductData(new EditProduct("editproduct",productId, PrefUtils.getUserId(this), "83Ide@$321!", userAbsorbFee, userProductName, userProductPrice, userproductShipping, userProductDescription, userProductButton));
            if (NetworkUtils.isNetworkConnected(this)) {
                call.enqueue(new Callback<EditProductResponse>() {

                    @Override
                    public void onResponse(Call<EditProductResponse> call, Response<EditProductResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getType() == 1) {
                                Log.e("abhi", "onResponse: ..................success");
                                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                Toast.makeText(getApplicationContext(), "Error Editing Product.", Toast.LENGTH_SHORT).show();
                            }
                            LoadingDialog.cancelLoading();


                        }
                    }

                    @Override
                    public void onFailure(Call<EditProductResponse> call, Throwable t) {
                        Log.e("abhi", "onFailure: ..........."+t.getMessage() );
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
        intentFrom= getIntent().getExtras().getString("INTENT_FROM");

        if (intentFrom !=null) {
            if (intentFrom.equals("AddProduct")) {
                tvAppTitle.setText(R.string.add_products);
            } else {
                productNameViaIntent = getIntent().getExtras().getString("PRODUCT_NAME");
                productPriceViaIntent = getIntent().getExtras().getString("PRODUCT_PRICE");
                productShippingViaIntent = getIntent().getExtras().getString("PRODUCT_SHIPPING");
                productDescriptionViaIntent = getIntent().getExtras().getString("PRODUCT_DESCRIPTION");
                productButtonViaIntent = getIntent().getExtras().getString("PRODUCT_BUTTON");
                productFeeViaIntent = getIntent().getExtras().getString("PRODUCT_FEE");
                productId = getIntent().getExtras().getString("PRODUCT_ID");
                Log.e("abhi", "onCreate:..... " +productFeeViaIntent  + " " + productButtonViaIntent );

                tvAppTitle.setText(R.string.edit_products);
                addProductButton.setText("EDIT PRODUCT");
                etAddProductName.setText(productNameViaIntent);
                etAddProductPrice.setText(productPriceViaIntent);
                etAddProductShipping.setText(productShippingViaIntent);
                etAddProductDescription.setText(productDescriptionViaIntent);
                if (productFeeViaIntent !=null) {

                    if (productFeeViaIntent.equals("1")) {
                        rbYes.setChecked(true);
                    } else {
                        rbNo.setChecked(true);
                    }
                }

                if (productButtonViaIntent !=null)
                {
                    setProductButton();
                }



            }
        }
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

    private void setProductButton() {
        if(productButtonViaIntent.equals("/02/01.png"))
        {
            rb_02_01.setChecked(true);
        }
        else if (productButtonViaIntent.equals("/02/02.png"))
        {
            rb_02_02.setChecked(true);
        }
        else if (productButtonViaIntent.equals("/02/03.png"))
        {
            rb_02_03.setChecked(true);
        }
        else if (productButtonViaIntent.equals("/02/04.png"))
        {
            rb_02_04.setChecked(true);
        }
        else if (productButtonViaIntent.equals("/02/05.png"))
        {
            rb_02_05.setChecked(true);
        }
        else if (productButtonViaIntent.equals("/02/06.png"))
        {
            rb_02_06.setChecked(true);
        }
        else if (productButtonViaIntent.equals("/02/07.png"))
        {
            rb_02_07.setChecked(true);
        }
        else if (productButtonViaIntent.equals("/02/08.png"))
        {
            rb_02_08.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/01.png"))
        {
            rb_03_01.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/02.png"))
        {
            rb_03_02.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/03.png"))
        {
            rb_03_03.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/04.png"))
        {
            rb_03_04.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/05.png"))
        {
            rb_03_05.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/06.png"))
        {
            rb_03_06.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/07.png"))
        {
            rb_03_07.setChecked(true);
        }
        else if ( productButtonViaIntent.equals("/03/08.png"))
        {
            rb_03_08.setChecked(true);
        }

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
        EditProductAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.editProductClient.class, BASE_URL, this);

    }

}
