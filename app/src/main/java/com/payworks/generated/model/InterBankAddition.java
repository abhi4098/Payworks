
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterBankAddition {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("banktype")
    @Expose
    private String banktype;
    @SerializedName("bankname")
    @Expose
    private String bankname;
    @SerializedName("bankaddress")
    @Expose
    private String bankaddress;
    @SerializedName("bankcountry")
    @Expose
    private String bankcountry;
    @SerializedName("bankstate")
    @Expose
    private String bankstate;
    @SerializedName("bankphone")
    @Expose
    private String bankphone;
    @SerializedName("routingnumber")
    @Expose
    private String routingnumber;
    @SerializedName("swiftcode")
    @Expose
    private String swiftcode;
    @SerializedName("accountholder")
    @Expose
    private String accountholder;
    @SerializedName("accountnumber")
    @Expose
    private String accountnumber;
    @SerializedName("accounttype")
    @Expose
    private String accounttype;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    public InterBankAddition(String type,String userID,String token, String banktype,String bankname,String bankaddress,
                             String accountholder,String accountnumber,String accounttype,String bankcountry,String bankstate,String bankphone,String routingnumber,String swiftcode)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.accountholder =accountholder;
        this.banktype = banktype;
        this.bankname =bankname;
        this.bankaddress =bankaddress;
        this.accounttype = accounttype;
        this.accountnumber =accountnumber;
        this.bankcountry =bankcountry;
        this.bankstate =bankstate;
        this.bankphone =bankphone;
        this.routingnumber =routingnumber;
        this.swiftcode=swiftcode;


    }
}
