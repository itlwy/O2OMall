package com.lndroid.lndroidlib.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import rx.functions.Func1;

/**
 * Created by mac on 16/11/23.
 * 对结果进行通用结果类 反序列化
 */

public class GsonResultFunc<T> implements Func1<String, HttpResultModel<T>> {

    @Override
    public HttpResultModel<T> call(String resultStr) {
        Gson gson = new Gson();
        HttpResultModel<T> resultModel = gson.fromJson(resultStr, new TypeToken<HttpResultModel<T>>() {
        }.getType());
        return resultModel;
    }
}
