缩略图(图片)预览/删除/(自动)上传
----
![images](/images/image_del_upload.png)

###### 1.布局文件中添加控件
```xml
<com.cloud.basicfun.piceditors.ImageEditorView
    android:id="@+id/select_img_sev"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/white_color"
    app:iev_addBackgoundResource="@drawable/pic_add"
    app:iev_eachRowNumber="4"
    app:iev_isAddImage="true"
    app:iev_isAllowDel="true"
    app:iev_isAllowModify="true"
    app:iev_isAutoUploadImage="false"
    app:iev_maxImageCount="5" />
```
###### 2.控件初始化设置
```java
//设置上下文
selectImgSev.setActivity(this);
//请求阿里云oss文件上传接口;该接口地址由后端提供;
selectImgSev.setOssAssumeRoleUrl("");
//图片上传完成回调
selectImgSev.setOnUploadCompletedListener(new OnUploadCompletedListener() {
    @Override
    public void onUploadCompleted(TreeMap<Integer, String> uploadedUrls) {

    }
});
```
###### 3.图片选择需要添加onActivityResult
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    selectImgSev.onActivityResult(requestCode, resultCode, data);
}
```
###### 4.最后一步调用以下方法检查并上传图片
```java
selectImgSev.checkAndUploads();
```
*至此控件基本用法已完成，相关的属性、方法及事件用法请继续往下看.*
###### 控件属性
| 控件属性                     | 描述                       |
|------------------------------|----------------------------|
| app:iev_addBackgoundResource | 设置添加按钮默认图片       |
| app:iev_eachRowNumber        | 允许每一行显示的个数       |
| app:iev_isAddImage           | 是否允许添加图片           |
| app:iev_isAllowDel           | 选择图片后是否允许删除     |
| app:iev_isAllowModify        | 是否允许对已选择的图片修改 |
| app:iev_isAutoUploadImage    | 选择图片后是否允许自动上传 |
| app:iev_maxImageCount        | 允许选择最大图片数量       |
| app:iev_isAlignMiddle        | 预览图片的网格是否居中对齐 |
| app:iev_delImage             | 设置删除图标               |
| app:iev_delImageMarginLeft   | 设置删除图标左边距         |
| app:iev_delImageMarginTop    | 设置删除图标上边距         |
###### 方法属性
| 控件属性                                                       | 描述                                                          |
|----------------------------------------------------------------|---------------------------------------------------------------|
| setActivity(Activity activity)                                 | 设置当前活动Activity                                          |
| setOssAssumeRoleUrl(String ossAssumeRoleUrl)                   | 设置oss授权Url地址                                            |
| onActivityResult(int requestCode, int resultCode, Intent data) | 选择图片时通过该方法返回数据                                  |
| checkAndUploads()                                              | 检查并上传；如果是自动上传则对所有未上传完成的图片显示总进度; |
| setAddImage(boolean isAddImage)                                | 是否允许添加图片                                              |
| setAllowDel(boolean isAllowDel)                                | 设置是否允许删除                                              |
| setOnlyRead(boolean isOnlyRead)                                | 设置该控件只允许预览(不能修改和删除图片)                      |
###### 事件监听
```java
selectImgSev.setOnUploadCompletedListener(new OnUploadCompletedListener() {
    @Override
    public void onUploadCompleted(TreeMap<Integer, String> uploadedUrls) {
    	//上传完成回调
    }
});

selectImgSev.setOnReviewImageListener(new OnReviewImageListener() {
    @Override
    public void onReview(List<String> images, int position) {
        //点击某一张图片进行大图预览
        //images：所有图片集合
        //position：当前要预览图片的索引
    }
});
```


