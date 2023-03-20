package com.antar.passenger.utils.api.service;

import com.antar.passenger.json.CheckStatusTransaksiRequest;
import com.antar.passenger.json.CheckStatusTransaksiResponse;
import com.antar.passenger.json.DetailRequestJson;
import com.antar.passenger.json.DetailTransResponseJson;
import com.antar.passenger.json.GetNearRideCarRequestJson;
import com.antar.passenger.json.GetNearRideCarResponseJson;
import com.antar.passenger.json.ItemRequestJson;
import com.antar.passenger.json.LokasiDriverRequest;
import com.antar.passenger.json.LokasiDriverResponse;
import com.antar.passenger.json.RideCarRequestJson;
import com.antar.passenger.json.RideCarResponseJson;
import com.antar.passenger.json.SendRequestJson;
import com.antar.passenger.json.SendResponseJson;
import com.antar.passenger.json.fcm.CancelBookRequestJson;
import com.antar.passenger.json.fcm.CancelBookResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface BookService {

    @POST("pelanggan/list_ride")
    Call<GetNearRideCarResponseJson> getNearRide(@Body GetNearRideCarRequestJson param);

    @POST("pelanggan/list_car")
    Call<GetNearRideCarResponseJson> getNearCar(@Body GetNearRideCarRequestJson param);

    @POST("pelanggan/request_transaksi")
    Call<RideCarResponseJson> requestTransaksi(@Body RideCarRequestJson param);

    @POST("pelanggan/inserttransaksimerchant")
    Call<RideCarResponseJson> requestTransaksiMerchant(@Body ItemRequestJson param);

    @POST("pelanggan/request_transaksi_send")
    Call<SendResponseJson> requestTransaksisend(@Body SendRequestJson param);

    @POST("pelanggan/check_status_transaksi")
    Call<CheckStatusTransaksiResponse> checkStatusTransaksi(@Body CheckStatusTransaksiRequest param);

    @POST("pelanggan/user_cancel")
    Call<CancelBookResponseJson> cancelOrder(@Body CancelBookRequestJson param);

    @POST("pelanggan/liat_lokasi_driver")
    Call<LokasiDriverResponse> liatLokasiDriver(@Body LokasiDriverRequest param);

    @POST("pelanggan/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);


}
