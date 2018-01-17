package com.payworks.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.facebook.login.LoginManager;
import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.ApiEndPoints;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyWalletResponse;
import com.payworks.generated.model.Usertransaction;
import com.payworks.ui.fragments.MerchantFragment;
import com.payworks.ui.fragments.MyBankAccountFragment;
import com.payworks.ui.fragments.MyProfileFragment;
import com.payworks.ui.fragments.MyTransactionsFragment;
import com.payworks.ui.fragments.NotificationFragment;
import com.payworks.ui.fragments.ProfileHomePageFragment;
import com.payworks.ui.fragments.ReferAFriendFragment;
import com.payworks.ui.fragments.SentMoneyRequestFragment;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,MyTransactionsFragment.OnListFragmentInteractionListener {

    Fragment profileHomePageFragment;
    private static final String TAG = "NavigationalActivity";
    private RetrofitInterface.UserWalletClient UserWalletAdapter;
    String walletBalance;
    TextView headerName,headerEmail,headerPhone,headerUploadPhoto;
    de.hdodenhof.circleimageview.CircleImageView personImage;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;
    @BindView(R.id.wallet_balance)
    TextView tvWalletBalance;
    private String frag;
    String imageUri;

    NavigationView navigationView;
    private ProgressBar imageProgressBar;
    private int PICK_FROM_GALLERY = 1;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private final String[] requiredPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigational);
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        //setSupportActionBar(toolbar);
        setUpRestAdapter();
        getWalletBalance();
        setFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.header_name);
        headerEmail = (TextView)navigationView.getHeaderView(0).findViewById(R.id.header_email);
        headerPhone = (TextView)navigationView.getHeaderView(0).findViewById(R.id.header_phone_num);
        headerUploadPhoto = (TextView)navigationView.getHeaderView(0).findViewById(R.id.upload_pic);
        imageProgressBar = (ProgressBar)navigationView.getHeaderView(0).findViewById(R.id.progress);
        personImage = (de.hdodenhof.circleimageview.CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.person_image);

        setHeaderData();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_Home);
        //navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
        MenuItem itemid = navigationView.getMenu().findItem(R.id.nav_Home);

        if (getFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            onNavigationItemSelected(itemid);
        }

        setUserLoggedIn();
    }

    private void setProfilePicURL(String profilepicUrlComplete) {
        Glide.with(this).load(profilepicUrlComplete).asBitmap().centerCrop().dontAnimate().dontTransform().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                imageProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                imageProgressBar.setVisibility(View.GONE);
                return false;
            }
        })
                .into(new BitmapImageViewTarget(personImage) {
                    @Override
                    protected void setResource(Bitmap bitmap) {
                        Bitmap output;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        } else {
                            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
                        }

                        Canvas canvas = new Canvas(output);

                        final int color = 0xff424242;
                        final Paint paint = new Paint();
                        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                        float r = 0;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            r = bitmap.getHeight() / 2;
                        } else {
                            r = bitmap.getWidth() / 2;
                        }

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawCircle(r, r, r, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, rect, rect, paint);
                        Log.e(TAG, "setResource: -----------output"+output );
                        personImage.setImageBitmap(output);
                        imageProgressBar.setVisibility(View.GONE);

                    }
                });
    }

    private void setHeaderData() {
        headerName.setText(PrefUtils.getFirstName(NavigationalActivity.this).concat(" ").concat(PrefUtils.getLastName(NavigationalActivity.this)));
        headerEmail.setText(PrefUtils.getEmail(NavigationalActivity.this));
        headerPhone.setText(PrefUtils.getPhone(NavigationalActivity.this));
        if (PrefUtils.getUserImage(NavigationalActivity.this) !=null) {
            imageUri = PrefUtils.getUserImage(NavigationalActivity.this);
            Log.e("abhi", "setHeaderData: ------------------"+imageUri );
            setProfilePicURL(imageUri);
        }
        headerUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkpermission();

            }
        });
    }


    private void Checkpermission() {

        if (getPermissions()) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                makeRequest();
            } else {
                makeRequest();
            }
        } else {
            setDialogForImage();
        }
    }


    private void setDialogForImage() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_from_source);
        ImageView btnCamera = (ImageView) dialog.findViewById(R.id.btnCamera);
        ImageView btnDocs = (ImageView) dialog.findViewById(R.id.btnDoc);
        TextView txtDoc = (TextView) dialog.findViewById(R.id.txtDoc);
        btnDocs.setVisibility(View.GONE);
        txtDoc.setVisibility(View.GONE);
        ImageView btnGallery = (ImageView) dialog.findViewById(R.id.btnGallery);

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP;
        wmlp.x = 0;   //x position
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        wmlp.y = (int) px; //y position
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        dialog.getWindow().setLayout((6 * width) / 10, Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.show();


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

                dialog.cancel();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                dialog.cancel();

            }
        });

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri tempUri) {
        Cursor cursor = getContentResolver().query(tempUri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 0 && resultCode == RESULT_OK) {


            Bitmap bp = (Bitmap) data.getExtras().get("data");
            Log.e("abhi", "onActivityResult: bp---------"+bp );
            personImage.setImageBitmap(getCircularBitmap(bp));
            Uri tempUri = getImageUri(getApplicationContext(), bp);
            File filePath = new File(getRealPathFromURI(tempUri));
           // sendImagesToServerFromCamera(filePath.getPath());


        } else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {

           personImage.setBackgroundResource(R.drawable.new_customer_icon);


            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            Log.e(TAG, "onActivityResult: image decodable "+imgDecodableString );
            imageProgressBar.setVisibility(View.VISIBLE);
           // sendImagesToServerFromCamera(imgDecodableString);


        }
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private boolean getPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                requiredPermissions,
                REQUEST_WRITE_STORAGE);
    }




    public void setFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        profileHomePageFragment = new ProfileHomePageFragment();
        fragmentTransaction.add(R.id.fragment_container, profileHomePageFragment, "PROFILE").addToBackStack(null);
        fragmentTransaction.commit();
        tvAppTitle.setText("WALLET");
        Log.e(TAG, "setFragment: =================="+walletBalance );
       // tvWalletBalance.setText(walletBalance);

    }

    private void setUserLoggedIn() {
        PrefUtils.storeUserLoggedIn(true, this);
    }

    private void callRestart() {
        //NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.cancel(5555);
        //notificationManager.cancel(4444);
        LoginManager.getInstance().logOut();
        PrefUtils.storeUserLoggedIn(false, this);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else if (getFragmentManager().getBackStackEntryCount() == 0)
        {

            MenuItem itemid = navigationView.getMenu().findItem(R.id.nav_Home);
            if (getFragmentManager().findFragmentById(R.id.fragment_container) == null) {
                onNavigationItemSelected(itemid);
            }
            Log.e("abhi", "onBackPressed: "+getFragmentManager().getBackStackEntryCount() );
            tvAppTitle.setText("WALLET");
            super.onBackPressed();


        }
        else
        {
            Log.e("abhi", "onBackPressed:else "+getFragmentManager().getBackStackEntryCount() );
            getFragmentManager().popBackStack();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigational, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id) {


            case R.id.nav_Home:
                fragment = new ProfileHomePageFragment();
                ivBackIcon.setVisibility(View.INVISIBLE);
                tvAppTitle.setText(item.getTitle());

                break;
            case R.id.nav_my_profile:
                fragment = new MyProfileFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);

                break;

            /*case R.id.nav_add_money:
                fragment = new AddMoneyFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);

                break;*/

            case R.id.nav_merchant:
                fragment = new MerchantFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);

                break;

            case R.id.nav_my_transactions:
                fragment = new MyTransactionsFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);
                break;

            case R.id.nav_sent_money_request:
                fragment = new SentMoneyRequestFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);
               // Toast.makeText(getApplicationContext(),"Feature will be implemented soon",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_refer_a_friend:
                fragment = new ReferAFriendFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);
                break;

            case R.id.nav_my_bank_account:
                fragment = new MyBankAccountFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);
               // Toast.makeText(getApplicationContext(),"Feature will be implemented soon",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_notification:
                /*fragment = new NotificationFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.VISIBLE);*/
                Toast.makeText(getApplicationContext(),"Feature will be implemented soon",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                callRestart();
                break;

            default:
                fragment = new ProfileHomePageFragment();
                tvAppTitle.setText(item.getTitle());
                ivBackIcon.setVisibility(View.INVISIBLE);
                break;


        }



        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            getWalletBalance();

            Log.e(TAG, "onNavigationItemSelected: ============wallet balance" +walletBalance );

        }
        return true;
    }


    @Override
    public void onClick(View view) {
        if (getFragmentManager().getBackStackEntryCount() == 0)
        {

            MenuItem itemid = navigationView.getMenu().findItem(R.id.nav_Home);
            if (getFragmentManager().findFragmentById(R.id.fragment_container) == null) {
                Log.e("abhi", "onClick: ----------------if"  );
                onNavigationItemSelected(itemid);
                tvAppTitle.setText("WALLET");
                ivBackIcon.setVisibility(View.INVISIBLE);
            }

            else
            {
                Log.e("abhi", "onClick: ----------------else"  );
                super.onBackPressed();
            }


        }
        else
        {
            Log.e("abhi", "onBackPressed:else "+getFragmentManager().getBackStackEntryCount() );
            getFragmentManager().popBackStack();

        }
    }

    private void getWalletBalance() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MyWalletResponse> call = UserWalletAdapter.userWallet(new MyProfile("walletbalance", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MyWalletResponse>() {

                @Override
                public void onResponse(Call<MyWalletResponse> call, Response<MyWalletResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body().getWalletbalance() );
                        walletBalance = response.body().getWalletbalance();
                        tvWalletBalance.setText(walletBalance);

                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyWalletResponse> call, Throwable t) {
                    Log.e("abhi", "onFailure: walletbalance------------" +t.toString());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
            LoadingDialog.cancelLoading();
        }
    }

    private void setUpRestAdapter() {
        UserWalletAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserWalletClient.class, ApiEndPoints.BASE_URL, this);
        // QueryNotificationAdapterForHome = ApiAdapter.createRestAdapter(RetrofitInterface.QueryNotificationClient.class, ApiEndPoints.BASE_URL, getActivity());
    }

    @Override
    public void onListFragmentInteraction(Usertransaction item) {

    }

}
