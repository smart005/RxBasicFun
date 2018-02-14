Scheme Url使用
----
###### 1.在AndroidManifest.xml对要换起的Activity做如下配置[以下协议可自行定义]
```xml
<activity
    android:name=".ui.StartAppActivity"
    android:screenOrientation="portrait"
    android:theme="@style/StartAppTheme">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data
        	//对应的主机名(或域名)
            android:host="tenancy"
            //换起该app的协议
            android:scheme="mibaomer" />
    </intent-filter>
</activity>

其中StartAppTheme为：
<style name="StartAppTheme" parent="android:Theme.Light.NoTitleBar.Fullscreen">
    <item name="android:windowBackground">@drawable/splash_bg</item>
</style>
```
###### 2.新建SchemeConfig配置类并继承BaseSchemeConfig设置上面配置协议及主机名
```java
public class SchemeConfig extends BaseSchemeConfig {
    public SchemeConfig() {
        super.setScheme("mibaomer");
        super.setHost("tenancy");
    }
}

```
###### 3.建新StartAppActivity
```java
public class StartAppActivity extends BaseActivity {
    @Override
    protected boolean isInitStatistics() {
    	//当前类必须实现该方法并返回false,不然会导致循环调用
    	//表示禁用事件统计
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent intent = this.getIntent();
            if (intent == null) {
                goMain();
                return;
            }
            Uri data = intent.getData();
            GoPagerUtils.getInstance().startActivityByScheme(this,
                    data,
                    new SchemeConfig(),
                    new Action<GoConfigItem>() {
                        @Override
                        public void call(GoConfigItem goConfigItem) {
                            //跳转至相应的页面
                            //这里可自行配置；在下一版本将会提供此类跳转的统一配置;
                        }
                    },
                    new Action0() {
                        @Override
                        public void call() {
                            //跳转至app首页
                        }
                    },
                    new Action0() {
                        @Override
                        public void call() {
                        	//关闭当前页面
                            RedirectUtils.finishActivity(getActivity());
                        }
                    });
        } catch (Exception e) {
            Logger.L.error(e);
            //处理失败跳转至app首页
        }
    }
}
```
###### 4.scheme路径配置
```json
//节后回为上传
```
*经过以上4步从h5或其它app就可以通过scheme来打开对应的app页面了*