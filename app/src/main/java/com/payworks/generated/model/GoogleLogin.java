
package com.payworks.generated.model;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleLogin {

    @SerializedName("gid")
    @Expose
    private String gid;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    public GoogleLogin(String email, String name,String gid,String profilePic,String type,String token) {
        this.email = email;
        this.name = name;
        this.gid = gid;
        this.profilePic = profilePic;
        this.type = type;
        this.token = token;


    }
}
