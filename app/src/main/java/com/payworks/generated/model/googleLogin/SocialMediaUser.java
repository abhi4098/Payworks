package com.payworks.generated.model.googleLogin;

import com.google.gson.annotations.SerializedName;


public class SocialMediaUser {

    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("dob")
    String birthday;
    @SerializedName("sex")
    String gender;
    @SerializedName("provider")
    String provider;
    @SerializedName("uid")
    String uid;
    @SerializedName("picture")
    SocialMediaProfilePic picture;

    @SerializedName("device")
    GCMDetails details;

    @SerializedName("signup_utility")
    String signup_utility;
    @SerializedName("jid")
    String jid;
    @SerializedName("chat_password")
    String chatPassword;




    public GCMDetails getDetails() {
        return details;
    }

    public void setDetails(GCMDetails details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public SocialMediaProfilePic getFacebookPicture() {
        return picture;
    }

    public void setFacebookPicture(SocialMediaProfilePic picture) {
        this.picture = picture;
    }

    public String getSignup_utility() {
        return signup_utility;
    }

    public void setSignup_utility(String signup_utility) {
        this.signup_utility = signup_utility;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getChatPassword() {
        return chatPassword;
    }

    public void setChatPassword(String chatPassword) {
        this.chatPassword = chatPassword;
    }
}
