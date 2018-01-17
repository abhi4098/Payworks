package com.payworks.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.ui.activities.EditProfileActivity;
import com.payworks.ui.activities.RegistrationActivity;
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
public class MyProfileFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(MyProfileFragment.class);
    private RetrofitInterface.UserMyProfileClient MyProfileAdapter;
    private String profilePicUrl;

    @BindView(R.id.user_qr_code)
    TextView tvQrCode;
    @BindView(R.id.user_name)
    TextView tvUserName;
    @BindView(R.id.user_phone_num)
    TextView tvUserPhone;
    @BindView(R.id.user_email)
    TextView tvUserEmail;
    @BindView(R.id.user_country)
    TextView tvUserCountry;

    @BindView(R.id.person_image)
    de.hdodenhof.circleimageview.CircleImageView imUserImage;



    @OnClick(R.id.edit_Profile)
    public void editProfile() {
        Intent activityChangeIntent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(activityChangeIntent);
    }


    public MyProfileFragment() {
    }


    @Override
    public void onResume(){
        super.onResume();
        setUpRestAdapter();
        getMyProfileDetails();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getMyProfileDetails();
        return rootView;
    }

    private void getMyProfileDetails() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyProfileResponse> call = MyProfileAdapter.userMyProfile(new MyProfile("profile", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyProfileResponse>() {

                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {

                    if (response.isSuccessful()) {
                        PrefUtils.storeEmail(response.body().getProfile().getEmail(),getActivity());
                        PrefUtils.storeFirstName(response.body().getProfile().getFirstName(),getActivity());
                        PrefUtils.storeLastName(response.body().getProfile().getLastName(),getActivity());
                        PrefUtils.storePhone(response.body().getProfile().getPhone(),getActivity());
                        PrefUtils.storeCountry(response.body().getProfile().getCountry(),getActivity());
                        PrefUtils.storeUserCity(response.body().getProfile().getCity(),getActivity());
                        PrefUtils.storeUserPassport(response.body().getProfile().getNibpassport(),getActivity());
                        PrefUtils.storeUserTinNumber(response.body().getProfile().getTinnumber(),getActivity());
                        PrefUtils.storeUserTitle(response.body().getProfile().getTitle(),getActivity());
                        PrefUtils.storeZip(response.body().getProfile().getZip(),getActivity());
                        PrefUtils.storeUserAdd(response.body().getProfile().getAddress(),getActivity());
                        PrefUtils.storeUserBio(response.body().getProfile().getBio(),getActivity());
                        PrefUtils.storeUserState(response.body().getProfile().getState(),getActivity());

                        //tvQrCode.setText(response.body().getProfile().getBio());
                        tvUserName.setText(String.format("%s %s", response.body().getProfile().getFirstName(), response.body().getProfile().getLastName()));
                        tvUserCountry.setText(response.body().getProfile().getCountry());
                        tvUserEmail.setText(response.body().getProfile().getEmail());
                        tvUserPhone.setText(response.body().getProfile().getPhone());
                       // imUserImage.setImageURI(response.body().getProfile().getProfilePic());
                        //profilePicUrl = response.body().getProfile().getProfilePic();

                        /*if (response.body().getProfile().getProfilePic() != null) {
                            profilePicUrl = response.body().getProfile().getProfilePic();
                            String profilePictureUrlComplete = BASE_URL_FOR_IMAGE + profilePicUrl;
                            setProfilePicURL(profilePictureUrlComplete);
                        }*/
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


}
