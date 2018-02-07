package com.simbirsoft.igorverbkin.mycards.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Card extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    private String id;
    @SerializedName("cardName")
    private String cardName;
    @SerializedName("price")
    private String price;
    @SerializedName("endDate")
    private Long endDate;
    @SerializedName("isPurchased")
    private Boolean isPurchased;

    public Card() {
    }

    public Card(String id, String cardName, String price, Long endDate, Boolean isPurchased) {
        this.id = id;
        this.cardName = cardName;
        this.price = price;
        this.endDate = endDate;
        this.isPurchased = isPurchased;
    }

    protected Card(Parcel in) {
        id = in.readString();
        cardName = in.readString();
        price = in.readString();
        if (in.readByte() == 0) {
            endDate = null;
        } else {
            endDate = in.readLong();
        }
        byte tmpIsPurchased = in.readByte();
        isPurchased = tmpIsPurchased == 0 ? null : tmpIsPurchased == 1;
    }


    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", cardName='" + cardName + '\'' +
                ", price='" + price + '\'' +
                ", endDate=" + endDate +
                ", isPurchased=" + isPurchased +
                '}';
    }
}
