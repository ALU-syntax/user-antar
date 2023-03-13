package com.insoftbumdesku.passenger.utils.api.service;

import com.insoftbumdesku.passenger.json.AllMerchantByNearResponseJson;
import com.insoftbumdesku.passenger.json.AllMerchantbyCatRequestJson;
import com.insoftbumdesku.passenger.json.AllTransResponseJson;
import com.insoftbumdesku.passenger.json.BankResponseJson;
import com.insoftbumdesku.passenger.json.BeritaDetailRequestJson;
import com.insoftbumdesku.passenger.json.BeritaDetailResponseJson;
import com.insoftbumdesku.passenger.json.ChangePassRequestJson;
import com.insoftbumdesku.passenger.json.DaftarWilayahRequestJson;
import com.insoftbumdesku.passenger.json.DaftarWilayahResponseJson;
import com.insoftbumdesku.passenger.json.DetailRequestJson;
import com.insoftbumdesku.passenger.json.EditprofileRequestJson;
import com.insoftbumdesku.passenger.json.FcmKeyResponseJson;
import com.insoftbumdesku.passenger.json.FcmRequestJson;
import com.insoftbumdesku.passenger.json.GetAllMerchantbyCatRequestJson;
import com.insoftbumdesku.passenger.json.GetFiturResponseJson;
import com.insoftbumdesku.passenger.json.GetHomeRequestJson;
import com.insoftbumdesku.passenger.json.GetHomeResponseJson;
import com.insoftbumdesku.passenger.json.GetMerchantbyCatRequestJson;
import com.insoftbumdesku.passenger.json.GetAyoPulsaBaseResponse;
import com.insoftbumdesku.passenger.json.LoginRequestJson;
import com.insoftbumdesku.passenger.json.LoginResponseJson;
import com.insoftbumdesku.passenger.json.MerchantByCatResponseJson;
import com.insoftbumdesku.passenger.json.MerchantByIdResponseJson;
import com.insoftbumdesku.passenger.json.MerchantByNearResponseJson;
import com.insoftbumdesku.passenger.json.MerchantbyIdRequestJson;
import com.insoftbumdesku.passenger.json.MobilePulsaHealthBPJSBaseResponse;
import com.insoftbumdesku.passenger.json.MobilePulsaPLNCheckResponse;
import com.insoftbumdesku.passenger.json.MobileTopUpPostPaidStatusJson;
import com.insoftbumdesku.passenger.json.MobileTopUpRequestModel;
import com.insoftbumdesku.passenger.json.PoinRequestJson;
import com.insoftbumdesku.passenger.json.PrivacyRequestJson;
import com.insoftbumdesku.passenger.json.PrivacyResponseJson;
import com.insoftbumdesku.passenger.json.PromoRequestJson;
import com.insoftbumdesku.passenger.json.PromoResponseJson;
import com.insoftbumdesku.passenger.json.RateRequestJson;
import com.insoftbumdesku.passenger.json.RateResponseJson;
import com.insoftbumdesku.passenger.json.RegisterRequestJson;
import com.insoftbumdesku.passenger.json.RegisterResponseJson;
import com.insoftbumdesku.passenger.json.ResponseJson;
import com.insoftbumdesku.passenger.json.SearchMerchantbyCatRequestJson;
import com.insoftbumdesku.passenger.json.SendLocationRequestJson;
import com.insoftbumdesku.passenger.json.SendLocationResponseJson;
import com.insoftbumdesku.passenger.json.TopUpAyoPulsaRequestJson;
import com.insoftbumdesku.passenger.json.TopUpAyoPulsaResponseModel;
import com.insoftbumdesku.passenger.json.TopUpBaseResponse;
import com.insoftbumdesku.passenger.json.TopUpRequestResponse;
import com.insoftbumdesku.passenger.json.TopupRequestJson;
import com.insoftbumdesku.passenger.json.TopupResponseJson;
import com.insoftbumdesku.passenger.json.WalletRequestJson;
import com.insoftbumdesku.passenger.json.WalletResponseJson;
import com.insoftbumdesku.passenger.json.WithdrawRequestJson;
import com.insoftbumdesku.passenger.json.MobileTopUpResponseModel;
import com.insoftbumdesku.passenger.json.XenditPaymentRequestJson;
import com.insoftbumdesku.passenger.models.ayopulsa.PriceListDataModel;
import com.insoftbumdesku.passenger.models.ayopulsa.TopUpStatusModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface UserService {

    @POST("pelanggan/fcmkey")
    Call<FcmKeyResponseJson> fcmgetkey(@Body FcmRequestJson param);

    @POST("pelanggan/sendlocation")
    Call<SendLocationResponseJson> sendlocation(@Body SendLocationRequestJson param);

    @POST("driver/daftarwilayah")
    Call<DaftarWilayahResponseJson> daftarwilayah(@Body DaftarWilayahRequestJson param);

    @POST("pelanggan/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("pelanggan/kodepromo")
    Call<PromoResponseJson> promocode(@Body PromoRequestJson param);

    @POST("pelanggan/listkodepromo")
    Call<PromoResponseJson> listpromocode(@Body PromoRequestJson param);

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("pelanggan/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("pelanggan/register_user")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @GET("pelanggan/detail_fitur")
    Call<GetFiturResponseJson> getFitur();

    @POST("pelanggan/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("pelanggan/home")
    Call<GetHomeResponseJson> home(@Body GetHomeRequestJson param);

    @POST("pelanggan/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("pelanggan/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("pelanggan/hitungpoin")
    Call<ResponseJson> hitungpoin(@Body PoinRequestJson param);

    @POST("pelanggan/rate_driver")
    Call<RateResponseJson> rateDriver(@Body RateRequestJson param);

    @POST("pelanggan/edit_profile")
    Call<RegisterResponseJson> editProfile(@Body EditprofileRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("pelanggan/history_progress")
    Call<AllTransResponseJson> history(@Body DetailRequestJson param);

    @POST("pelanggan/detail_berita")
    Call<BeritaDetailResponseJson> beritadetail(@Body BeritaDetailRequestJson param);

    @POST("pelanggan/all_berita")
    Call<BeritaDetailResponseJson> allberita(@Body BeritaDetailRequestJson param);

    @POST("pelanggan/merchantbykategoripromo")
    Call<MerchantByCatResponseJson> getmerchanbycat(@Body GetMerchantbyCatRequestJson param);

    @POST("pelanggan/merchantbykategori")
    Call<MerchantByNearResponseJson> getmerchanbynear(@Body GetMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchantbykategori")
    Call<AllMerchantByNearResponseJson> getallmerchanbynear(@Body GetAllMerchantbyCatRequestJson param);

    @POST("pelanggan/itembykategori")
    Call<MerchantByIdResponseJson> getitembycat(@Body GetAllMerchantbyCatRequestJson param);

    @POST("pelanggan/searchmerchant")
    Call<AllMerchantByNearResponseJson> searchmerchant(@Body SearchMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchant")
    Call<AllMerchantByNearResponseJson> allmerchant(@Body AllMerchantbyCatRequestJson param);

    @POST("pelanggan/merchantbyid")
    Call<MerchantByIdResponseJson> merchantbyid(@Body MerchantbyIdRequestJson param);

    @POST()
    Call<MobileTopUpResponseModel> getAllMobileTopUpType(@Url String url, @Body MobileTopUpRequestModel param);

    @POST()
    Call<MobilePulsaPLNCheckResponse> checkMobilePulsaPlnSubscriber(@Url String url, @Body MobileTopUpRequestModel param);

    @Headers({"Content-Type: application/json"})
    @POST("https://mobilepulsa.net/api/v1/bill/check")
    Call<MobilePulsaHealthBPJSBaseResponse> checkBPJSKesSubscriber(@Body MobileTopUpRequestModel param);

    @POST()
    Call<TopUpRequestResponse> requestTopUp(@Url String url, @Body MobileTopUpRequestModel param);

    @Headers({"Content-Type: application/json"})
    @POST()
    Call<TopUpBaseResponse> requestTopUpWithBaseResponse(@Url String url, @Body MobileTopUpRequestModel param);

    @Headers({"Content-Type: application/json"})
    @POST("https://mobilepulsa.net/api/v1/bill/check")
    Call<MobileTopUpPostPaidStatusJson> checkPostPaidStatus(@Body MobileTopUpRequestModel param);

    @POST("http://share.karinesia.com/api/xendit/data_post")
    Call<ResponseBody> xenditPaymentRequest(@Body XenditPaymentRequestJson param);

    @GET("https://ayo-pesan.com/api/v1/prabayar/pulsa/operator")
    Call<GetAyoPulsaBaseResponse> getAyoPesanPulsaAndPaketDataPriceList(@Header("Authorization") String header);

    @GET("https://ayo-pesan.com/api/v1/prabayar/listrik/nominal")
    Call<PriceListDataModel> getAyoPesanPlnPriceList(@Header("Authorization") String header);

    @POST("https://ayo-pesan.com/api/v1/prabayar/pulsa/topup")
    Call<TopUpAyoPulsaResponseModel> ayoPesanTopUp(@Body TopUpAyoPulsaRequestJson params, @Header("Authorization") String header);

    @GET
    Call<TopUpStatusModel> checkAyoPesanStatusPayment(@Url String url, @Header("Authorization") String header);

}
