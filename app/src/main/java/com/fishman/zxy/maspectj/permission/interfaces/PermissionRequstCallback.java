package com.fishman.zxy.maspectj.permission.interfaces;

public interface PermissionRequstCallback {
    void permissionSuccess();
    void permissionCancle();
    void permissionDenied();
}
