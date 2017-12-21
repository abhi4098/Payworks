
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donation {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("donationname")
    @Expose
    private String donationname;
    @SerializedName("donationprice")
    @Expose
    private String donationprice;
    @SerializedName("donationreturnurl")
    @Expose
    private String donationreturnurl;
    @SerializedName("donationnotifyurl")
    @Expose
    private String donationnotifyurl;
    @SerializedName("donationcancelurl")
    @Expose
    private String donationcancelurl;
    @SerializedName("donationbutton")
    @Expose
    private String donationbutton;
    @SerializedName("sold")
    @Expose
    private String sold;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("paidby")
    @Expose
    private String paidby;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDonationname() {
        return donationname;
    }

    public void setDonationname(String donationname) {
        this.donationname = donationname;
    }

    public String getDonationprice() {
        return donationprice;
    }

    public void setDonationprice(String donationprice) {
        this.donationprice = donationprice;
    }

    public String getDonationreturnurl() {
        return donationreturnurl;
    }

    public void setDonationreturnurl(String donationreturnurl) {
        this.donationreturnurl = donationreturnurl;
    }

    public String getDonationnotifyurl() {
        return donationnotifyurl;
    }

    public void setDonationnotifyurl(String donationnotifyurl) {
        this.donationnotifyurl = donationnotifyurl;
    }

    public String getDonationcancelurl() {
        return donationcancelurl;
    }

    public void setDonationcancelurl(String donationcancelurl) {
        this.donationcancelurl = donationcancelurl;
    }

    public String getDonationbutton() {
        return donationbutton;
    }

    public void setDonationbutton(String donationbutton) {
        this.donationbutton = donationbutton;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPaidby() {
        return paidby;
    }

    public void setPaidby(String paidby) {
        this.paidby = paidby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
