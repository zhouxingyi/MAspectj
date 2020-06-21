package com.fishman.zxy.maspectj.uplodPoint.utils;

import java.io.IOException;

public interface HttpCallback <T> {
    // 成功返回
    void onSuccess(T t);

    //失败返回
    void onfailure(IOException e);
}
