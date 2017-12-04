Activity或Fragment继承的基类——BaseFragment
----
```java
/**
 * 获取当前Activity对象
 *
 * @return
 */
public AppCompatActivity getActivity()
```

###### 1.实例
```java
/**
 * 实例Fragment对象
 *
 * @param t    要实例的Fragment类对象
 * @param args bundle对象
 * @param <T>
 * @return
 */
public static <T extends BaseFragment> T newInstance(T t, Bundle args)

/**
 * 实例Fragment对象
 *
 * @param t   要实例的Fragment类对象
 * @param <T>
 * @return
 */
public static <T extends BaseFragment> T newInstance(T t)
```
###### 2.回调
```java
/**
 * Fragment回调方法,在其它页面拿到当前Fragment对象后调用onFragmentCall时执行
 *
 * @param action    区分是哪一次回调
 * @param bundleMap BundleMap参数集合
 */
public void onFragmentCall(String action, BundleMap bundleMap)

/**
 * 使用{@link com.cloud.resources.hvlayout.HeaderTabsViewLayout}控件时,
 * Fragment需要通过此回调返回具体滚动的视图
 *
 * @return
 */
@Override
public View getScrollableView()
```