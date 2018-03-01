package com.payworks.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.BankAccount;
import com.payworks.generated.model.BankAccountResponse;
import com.payworks.generated.model.Bill;
import com.payworks.generated.model.MyBills;
import com.payworks.generated.model.MyBillsResponse;
import com.payworks.generated.model.Userbankaccount;
import com.payworks.ui.activities.AddBankActivity;
import com.payworks.ui.adapters.BankAccountDetailsAdapter;
import com.payworks.ui.adapters.MyBillsShowAdapter;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;

/**
 * Created by Abhinandan on 18/8/17.
 */
public class MyBillsFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(MyBillsFragment.class);
    private RetrofitInterface.myBillsClient MyBillsAdapter;
    ArrayList<Bill> myBillsList = null;
    MyBillsShowAdapter myBillsShowAdapter;


    @BindView(R.id.empty)
    TextView tvEmpty;
    @BindView(R.id.listview)
    ListView listview;


    public MyBillsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_bills, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getMyBills();
        return rootView;
    }

    private void getMyBills() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyBillsResponse> call = MyBillsAdapter.myBillsData(new MyBills("getmybills", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyBillsResponse>() {

                @Override
                public void onResponse(Call<MyBillsResponse> call, Response<MyBillsResponse> response) {

                    if (response.isSuccessful()) {


                        setMyBills(response);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyBillsResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }

    private void setMyBills(Response<MyBillsResponse> response) {
        myBillsList = new ArrayList<>();
        Log.e(TAG, "setSentmoney: size--------------"+response.body().getBills().size() );
        if (response.body().getBills().size() == 0)
        {
            tvEmpty.setText("No Data Available");
        }
        for (int i = 0; i < response.body().getBills().size(); i++) {
            Bill bill = new Bill();

            bill.setBillamount(response.body().getBills().get(i).getBillamount());
            bill.setBilltemplatename(response.body().getBills().get(i).getBilltemplatename());
            bill.setConsumerid(response.body().getBills().get(i).getConsumerid());
            bill.setConsumername(response.body().getBills().get(i).getConsumername());

            Log.e(TAG, "setBankDetails: bank name........"+response.body().getBills().get(i).getConsumername() );
            myBillsList.add(bill);
        }

        myBillsShowAdapter = new MyBillsShowAdapter(this.getActivity(), R.layout.layout_my_bills_item, R.id.consumer_name, myBillsList);
        listview.setAdapter(myBillsShowAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(Color.TRANSPARENT));  //hide the divider
        listview.setClipToPadding(false);
        listview.setDividerHeight(50);
        listview.setTextFilterEnabled(true);
    }


    private void setUpRestAdapter() {
        MyBillsAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.myBillsClient.class, BASE_URL, getActivity());

    }


}
