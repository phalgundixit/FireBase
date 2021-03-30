package com.example.phalguns.firebase;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface api {

    @POST("https://docs.google.com/forms/d/e/1FAIpQLScntMwwx3hsE6_LZoJwc01RbHIkm6oTf9rRvywfww6_Z9EgYw/formResponse")
    @FormUrlEncoded
    Call<Void> spreadsheet(
            @Field("entry.1669586910") String no,
            @Field("entry.1542308193") String name,
            @Field("entry.593524800") String from,
            @Field("entry.2087730370") String to);
}
