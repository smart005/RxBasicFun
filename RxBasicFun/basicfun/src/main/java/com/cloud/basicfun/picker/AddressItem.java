package com.cloud.basicfun.picker;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/22
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Entity(nameInDb = "rx_address_items")
public class AddressItem {
    /**
     * 地址id
     */
    @Property(nameInDb = "id")
    private String id = "";
    /**
     * 地址名称
     */
    @Property(nameInDb = "name")
    private String name = "";
    /**
     * 父id
     */
    @Property(nameInDb = "parentId")
    private String parentId = "";

    @Generated(hash = 1274811449)
    public AddressItem(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    @Generated(hash = 896359941)
    public AddressItem() {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
