package com.cloud.basicfun.picker;

import com.cloud.resources.picker.model.IPickerViewData;

import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/9/27
 * @Description:省市区item
 * @Modifier:
 * @ModifyContent:
 */
public class OptionsItem implements IPickerViewData {
    private String id = "";
    private String name = "";

    private List<OptionsItem> children = null;

    public OptionsItem() {
    }

    public OptionsItem(String name) {
        this.name = name;
    }

    public OptionsItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OptionsItem> getChildren() {
        return children;
    }

    public void setChildren(List<OptionsItem> children) {
        this.children = children;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
