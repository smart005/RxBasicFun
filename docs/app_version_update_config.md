APP版本更新配置
----
```java
在项目的assets目录下新建文件324cbc6f28014d49bcf0c0a62afde699.json
注意文件名不可更改!!!
```
###### 1.效果图

###### 2.使用
```java
æ 在全局配置中添加以下配置项
"appVersionCheck": {
    "state": true,
    "urlType": "UPDATE_INFO_URL"
}

æ 在项目的Application中创建获取更新信息接口url的监听
private OnConfigItemUrlListener configItemUrlListener = new OnConfigItemUrlListener() {
    @Override
    public String getUrl(String urlType) {
        String apiUrl = getBasicConfigBean().getApiUrl();
        if (TextUtils.equals(urlType, "UPDATE_INFO_URL")) {
            //返回完整地址
        }
        return "";
    }
};
@Override
public void onCreate() {
    //版本更新url监听
    this.addOrUpdateObjectValue("UPDATE_INFO_URL", configItemUrlListener);
}

æ 下载apk文件
/**
 * 下载apk
 *
 * @param updateInfo 版本更新信息
 */
public void downloadApk(UpdateInfo updateInfo)

/**
 * 如果版本检测中有不再提醒按钮或稍候更新按钮则点击后需要调用此方法
 */
public void intervalPromptInit()

/**
 * 在版本检测时需要调用此方法,用于一段时间不提醒;
 * 一般与intervalPromptInit一起使用
 *
 * @return true:继续版本更新流程;false:以return方式结束更新流程;
 */
public boolean intervalPromptCheck()
```

###### 3.属性说明
```json
//版本code(主要与gradle中的versionCode区分开来，避免因其它配置改变)
"versionCode": 1,

//版本名称(主要与gradle中的versionCode区分开来，避免因其它配置改变)
"versionName": "1.0.0",

"updatePrompt": {
	//提醒类型(即弹窗类型):NORMAL	
	//NORMAL:在可检测版本的页面基础上，每次打开页面都会提醒;
	//No_LONGER_DISPLAY:在可检测版本的页面基础上，根据设定的时间间隔来显示
    "type":"No_LONGER_DISPLAY",

    //更新检测的时间间隔,单位秒；当type=No_LONGER_DISPLAY时生效;若type为NORMAL时此属性值不生效;
    //同时如果点击稍候更新也按此时间来计算
    "timeInterval": 7200,

    //检测loading提示文本
    "checkLoading": "检测新版本...",

    //无网络时提醒
    "noNetworkPrompt": "没有可用的网络,请连接后再更新",

    //下载应用地址异常提醒
    "apkAddressErrorPrompt": "下载应用地址异常",

    //最新版本提醒
    "lastVersionPrompt": "已经是最新版本了"
}

//更新用户分隔符
"updateUsersSeparators": [',','|',' ',';'],

//参考第2步使用
"appVersionCheck": {
    //true:当打开updateCheckActivityNames下页面时检测;false:不检测;
    "state": true,
    "urlType": "UPDATE_INFO_URL"
},

//更新需要检测的Activity名称
"updateCheckActivityNames": [
    "Main",
    "DialogUpdateActivity",
    "NotificationUpdateActivity",
    "CustomDialogUpdateActivity",
    "CustomNotificationUpdateActivity"
],

//true:选择稍候更新或不再提醒且当前网络为wifi的情况下，
//当检测到新版本后先下载但不安装;待下载检测时用户点击"立即更新"进行安装;
//false:不检测
"silentDownload": true
```