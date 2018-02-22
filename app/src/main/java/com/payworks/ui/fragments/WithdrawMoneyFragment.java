package com.payworks.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.BankAccount;
import com.payworks.generated.model.BankAccountResponse;
import com.payworks.generated.model.LocalBankAccount;
import com.payworks.generated.model.LocalBankAccountResponse;
import com.payworks.generated.model.Localbank;
import com.payworks.generated.model.Userbankaccount;
import com.payworks.generated.model.Withdrawal;
import com.payworks.generated.model.WithdrawalResponse;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.MyInvoicesActivity;
import com.payworks.ui.activities.MyProductActivity;
import com.payworks.ui.activities.MySubscriptionsActivity;
import com.payworks.ui.activities.MyTicketsActivity;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.payworks.api.ApiEndPoints.BASE_URL;

/**
 * Created by Abhinandan on 18/8/17.
 */
public class WithdrawMoneyFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(WithdrawMoneyFragment.class);
   private RetrofitInterface.getLocalBanksClient MyLocalBankAdapter;
    private RetrofitInterface.withdrawMoneyClient WithdrawMoneyAdapter;
    ArrayList<Localbank> myLocalBankAccountList = null;
    ArrayList<String> myLocalBanknameList = null;
    String spLocalBank,bankDetailsToBeSend;
    int withdrawAmount;
    String userWithdrawalAmount,userLocalBank,userTransferMethod;
    @BindView(R.id.spinner2)
    Spinner spinner;

    @BindView(R.id.withdraw_amount)
    EditText etWithdrawAmount;
    @BindView(R.id.radio_button_direct_deposit)
    RadioButton rbDirectDeposit;

    @BindView(R.id.radio_button_regular_mail)
    RadioButton rbRegularMail;

    @BindView(R.id.radio_button_check)
    RadioButton rbCheck;

    @BindView(R.id.rl_spinner)
    RelativeLayout rlSpinnerLayout;

    @BindView(R.id.withdraw_button)
    Button btnWithdraw;




    /*@OnClick(R.id.direct_deposit)
    public void directDeposit() {
        Intent activityChangeIntent = new Intent(getActivity(), MyProductActivity.class);
        startActivity(activityChangeIntent);
    }
*/


    public WithdrawMoneyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_withdraw_money, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getMyLocalBanks();
        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userWithdrawalAmount = etWithdrawAmount.getText().toString();

                if (isRegistrationValid()) {
                    withdrawAmount = Integer.parseInt(userWithdrawalAmount);
                    Log.e(TAG, "onClick: ........" +withdrawAmount);

                    if (withdrawAmount<10)
                    {
                        etWithdrawAmount.setError(getString(R.string.error_small_value));
                    }
                    else
                    {
                        if(rbDirectDeposit.isChecked())
                        {
                            userLocalBank = spLocalBank;
                            for (int i=0; i<myLocalBankAccountList.size(); i++)
                            {
                                if (myLocalBankAccountList.get(i).getLocalbankname().equals(userLocalBank))
                                {
                                    bankDetailsToBeSend = myLocalBankAccountList.get(i).getId().concat("-").concat(myLocalBankAccountList.get(i).getLocalbankname()).concat("-").concat(myLocalBankAccountList.get(i).getAccountnumber()).concat("-").concat(myLocalBankAccountList.get(i).getBranchname()).concat("-").concat(myLocalBankAccountList.get(i).getTransit());

                                }
                            }
                            if(isSpinnerValueAdded())
                            {
                                userTransferMethod ="directdeposit";
                                makeWithdrawalRequest();
                            }
                        }
                        else if (rbRegularMail.isChecked())
                        {
                            userTransferMethod= "regularmail";
                            makeWithdrawalRequest();
                        }
                        else if (rbCheck.isChecked())
                        {
                            userTransferMethod= "checkpickup";
                            makeWithdrawalRequest();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Select Payment Option",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        rbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    rlSpinnerLayout.setVisibility(View.GONE);
                    rbDirectDeposit.setChecked(false);
                    rbRegularMail.setChecked(false);
                    bankDetailsToBeSend = null;


            }
        });

        rbRegularMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    rlSpinnerLayout.setVisibility(View.GONE);
                    rbCheck.setChecked(false);
                    rbDirectDeposit.setChecked(false);
                    bankDetailsToBeSend = null;


            }
        });

        rbDirectDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbDirectDeposit.isChecked()) {
                  rlSpinnerLayout.setVisibility(View.VISIBLE);
                  rbCheck.setChecked(false);
                  rbRegularMail.setChecked(false);

                }
            }
        });

        return rootView;
    }



    private boolean isRegistrationValid() {
        if (userWithdrawalAmount == null || userWithdrawalAmount.equals(""))

        {

            if (userWithdrawalAmount == null || userWithdrawalAmount.equals("") ) {
                etWithdrawAmount.setError(getString(R.string.error_compulsory_field));
            }


            return false;
        } else
            return true;

    }

    private boolean isSpinnerValueAdded() {

        if (userLocalBank.equals("Select Local Bank")|| userLocalBank == null|| userLocalBank.equals(""))

        {

            if (userLocalBank == null || userLocalBank.equals("")||userLocalBank.equals("Select Local Bank"))
                Toast.makeText(getApplicationContext(),"Select Local Bank",Toast.LENGTH_SHORT).show();

            return false;
        } else
            return true;

    }
    private void getMyLocalBanks() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<LocalBankAccountResponse> call = MyLocalBankAdapter.localBankDataData(new LocalBankAccount("getuserlocalaccounts", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<LocalBankAccountResponse>() {

                @Override
                public void onResponse(Call<LocalBankAccountResponse> call, Response<LocalBankAccountResponse> response) {

                    if (response.isSuccessful()) {
                        setLocalBankDetails(response);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<LocalBankAccountResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }


    private void makeWithdrawalRequest() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<WithdrawalResponse> call = WithdrawMoneyAdapter.withdrawMoneyData(new Withdrawal("withdraw", PrefUtils.getUserId(getActivity()),"83Ide@$321!",userWithdrawalAmount,userTransferMethod,bankDetailsToBeSend));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<WithdrawalResponse>() {

                @Override
                public void onResponse(Call<WithdrawalResponse> call, Response<WithdrawalResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() ==1)
                        {
                            Toast.makeText(getApplicationContext(),"Withdrawal Request Sent.",Toast.LENGTH_LONG).show();


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Withdrawal Request Failed.",Toast.LENGTH_SHORT).show();
                        }
                        //setLocalBankDetails(response);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<WithdrawalResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }

    private void setLocalBankDetails(Response<LocalBankAccountResponse> response) {
        myLocalBankAccountList = new ArrayList<>();
        myLocalBanknameList = new ArrayList<>();
        //myLocalBanknameList.add("Select Local Bank");
        for (int i = 0; i < response.body().getLocalbanks().size(); i++) {
            Localbank localbank = new Localbank();

            localbank.setId(response.body().getLocalbanks().get(i).getId());
            localbank.setAccountnumber(response.body().getLocalbanks().get(i).getAccountnumber());
            localbank.setLocalbankname(response.body().getLocalbanks().get(i).getLocalbankname());
            localbank.setBranchname(response.body().getLocalbanks().get(i).getBranchname());
            localbank.setTransit(response.body().getLocalbanks().get(i).getTransit());
            localbank.setIsdefault(response.body().getLocalbanks().get(i).getIsdefault());
            myLocalBankAccountList.add(localbank);
            myLocalBanknameList.add(response.body().getLocalbanks().get(i).getLocalbankname());
            if (response.body().getLocalbanks().get(i).getIsdefault().equals("1"))
            {

            }
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item_layout, myLocalBanknameList);
        spinner.setPrompt("Direct Deposit");
        //spinner.setSelection();
        dataAdapter.setDropDownViewResource(R.layout.select_dialog_singlechoice_custom);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spLocalBank = spinner.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setUpRestAdapter() {
        MyLocalBankAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getLocalBanksClient.class, BASE_URL, getActivity());
        WithdrawMoneyAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.withdrawMoneyClient.class, BASE_URL, getActivity());

    }



}
