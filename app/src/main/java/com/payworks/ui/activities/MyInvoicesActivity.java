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
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.Invoice;
import com.payworks.generated.model.MerchantData;
import com.payworks.generated.model.MerchantDonationResponse;
import com.payworks.generated.model.MerchantInvoicesResponse;
import com.payworks.ui.adapters.MyDonationsAdapter;
import com.payworks.ui.adapters.MyInvoicesAdapter;
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

public class MyInvoicesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;
    @BindView(R.id.listview)
    ListView listview;
    private RetrofitInterface.UserMyInvoicesClient MyMerchantAdapter;
    MyInvoicesAdapter myInvoicesAdapter;
    ArrayList<Invoice> myInvoicesList = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_my_invoices;
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
        tvAppTitle.setText(R.string.my_invoices);
        setUpRestAdapter();
        getMyInvoices();
    }

    private void getMyInvoices() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MerchantInvoicesResponse> call = MyMerchantAdapter.merchantsData(new MerchantData("userinvoices", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MerchantInvoicesResponse>() {

                @Override
                public void onResponse(Call<MerchantInvoicesResponse> call, Response<MerchantInvoicesResponse> response) {

                    if (response.isSuccessful()) {
                        setInvoices(response);
                        LoadingDialog.cancelLoading();

                        Log.e("abhi", "onResponse: donations--"+response.body().getInvoices().size() );

                    }
                }

                @Override
                public void onFailure(Call<MerchantInvoicesResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setInvoices(Response<MerchantInvoicesResponse> response) {
        myInvoicesList = new ArrayList<>();
        for (int i = 0; i < response.body().getInvoices().size(); i++) {
            Invoice invoice = new Invoice();


            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getInvoices().get(i).getCreatedDate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);

            invoice.setCustomerName(response.body().getInvoices().get(i).getCustomerName());
            invoice.setAmount(response.body().getInvoices().get(i).getAmount());
            invoice.setInvoicenumber(response.body().getInvoices().get(i).getInvoicenumber());
            invoice.setUpdatedDate(outputDateStr);

            Log.e("abhi", "setUserProducts: =========" );

            myInvoicesList.add(invoice);
        }

        myInvoicesAdapter = new MyInvoicesAdapter(this, R.layout.layout_my_invoices, R.id.product_name, myInvoicesList);
        listview.setAdapter(myInvoicesAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);


    }


    private void setUpRestAdapter() {
        MyMerchantAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyInvoicesClient.class, BASE_URL, this);

    }


}
