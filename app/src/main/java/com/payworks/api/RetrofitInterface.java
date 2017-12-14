package com.payworks.api;



import com.payworks.generated.model.ForgotPassword;
import com.payworks.generated.model.ForgotPasswordResponse;
import com.payworks.generated.model.Login;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.generated.model.Registration;
import com.payworks.generated.model.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
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
        Call<MyProfileResponse> userWallet(@Body MyProfile myProfile);

    }

    public interface UserForgotPasswordClient {
        @POST("forgot.html")
        Call<ForgotPasswordResponse> userForgotPassword(@Body ForgotPassword forgotPassword);

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
