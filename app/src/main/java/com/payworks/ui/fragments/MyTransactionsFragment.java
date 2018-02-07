package com.payworks.ui.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.GetClient;
import com.payworks.generated.model.GetClientResponse;
import com.payworks.generated.model.GetUser;
import com.payworks.generated.model.GetUserResponse;
import com.payworks.generated.model.MyTransactions;
import com.payworks.generated.model.MyTransactionsResponse;
import com.payworks.generated.model.Usertransaction;
import com.payworks.ui.adapters.MyTransactionsAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MyTransactionsFragment extends Fragment implements View.OnClickListener{

    ListView listview;
    MyTransactionsAdapter myTransactionsAdapter;
    Button showMoreButton;
    int totalItems = 0;
    TextView emptyMessage, tvPageNum, tvShowStats;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private static final String TAG = LogUtils.makeLogTag(MyTransactionFragment.class);
    private RetrofitInterface.UserTransactionsClient MyTransactionAdapter;
    ArrayList<Usertransaction> myTransactionList = null;
    private RetrofitInterface.getUserDetailsClient UserDetailAdapter;
    private RetrofitInterface.getClientDetailsClient ClientDetailAdapter;
    String userName,clientName;
    int listSize;
    int count=0;
    int pageNum =1;
    int  totalNoPages = 0;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyTransactionsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyTransactionsFragment newInstance(int columnCount) {
        MyTransactionsFragment fragment = new MyTransactionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        listview = (ListView) view.findViewById(R.id.listview);
        showMoreButton = (Button) view.findViewById(R.id.show_more_button);
        showMoreButton.setOnClickListener(this);
        setUpRestAdapter();
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        getUserTransactions(view);
        // Set the adapter

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Usertransaction item);
    }

    private void getUserTransactions(final View view) {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyTransactionsResponse> call = MyTransactionAdapter.userTransactions(new MyTransactions("usertransactions", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyTransactionsResponse>() {

                @Override
                public void onResponse(Call<MyTransactionsResponse> call, Response<MyTransactionsResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body().getUsertransactions().size() );
                        setUserTransaction(response,view);
                       // LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyTransactionsResponse> call, Throwable t) {
                    Log.e("abhi", "onFailure: my transactions------------" +t.toString());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }

    private void setUserTransaction(Response<MyTransactionsResponse> response, View view) {
        pageNum = 1;
        showMoreButton.setEnabled(true);
        myTransactionList = new ArrayList<>();
        totalItems =  response.body().getUsertransactions().size();
       /* if (response.body().getUsertransactions().size()< 20)
        {
           listSize =  response.body().getUsertransactions().size();

        }
        else
        {
            listSize =  response.body().getUsertransactions().size();
            //listSize =  20;
        }*/
        for (int i = 0; i < totalItems; i++) {
            Usertransaction usertransaction = new Usertransaction();

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getUsertransactions().get(i).getCreatedDate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            usertransaction.setCreatedDate(outputDateStr);
            usertransaction.setEmail(response.body().getUsertransactions().get(i).getEmail());
            usertransaction.setId(response.body().getUsertransactions().get(i).getId());
            usertransaction.setEmailotheruser(response.body().getUsertransactions().get(i).getEmailotheruser());
            usertransaction.setTransactionMode(response.body().getUsertransactions().get(i).getTransactionMode());
            usertransaction.setFullname(response.body().getUsertransactions().get(i).getFullname());
            usertransaction.setMerchantType(response.body().getUsertransactions().get(i).getMerchantType());
            if (response.body().getUsertransactions().get(i).getTransactionMode().equals("4") || response.body().getUsertransactions().get(i).getTransactionMode().equals("5") )
            {
                getClientDetails(response.body().getUsertransactions().get(i).getTransactedTo(),usertransaction,view);

            }
            else
            {
                getUserDetails(response.body().getUsertransactions().get(i).getTransactedTo(),usertransaction,view);

            }

            usertransaction.setTransactionId(response.body().getUsertransactions().get(i).getTransactionId());
            usertransaction.setTransactionStatus(response.body().getUsertransactions().get(i).getTransactionStatus());
            usertransaction.setReferenceid(response.body().getUsertransactions().get(i).getReferenceid());
            usertransaction.setTransactionComment(response.body().getUsertransactions().get(i).getTransactionComment());
            usertransaction.setTransactionAmount(response.body().getUsertransactions().get(i).getTransactionAmount());
            usertransaction.setTransactedTo(response.body().getUsertransactions().get(i).getTransactedTo());


            myTransactionList.add(usertransaction);


        }

        if (totalItems != 0)

        {
            if ((totalItems % 20) == 0) {
                // number is even
                totalNoPages = totalItems / 20;

            }

            else {

                totalNoPages = ((totalItems / 20)+1);

            }

        }



    }


    private void setUpRestAdapter() {
        MyTransactionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserTransactionsClient.class, BASE_URL, getActivity());
        ClientDetailAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getClientDetailsClient.class, BASE_URL,getActivity());
        UserDetailAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getUserDetailsClient.class, BASE_URL, getActivity());
    }

    private void getUserDetails(String transactedTo, final Usertransaction usertransaction, final View view) {
      //  LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<GetUserResponse> call = UserDetailAdapter.userData(new GetUser(transactedTo,"getUser","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {

                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().getMsg().size(); i++) {
                            userName =response.body().getMsg().get(i).getFullname();
                            usertransaction.setFullname(userName);
                            count++;
                            populateTransactionList(view);
                            Log.e("abhi", "onResponse:username..................... " +userName );
                        }


                       // LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {
                     LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }

    private void getClientDetails(String transactedTo, final Usertransaction usertransaction, final View view) {
       // LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<GetClientResponse> call = ClientDetailAdapter.clientData(new GetClient(transactedTo,"getClient","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<GetClientResponse>() {

                @Override
                public void onResponse(Call<GetClientResponse> call, Response<GetClientResponse> response) {

                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().getMsg().size(); i++) {
                            clientName =response.body().getMsg().get(i).getFullname();
                            usertransaction.setFullname(clientName);
                            count++;
                            populateTransactionList(view);
                            Log.e("abhi", "onResponse:username..................... " +clientName );
                        }


                       // LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<GetClientResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
             LoadingDialog.cancelLoading();
        }
    }

    private void populateTransactionList(View view) {

        if (count == 4)
        {
            LoadingDialog.cancelLoading();

            myTransactionsAdapter = new MyTransactionsAdapter(getActivity(), R.layout.fragment_item, R.id.transactions_name, myTransactionList);
            listview.setAdapter(myTransactionsAdapter);
            LoadingDialog.cancelLoading();
            listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
            listview.setDividerHeight(1);
            listview.setTextFilterEnabled(true);


        }
    }
}
