package com.cloud.basicfun.utils;

import com.cloud.core.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/18
 * @Description:获取或设置各数据类型
 * @Modifier:
 * @ModifyContent:
 */
public class BundleMap {

    private HashMap<String, Object> map = new HashMap<String, Object>();

    public void setInt(String key, int value) {
        map.put(key, value);
    }

    public void setFloat(String key, float value) {
        map.put(key, value);
    }

    public void setDouble(String key, double value) {
        map.put(key, value);
    }

    public void setObject(String key, Object value) {
        map.put(key, value);
    }

    public void setString(String key, String value) {
        map.put(key, value);
    }

    public <T> void setArrays(String key, List<T> value) {
        map.put(key, value);
    }

    public <T> void setArrays(String key, ArrayList<T> value) {
        map.put(key, value);
    }

    public <T> void setArrays(String key, T[] value) {
        map.put(key, value);
    }

    public HashMap<String, Object> getMap() {
        return this.map;
    }

    public int getInt(String key) {
        if (map.containsKey(key)) {
            return ConvertUtils.toInt(map.get(key));
        } else {
            return 0;
        }
    }

    public float getFloat(String key) {
        if (map.containsKey(key)) {
            return ConvertUtils.toFloat(map.get(key));
        } else {
            return 0;
        }
    }

    public double getDouble(String key) {
        if (map.containsKey(key)) {
            return ConvertUtils.toDouble(map.get(key));
        } else {
            return 0;
        }
    }

    public Object getObject(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    public String getString(String key) {
        if (map.containsKey(key)) {
            return ConvertUtils.toString(map.get(key));
        } else {
            return "";
        }
    }

    public <T> List<T> getList(String key) {
        if (map.containsKey(key)) {
            return (List<T>) map.get(key);
        } else {
            return null;
        }
    }

    public <T> ArrayList<T> getArrayList(String key) {
        if (map.containsKey(key)) {
            return (ArrayList<T>) map.get(key);
        } else {
            return null;
        }
    }

    public <T> T[] getArrays(String key) {
        if (map.containsKey(key)) {
            return (T[]) map.get(key);
        } else {
            return null;
        }
    }
}
