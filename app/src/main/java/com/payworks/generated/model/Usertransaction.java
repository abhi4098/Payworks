
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usertransaction {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transacted_to")
    @Expose
    private String transactedTo;
    @SerializedName("transacted_from")
    @Expose
    private String transactedFrom;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("transaction_mode")
    @Expose
    private String transactionMode;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("transaction_comment")
    @Expose
    private String transactionComment;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("referenceid")
    @Expose
    private String referenceid;
    @SerializedName("quantity")
    @Expose
    private Object quantity;
    @SerializedName("merchant_type")
    @Expose
    private Object merchantType;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("fullothername")
    @Expose
    private String fullothername;
    @SerializedName("emailuser")
    @Expose
    private String emailuser;
    @SerializedName("emailotheruser")
    @Expose
    private String emailotheruser;
    @SerializedName("email")
    @Expose
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactedTo() {
        return transactedTo;
    }

    public void setTransactedTo(String transactedTo) {
        this.transactedTo = transactedTo;
    }

    public String getTransactedFrom() {
        return transactedFrom;
    }

    public void setTransactedFrom(String transactedFrom) {
        this.transactedFrom = transactedFrom;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionComment() {
        return transactionComment;
    }

    public void setTransactionComment(String transactionComment) {
        this.transactionComment = transactionComment;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(String referenceid) {
        this.referenceid = referenceid;
    }

    public Object getQuantity() {
        return quantity;
    }

    public void setQuantity(Object quantity) {
        this.quantity = quantity;
    }

    public Object getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(Object merchantType) {
        this.merchantType = merchantType;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullothername() {
        return fullothername;
    }

    public void setFullothername(String fullothername) {
        this.fullothername = fullothername;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public String getEmailotheruser() {
        return emailotheruser;
    }

    public void setEmailotheruser(String emailotheruser) {
        this.emailotheruser = emailotheruser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
