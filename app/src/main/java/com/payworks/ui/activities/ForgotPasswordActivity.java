package com.payworks.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.ForgotPassword;
import com.payworks.generated.model.ForgotPasswordResponse;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;
import static com.payworks.api.ApiEndPoints.BASE_URL;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private RetrofitInterface.UserForgotPasswordClient ForgotPasswordAdapter;
    @BindView(R.id.user_email)
    EditText etUserEmailId;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.register_account)
    LinearLayout btnSubmit;

    String userEmail;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public int getNavigationIconId() {
        return 0;
    }

    @Override
    public void onNavigationIconClick(View v) {

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
        tvAppTitle.setText("FORGOT PASSWORD");
        btnSubmit.setOnClickListener(this);
        setUpRestAdapter();


    }

    private void setUpRestAdapter() {
        ForgotPasswordAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserForgotPasswordClient.class, BASE_URL, this);

    }

    private void getForgotPassword() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<ForgotPasswordResponse> call = ForgotPasswordAdapter.userForgotPassword(new ForgotPassword(userEmail,"forgotpassword" ,"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(ForgotPasswordActivity.this)) {
            call.enqueue(new Callback<ForgotPasswordResponse>() {

                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getType() ==0)
                        {
                            Toast.makeText(getApplicationContext(),"Ooops! this email address is not associated with any account.", LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Success", LENGTH_SHORT).show();
                            finish();
                        }
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(ForgotPasswordActivity.this);
        }
    }

    @Override
    public void onClick(View view) {
        userEmail = etUserEmailId.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            Log.e("abhi", "onClick:emppty-------- " );
            etUserEmailId.setError(getString(R.string.error_field_required));

        } else if (!isEmailValid(userEmail)) {
            Log.e("abhi", "onClick:invalid-------- ");
            etUserEmailId.setError(getString(R.string.error_invalid_email));

        }
        else {
            getForgotPassword();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
}
