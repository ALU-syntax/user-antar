package com.antar.passenger.models;

import java.io.Serializable;

public class TopUpPlnHistoryModel implements Serializable {
    private String destinationNumber;
    private int price;
    private String refId;

    public TopUpPlnHistoryModel(){};

    public TopUpPlnHistoryModel(String destinationNumber, int price, String refId) {
        this.destinationNumber = destinationNumber;
        this.price = price;
        this.refId = refId;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
