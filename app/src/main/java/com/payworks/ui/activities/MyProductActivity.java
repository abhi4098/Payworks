package com.payworks.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MerchantData;
import com.payworks.generated.model.MerchantProductResponse;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;

public class MyProductActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    private RetrofitInterface.UserMyProductClient MyMerchantAdapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_my_product;
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
        tvAppTitle.setText(R.string.my_products);
         setUpRestAdapter();
         getMyProducts();
    }

    private void getMyProducts() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MerchantProductResponse> call = MyMerchantAdapter.merchantsData(new MerchantData("userproducts", "7"/*PrefUtils.getUserId(this)*/,"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MerchantProductResponse>() {

                @Override
                public void onResponse(Call<MerchantProductResponse> call, Response<MerchantProductResponse> response) {

                    if (response.isSuccessful()) {
                        LoadingDialog.cancelLoading();

                        Log.e("abhi", "onResponse: products--"+response.body().getProducts().size() );

                    }
                }

                @Override
                public void onFailure(Call<MerchantProductResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }


    private void setUpRestAdapter() {
        MyMerchantAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyProductClient.class, BASE_URL, this);

    }

}
