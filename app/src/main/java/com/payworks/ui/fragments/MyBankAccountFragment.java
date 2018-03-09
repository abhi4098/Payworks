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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.BankAccount;
import com.payworks.generated.model.BankAccountResponse;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.generated.model.Sentrequest;
import com.payworks.generated.model.Userbankaccount;
import com.payworks.ui.activities.AddBankActivity;
import com.payworks.ui.activities.EditProfileActivity;
import com.payworks.ui.activities.RequestMoneyActivity;
import com.payworks.ui.adapters.BankAccountDetailsAdapter;
import com.payworks.ui.adapters.SentMoneyRequestAdapter;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
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

import static com.payworks.api.ApiEndPoints.BASE_URL;

/**
 * Created by Abhinandan on 18/8/17.
 */
public class MyBankAccountFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(MyBankAccountFragment.class);
    private RetrofitInterface.getMyBankAccountClient MyBankAccountAdapter;
    ArrayList<Userbankaccount> myBankAccountList = null;
    ArrayList<Userbankaccount> searchMyList = null;
    BankAccountDetailsAdapter bankAccountDetailsAdapter;


    @BindView(R.id.empty)
    TextView tvEmpty;
    @BindView(R.id.listview)
    ListView listview;

    @OnClick(R.id.add_bank_button)
    public void addBank()
    {
        Intent activityChangeIntent = new Intent(getActivity(), AddBankActivity.class);
        startActivity(activityChangeIntent);
    }


    public MyBankAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_bank_account, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getBankAccountDetails();
        return rootView;
    }

    private void getBankAccountDetails() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<BankAccountResponse> call = MyBankAccountAdapter.myBankAccountData(new BankAccount("getUserBankAccounts", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<BankAccountResponse>() {

                @Override
                public void onResponse(Call<BankAccountResponse> call, Response<BankAccountResponse> response) {

                    if (response.isSuccessful()) {

                      setBankDetails(response);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<BankAccountResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }

    private void setBankDetails(Response<BankAccountResponse> response) {
        myBankAccountList = new ArrayList<>();
        Log.e(TAG, "setSentmoney: size--------------"+response.body().getUserbankaccounts().size() );
        if (response.body().getUserbankaccounts().size() == 0)
        {
            tvEmpty.setText("No Data Available");
        }
        for (int i = 0; i < response.body().getUserbankaccounts().size(); i++) {
            Userbankaccount userbankaccount = new Userbankaccount();

            userbankaccount.setAccountholder(response.body().getUserbankaccounts().get(i).getAccountholder());
            userbankaccount.setAccountnumber(response.body().getUserbankaccounts().get(i).getAccountnumber());
            if (response.body().getUserbankaccounts().get(i).getAccounttype().equals("1"))
            {
                userbankaccount.setAccounttype("Local Account");
            }
            else
            {
                userbankaccount.setAccounttype("International Account");
            }

            userbankaccount.setBankname(response.body().getUserbankaccounts().get(i).getBankname());
            userbankaccount.setBranchname(response.body().getUserbankaccounts().get(i).getBranchname());
            userbankaccount.setBankphone(response.body().getUserbankaccounts().get(i).getBankphone());
            Log.e(TAG, "setBankDetails: bank name........"+response.body().getUserbankaccounts().get(i).getBankname() );
            myBankAccountList.add(userbankaccount);
        }

        bankAccountDetailsAdapter = new BankAccountDetailsAdapter(this.getActivity(), R.layout.layout_bank_details, R.id.account_holder_name, myBankAccountList);
        listview.setAdapter(bankAccountDetailsAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(Color.TRANSPARENT));  //hide the divider
        listview.setClipToPadding(false);
        listview.setDividerHeight(50);
        listview.setTextFilterEnabled(true);
    }


    private void setUpRestAdapter() {
        MyBankAccountAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getMyBankAccountClient.class, BASE_URL, getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpRestAdapter();
        getBankAccountDetails();
    }
}
