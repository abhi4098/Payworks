package com.payworks.ui.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.MerchantData;
import com.payworks.generated.model.MerchantDonationResponse;
import com.payworks.generated.model.MerchantProductResponse;
import com.payworks.generated.model.Product;
import com.payworks.ui.adapters.MyDonationsAdapter;
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

public class MyDonationsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.search_item)
    EditText etSearch;


    private RetrofitInterface.UserMyDonationstClient MyMerchantAdapter;
    MyDonationsAdapter myDonationsAdapter;
    ArrayList<Donation> searchMyList = null;
    ArrayList<Donation> myDonationList = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_my_donation;
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
        tvAppTitle.setText(R.string.my_donations);
        setSearchFunctionality();
        setUpRestAdapter();
        getMyDonations();
    }

    private void setSearchFunctionality() {

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (myDonationList != null) {
                    //manageCategoriesAdapter.getFilter().filter(s.toString());
                    filterSearch(s.toString());
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                if (myDonationList != null) {
                   // MyDonationsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterSearch(String constraint) {
        constraint = constraint.toString().toLowerCase();
        searchMyList =new ArrayList<>();
        for (int i = 0; i < myDonationList.size(); i++) {
            String data = myDonationList.get(i).getDonationname();
            if (data.toLowerCase().startsWith(constraint.toString())) {
                Donation donation = new Donation();
                donation.setDonationname(myDonationList.get(i).getDonationname());
                donation.setDonationprice(myDonationList.get(i).getDonationprice());
                donation.setUpdatedDate(myDonationList.get(i).getUpdatedDate());
                searchMyList.add(donation);

            }
        }


        myDonationsAdapter = new MyDonationsAdapter(this, R.layout.layout_my_donations, R.id.donation_name, searchMyList);
        listview.setAdapter(myDonationsAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);

    }

    private void getMyDonations() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MerchantDonationResponse> call = MyMerchantAdapter.merchantsData(new MerchantData("userdonations", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MerchantDonationResponse>() {

                @Override
                public void onResponse(Call<MerchantDonationResponse> call, Response<MerchantDonationResponse> response) {

                    if (response.isSuccessful()) {
                        setDonations(response);
                        LoadingDialog.cancelLoading();

                        Log.e("abhi", "onResponse: donations--"+response.body().getDonations().size() );

                    }
                }

                @Override
                public void onFailure(Call<MerchantDonationResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setDonations(Response<MerchantDonationResponse> response) {
        myDonationList = new ArrayList<>();
        for (int i = 0; i < response.body().getDonations().size(); i++) {
            Donation donation = new Donation();


            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getDonations().get(i).getCreatedDate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);

            donation.setDonationname(response.body().getDonations().get(i).getDonationname());
            donation.setDonationprice(response.body().getDonations().get(i).getDonationprice());
            donation.setUpdatedDate(outputDateStr);

            Log.e("abhi", "setUserProducts: =========" );

            myDonationList.add(donation);
        }

        myDonationsAdapter = new MyDonationsAdapter(this, R.layout.layout_my_donations, R.id.donation_name, myDonationList);
        listview.setAdapter(myDonationsAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);


    }


    private void setUpRestAdapter() {
        MyMerchantAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyDonationstClient.class, BASE_URL, this);

    }


}
