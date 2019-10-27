package com.attlib.attpromodialogx;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromoDialogManager {
    private static final String BASE_SERVER = "http://resources.attsolution.com/";
    private static PromoDialogManager instance;
    private Retrofit retrofit = null;
    private AdDialogInfo[] mAdDialogInfos;

    public static PromoDialogManager getInstance() {
        if (instance == null) instance = new PromoDialogManager();
        return instance;
    }

    public void load(String packageName) {
        IAdDialogService adDialogService = getRetrofitClient().create(IAdDialogService.class);
        adDialogService.getAds(getConFigFileName(packageName)).enqueue(new Callback<AdDialogInfo[]>() {
            @Override
            public void onResponse(Call<AdDialogInfo[]> call, Response<AdDialogInfo[]> response) {
                if (response.code() != 200) return;
                mAdDialogInfos = response.body();
            }

            @Override
            public void onFailure(Call<AdDialogInfo[]> call, Throwable t) {

            }
        });
    }

    public PromoDialog newDialog() {
        return new PromoDialog().setData(mAdDialogInfos);
    }

    private String getConFigFileName(String packageName) {
        String[] splitText = packageName.split("[.]");
        String res = "";
        for (String text : splitText) {
            res += text;
        }
        res += ".json";
        return res;
    }

    public Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
