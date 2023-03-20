package com.antar.passenger.utils.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.json.MobilePulsaHealthBPJSBaseResponse;
import com.antar.passenger.json.MobilePulsaPLNCheckResponse;
import com.antar.passenger.json.MobileTopUpPostPaidStatusJson;
import com.antar.passenger.json.MobileTopUpRequestModel;
import com.antar.passenger.json.MobileTopUpResponseModel;
import com.antar.passenger.json.ResponseJson;
import com.antar.passenger.json.TopUpBaseResponse;
import com.antar.passenger.json.WithdrawRequestJson;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.ProjectUtils;
import com.antar.passenger.utils.SettingPreference;
import com.antar.passenger.utils.api.service.ApiListener;
import com.antar.passenger.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobilePulsaApiHelper {
    public static Call<MobileTopUpResponseModel> getMobilePulsaPriceList(String url, String sign, String command, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(sign);
        request.setCommand(command);
        request.setStatus("all");
        UserService service = ServiceGenerator.createService(UserService.class);
        return service.getAllMobileTopUpType(url, request);
    }

    public static Call<MobilePulsaPLNCheckResponse> checkPLNSubscriber(String url, String sign, String command, String hp,
                                                                       SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(sign);
        request.setCommand(command);
        request.setDestinationPhoneNumber(hp);
        UserService service = ServiceGenerator.createService(UserService.class);
        return service.checkMobilePulsaPlnSubscriber(url, request);
    }

    public static Call<MobilePulsaHealthBPJSBaseResponse> checkHealthBPJS(String destinationNumber, int numberMonthToPay,
                                                                          SettingPreference sp) {
        String refId = ProjectUtils.createTransactionID();
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(refId,sp)));
        request.setCode("BPJS");
        request.setCommand("inq-pasca");
        request.setOrderId(refId);
        request.setMonth(String.valueOf(numberMonthToPay));
        request.setDestinationPhoneNumber(destinationNumber);
        UserService service = ServiceGenerator.createService(UserService.class);
        return service.checkBPJSKesSubscriber(request);
    }

    public static Call<MobilePulsaHealthBPJSBaseResponse> payBPJSKes(String inquiryId, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(inquiryId,sp)));
        request.setTransferId(inquiryId);
        request.setCommand("pay-pasca");
        UserService service = ServiceGenerator.createService(UserService.class);
        return service.checkBPJSKesSubscriber(request);
    }

    public static Call<TopUpBaseResponse> topUpRequestToken(String hp, String pulsaCode, SettingPreference sp) {
        String refId = ProjectUtils.createTransactionID();
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);

        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(refId,sp)));
        request.setCommand("topup");
        request.setDestinationPhoneNumber(hp);
        request.setPhoneCreditCode(pulsaCode);
        request.setOrderId(refId);
        UserService service = ServiceGenerator.createService(UserService.class);
        return service.requestTopUpWithBaseResponse(Constants.MOBILEPULSA_PRODUCTION_URL, request);
    }

    public static Call<TopUpBaseResponse> checkPrepaidStatus(String refId, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa(refId,sp)));
        request.setCommand("inquiry");
        request.setOrderId(refId);
        UserService service = ServiceGenerator.createService(UserService.class);
        return service.requestTopUpWithBaseResponse(Constants.MOBILEPULSA_PRODUCTION_URL, request);
    }

    public static Call<MobileTopUpPostPaidStatusJson> checkPostPaidStatus(String refId, SettingPreference sp) {
        MobileTopUpRequestModel request = new MobileTopUpRequestModel();
        request.setUsername(sp.getSetting()[7]);
        request.setSign(ProjectUtils.md5(ProjectUtils.generateSignInMobilePulsa("cs",sp)));
        request.setCommand("checkstatus");
        request.setOrderId(refId);
        UserService service = ServiceGenerator.createService(UserService.class);
        return service.checkPostPaidStatus(request);
    }

    public static void trackUserTopUp(String totalAmount, String operator, String idNumber, Context context, SettingPreference sp,
                                      ApiListener listener) {
        final User user = BaseApp.getInstance(context).getLoginUser();

        int amount = Integer.parseInt(totalAmount) + 600;
        WithdrawRequestJson request = new WithdrawRequestJson();
        request.setId(user.getId());
        request.setBank(operator);
        request.setCard(idNumber);
        request.setName(user.getFullnama());
        request.setAmount(String.valueOf(amount).replace(".", "").replace(sp.getSetting()[0], ""));
        request.setNotelepon(user.getNoTelepon());
        request.setEmail(user.getEmail());
        request.setType("withdraw");

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.withdraw(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listener.onSuccess();
                    } else {
                        listener.onError();
                    }
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                listener.onError();
            }
        });
    }
}
