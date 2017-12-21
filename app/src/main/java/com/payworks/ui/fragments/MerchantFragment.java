package com.payworks.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.ui.activities.EditProfileActivity;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.MyProductActivity;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

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
public class MerchantFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(MerchantFragment.class);
   // private RetrofitInterface.UserMyProfileClient MyProfileAdapter;

/*    @BindView(R.id.my_product)
    LinearLayout llMyProducts;
    @BindView(R.id.my_donation)
    LinearLayout llDonations;
    @BindView(R.id.my_invoices)
    LinearLayout llInvoices;
    @BindView(R.id.my_subscription)
    LinearLayout llSubscriptions;
    @BindView(R.id.my_tickets)
    LinearLayout llTickets;*/

    @OnClick(R.id.my_product)
    public void myProduct() {
        Intent activityChangeIntent = new Intent(getActivity(), MyProductActivity.class);
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.my_donation)
    public void myDonation() {
        Intent activityChangeIntent = new Intent(getActivity(), MyDonationsActivity.class);
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.my_tickets)
    public void myTicket() {
        Intent activityChangeIntent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.my_subscription)
    public void mySubscriptions() {
        Intent activityChangeIntent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.my_invoices)
    public void myInvoices() {
        Intent activityChangeIntent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(activityChangeIntent);
    }

    public MerchantFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_merchant, container, false);
        ButterKnife.bind(this,rootView);
       // setUpRestAdapter();
       // getMyProfileDetails();
        return rootView;
    }



}
