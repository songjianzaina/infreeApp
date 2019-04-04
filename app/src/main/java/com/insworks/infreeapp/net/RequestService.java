package com.insworks.infreeapp.net;

import cc.urowks.ulibrary.net.retrofit2.RetrofitManager;

/**
 * Created by songjian on 2019/4/3.
 */

public class RequestService {
    private static RequestService mInstance;

    public ApiService getRequestService() {
        return RetrofitManager.getInstance().getRetrofit().create(ApiService.class);
    }

    public static RequestService getInstance() {
        if (mInstance == null) {
            synchronized (RequestService.class) {
                if (mInstance == null) {
                    mInstance = new RequestService();
                }
            }
        }
        return mInstance;
    }
}
