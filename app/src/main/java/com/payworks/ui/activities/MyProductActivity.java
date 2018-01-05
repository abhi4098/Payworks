package com.payworks.ui.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;

public class MyProductActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.listview)
    ListView listview;

    private RetrofitInterface.UserMyProductClient MyMerchantAdapter;
    ArrayList<Product> myProductList = null;
    MyProductAdapter myProductAdapter;

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
        Call<MerchantProductResponse> call = MyMerchantAdapter.merchantsData(new MerchantData("userproducts", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MerchantProductResponse>() {

                @Override
                public void onResponse(Call<MerchantProductResponse> call, Response<MerchantProductResponse> response) {

                    if (response.isSuccessful()) {
                        setMyProduct(response);
                        LoadingDialog.cancelLoading();


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

    private void setMyProduct(Response<MerchantProductResponse> response) {

        myProductList = new ArrayList<>();
        for (int i = 0; i < response.body().getProducts().size(); i++) {
            Product product = new Product();

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getProducts().get(i).getCreatedDate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);


            product.setProductname(response.body().getProducts().get(i).getProductname());
            product.setProductprice(response.body().getProducts().get(i).getProductprice());
            product.setProductshipping(response.body().getProducts().get(i).getProductshipping());
            product.setSold(response.body().getProducts().get(i).getSold());
            product.setUpdatedDate(outputDateStr);

            Log.e("abhi", "setUserProducts: =========" );

            myProductList.add(product);
        }

        myProductAdapter = new MyProductAdapter(this, R.layout.layout_my_product_list, R.id.product_name, myProductList);
        listview.setAdapter(myProductAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);


    }


    private void setUpRestAdapter() {
        MyMerchantAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyProductClient.class, BASE_URL, this);

    }

}
