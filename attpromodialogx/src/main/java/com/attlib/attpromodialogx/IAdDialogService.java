package com.attlib.attpromodialogx;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAdDialogService {
    @GET("adDialogConfigs/{configFile}")
    Call<AdDialogInfo[]> getAds(@Path("configFile") String configFile);
}
