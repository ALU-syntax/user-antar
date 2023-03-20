package com.antar.passenger.api;



import com.antar.passenger.models.city.ItemCity;
import com.antar.passenger.models.cost.ItemCost;
import com.antar.passenger.models.province.ItemProvince;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Province
    @GET("province")
    @Headers("key:2c1cf2e4614041e9e929dbee9b2ccfd1")
    Call<ItemProvince> getProvince();

    // City
    @GET("city")
    @Headers("key:2c1cf2e4614041e9e929dbee9b2ccfd1")
    Call<ItemCity> getCity(@Query("province") String province);

    // Cost
    @FormUrlEncoded
    @POST("cost")
    Call<ItemCost> getCost(@Field("key") String Token,
                           @Field("origin") String origin,
                           @Field("destination") String destination,
                           @Field("weight") String weight,
                           @Field("courier") String courier);

}
