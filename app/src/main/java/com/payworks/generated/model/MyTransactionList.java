package com.payworks.generated.model;

/**
 * Created by Abhinandan on 19/12/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTransactionList {

    @SerializedName("MyTransactionsResponse")
    @Expose
    private List<MyTransactionsResponse> myTransactionsResponse = null;

    public List<MyTransactionsResponse> getMyTransactionsResponse() {
        return myTransactionsResponse;
    }

    public void setMyTransactionsResponse(List<MyTransactionsResponse> myTransactionsResponse) {
        this.myTransactionsResponse = myTransactionsResponse;
    }
}
