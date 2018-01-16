package com.payworks.ui.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.SentMoney;
import com.payworks.generated.model.SentMoneyResponse;
import com.payworks.generated.model.Sentrequest;
import com.payworks.generated.model.Ticket;
import com.payworks.ui.activities.EditProfileActivity;
import com.payworks.ui.adapters.MyTicketAdapter;
import com.payworks.ui.adapters.SentMoneyRequestAdapter;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
public class SentMoneyRequestFragment extends Fragment {

    SentMoneyRequestAdapter sentMoneyRequestAdapter;

    private static final String TAG = LogUtils.makeLogTag(SentMoneyRequestFragment.class);
    private RetrofitInterface.getSentMoneyClient SentMoneyAdapter;
    ArrayList<Sentrequest> mySentMoneyList = null;
    ArrayList<Sentrequest> searchMyList = null;


    @BindView(R.id.search_item)
    EditText etSearch;
    @BindView(R.id.listview)
    ListView listview;




    public SentMoneyRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sent_money, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getSentMoney(rootView);
        setSearchFunctionality();
        return rootView;
    }


    private void setSearchFunctionality() {

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if ( mySentMoneyList!= null) {
                    //manageCategoriesAdapter.getFilter().filter(s.toString());
                    filterSearch(s.toString());
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                if (mySentMoneyList != null) {
                    // MyDonationsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterSearch(String constraint) {
        constraint = constraint.toString().toLowerCase();
        searchMyList =new ArrayList<>();
        for (int i = 0; i < mySentMoneyList.size(); i++) {
            String data = mySentMoneyList.get(i).getFirstName();
            if (data.toLowerCase().startsWith(constraint.toString())) {
                Sentrequest sentrequest = new Sentrequest();
                sentrequest.setFirstName(mySentMoneyList.get(i).getFirstName());
                sentrequest.setLastName(mySentMoneyList.get(i).getLastName());
                sentrequest.setPriority(mySentMoneyList.get(i).getPriority());
                sentrequest.setStatus(mySentMoneyList.get(i).getStatus());
                sentrequest.setNotes(mySentMoneyList.get(i).getNotes());
                sentrequest.setAmount(mySentMoneyList.get(i).getAmount());
                sentrequest.setDuedate(mySentMoneyList.get(i).getDuedate());
                searchMyList.add(sentrequest);

            }
        }


        sentMoneyRequestAdapter = new SentMoneyRequestAdapter(this.getActivity(), R.layout.layout_sent_money, R.id.client_name, searchMyList);
        listview.setAdapter(sentMoneyRequestAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);

    }



    private void getSentMoney(final View rootView) {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<SentMoneyResponse> call = SentMoneyAdapter.sentMoneyData(new SentMoney("usersentmoneyrequests", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<SentMoneyResponse>() {

                @Override
                public void onResponse(Call<SentMoneyResponse> call, Response<SentMoneyResponse> response) {

                    if (response.isSuccessful()) {

                        setSentmoney(response,rootView);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<SentMoneyResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }


    private void setSentmoney(Response<SentMoneyResponse> response, View view) {

        mySentMoneyList = new ArrayList<>();
        Log.e(TAG, "setSentmoney: size--------------"+response.body().getSentrequests().size() );
        for (int i = 0; i < response.body().getSentrequests().size(); i++) {
            Sentrequest sentrequest = new Sentrequest();

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getSentrequests().get(i).getDuedate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            sentrequest.setDuedate(outputDateStr);
            sentrequest.setAmount(response.body().getSentrequests().get(i).getAmount());
            sentrequest.setPriority(response.body().getSentrequests().get(i).getPriority());
            sentrequest.setFirstName(response.body().getSentrequests().get(i).getFirstName());
            sentrequest.setLastName(response.body().getSentrequests().get(i).getLastName());
            sentrequest.setStatus(response.body().getSentrequests().get(i).getStatus());
            sentrequest.setNotes(response.body().getSentrequests().get(i).getNotes());
            mySentMoneyList.add(sentrequest);
        }

        sentMoneyRequestAdapter = new SentMoneyRequestAdapter(this.getActivity(), R.layout.layout_sent_money, R.id.client_name, mySentMoneyList);
        listview.setAdapter(sentMoneyRequestAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);

    }

    private void setUpRestAdapter() {
        SentMoneyAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getSentMoneyClient.class, BASE_URL, getActivity());

    }


}
