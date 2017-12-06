package com.payworks.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Login;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;
import static com.payworks.api.ApiEndPoints.BASE_URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    private RetrofitInterface.UserLoginClient UserLoginAdapter;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    SignInButton btnGoogleLogin;
    LoginButton btnfacebookLogin;
    String email ,password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        btnfacebookLogin = (LoginButton) findViewById(R.id.login_with_facebook);
        btnGoogleLogin = (SignInButton) findViewById(R.id.login_with_google);
        mPasswordView = (EditText) findViewById(R.id.password);
        setUpRestAdapter();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

       /* handleFacebookLoginButton();
        setFacebookDrawable();*/
    }

 /*   public void setFacebookDrawable() {
        float fbIconScale = 1.45F;
        Drawable drawable = getResources().getDrawable(
                com.facebook.R.drawable.com_facebook_button_icon);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * fbIconScale),
                (int) (drawable.getIntrinsicHeight() * fbIconScale));
        btnfacebookLogin.setCompoundDrawables(drawable, null, null, null);
        btnfacebookLogin.setCompoundDrawablePadding(getResources().
                getDimensionPixelSize(R.dimen.fb_margin_override_textpadding));
        btnfacebookLogin.setPadding(
                getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_lr),
                getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_top),
                0,
                getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_bottom));
    }

    public void handleFacebookLoginButton() {
        callbackManager = CallbackManager.Factory.create();
        btnfacebookLogin.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        // If using in a fragment
        btnfacebookLogin.setFragment(this);
        // Other app specific specialization

        // Callback registration
        btnfacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {

                            mProfileTracker.stopTracking();
                            getDetailsFromFacebook();
                        }
                    };
                    mProfileTracker.startTracking();
                } else {

                    getDetailsFromFacebook();

                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                LogUtils.LOGD("", exception.getMessage());
            }
        });
    }*/




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        /*email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();*/
        email = "a@gmail.com";
        password = "abhinandan";

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            getLoginDetails();

        }
    }

    private void getLoginDetails() {

        Call<Login.LoginResponse> call = UserLoginAdapter.userLogIn(new Login.LoginDetails(/*PrefUtils.getAuthToken(getContext()),*/ email,password,"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<Login.LoginResponse>() {

                @Override
                public void onResponse(Call<Login.LoginResponse> call, Response<Login.LoginResponse> response) {
                    Log.e("abhi", "onResponse: ---------------" );

                    if (response.isSuccessful()) {
                        Log.e("abhi", "onResponse: ==================success" + response.body());



                    }
                }

                @Override
                public void onFailure(Call<Login.LoginResponse> call, Throwable t) {
                    Log.e("abhi", "onFailure: ---------------" );
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void setUpRestAdapter() {
        UserLoginAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserLoginClient.class, BASE_URL, this);

    }
}

