package com.antar.passenger.models.ayopulsa;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopUpStatusModel implements Serializable {

    private String message;
    @SerializedName("status_code")
    private int statusCode;
    private TopUpStatusDetailModel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public TopUpStatusDetailModel getData() {
        return data;
    }

    public void setData(TopUpStatusDetailModel data) {
        this.data = data;
    }

    public static class TopUpStatusDetailModel {

        @SerializedName("ref_id")
        private String refId;
        @SerializedName("product_code")
        private String productCode;
        @SerializedName("customer_no")
        private String customerNo;

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getCustomerNo() {
            return customerNo;
        }

        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }
    }
}
