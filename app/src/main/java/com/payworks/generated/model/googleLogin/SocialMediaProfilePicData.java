package com.payworks.generated.model.googleLogin;

import com.google.gson.annotations.SerializedName;


public class SocialMediaProfilePicData {
    boolean is_silhouette;
    @SerializedName("url")
    String url;

    public boolean is_silhouette() {
        return is_silhouette;
    }

    public void setIs_silhouette(boolean is_silhouette) {
        this.is_silhouette = is_silhouette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

