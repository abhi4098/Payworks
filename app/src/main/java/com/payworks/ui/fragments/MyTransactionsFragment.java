package com.payworks.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyTransactions;
import com.payworks.generated.model.MyTransactionsResponse;
import com.payworks.generated.model.Usertransaction;
import com.payworks.ui.adapters.MyItemRecyclerViewAdapter;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.util.ArrayList;

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
public class MyTransactionsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private static final String TAG = LogUtils.makeLogTag(MyTransactionFragment.class);
    private RetrofitInterface.UserTransactionsClient MyTransactionAdapter;
    ArrayList<Usertransaction> myTransactionList = null;

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
        setUpRestAdapter();
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
                        LoadingDialog.cancelLoading();



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

        myTransactionList = new ArrayList<>();
        for (int i = 0; i < response.body().getUsertransactions().size(); i++) {
            Usertransaction usertransaction = new Usertransaction();
            usertransaction.setCreatedDate(response.body().getUsertransactions().get(i).getCreatedDate());
            usertransaction.setEmail(response.body().getUsertransactions().get(i).getEmail());
            usertransaction.setId(response.body().getUsertransactions().get(i).getId());
            usertransaction.setEmailotheruser(response.body().getUsertransactions().get(i).getEmailotheruser());
            usertransaction.setTransactionMode(response.body().getUsertransactions().get(i).getTransactionMode());
            usertransaction.setFullname(response.body().getUsertransactions().get(i).getFullname());
            usertransaction.setMerchantType(response.body().getUsertransactions().get(i).getMerchantType());
            usertransaction.setFullothername(response.body().getUsertransactions().get(i).getFullothername());
            usertransaction.setTransactionId(response.body().getUsertransactions().get(i).getTransactionId());
            usertransaction.setTransactionStatus(response.body().getUsertransactions().get(i).getTransactionStatus());
            usertransaction.setReferenceid(response.body().getUsertransactions().get(i).getReferenceid());
            usertransaction.setTransactionComment(response.body().getUsertransactions().get(i).getTransactionComment());
            usertransaction.setTransactionAmount(response.body().getUsertransactions().get(i).getTransactionAmount());
            Log.e(TAG, "setUserTransaction: =========" );

            myTransactionList.add(usertransaction);
        }

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            Log.e(TAG, "onCreateView: ------------reached set adapter" );
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(myTransactionList, mListener));
        }
    }


    private void setUpRestAdapter() {
        MyTransactionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserTransactionsClient.class, BASE_URL, getActivity());

    }
}
