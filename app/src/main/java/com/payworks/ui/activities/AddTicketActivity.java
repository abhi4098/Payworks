package com.payworks.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.AddProduct;
import com.payworks.generated.model.AddProductResponse;
import com.payworks.generated.model.AddTicket;
import com.payworks.generated.model.AddTicketResponse;
import com.payworks.generated.model.EditTicket;
import com.payworks.generated.model.EditTicketResponse;
import com.payworks.utils.LoadingDialog;
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

public class AddTicketActivity extends BaseActivity implements View.OnClickListener {

    String userTicketName,userTicketPrice,userTicketAvailable,userTicketDescription,userAbsorbFee,userTicketButton;
    private RetrofitInterface.addTicketClient AddTicketAdapter;
    private RetrofitInterface.editTicketClient EditTicketAdapter;
    String ticketNameViaIntent ,ticketPriceViaIntent,ticketAvailViaIntent,ticketDescriptionViaIntent,ticketButtonViaIntent,ticketFeeViaIntent,ticketId;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String intentFrom ;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;

    @BindView(R.id.radio_button_yes)
    RadioButton rbYes;
    @BindView(R.id.radio_button_no)
    RadioButton rbNo;

    @BindView(R.id.radio_button_02_01)
    RadioButton rb_02_01;
    @BindView(R.id.radio_button_02_02)
    RadioButton rb_02_02;
    @BindView(R.id.radio_button_02_03)
    RadioButton rb_02_03;
    @BindView(R.id.radio_button_02_04)
    RadioButton rb_02_04;
    @BindView(R.id.radio_button_02_05)
    RadioButton rb_02_05;
    @BindView(R.id.radio_button_02_06)
    RadioButton rb_02_06;
    @BindView(R.id.radio_button_02_07)
    RadioButton rb_02_07;
    @BindView(R.id.radio_button_02_08)
    RadioButton rb_02_08;

    @BindView(R.id.radio_button_03_01)
    RadioButton rb_03_01;
    @BindView(R.id.radio_button_03_02)
    RadioButton rb_03_02;
    @BindView(R.id.radio_button_03_03)
    RadioButton rb_03_03;
    @BindView(R.id.radio_button_03_04)
    RadioButton rb_03_04;
    @BindView(R.id.radio_button_03_05)
    RadioButton rb_03_05;
    @BindView(R.id.radio_button_03_06)
    RadioButton rb_03_06;
    @BindView(R.id.radio_button_03_07)
    RadioButton rb_03_07;
    @BindView(R.id.radio_button_03_08)
    RadioButton rb_03_08;

    @BindView(R.id.add_ticket_name)
    EditText etAddTicketName;

    @BindView(R.id.add_ticket_price)
    EditText etAddTicketPrice;

    @BindView(R.id.add_ticket_avail)
    EditText etAddTicketAvailable;

    @BindView(R.id.add_description)
    EditText etAddTicketDescription;

    @BindView(R.id.add_ticket_button)
    Button addTicketButton;

    @OnClick(R.id.add_ticket_button)
    public void addProduct()
    {
        userTicketName = etAddTicketName.getText().toString();
        userTicketPrice = etAddTicketPrice.getText().toString();
        userTicketAvailable =etAddTicketAvailable.getText().toString();
        userTicketDescription = etAddTicketDescription.getText().toString();

        if (isRegistrationValid()) {

            if (rbYes.isChecked())
            {
                userAbsorbFee = "1";
                setButtonSelection();
            }
            else if (rbNo.isChecked())
            {
                userAbsorbFee = "2";
                setButtonSelection();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Select Yes or No",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setButtonSelection() {

        if(rb_02_01.isChecked())
        {
           userTicketButton = "/02/01.png";
           addTicketDetails();
        }
        else if (rb_02_02.isChecked())
        {
            userTicketButton = "/02/02.png";
            addTicketDetails();
        }
        else if (rb_02_03.isChecked())
        {
            userTicketButton = "/02/03.png";
            addTicketDetails();
        }
        else if (rb_02_04.isChecked())
        {
            userTicketButton = "/02/04.png";
            addTicketDetails();
        }
        else if (rb_02_05.isChecked())
        {
            userTicketButton = "/02/05.png";
            addTicketDetails();
        }
        else if (rb_02_06.isChecked())
        {
            userTicketButton = "/02/06.png";
            addTicketDetails();
        }
        else if (rb_02_07.isChecked())
        {
            userTicketButton = "/02/07.png";
            addTicketDetails();
        }
        else if (rb_02_08.isChecked())
        {
            userTicketButton = "/02/08.png";
            addTicketDetails();
        }
        else if (rb_03_01.isChecked())
        {
            userTicketButton = "/03/01.png";
            addTicketDetails();
        }
        else if (rb_03_02.isChecked())
        {
            userTicketButton = "/03/02.png";
            addTicketDetails();
        }
        else if (rb_03_03.isChecked())
        {
            userTicketButton = "/03/03.png";
            addTicketDetails();
        }
        else if (rb_03_04.isChecked())
        {
            userTicketButton = "/03/04.png";
            addTicketDetails();
        }
        else if (rb_03_05.isChecked())
        {
            userTicketButton = "/03/05.png";
            addTicketDetails();
        }
        else if (rb_03_06.isChecked())
        {
            userTicketButton = "/03/06.png";
            addTicketDetails();
        }
        else if (rb_03_07.isChecked())
        {
            userTicketButton = "/03/07.png";
            addTicketDetails();
        }
        else if (rb_03_08.isChecked())
        {
            userTicketButton = "/03/08.png";
            addTicketDetails();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Choose a button",Toast.LENGTH_SHORT).show();
        }
    }

    private void addTicketDetails() {
        if (intentFrom.equals("AddTicket")) {

            LoadingDialog.showLoadingDialog(this, "Loading...");
            Call<AddTicketResponse> call = AddTicketAdapter.addTicketData(new AddTicket("addticket", PrefUtils.getUserId(this), "83Ide@$321!", userAbsorbFee, userTicketName, userTicketPrice, userTicketAvailable, userTicketDescription, userTicketButton));
            if (NetworkUtils.isNetworkConnected(this)) {
                call.enqueue(new Callback<AddTicketResponse>() {

                    @Override
                    public void onResponse(Call<AddTicketResponse> call, Response<AddTicketResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getType() == 1) {
                                Toast.makeText(getApplicationContext(), "Ticket added successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Adding Ticket.", Toast.LENGTH_SHORT).show();
                            }
                            LoadingDialog.cancelLoading();


                        }
                    }

                    @Override
                    public void onFailure(Call<AddTicketResponse> call, Throwable t) {
                        LoadingDialog.cancelLoading();
                    }


                });

            } else {
                SnakBarUtils.networkConnected(this);
            }
        }
        else
        {
            Log.e("abhi", "addTicketDetails: ....." + ticketAvailViaIntent);
            LoadingDialog.showLoadingDialog(this, "Loading...");
            Call<EditTicketResponse> call = EditTicketAdapter.editTicketsData(new EditTicket("editticket", PrefUtils.getUserId(this),ticketId, "83Ide@$321!", userAbsorbFee, userTicketName, userTicketPrice, userTicketAvailable, userTicketDescription, userTicketButton));
            if (NetworkUtils.isNetworkConnected(this)) {
                call.enqueue(new Callback<EditTicketResponse>() {

                    @Override
                    public void onResponse(Call<EditTicketResponse> call, Response<EditTicketResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getType() == 1) {
                                Toast.makeText(getApplicationContext(), "Ticket edited successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error editing Ticket.", Toast.LENGTH_SHORT).show();
                            }
                            LoadingDialog.cancelLoading();


                        }
                    }

                    @Override
                    public void onFailure(Call<EditTicketResponse> call, Throwable t) {
                        LoadingDialog.cancelLoading();
                    }


                });

            } else {
                SnakBarUtils.networkConnected(this);
            }
        }
    }






    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_tickets;
    }

    @Override
    public int getNavigationIconId() {
        return R.drawable.ic_keyboard_arrow_left_white_24dp;
    }

    @Override
    public void onNavigationIconClick(View v) {
        onBackPressed();
    }

    @Override
    public String getActivityTitle() {
        return null;
    }

    @Override
    public boolean focusAtLaunch() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        intentFrom= getIntent().getExtras().getString("INTENT_FROM");

        if (intentFrom !=null) {
            if (intentFrom.equals("AddTicket")) {
                tvAppTitle.setText(R.string.add_tickets);
            } else {
                ticketNameViaIntent = getIntent().getExtras().getString("TICKET_NAME");
                ticketPriceViaIntent = getIntent().getExtras().getString("TICKET_PRICE");
                ticketAvailViaIntent = getIntent().getExtras().getString("TICKET_AVAIL");
                ticketDescriptionViaIntent = getIntent().getExtras().getString("TICKET_DESCRIPTION");
                ticketButtonViaIntent = getIntent().getExtras().getString("TICKET_BUTTON");
                ticketFeeViaIntent = getIntent().getExtras().getString("TICKET_FEE");
                ticketId = getIntent().getExtras().getString("TICKET_ID");
                Log.e("abhi", "onCreate:..... " +ticketFeeViaIntent  + " " + ticketButtonViaIntent );

                tvAppTitle.setText(R.string.edit_tickets);
                addTicketButton.setText("EDIT TICKET");
                etAddTicketName.setText(ticketNameViaIntent);
                etAddTicketPrice.setText(ticketPriceViaIntent);
                etAddTicketAvailable.setText(ticketAvailViaIntent);
                etAddTicketDescription.setText(ticketDescriptionViaIntent);
                if (ticketFeeViaIntent !=null) {

                    if (ticketFeeViaIntent.equals("1")) {
                        rbYes.setChecked(true);
                    } else {
                        rbNo.setChecked(true);
                    }
                }

                if (ticketButtonViaIntent !=null)
                {
                    setProductButton();
                }



            }
        }

        notificationIcon.setVisibility(View.GONE);
        rbYes.setOnClickListener(this);
        rbNo.setOnClickListener(this);

        rb_02_01.setOnClickListener(this);
        rb_02_02.setOnClickListener(this);
        rb_02_03.setOnClickListener(this);
        rb_02_04.setOnClickListener(this);
        rb_02_05.setOnClickListener(this);
        rb_02_06.setOnClickListener(this);
        rb_02_07.setOnClickListener(this);
        rb_02_08.setOnClickListener(this);

        rb_03_01.setOnClickListener(this);
        rb_03_02.setOnClickListener(this);
        rb_03_03.setOnClickListener(this);
        rb_03_04.setOnClickListener(this);
        rb_03_05.setOnClickListener(this);
        rb_03_06.setOnClickListener(this);
        rb_03_07.setOnClickListener(this);
        rb_03_08.setOnClickListener(this);


         setUpRestAdapter();

    }

    private boolean isRegistrationValid() {
        if (userTicketName == null || userTicketName.equals("")||userTicketPrice == null || userTicketPrice.equals("")
                ||userTicketAvailable == null || userTicketAvailable.equals(""))

        {

            if (userTicketName == null || userTicketName.equals("") ) {
                etAddTicketName.setError(getString(R.string.error_compulsory_field));
            }

            if (userTicketPrice == null || userTicketPrice.equals("") ) {
                etAddTicketPrice.setError(getString(R.string.error_compulsory_field));
            }

            if (userTicketAvailable == null || userTicketAvailable.equals("") ) {
                etAddTicketAvailable.setError(getString(R.string.error_compulsory_field));
            }


            return false;
        } else
            return true;

    }


    private void setProductButton() {
        if(ticketButtonViaIntent.equals("/02/01.png"))
        {
            rb_02_01.setChecked(true);
        }
        else if (ticketButtonViaIntent.equals("/02/02.png"))
        {
            rb_02_02.setChecked(true);
        }
        else if (ticketButtonViaIntent.equals("/02/03.png"))
        {
            rb_02_03.setChecked(true);
        }
        else if (ticketButtonViaIntent.equals("/02/04.png"))
        {
            rb_02_04.setChecked(true);
        }
        else if (ticketButtonViaIntent.equals("/02/05.png"))
        {
            rb_02_05.setChecked(true);
        }
        else if (ticketButtonViaIntent.equals("/02/06.png"))
        {
            rb_02_06.setChecked(true);
        }
        else if (ticketButtonViaIntent.equals("/02/07.png"))
        {
            rb_02_07.setChecked(true);
        }
        else if (ticketButtonViaIntent.equals("/02/08.png"))
        {
            rb_02_08.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/01.png"))
        {
            rb_03_01.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/02.png"))
        {
            rb_03_02.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/03.png"))
        {
            rb_03_03.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/04.png"))
        {
            rb_03_04.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/05.png"))
        {
            rb_03_05.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/06.png"))
        {
            rb_03_06.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/07.png"))
        {
            rb_03_07.setChecked(true);
        }
        else if ( ticketButtonViaIntent.equals("/03/08.png"))
        {
            rb_03_08.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.radio_button_yes:
                rbNo.setChecked(false);
                break;

            case R.id.radio_button_no:
                rbYes.setChecked(false);
                break;

            case R.id.radio_button_02_01:

                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_02:
                rb_02_01.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_03:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_04:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;
            case R.id.radio_button_02_05:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_06:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_07:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_02_08:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_01:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_02:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_03:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_04:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;
            case R.id.radio_button_03_05:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_06:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_07.setChecked(false);
                rb_03_08.setChecked(false);
                break;
            case R.id.radio_button_03_07:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_08.setChecked(false);
                break;

            case R.id.radio_button_03_08:
                rb_02_01.setChecked(false);
                rb_02_02.setChecked(false);
                rb_02_03.setChecked(false);
                rb_02_04.setChecked(false);
                rb_02_05.setChecked(false);
                rb_02_06.setChecked(false);
                rb_02_07.setChecked(false);
                rb_02_08.setChecked(false);

                rb_03_01.setChecked(false);
                rb_03_02.setChecked(false);
                rb_03_03.setChecked(false);
                rb_03_04.setChecked(false);
                rb_03_05.setChecked(false);
                rb_03_06.setChecked(false);
                rb_03_07.setChecked(false);
                break;
        }

    }


    private void setUpRestAdapter() {
        AddTicketAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.addTicketClient.class, BASE_URL, this);
        EditTicketAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.editTicketClient.class, BASE_URL, this);

    }

}
