package com.payworks.api;



import com.payworks.generated.model.AddMoney;
import com.payworks.generated.model.AddMoneyResponse;
import com.payworks.generated.model.BankAccount;
import com.payworks.generated.model.BankAccountResponse;
import com.payworks.generated.model.CountryList;
import com.payworks.generated.model.CountryListResponse;
import com.payworks.generated.model.EditProfile;
import com.payworks.generated.model.EditProfileResponse;
import com.payworks.generated.model.ForgotPassword;
import com.payworks.generated.model.ForgotPasswordResponse;
import com.payworks.generated.model.GetOTP;
import com.payworks.generated.model.GetOTPResponse;
import com.payworks.generated.model.Login;
import com.payworks.generated.model.MerchantData;
import com.payworks.generated.model.MerchantDonationResponse;
import com.payworks.generated.model.MerchantInvoicesResponse;
import com.payworks.generated.model.MerchantProductResponse;
import com.payworks.generated.model.MerchantSubscriptionsResponse;
import com.payworks.generated.model.MerchantTicketsResponse;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.generated.model.MyTransactions;
import com.payworks.generated.model.MyTransactionsResponse;
import com.payworks.generated.model.MyWalletResponse;
import com.payworks.generated.model.Notification;
import com.payworks.generated.model.NotificationResponse;
import com.payworks.generated.model.ReceiveMoneyRequests;
import com.payworks.generated.model.ReceiveMoneyRequestsResponse;
import com.payworks.generated.model.ReferFriend;
import com.payworks.generated.model.ReferFriendResponse;
import com.payworks.generated.model.Registration;
import com.payworks.generated.model.RegistrationResponse;
import com.payworks.generated.model.RequestMoney;
import com.payworks.generated.model.RequestMoneyResponse;
import com.payworks.generated.model.SendMoneyVerified;
import com.payworks.generated.model.SendMoneyVerifiedReponse;
import com.payworks.generated.model.SentMoney;
import com.payworks.generated.model.SentMoneyResponse;
import com.payworks.generated.model.StateList;
import com.payworks.generated.model.StateListResponse;
import com.payworks.generated.model.UploadPhoto;
import com.payworks.generated.model.UploadPhotoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RetrofitInterface {

    public interface UserLoginClient {

        @POST("login.html")
        Call<Login.LoginResponse> userLogIn(@Body Login.LoginDetails loginDetails);

    }


    public interface UserRegistrationClient {
        @POST("register.html")
        Call<RegistrationResponse> userRegistration(@Body Registration registration);

    }

    public interface UserMyProfileClient {
        @POST("getProfile")
        Call<MyProfileResponse> userMyProfile(@Body MyProfile myProfile);

    }

    public interface UserWalletClient {
        @POST("walletbalance")
        Call<MyWalletResponse> userWallet(@Body MyProfile myProfile);

    }

    public interface UserForgotPasswordClient {
        @POST("forgot.html")
        Call<ForgotPasswordResponse> userForgotPassword(@Body ForgotPassword forgotPassword);

    }

    public interface UserTransactionsClient {
        @POST("getusertransactions")
        Call<MyTransactionsResponse> userTransactions(@Body MyTransactions myTransactions);

    }

    public interface UserMyProductClient {
        @POST("getmyproducts")
        Call<MerchantProductResponse> merchantsData(@Body MerchantData merchantData);

    }

    public interface UserMyInvoicesClient {
        @POST("getmyinvoices")
        Call<MerchantInvoicesResponse> merchantsData(@Body MerchantData merchantData);

    }
    public interface UserMySubscriptionClient {
        @POST("getmysubscriptions")
        Call<MerchantSubscriptionsResponse> merchantsData(@Body MerchantData merchantData);

    }
    public interface UserMyTicketsClient {
        @POST("getmytickets")
        Call<MerchantTicketsResponse> merchantsData(@Body MerchantData merchantData);

    }
    public interface UserMyDonationstClient {
        @POST("getmydonations")
        Call<MerchantDonationResponse> merchantsData(@Body MerchantData merchantData);

    }

    public interface UserRequestMoneyClient {
        @POST("requestmoney.html")
        Call<RequestMoneyResponse> requestMoneyData(@Body RequestMoney requestMoney);

    }

    public interface UserAddMoneyClient {
        @POST("add_money.html")
        Call<AddMoneyResponse> addMoneyData(@Body AddMoney addMoney);

    }


    public interface UserReceivedMoneyRequestClient {
        @POST("receivedrequest")
        Call<ReceiveMoneyRequestsResponse> receiveMoneyRequestData(@Body ReceiveMoneyRequests receiveMoneyRequests);

    }

    public interface getOTPClient {
        @POST("generateSendOTP.html")
        Call<GetOTPResponse> getOTPData(@Body GetOTP getOTP);

    }

    public interface sendMoneyVerifiedClient {
        @POST("paymoneyrequest.html")
        Call<SendMoneyVerifiedReponse> sendMoneyVerifiedData(@Body SendMoneyVerified sendMoneyVerified);

    }

    public interface referFriendClient {
        @POST("referfriend.html")
        Call<ReferFriendResponse> referFriendData(@Body ReferFriend referFriend);

    }

    public interface editProfileClient {
        @POST("updatemyprofile")
        Call<EditProfileResponse> editProfileData(@Body EditProfile editProfile);

    }


    public interface getCountryListClient {
        @POST("getcountryjson")
        Call<CountryListResponse> countryListData(@Body CountryList countryList);

    }


    public interface getStateListClient {
        @POST("getstatesjson")
        Call<StateListResponse> stateListData(@Body StateList stateList);

    }

    public interface getSentMoneyClient {
        @POST("sentrequest")
        Call<SentMoneyResponse> sentMoneyData(@Body SentMoney sentMoney);

    }

    public interface getMyBankAccountClient {
        @POST("getUserBankAccounts")
        Call<BankAccountResponse> myBankAccountData(@Body BankAccount bankAccount);

    }

    public interface getNotificationClient {
        @POST("getUserActivity")
        Call<NotificationResponse> notificationData(@Body Notification notification);

    }

    public interface updateProfilePicClient {
        @POST("uploadProfileImage.html")
        Call<UploadPhotoResponse> uploadImageData(@Body UploadPhoto uploadPhoto);

    }

    /*public interface MerchantLoginClient {

        @POST("api/")
        Call<SignInResponse> merchantSignIn(@Body SignIn signinDetails);

    }

    public interface MerchantRegisterClient {

        @POST("api/")
        Call<RegistrationResponse> merchantSignUp(@Body Registration registrationDetails);

    }

    public interface MerchantForgotPasswordClient {

        @POST("api/")
        Call<ForgotPasswordResponse> merchantForgotPassword(@Body ForgotPassword forgotPasswordDetails);

    }

    public interface MerchantStatesClient {

        @POST("api/")
        Call<StateListResponse> merchantState(@Body StateList stateList);

    }

    public interface MerchantProfileClient {

        @POST("api/")
        Call<ProfileResponse> merchantProfile(@Body Profile profile);

    }

    public interface MerchantUpdateProfileClient {

        @POST("api/")
        Call<UpdateProfileResponse> merchantUpdateProfile(@Body UpdateProfile updateProfile);

    }

    public interface MerchantUpdateProductClient {

        @POST("api/")
        Call<ProductUpdateResponse> merchantUpdateProduct(@Body ProductUpdate productUpdate);

    }

    public interface MerchantMyProductClient {

        @POST("api/")
        Call<MyCloverProductResponse> merchantMyProduct(@Body MyCloverProduct myCloverProduct);

    }

    //This api is used for updating margin ,minimum stock and default margin
    public interface MerchantMarginUpdateClient {

        @POST("api/")
        Call<MarginUpdateResponse> merchantMarginUpdate(@Body MarginUpdate marginUpdate);

    }

    public interface MerchantDefaultMarginUpdateClient {

        @POST("api/")
        Call<MarginUpdateResponse> merchantDefaultMarginUpdate(@Body DefaultMarginUpdate marginUpdate);

    }

    public interface MerchantMinStockUpdateClient {

        @POST("api/")
        Call<MarginUpdateResponse> merchantMinStockUpdate(@Body MinimumStockUpdate marginUpdate);

    }

    public interface MerchantUpdateToCloverClient {

        @POST("api/")
        Call<MarginUpdateAllResponse> merchantUpdateToClover(@Body MarginUpdateAll marginUpdate);

    }

    public interface MerchantFetchFromCloverClient {

        @POST("api/")
        Call<MarginUpdateAllResponse> merchantFetchFromClover(@Body MarginUpdateAll marginUpdate);

    }

    public interface DashboardDataClient {

        @POST("api/")
        Call<DashboardDataResponse> merchantDashboardData(@Body DashboardData dashboardData);

    }

    public interface MerchantMyProductCategoryClient {

        @POST("api/")
        Call<CategoryListResponse> merchantMyProductCategory(@Body MyCloverProduct myCloverProduct);

    }*/
}
