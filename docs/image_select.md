图片选择组件
----

```doc
根据业务需要图片选择控件大致包含以下几项功能:
1.只拍照功能;
2.只拍照+裁剪功能;
3.只选择功能;
4.只选择+裁剪功能;
5.拍照+选择功能;
6.拍照+选择+裁剪功能;

以上操作输出的结果均是经过压缩的无须额外处理;
```

#### 功能介绍

###### 初始化
```doc
1.在gradle中添加 api 'com.github.lovetuzitong:MultiImageSelector:1.2' 引用
2.如果sdk版本<19的话需要注册以下activity
  <activity
        android:name="com.cloud.basicfun.cropimage.CropImageActivity"
        android:launchMode="singleTop"
        android:screenOrientation="portrait"
        android:theme="@style/activity_anim" />
    <activity
        android:name="me.nereo.multi_image_selector.MultiImageSelectorActivity"
        android:configChanges="orientation|screenSize" />
3.实例化图片选择控件
  private ImageSelectDialog imageSelectDialog = new ImageSelectDialog() {
     @Override
     protected void onSelectCompleted(List<SelectImageProperties> selectImageProperties, Object extra) {
			//这里是所有情况处理后的结果;
			//如果内部处理失败则返回selectImageProperties=null
     }
 };
4.添加onActivityResult回调处理
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     imageSelectDialog.onActivityResult(this, requestCode, resultCode, data);
 }
```

###### 只拍照功能
```java
imageSelectDialog.showTaking(this);
```
###### 只拍照+裁剪功能
```java
//true拍照完成后需要进行裁剪
imageSelectDialog.setTailoring(true);
imageSelectDialog.showTaking(this);
```
###### 只选择功能
```java
//压缩后最大图片大小为1024字节,可自定义
imageSelectDialog.setMaxFileSize(1024);
//图片最多选择几张
imageSelectDialog.setMaxSelectNumber(1);
//false选择图片不显示拍照选项;
imageSelectDialog.setShowTakingPictures(false);
//压缩后图片最大宽度
imageSelectDialog.setMaxImageWidth(720);
//压缩后图片最大高度
imageSelectDialog.setMaxImageHeight(1920);
imageSelectDialog.show(getActivity());
```
###### 只选择+裁剪功能
```java
//压缩后最大图片大小为1024字节,可自定义
imageSelectDialog.setMaxFileSize(1024);
//图片最多选择几张
imageSelectDialog.setMaxSelectNumber(1);
//false选择图片不显示拍照选项;
imageSelectDialog.setShowTakingPictures(false);
//压缩后图片最大宽度
imageSelectDialog.setMaxImageWidth(720);
//压缩后图片最大高度
imageSelectDialog.setMaxImageHeight(1920);
//true选择图片后进行裁剪
imageSelectDialog.setTailoring(true);
//裁剪框的最小宽、高
imageSelectDialog.setTailoringSize(200, 300);
//裁剪框的最大宽、高
imageSelectDialog.withMaxSize(400, 600);
//裁剪框宽、高比例
imageSelectDialog.withAspect(2, 3);
imageSelectDialog.show(getActivity());
```
###### 拍照+选择功能
```java
//压缩后最大图片大小为1024字节,可自定义
imageSelectDialog.setMaxFileSize(1024);
//图片最多选择几张
imageSelectDialog.setMaxSelectNumber(1);
//true选择图片的同时显示拍照选项
imageSelectDialog.setShowTakingPictures(true);
//压缩后图片最大宽度
imageSelectDialog.setMaxImageWidth(720);
//压缩后图片最大高度
imageSelectDialog.setMaxImageHeight(1920);
imageSelectDialog.show(getActivity());
```
###### 拍照+选择+裁剪功能
```java
//压缩后最大图片大小为1024字节,可自定义
imageSelectDialog.setMaxFileSize(1024);
//图片最多选择几张
imageSelectDialog.setMaxSelectNumber(1);
//false选择图片不显示拍照选项;
imageSelectDialog.setShowTakingPictures(true);
//压缩后图片最大宽度
imageSelectDialog.setMaxImageWidth(720);
//压缩后图片最大高度
imageSelectDialog.setMaxImageHeight(1920);
//true选择图片后进行裁剪
imageSelectDialog.setTailoring(true);
//裁剪框的最小宽、高
imageSelectDialog.setTailoringSize(200, 300);
//裁剪框的最大宽、高
imageSelectDialog.withMaxSize(400, 600);
//裁剪框宽、高比例
imageSelectDialog.withAspect(2, 3);
imageSelectDialog.show(getActivity());
```

