Activity或Fragment继承的基类——公用方法
---
###### 0.数据分页请求属性、变量
```java
/**
 * 当前分页索引,当加载更多时对此索引加1表示加载下一页数据
 */
protected int currPageIndex = 1;

/**
 * 将分页索引currPageIndex进行初始化设置为1
 *
 * @return
 */
protected int getCurrPageIndex()
```
###### 1.广播接收
```java
protected void receiveRSCResult(Intent intent)

protected void receiveRSCResult(Intent intent, String action)
```
###### 2.版本检测
```java
æ 自动调用：只要在RxConfig下设置setUpdateActivityNamesResId(resId)即可自动检测;
	resId形式:
	<string-array name="updateActivityNames">
        <item>Main</item>
    </string-array>
æ 手动调用：
	VersionUpdateProperties properties = new VersionUpdateProperties();
	//提供activity的上下文
    properties.setActivity();
    //app应用图标
    properties.setAppIcon();
    //设置是否需要自动检测更新(一般手动调用设置为false)
    properties.setIsAutoUpdate(false);
    //设置是否需要检测更新提醒
    properties.setIsCheckUpdatePrompt(false);
    //设置请求更新信息的url地址
    properties.setCheckUpdateUrl();
    //检测更新
    mUBll.checkVersionUpdate(properties);
```
###### 3.Bundle对象,下面提供的方法取值已做空值和containsKey判断
```java
/**
 * 获取bundle对象
 *
 * @return
 */
public Bundle getBundle()

/**
 * 从bundle中获取字符串
 *
 * @param bundle       bundle对象
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public String getStringBundle(Bundle bundle, String key, String defaultValue)

/**
 * 从bundle中获取字符串
 *
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public String getStringBundle(String key, String defaultValue)

/**
 * 从bundle中获取字符串
 *
 * @param key
 * @return
 */
public String getStringBundle(String key)

/**
 * 从bundle中获取int值
 *
 * @param bundle       bundle对象
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public int getIntBundle(Bundle bundle, String key, int defaultValue)

/**
 * 从bundle中获取int值
 *
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public int getIntBundle(String key, int defaultValue)

/**
 * 从bundle中获取int值
 *
 * @param key
 * @return
 */
public int getIntBundle(String key)

/**
 * 从bundle中获取boolean值
 *
 * @param bundle       bundle对象
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public boolean getBooleanBundle(Bundle bundle, String key, boolean defaultValue)

/**
 * 从bundle中获取boolean值
 *
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public boolean getBooleanBundle(String key, boolean defaultValue)

/**
 * 从bundle中获取boolean值
 *
 * @param key
 * @return
 */
public boolean getBooleanBundle(String key)

/**
 * 从bundle中获取object值
 *
 * @param bundle       bundle对象
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public Object getObjectBundle(Bundle bundle, String key, Object defaultValue)

/**
 * 从bundle中获取object值
 *
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public Object getObjectBundle(String key, Object defaultValue)

/**
 * 从bundle中获取object值
 *
 * @param key
 * @return
 */
public Object getObjectBundle(String key)

/**
 * 从bundle中获取float值
 *
 * @param bundle       bundle对象
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public float getFloatBundle(Bundle bundle, String key, float defaultValue)

/**
 * 从bundle中获取float值
 *
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public float getFloatBundle(String key, float defaultValue)

/**
 * 从bundle中获取float值
 *
 * @param key
 * @return
 */
public float getFloatBundle(String key)

/**
 * 从bundle中获取double值
 *
 * @param bundle       bundle对象
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public double getDoubleBundle(Bundle bundle, String key, double defaultValue)

/**
 * 从bundle中获取double值
 *
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public double getDoubleBundle(String key, double defaultValue)

/**
 * 从bundle中获取double值
 *
 * @param key
 * @return
 */
public double getDoubleBundle(String key)

/**
 * 从bundle中获取long值
 *
 * @param bundle       bundle对象
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public long getLongBundle(Bundle bundle, String key, long defaultValue)

/**
 * 从bundle中获取long值
 *
 * @param key
 * @param defaultValue 默认值
 * @return
 */
public long getLongBundle(String key, long defaultValue)

/**
 * 从bundle中获取long值
 *
 * @param key
 * @return
 */
public long getLongBundle(String key)
```
###### 4.启动Activity
```java
/**
 * 获取bundle map集合
 * 调用方法startActivity时传入的参数
 *
 * @return
 */
protected static BundleMap getBundleMap()

/**
 * 启动activity
 *
 * @param activity  提供上下文的activity
 * @param cls       要启动的activity类对象
 * @param paramsMap 参数集合,一般配合getBundleMap()使用
 */
protected static void startActivity(Activity activity, Class<?> cls, BundleMap paramsMap)
```