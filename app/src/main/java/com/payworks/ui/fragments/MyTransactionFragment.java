package com.payworks.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.generated.model.MyTransactionList;
import com.payworks.generated.model.MyTransactions;
import com.payworks.generated.model.MyTransactionsResponse;
import com.payworks.ui.activities.EditProfileActivity;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
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

/**
 * Created by Abhinandan on 18/8/17.
 */
public class MyTransactionFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(MyTransactionFragment.class);
    private RetrofitInterface.UserTransactionsClient MyTransactionAdapter;

   /* @BindView(R.id.user_qr_code)
    TextView tvQrCode;
    @BindView(R.id.user_name)
    TextView tvUserName;
    @BindView(R.id.user_phone_num)
    TextView tvUserPhone;
    @BindView(R.id.user_email)
    TextView tvUserEmail;
    @BindView(R.id.user_country)
    TextView tvUserCountry;

    @OnClick(R.id.edit_Profile)
    public void editProfile() {
        Intent activityChangeIntent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(activityChangeIntent);
    }*/


    public MyTransactionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getUserTransactions();
        return rootView;
    }

    private void getUserTransactions() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyTransactionList> call = MyTransactionAdapter.userTransactions(new MyTransactions("usertransactions", "7"/*PrefUtils.getUserId(getActivity())*/,"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyTransactionList>() {

                @Override
                public void onResponse(Call<MyTransactionList> call, Response<MyTransactionList> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body().getMyTransactionsResponse() );

                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyTransactionList> call, Throwable t) {
                    Log.e("abhi", "onFailure: my transactions------------" +t.toString());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }


    private void setUpRestAdapter() {
        MyTransactionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserTransactionsClient.class, BASE_URL, getActivity());

    }


}
