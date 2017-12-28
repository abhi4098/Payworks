
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestMoney {

    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("tagnumber")
    @Expose
    private String tagnumber;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("duedate")
    @Expose
    private String duedate;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    public RequestMoney(String type,String userID,String token,String notes,String duedate,String priority,String amount,String tagnumber,String tags) {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.amount = amount;
        this.duedate = duedate;
        this.notes = notes;
        this.priority=priority;
        this.tagnumber = tagnumber;
        this.tags = tags;

    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTagnumber() {
        return tagnumber;
    }

    public void setTagnumber(String tagnumber) {
        this.tagnumber = tagnumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
