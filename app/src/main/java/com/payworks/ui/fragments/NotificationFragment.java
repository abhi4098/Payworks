package com.payworks.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Activity;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.generated.model.Notification;
import com.payworks.generated.model.NotificationResponse;
import com.payworks.generated.model.Userbankaccount;
import com.payworks.ui.activities.EditProfileActivity;
import com.payworks.ui.adapters.BankAccountDetailsAdapter;
import com.payworks.ui.adapters.NotificationsAdapter;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.util.ArrayList;

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
public class NotificationFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(NotificationFragment.class);
    private RetrofitInterface.getNotificationClient NotificatonAdapter;
    ArrayList<Activity> notificationList = null;
    NotificationsAdapter notificationsAdapter;

    @BindView(R.id.empty)
    TextView tvEmpty;
    @BindView(R.id.listview)
    ListView listview;



    public NotificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getNotifications();
        return rootView;
    }

    private void getNotifications() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<NotificationResponse> call = NotificatonAdapter.notificationData(new Notification("profile", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<NotificationResponse>() {

                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {

                    if (response.isSuccessful()) {

                        setNotification(response);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }

    private void setNotification(Response<NotificationResponse> response) {

        notificationList = new ArrayList<>();
        Log.e(TAG, "setSentmoney: size--------------"+response.body().getActivity().size() );
       /* if (response.body().getActivity().size() == 0)
        {
            tvEmpty.setText("No Data Available");
        }*/
        for (int i = 0; i < response.body().getActivity().size(); i++) {
            Activity activity = new Activity();

            activity.setFirstName(response.body().getActivity().get(i).getFirstName());
            activity.setLastName(response.body().getActivity().get(i).getLastName());
            activity.setMessage(response.body().getActivity().get(i).getMessage());
            activity.setCreateDate(response.body().getActivity().get(i).getCreateDate());
            activity.setType(response.body().getActivity().get(i).getType());
            activity.setId(response.body().getActivity().get(i).getId());


            notificationList.add(activity);
            Log.e(TAG, "setNotification: ................"+notificationList.get(i).getMessage() );
        }
        notificationsAdapter = new NotificationsAdapter(this.getActivity(), R.layout.layout_notification, R.id.account_holder_name, notificationList);
        listview.setAdapter(notificationsAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(Color.TRANSPARENT));  //hide the divider
        listview.setClipToPadding(false);
        listview.setDividerHeight(20);
        listview.setTextFilterEnabled(true);
    }


    private void setUpRestAdapter() {
        NotificatonAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.getNotificationClient.class, BASE_URL, getActivity());

    }


}
