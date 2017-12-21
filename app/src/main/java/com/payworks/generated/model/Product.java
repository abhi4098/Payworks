
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("productdescription")
    @Expose
    private String productdescription;
    @SerializedName("productprice")
    @Expose
    private String productprice;
    @SerializedName("producttax")
    @Expose
    private String producttax;
    @SerializedName("productshipping")
    @Expose
    private String productshipping;
    @SerializedName("productreturnurl")
    @Expose
    private String productreturnurl;
    @SerializedName("productnotifyurl")
    @Expose
    private String productnotifyurl;
    @SerializedName("productcancelurl")
    @Expose
    private String productcancelurl;
    @SerializedName("productbutton")
    @Expose
    private String productbutton;
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
    private Object updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProducttax() {
        return producttax;
    }

    public void setProducttax(String producttax) {
        this.producttax = producttax;
    }

    public String getProductshipping() {
        return productshipping;
    }

    public void setProductshipping(String productshipping) {
        this.productshipping = productshipping;
    }

    public String getProductreturnurl() {
        return productreturnurl;
    }

    public void setProductreturnurl(String productreturnurl) {
        this.productreturnurl = productreturnurl;
    }

    public String getProductnotifyurl() {
        return productnotifyurl;
    }

    public void setProductnotifyurl(String productnotifyurl) {
        this.productnotifyurl = productnotifyurl;
    }

    public String getProductcancelurl() {
        return productcancelurl;
    }

    public void setProductcancelurl(String productcancelurl) {
        this.productcancelurl = productcancelurl;
    }

    public String getProductbutton() {
        return productbutton;
    }

    public void setProductbutton(String productbutton) {
        this.productbutton = productbutton;
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

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

}
