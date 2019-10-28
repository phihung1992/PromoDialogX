package com.attlib.attpromodialogx;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class AdDialogInfo {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mainAppPackage")
    @Expose
    private String mainAppPackage;
    @SerializedName("promoAppName")
    @Expose
    private String promoAppName;
    @SerializedName("promoAppPackage")
    @Expose
    private String promoAppPackage;
    @SerializedName("promoLogo")
    @Expose
    private String promoLogo;
    @SerializedName("promoImage")
    @Expose
    private String promoImage;
    @SerializedName("promoImage2")
    @Expose
    private String promoImage2;
    @SerializedName("promoImage3")
    @Expose
    private String promoImage3;
    @SerializedName("promoImage4")
    @Expose
    private String promoImage4;
    @SerializedName("promoImage5")
    @Expose
    private String promoImage5;
    @SerializedName("promoDescription")
    @Expose
    private String promoDescription;
    @SerializedName("isAppeared")
    @Expose
    private Boolean isAppeared;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainAppPackage() {
        return mainAppPackage;
    }

    public void setMainAppPackage(String mainAppPackage) {
        this.mainAppPackage = mainAppPackage;
    }

    public String getPromoAppName() {
        return promoAppName;
    }

    public void setPromoAppName(String promoAppName) {
        this.promoAppName = promoAppName;
    }

    public String getPromoAppPackage() {
        return promoAppPackage;
    }

    public void setPromoAppPackage(String promoAppPackage) {
        this.promoAppPackage = promoAppPackage;
    }

    public String getPromoLogo() {
        return promoLogo;
    }

    public void setPromoLogo(String promoLogo) {
        this.promoLogo = promoLogo;
    }

    public String getPromoImage() {
        return promoImage;
    }

    public void setPromoImage(String promoImage) {
        this.promoImage = promoImage;
    }

    public String getPromoImage2() {
        return promoImage2;
    }

    public void setPromoImage2(String promoImage2) {
        this.promoImage2 = promoImage2;
    }

    public String getPromoImage3() {
        return promoImage3;
    }

    public void setPromoImage3(String promoImage3) {
        this.promoImage3 = promoImage3;
    }

    public String getPromoImage4() {
        return promoImage4;
    }

    public void setPromoImage4(String promoImage4) {
        this.promoImage4 = promoImage4;
    }

    public String getPromoImage5() {
        return promoImage5;
    }

    public void setPromoImage5(String promoImage5) {
        this.promoImage5 = promoImage5;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public Boolean getIsAppeared() {
        return isAppeared;
    }

    public void setIsAppeared(Boolean isAppeared) {
        this.isAppeared = isAppeared;
    }

}
