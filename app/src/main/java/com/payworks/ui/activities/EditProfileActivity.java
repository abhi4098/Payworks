package com.payworks.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.EditProfile;
import com.payworks.generated.model.EditProfileResponse;
import com.payworks.generated.model.RequestMoney;
import com.payworks.generated.model.RequestMoneyResponse;
import com.payworks.utils.LoadingDialog;
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

public class EditProfileActivity extends BaseActivity {

    private RetrofitInterface.editProfileClient EditProfileAdapter;
    String userTitle,userFirstName,userLastName,userAddress,userPhone,userEmail,userBio,userTinnumber,usernibpassport,userZip,userCity;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.user_first_name)
    EditText etUserFirstName;

    @BindView(R.id.user_last_name)
    EditText etUserLastName;

    @BindView(R.id.user_email)
    EditText etUserEmailId;


    @BindView(R.id.user_phone_num)
    EditText etUserPhoneNumber;

    @BindView(R.id.user_address)
    EditText etUserAddress;

    @BindView(R.id.user_city)
    EditText etUserCity;


    @BindView(R.id.user_zip)
    EditText etUserZip;

    @BindView(R.id.user_passport)
    EditText etUserPassport;

    @BindView(R.id.user_tin_number)
    EditText etUserTinNumber;

    @BindView(R.id.user_title)
    EditText etUserTitle;

    @BindView(R.id.user_bio)
    EditText etUserBio;

    @OnClick(R.id.edit_profile_button)
    public void editUserProfile()
    {
        userTitle = etUserTitle.getText().toString();
        userFirstName = etUserFirstName.getText().toString();
        userLastName = etUserLastName.getText().toString();
        userAddress = etUserAddress.getText().toString();
        userPhone = etUserPhoneNumber.getText().toString();
        userEmail = etUserEmailId.getText().toString();
        userBio = etUserBio.getText().toString();
        userTinnumber = etUserTinNumber.getText().toString();
        usernibpassport = etUserPassport.getText().toString();
        userZip = etUserZip.getText().toString();
        userCity = etUserCity.getText().toString();

        editProfileDetails();
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_edit_profile;
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
         return getString(R.string.title_edit_profile);
    }

    @Override
    public boolean focusAtLaunch() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvAppTitle.setText(R.string.edit_profile_title);

        etUserEmailId.setText(PrefUtils.getEmail(EditProfileActivity.this));
        etUserFirstName.setText(PrefUtils.getFirstName(EditProfileActivity.this));
        etUserLastName.setText(PrefUtils.getLastName(EditProfileActivity.this));
        etUserPhoneNumber.setText(PrefUtils.getPhone(EditProfileActivity.this));
        etUserTitle.setText(PrefUtils.getUserTitle(EditProfileActivity.this));
        etUserAddress.setText(PrefUtils.getUserAdd(EditProfileActivity.this));
        etUserCity.setText(PrefUtils.getUserCity(EditProfileActivity.this));
        etUserZip.setText(PrefUtils.getUserZip(EditProfileActivity.this));
        etUserPassport.setText(PrefUtils.getUserPassport(EditProfileActivity.this));
        etUserTinNumber.setText(PrefUtils.getUserTinNumber(EditProfileActivity.this));
        etUserBio.setText(PrefUtils.getUserBio(EditProfileActivity.this));

        setUpRestAdapter();

       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void setUpRestAdapter() {
        EditProfileAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.editProfileClient.class, BASE_URL, this);

    }


    private void editProfileDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<EditProfileResponse> call = EditProfileAdapter.editProfileData(new EditProfile("updatemyprofile", PrefUtils.getUserId(this),"83Ide@$321!",userTitle,userFirstName,userLastName,userAddress,userPhone,userEmail,userBio,userTinnumber,usernibpassport,userZip,userCity));
        if (NetworkUtils.isNetworkConnected(EditProfileActivity.this)) {
            call.enqueue(new Callback<EditProfileResponse>() {

                @Override
                public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {

                    if (response.isSuccessful()) {


                        if (response.body().getTokenid() !=null) {

                            if (response.body().getType() == 1) {
                                Log.e("abhi", "onResponse: "+response.body().getMsg() );
                                Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                                LoadingDialog.cancelLoading();
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(EditProfileActivity.this);
            LoadingDialog.cancelLoading();
        }
    }
}
