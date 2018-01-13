package com.payworks.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.ReferFriend;
import com.payworks.generated.model.ReferFriendResponse;
import com.payworks.ui.activities.EditProfileActivity;
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

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.payworks.api.ApiEndPoints.BASE_URL;

/**
 * Created by Abhinandan on 18/8/17.
 */
public class ReferAFriendFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(ReferAFriendFragment.class);
    private RetrofitInterface.referFriendClient ReferFriendAdapter;

    /*@BindView(R.id.user_phone_num)
    EditText etPhoneNum;*/

    @BindView(R.id.user_email)
    EditText etemail;
    String emailFriend,email2,email3,email4,email5;


    @OnClick(R.id.refer_button)
    public void referFriend() {
        emailFriend =etemail.getText().toString();

        if (isRegistrationValid()) {
            sendRefferal();
        }
    }

    public ReferAFriendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_refer_a_friend, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();

        return rootView;
    }

    private boolean isRegistrationValid() {

        if (emailFriend == null || emailFriend.equals("")  || emailFriend == null   || !isValidEmail(emailFriend))

        {

            if (emailFriend == null || emailFriend.equals("") )
                etemail.setError(getString(R.string.error_compulsory_field));


            if (!isValidEmail(emailFriend) )
                etemail.setError("Invalid Email");

            return false;
        } else
            return true;

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void sendRefferal() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<ReferFriendResponse> call = ReferFriendAdapter.referFriendData(new ReferFriend(emailFriend,email2,email3,email4,email5, PrefUtils.getUserId(getActivity()),"83Ide@$321!","referfriend"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<ReferFriendResponse>() {

                @Override
                public void onResponse(Call<ReferFriendResponse> call, Response<ReferFriendResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getType() ==1)
                        {
                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();

                        }
                        else {
                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<ReferFriendResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }


    private void setUpRestAdapter() {
        ReferFriendAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.referFriendClient.class, BASE_URL, getActivity());

    }

}
