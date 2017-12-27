package com.payworks.ui.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.payworks.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestMoneyActivity extends BaseActivity {

    Calendar myCalendar = Calendar.getInstance();
    String selectedPriority;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.user_Date)
    EditText etDueDate;

    @BindView(R.id.user_email_phone_name)
    EditText etUserEmailOrPhone;

    @BindView(R.id.user_add_amount)
    EditText etAddAmount;

    @BindView(R.id.user_comment)
    EditText etUserComment;

    @BindView(R.id.user_priority)
    AutoCompleteTextView spPriority;

    @OnClick(R.id.user_priority)
    public void userPriority()
    {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(spPriority.getWindowToken(), 0);
        spPriority.showDropDown();

    }

    @OnClick(R.id.user_Date)
    public void dueDate()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etDueDate.getWindowToken(), 0);
        new DatePickerDialog(RequestMoneyActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_request_money;
    }

    @Override
    public int getNavigationIconId() {
        return R.drawable.ic_keyboard_arrow_left_white_24dp;
    }

    @Override
    public void onNavigationIconClick(View v) {
    onBackPressed();
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
        tvAppTitle.setText(R.string.request_money);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);
        spPriority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPriority = (String)adapterView.getItemAtPosition(i);
                Log.e("abhi", "onCreate: ------------"+selectedPriority );
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDueDate.setText(sdf.format(myCalendar.getTime()));

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
}
