package com.android.we3.stalkforces.apiservice;

import com.android.we3.stalkforces.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// retrofit api client
public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
