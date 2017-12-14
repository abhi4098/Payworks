package com.payworks.ui.activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.generated.model.Registration;
import com.payworks.generated.model.RegistrationResponse;
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

public class MyProfileActivity extends BaseActivity {
    private static final String TAG = LogUtils.makeLogTag(MyProfileActivity.class);
    private RetrofitInterface.UserMyProfileClient MyProfileAdapter;

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

    @OnClick(R.id.edit_Profile)
    public void editProfile() {
        Intent activityChangeIntent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
        startActivity(activityChangeIntent);
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_my_profile;
    }

    @Override
    public int getNavigationIconId() {
        return R.drawable.ic_keyboard_arrow_left_white_24dp;
    }

    @Override
    public void onNavigationIconClick(View v) {
        super.onBackPressed();
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
        setUpRestAdapter();
        getMyProfileDetails();
       // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void getMyProfileDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MyProfileResponse> call = MyProfileAdapter.userMyProfile(new MyProfile("profile", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(MyProfileActivity.this)) {
            call.enqueue(new Callback<MyProfileResponse>() {

                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {

                    if (response.isSuccessful()) {

                        tvQrCode.setText(response.body().getBio());
                        tvUserName.setText(String.format("%s%s", response.body().getFirstName(), response.body().getLastName()));
                        tvUserCountry.setText(response.body().getCountry());
                        tvUserEmail.setText(response.body().getEmail());
                        tvUserPhone.setText(response.body().getPhone());
                        LoadingDialog.cancelLoading();


                       /* if (response.body().getTokenid() !=null) {

                            if (response.body().getType() == 1) {

                                Intent intent = new Intent(RegistrationActivity.this, NavigationalActivity.class);
                                startActivity(intent);
                                LoadingDialog.cancelLoading();
                            }
                            else
                                Toast.makeText(getApplicationContext(),"You have already registered ",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(MyProfileActivity.this);
        }
    }


    private void setUpRestAdapter() {
        MyProfileAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyProfileClient.class, BASE_URL, this);

    }
    
    
}
