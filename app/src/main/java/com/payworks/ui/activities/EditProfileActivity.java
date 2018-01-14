package com.payworks.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends BaseActivity {



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

       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
