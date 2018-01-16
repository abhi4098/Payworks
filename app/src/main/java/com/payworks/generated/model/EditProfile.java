
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfile {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("emailclient")
    @Expose
    private String emailclient;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("nibpassport")
    @Expose
    private String nibpassport;
    @SerializedName("tinnumber")
    @Expose
    private String tinnumber;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;

    public EditProfile(String type,String userID,String token,String title,String firstName,String lastName,String address,String phone,String emailclient,String bio,String tinnumber,String nibpassport,String zip,String city,String country,String state) {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.title=title;
        this.address = address;
        this.emailclient = emailclient;
        this.bio = bio;
        this.tinnumber = tinnumber;
        this.zip = zip;
        this.city = city;
        this.nibpassport = nibpassport;
        this.country = country;
        this.state = state;


    }

}
