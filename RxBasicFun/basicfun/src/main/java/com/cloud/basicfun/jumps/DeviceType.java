package com.cloud.basicfun.jumps;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/9
 * @Description:设备类型
 * @Modifier:
 * @ModifyContent:
 */
public enum DeviceType {

    Android(2),
    IOS(1);

    private int value = 0;

    private DeviceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
