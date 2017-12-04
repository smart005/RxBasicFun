地区选择器
----
###### 1.创建对象
```java
AddressPickerUtils pickerUtils = new AddressPickerUtils() {
    @Override
    protected void onAddressPicked(OptionsItem province, OptionsItem city, OptionsItem region, View v) {
    	//选择地区后回调
    }
};
```
###### 2.构建builder对象并显示
```java
//构建选择器
OptionsPickerView.Builder builder = pickerUtils.getBuilder(this);
//选择器标题
builder.setTitleText("城市选择");
//选择器颜色
builder.setDividerColor(Color.BLACK);
//当前选择项文本颜色
builder.setTextColorCenter(Color.BLACK);
//文本大小
builder.setContentTextSize(20);
//设置每一项是否具有滚轮效果
builder.setCyclic(true, false, false);
//显示省市区
pickerUtils.show(this, builder, "allarea.rx");
```

*builder其它属性请参考[Android-PickerView](https://github.com/Bigkoo/Android-PickerView)*