package com.payworks.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.FacebookLogin;
import com.payworks.generated.model.FacebookLoginResponse;
import com.payworks.generated.model.GoogleLogin;
import com.payworks.generated.model.GoogleLoginResponse;
import com.payworks.generated.model.Login;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.generated.model.googleLogin.GCMDetails;
import com.payworks.generated.model.googleLogin.SocialMediaProfilePic;
import com.payworks.generated.model.googleLogin.SocialMediaProfilePicData;
import com.payworks.generated.model.googleLogin.SocialMediaUser;
import com.payworks.utils.InputUtils;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.LogUtils;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;
import static com.payworks.api.ApiEndPoints.BASE_URL;
import static com.payworks.api.ApiEndPoints.BASE_URL_FOR_IMAGE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    private RetrofitInterface.UserLoginClient UserLoginAdapter;
    private RetrofitInterface.facebookLoginClient FacebookLoginAdapter;
    private RetrofitInterface.googleLoginClient GoogleLoginAdapter;
    String emailFromFacebook,facebookId,firstNameFromFb,lastNamefromFb,genderFromFb;
    Uri test;

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
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Button btnRegistration;
    TextView btnForgotPassword;
    String testImage = "test";
    Button mSignInButton;
    SignInButton btnGoogleLogin;
    LoginButton btnFacebookLogin;
    String email ,password = "";
    GoogleApiClient mGoogleApiClient;
    CallbackManager callbackManager;
    ProfileTracker mProfileTracker;
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        btnFacebookLogin = (LoginButton) findViewById(R.id.login_with_facebook);
        btnGoogleLogin = (SignInButton) findViewById(R.id.login_with_google);
        btnRegistration = (Button) findViewById(R.id.register);
        btnForgotPassword = (TextView) findViewById(R.id.click_here);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSignInButton =  (Button) findViewById(R.id.email_sign_in_button);
        setOnClickListener();
        initGoogleAPIClient();
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

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        handleFacebookLoginButton();
        //setFacebookDrawable();
        setGooglePlusButtonText(btnGoogleLogin, "Login with Google              "); // Do not remove spaces. These are added to align with facebook login text... A way to avoid adding custom button for google login
    }



    private void setOnClickListener() {
        btnGoogleLogin.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Log.e("abhi", "onStart: " +account );
    }

    private void initGoogleAPIClient() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogleLogin.setSize(SignInButton.SIZE_WIDE);
        btnGoogleLogin.setScopes(gso.getScopeArray());
       /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btnGoogleLogin.setSize(SignInButton.SIZE_WIDE);
        btnGoogleLogin.setScopes(gso.getScopeArray());*/
    }




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
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
       /* email = "a@gmail.com";
        password = "abhinandan";*/

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
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<Login.LoginResponse> call = UserLoginAdapter.userLogIn(new Login.LoginDetails(/*PrefUtils.getAuthToken(getContext()),*/ email,password,"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<Login.LoginResponse>() {

                @Override
                public void onResponse(Call<Login.LoginResponse> call, Response<Login.LoginResponse> response) {

                    if (response.isSuccessful()) {
                        if(response.body().getId() !=null) {
                            Log.e("abhi", "onResponse: " + response.body().getId());
                            PrefUtils.storeUsernId(response.body().getId(), LoginActivity.this);
                            PrefUtils.storePhone(response.body().getPhone().toString(), LoginActivity.this);
                            PrefUtils.storeFirstName(response.body().getFirstName(), LoginActivity.this);
                            PrefUtils.storeLastName(response.body().getLastName(), LoginActivity.this);
                            PrefUtils.storeEmail(response.body().getEmail(), LoginActivity.this);
                            Intent intent = new Intent(getApplicationContext(), NavigationalActivity.class);
                            intent.putExtra("type", "GetStarted");
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Incorrect Credentials",Toast.LENGTH_SHORT).show();
                        }
                        LoadingDialog.cancelLoading();

                    }
                }

                @Override
                public void onFailure(Call<Login.LoginResponse> call, Throwable t) {
                    Log.e("abhi", "onFailure: ---------------" +t.getLocalizedMessage());
                    LoadingDialog.cancelLoading();
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
        FacebookLoginAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.facebookLoginClient.class, BASE_URL, this);
        GoogleLoginAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.googleLoginClient.class, BASE_URL, this);

    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView mTextView = (TextView) v;
                mTextView.setLayoutParams(
                        new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                mTextView.setText(buttonText);
                mTextView.setGravity(Gravity.CENTER);
                return;
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void handleFacebookLoginButton() {
        callbackManager = CallbackManager.Factory.create();
        btnFacebookLogin.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        // If using in a fragment
        //btnfacebookLogin.setFragment(this);
        // Other app specific specialization

        // Callback registration
        btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {

                            mProfileTracker.stopTracking();
                            Log.e("abhi", "onCurrentProfileChanged: ............." );
                            getDetailsFromFacebook();
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    Log.e("abhi", "onSuccess:''''''''''''''''''''else " );
                    getDetailsFromFacebook();

                }
            }

            @Override
            public void onCancel() {
                Log.e("abhi", "onCancel...................: " );
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("abhi", "onError:--------------------  ");
                LogUtils.LOGD("", exception.getMessage());
            }
        });
    }

    private void getDetailsFromFacebook() {
        Log.e("abhi", "getDetailsFromFacebook: .............." );
        LoadingDialog.showLoadingDialog(this, "Loading..");
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        SocialMediaUser socialMediaUser = new SocialMediaUser();
                        Profile profile = Profile.getCurrentProfile();
                        try {

                            if (object.has("email")) {

                                emailFromFacebook =object.getString("email");

                                Log.e("abhi", "onCompleted: -------------"+object.getString("email") );
                                socialMediaUser.setEmail(object.getString("email"));
                                PrefUtils.storeEmail(emailFromFacebook,getApplicationContext());
                            }
                            if (object.has("birthday")) {

                                Log.e("abhi", "onCompleted: -------------"+object.getString("birthday") );
                                socialMediaUser.setBirthday(object.getString("birthday"));
                            }
                            if (object.has("gender")) {
                                genderFromFb =object.getString("gender");
                                Log.e("abhi", "onCompleted: -------------"+object.getString("gender") );
                                socialMediaUser.setGender(object.getString("gender"));
                            }
                            if (object.has("name")) {
                                if (object.getString("name") !=null) {
                                    String str = object.getString("name");
                                    String[] splited = str.split("\\s+");
                                    firstNameFromFb = splited[0];
                                    lastNamefromFb = splited[1];
                                    Log.e("abhi", "onCompleted: " + firstNameFromFb + " " + lastNamefromFb );
                                    PrefUtils.storeFirstName(firstNameFromFb,getApplicationContext());
                                    PrefUtils.storeLastName(lastNamefromFb,getApplicationContext());
                                }
                                Log.e("abhi", "onCompleted: -------------"+object.getString("name") );

                                //MyProfileFragment.nameOfPatient=object.getString("name").toString();
                                socialMediaUser.setName(object.getString("name"));
                            }
                            if (object.has("id")) {
                                Log.e("abhi", "onCompleted: -------------"+object.getString("id") );
                                Log.e("abhi", "onCompleted: -------------"+object.getString("id") );
                                facebookId = object.getString("id");
                                socialMediaUser.setId(object.getString("id"));
                                socialMediaUser.setUid(object.getString("id"));

                            }

                            loginViaFacebook();

                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                        /*SocialMediaProfilePicData socialMediaProfilePicData = new SocialMediaProfilePicData();
                        socialMediaProfilePicData.setIs_silhouette(false);
                        socialMediaProfilePicData.setUrl(profile.getProfilePictureUri(500, 500).toString());
                        SocialMediaProfilePic socialMediaProfilePic = new SocialMediaProfilePic();
                        socialMediaProfilePic.setData(socialMediaProfilePicData);
                        socialMediaUser.setFacebookPicture(socialMediaProfilePic);
                        socialMediaUser.setProvider("facebook");
                        socialMediaUser.setSignup_utility("facebook");*/
                       // userLoggigInUsingSocialMedia(socialMediaUser);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();



        LoadingDialog.cancelLoading();
    }

    private void loginViaFacebook() {
       // LoginManager.getInstance().logOut();

        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<FacebookLoginResponse> call = FacebookLoginAdapter.facebookLoginData(new FacebookLogin(emailFromFacebook,firstNameFromFb,lastNamefromFb,genderFromFb,facebookId,"fblogin","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<FacebookLoginResponse>() {

                @Override
                public void onResponse(Call<FacebookLoginResponse> call, Response<FacebookLoginResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getType() ==1)
                        {
                            PrefUtils.storePhone(" ", LoginActivity.this);
                            PrefUtils.storeUsernId(response.body().getTokenid(), LoginActivity.this);
                            Log.e("abhi", "onResponse: facebook user id............" +response.body().getTokenid() );
                            Intent intent = new Intent(getApplicationContext(), NavigationalActivity.class);
                            intent.putExtra("type", "GetStarted");
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Log.e("abhi", "onResponse: facebook................else ");
                            LoginManager.getInstance().logOut();
                            loginViaFacebook();
                        }

                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<FacebookLoginResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.email_sign_in_button:
                InputUtils.hideKeyboard(this);
                attemptLogin();
                break;

            case R.id.register:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
               /* Intent intent = new Intent(this, NavigationalActivity.class);
                startActivity(intent);*/
                break;
            case R.id.click_here:
                intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.login_with_google:
                LoadingDialog.showLoadingDialog(this, "Loading..");
                /*Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);*/

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
            default:
                callbackManager.onActivityResult(requestCode, resultCode, data);

        }
        if (requestCode == 100 && resultCode == 100) {
            this.setResult(100);
            this.finish();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            LoadingDialog.cancelLoading();
            PrefUtils.storeEmail(account.getEmail(),this);
            if (account.getDisplayName() !=null) {
                String str = account.getDisplayName();
                String[] splited = str.split("\\s+");
                String firstName = splited[0];
                String lastName = splited[1];
                PrefUtils.storeFirstName(firstName,this);
                PrefUtils.storeLastName(lastName,this);
            }


            Log.e("abhi", "handleSignInResult:Signed in successfully............ " + account.getAccount() + " " +
                    account.getEmail() + " " + account.getDisplayName() + " " + account.getId()+ " " +account.zzaba());
            loginViaGoogle(account);
            /*Intent intent = new Intent(getApplicationContext(), NavigationalActivity.class);
            intent.putExtra("type", "GetStarted");
            startActivity(intent);*/

        } catch (ApiException e) {
            LoadingDialog.cancelLoading();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("abhi", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void loginViaGoogle(GoogleSignInAccount account) {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<GoogleLoginResponse> call = GoogleLoginAdapter.googleLoginData(new GoogleLogin(account.getEmail(),account.getDisplayName(),account.getId(),testImage,"glogin","83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<GoogleLoginResponse>() {

                @Override
                public void onResponse(Call<GoogleLoginResponse> call, Response<GoogleLoginResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getType() ==1)
                        {
                            PrefUtils.storeUsernId(response.body().getTokenid(), LoginActivity.this);
                            PrefUtils.storePhone(" ", LoginActivity.this);
                            Log.e("abhi", "onResponse: google user idccccccccccc............" +response.body().getTokenid() );
                            Intent intent = new Intent(getApplicationContext(), NavigationalActivity.class);
                            intent.putExtra("type", "GetStarted");
                            startActivity(intent);
                            finish();
                        }

                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<GoogleLoginResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }

    }


}

