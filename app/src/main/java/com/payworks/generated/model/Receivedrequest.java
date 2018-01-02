
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receivedrequest {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("requestedby")
    @Expose
    private String requestedby;
    @SerializedName("requestedto")
    @Expose
    private String requestedto;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("duedate")
    @Expose
    private String duedate;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("countryname")
    @Expose
    private String countryname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

    public String getRequestedto() {
        return requestedto;
    }

    public void setRequestedto(String requestedto) {
        this.requestedto = requestedto;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

}
