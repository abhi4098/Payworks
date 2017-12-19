package com.payworks.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.payworks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends BaseActivity {


    @BindView(R.id.user_first_name)
    EditText etUserFirstName;

    @BindView(R.id.user_last_name)
    EditText etUserLastName;

    @BindView(R.id.user_email)
    EditText etUserEmailId;

    @BindView(R.id.user_country)
    EditText etUserCountry;

    @BindView(R.id.user_phone_num)
    EditText etUserPhoneNumber;

    @BindView(R.id.user_address)
    EditText etUserAddress;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
