package com.payworks.generated.model.googleLogin;

import com.google.gson.annotations.SerializedName;

public class SocialMediaProfilePic {
    @SerializedName("data")
    SocialMediaProfilePicData data;

    public SocialMediaProfilePicData getData() {
        return data;
    }

    public void setData(SocialMediaProfilePicData data) {
        this.data = data;
    }
}
