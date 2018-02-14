Easypermissions授权
----
###### 1.相关文档
[官方文档](https://github.com/googlesamples/easypermissions)
[运行时权限官方文档解释](https://developer.android.com/training/permissions/requesting.html)
###### 2.什么情况下使用
```java
在Android 6.0之后部分授权需要动态去获取，很多时候这句会让人产生误解；
其实最终决定是否需要用动态权限获取，是我们在打包的时候targetSdkVersion的值决定的;

æ 如果你的app targetSdkVersion>=23时，那么需要动态去获取；
  反之只需要在AndroidManifest.xml配置即可;
æ 如果你的应用中AndroidManifest.xml中设置的targetSdkVersion<23，
  部分功能做兼容在代码里标注的TargetApi(>=23),此时只需要对标注有TargetApi地方做权限请求即可;

(Easypermissions在框架中已加入无需再引用,具体使用方式可参考官方文档;)
注：使用时相关页面基类需要继承BaseActivity、BaseAppCompatActivity、BaseFragmentActivity和BaseFragment
```
###### 3.下面给出使用例子
```java
//检测有没有权限
if (EasyPermissions.hasPermissions(this,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_CONTACTS)) {
    //has permision
} else {
    //no permision
    //没有权限则请求权限提示用户
    EasyPermissions.requestPermissions(
            this,
            "获取联系人权限",
            RC_LOCATION_CONTACTS_PERM,
            LOCATION_AND_CONTACTS);
}

//添加权限请求结果处理回调(此处不需要额外处理)
@Override
public void onRequestPermissionsResult(int requestCode,
                                       String[] permissions,
                                       int[] grantResults) {
    EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this);
}

@Override
public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    //申请成功
}

@Override
public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    //申请失败,提示用户是否转向设置页面
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
        new AppSettingsDialog.Builder(this).build().show();
    }
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
        //其中requestCode是上面权限请求时传入的
        //app setting界面权限设置完成后回调
        //如果AppSettingsDialog.Builder未调用的话,那么此回调也可以不需要
    }
}
```
###### 4.AndroidManifest.xml需要注册以下信息
```xml
<activity
android:name="com.cloud.basicfun.permissions.AppSettingsDialogHolderActivity"
android:exported="false"
android:label=""
android:theme="@style/EasyPermissions.Transparent"/>
```
```java
具体需要用到哪些权限可以从Manifest.permission.*下面取;
```