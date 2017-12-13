package com.payworks.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.payworks.R;


import butterknife.BindView;
import butterknife.ButterKnife;



public class ProfileHomePageFragment extends Fragment {
    private static final String TAG = "ProfileHomePageFragment";
/*    private ActiveConsultationInterface.ActiveConsultationClient ActiveConsultationAdapter;
    private DoctorActiveConsultationAdapter activeConsultanceAdapter;
    ArrayList<QueryNotification> activeConsultanceList = new ArrayList<>();
    ArrayList<ActiveConsultationTable> activeConsulTableList = new ArrayList<>();*/
    Context mContext;

  /*  public static int QUERY_NOTIFICATION_COUNT = 0;
    @BindView(R.id.relative_Layout)
    RelativeLayout rl;

    @BindView(R.id.complete_profile_progressbar)
    ProgressBar pbCompleteProfile;

    @BindView(R.id.doctor_queries)
    TextView tvDocQueries;

    @BindView(R.id.active_consultance)
    TextView tvActiveConsultance;

    @BindView(R.id.active_consultance_list)
    TextView tvActiveConsulList;

    @BindView(R.id.doctor_queries_number)
    TextView tvQueryNuumber;

    @BindView(R.id.query_number_layout)
    LinearLayout queryNumberLayout;

    @BindView(R.id.active_consultation_layout)
    LinearLayout activeConsultLayout;

    @BindView(R.id.active_consultance_recycler_view)
    RecyclerView activeConsultationRecyclerView;

    @BindView(R.id.active_consultation_blank_layout)
    LinearLayout activeConsultBlankLayout;


    @BindView(R.id.home_fragment_profile)
    LinearLayout homeFragmentProfile;

    private RetrofitInterface.QueryNotificationClient QueryNotificationAdapterForHome;
    private boolean isQueryCounterZero;
    private boolean isActiveConsulationZero;*/


    public ProfileHomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
      /*  QUERY_NOTIFICATION_COUNT = 0;
        PrefUtils.setActiveConsultationScreenVisible(getActivity(), true);
        getActivity().registerReceiver(networkReceiver, new IntentFilter("internet_connectivity_check"));
        getActivity().registerReceiver(queryReceiver, new IntentFilter(DocFirebaseMessagingService.INTENT_FILTER));
        getActivity().registerReceiver(chatMessageReceiver, new IntentFilter(ChatBroadCastReceiver.CHAT_FILTER));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            getOpenQuerynumber();
        } else {
            setScreenFromDatabse();
            if (getActivity() != null)
                SnakBarUtils.networkConnected(getActivity());
        }*/
    }


    @Override
    public void onPause() {
        super.onPause();
       /* PrefUtils.setActiveConsultationScreenVisible(getActivity(), false);
        getActivity().unregisterReceiver(queryReceiver);
        getActivity().unregisterReceiver(chatMessageReceiver);
        getActivity().unregisterReceiver(networkReceiver);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home_page, container, false);
        Log.e(TAG, "onCreateView: " );
        ButterKnife.bind(this, view);
        //setUpRestAdapter();
        return view;

    }





  /*  private void setUpRestAdapter() {
        ActiveConsultationAdapter = ApiAdapter.createRestAdapter(ActiveConsultationInterface.ActiveConsultationClient.class, ApiEndPoints.BASE_URL, getActivity());
        QueryNotificationAdapterForHome = ApiAdapter.createRestAdapter(RetrofitInterface.QueryNotificationClient.class, ApiEndPoints.BASE_URL, getActivity());
    }
*/





}