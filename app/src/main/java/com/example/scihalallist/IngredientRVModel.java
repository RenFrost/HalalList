package com.example.scihalallist;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientRVModel implements Parcelable {
    // creating variables for our different fields.
    private String ingredientName;
    private String ingredientDescription;
    private String ingredientCertificateId;
    private String ingredientType;
    private String validUntil;
    private String productionBy;
    private String ingredientImg;
    private String ingredientID;


    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }


    // creating an empty constructor.
    public IngredientRVModel() {

    }

    protected IngredientRVModel(Parcel in) {
        ingredientID = in.readString();
        ingredientName = in.readString();
        ingredientType = in.readString();
        ingredientImg = in.readString();
        ingredientCertificateId = in.readString();
        validUntil = in.readString();
        productionBy = in.readString();
        ingredientDescription = in.readString();
    }

    public static final Creator<IngredientRVModel> CREATOR = new Creator<IngredientRVModel>() {
        @Override
        public IngredientRVModel createFromParcel(Parcel in) {
            return new IngredientRVModel(in);
        }

        @Override
        public IngredientRVModel[] newArray(int size) {
            return new IngredientRVModel[size];
        }
    };

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientDescription() {
        return ingredientDescription;
    }

    public void setIngredientDescription(String ingredientDescription) {
        this.ingredientDescription = ingredientDescription;
    }

    public String getIngredientCertificateId() {
        return ingredientCertificateId;
    }

    public void setIngredientCertificateId(String ingredientCertificateId) {
        this.ingredientCertificateId = ingredientCertificateId;
    }

    public String getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(String ingredientType) {
        this.ingredientType = ingredientType;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getProductionBy() {
        return productionBy;
    }

    public void setProductionBy(String productionBy) {
        this.productionBy = productionBy;
    }

    public String getIngredientImg() {
        return ingredientImg;
    }

    public void setIngredientImg(String ingredientImg) {
        this.ingredientImg = ingredientImg;
    }

    public IngredientRVModel(String ingredientID, String ingredientName, String ingredientType, String ingredientImg, String ingredientCertificateId, String validUntil, String productionBy, String ingredientDescription) {
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.ingredientImg = ingredientImg;
        this.ingredientCertificateId = ingredientCertificateId;
        this.validUntil = validUntil;
        this.productionBy = productionBy;
        this.ingredientDescription = ingredientDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientID);
        dest.writeString(ingredientName);
        dest.writeString(ingredientType);
        dest.writeString(ingredientImg);
        dest.writeString(ingredientCertificateId);
        dest.writeString(validUntil);
        dest.writeString(productionBy);
        dest.writeString(ingredientDescription);
    }
}
