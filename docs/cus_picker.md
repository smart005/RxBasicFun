自定义选择器
----
###### 1.创建对象
```java
private CusPickerUtils cusPickerUtils = new CusPickerUtils() {
    @Override
    protected void onOptionsSelected(OptionsItem optionsItem, View v) {

    }
};
```
###### 2.构建builder对象并显示
```java
//构建选择器对象
OptionsPickerView.Builder builder = cusPickerUtils.getBuilder(this);
//创建选择项
List<OptionsItem> items = new ArrayList<OptionsItem>();
items.add(new OptionsItem("选项1"));
items.add(new OptionsItem("选项2"));
items.add(new OptionsItem("选项3"));
items.add(new OptionsItem("选项4"));
items.add(new OptionsItem("选项5"));
items.add(new OptionsItem("选项6"));
cusPickerUtils.setOptionsItems(items);
//显示视图
cusPickerUtils.show(builder, null);
```

*builder其它属性请参考[Android-PickerView](https://github.com/Bigkoo/Android-PickerView)*