package com.payworks.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payworks.R;
import com.payworks.api.RetrofitInterface;
import com.payworks.ui.activities.AddCardDetailActivity;
import com.payworks.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abhinandan on 18/8/17.
 */
public class AddMoneyFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(AddMoneyFragment.class);
    private RetrofitInterface.UserMyProfileClient MyProfileAdapter;

    /*@BindView(R.id.user_qr_code)
    TextView tvQrCode;
    @BindView(R.id.user_name)
    TextView tvUserName;
    @BindView(R.id.user_phone_num)
    TextView tvUserPhone;
    @BindView(R.id.user_email)
    TextView tvUserEmail;
    @BindView(R.id.user_country)
    TextView tvUserCountry;*/

    @OnClick(R.id.saved_card)
    public void editProfile() {
        Intent activityChangeIntent = new Intent(getActivity(), AddCardDetailActivity.class);
        startActivity(activityChangeIntent);
    }


    public AddMoneyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_money, container, false);
        ButterKnife.bind(this,rootView);
       // setUpRestAdapter();
       // getMyProfileDetails();
        return rootView;
    }

    /*private void getMyProfileDetails() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyProfileResponse> call = MyProfileAdapter.userMyProfile(new MyProfile("profile", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyProfileResponse>() {

                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {

                    if (response.isSuccessful()) {

                      *//*  tvQrCode.setText(response.body().getBio());
                        tvUserName.setText(String.format("%s%s", response.body().getFirstName(), response.body().getLastName()));
                        tvUserCountry.setText(response.body().getCountry());
                        tvUserEmail.setText(response.body().getEmail());
                        tvUserPhone.setText(response.body().getPhone());*//*
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }


    private void setUpRestAdapter() {
        MyProfileAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyProfileClient.class, BASE_URL, getActivity());

    }

*/
}
