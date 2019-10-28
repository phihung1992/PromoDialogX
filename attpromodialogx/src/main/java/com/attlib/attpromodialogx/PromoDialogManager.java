package com.attlib.attpromodialogx;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromoDialogManager {
    private static final String BASE_SERVER = "http://api.attsolution.com/";
    private static PromoDialogManager instance;
    private Retrofit retrofit = null;
    private AdDialogInfo[] mAdDialogInfos;

    public static PromoDialogManager getInstance() {
        if (instance == null) instance = new PromoDialogManager();
        return instance;
    }

    public void load(String userName, String password, String packageName) {
        IAdDialogService adDialogService = getRetrofitClient(userName, password, packageName).create(IAdDialogService.class);
        AdDialogInfoRequestArgs requestArgs = new AdDialogInfoRequestArgs();
        requestArgs.setPackageName(packageName);
        adDialogService.getAds(requestArgs).enqueue(new Callback<AdDialogInfo[]>() {
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

    private Retrofit getRetrofitClient(final String userName, final String password, final String packageName) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Username", userName)
                            .addHeader("Password", password)
                            .addHeader("AppPackageName", packageName)
                            .build();
                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
