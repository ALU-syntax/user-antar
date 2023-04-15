package com.antar.passenger.utils.api.service;

import com.antar.passenger.json.AllMerchantByNearResponseJson;
import com.antar.passenger.json.AllMerchantbyCatRequestJson;
import com.antar.passenger.json.AllTransResponseJson;
import com.antar.passenger.json.BankResponseJson;
import com.antar.passenger.json.BeritaDetailRequestJson;
import com.antar.passenger.json.BeritaDetailResponseJson;
import com.antar.passenger.json.BuyVoucherRequestJson;
import com.antar.passenger.json.BuyVoucherResponse;
import com.antar.passenger.json.ChangePassRequestJson;
import com.antar.passenger.json.DaftarWilayahRequestJson;
import com.antar.passenger.json.DaftarWilayahResponseJson;
import com.antar.passenger.json.DetailRequestJson;
import com.antar.passenger.json.EditprofileRequestJson;
import com.antar.passenger.json.FcmKeyResponseJson;
import com.antar.passenger.json.FcmRequestJson;
import com.antar.passenger.json.GetAllMerchantbyCatRequestJson;
import com.antar.passenger.json.GetFiturResponseJson;
import com.antar.passenger.json.GetHomeRequestJson;
import com.antar.passenger.json.GetHomeResponseJson;
import com.antar.passenger.json.GetMerchantbyCatRequestJson;
import com.antar.passenger.json.GetAyoPulsaBaseResponse;
import com.antar.passenger.json.LoginRequestJson;
import com.antar.passenger.json.LoginResponseJson;
import com.antar.passenger.json.MerchantByCatResponseJson;
import com.antar.passenger.json.MerchantByIdResponseJson;
import com.antar.passenger.json.MerchantByNearResponseJson;
import com.antar.passenger.json.MerchantbyIdRequestJson;
import com.antar.passenger.json.MobilePulsaHealthBPJSBaseResponse;
import com.antar.passenger.json.MobilePulsaPLNCheckResponse;
import com.antar.passenger.json.MobileTopUpPostPaidStatusJson;
import com.antar.passenger.json.MobileTopUpRequestModel;
import com.antar.passenger.json.PoinRequestJson;
import com.antar.passenger.json.PrivacyRequestJson;
import com.antar.passenger.json.PrivacyResponseJson;
import com.antar.passenger.json.PromoRequestJson;
import com.antar.passenger.json.PromoResponseJson;
import com.antar.passenger.json.RateRequestJson;
import com.antar.passenger.json.RateResponseJson;
import com.antar.passenger.json.RegisterRequestJson;
import com.antar.passenger.json.RegisterResponseJson;
import com.antar.passenger.json.ResponseJson;
import com.antar.passenger.json.SearchMerchantbyCatRequestJson;
import com.antar.passenger.json.SendLocationRequestJson;
import com.antar.passenger.json.SendLocationResponseJson;
import com.antar.passenger.json.TopUpAyoPulsaRequestJson;
import com.antar.passenger.json.TopUpAyoPulsaResponseModel;
import com.antar.passenger.json.TopUpBaseResponse;
import com.antar.passenger.json.TopUpRequestResponse;
import com.antar.passenger.json.TopupRequestJson;
import com.antar.passenger.json.TopupResponseJson;
import com.antar.passenger.json.UserVoucherRequestJson;
import com.antar.passenger.json.UserVoucherResponseJson;
import com.antar.passenger.json.VoucherResponseJson;
import com.antar.passenger.json.WalletRequestJson;
import com.antar.passenger.json.WalletResponseJson;
import com.antar.passenger.json.WithdrawRequestJson;
import com.antar.passenger.json.MobileTopUpResponseModel;
import com.antar.passenger.json.XenditPaymentRequestJson;
import com.antar.passenger.models.VoucherModel;
import com.antar.passenger.models.ayopulsa.PriceListDataModel;
import com.antar.passenger.models.ayopulsa.TopUpStatusModel;

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

    @GET("pelanggan/voucherpromo")
    Call<VoucherResponseJson> getVoucherPromo();

    @POST("pelanggan/uservoucher")
    Call<UserVoucherResponseJson> userVoucher(@Body UserVoucherRequestJson param);

    @POST("pelanggan/buyvoucher")
    Call<BuyVoucherResponse> buyVoucher(@Body BuyVoucherRequestJson request);

}
