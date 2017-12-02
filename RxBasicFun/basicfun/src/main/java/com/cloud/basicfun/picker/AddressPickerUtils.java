package com.cloud.basicfun.picker;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;

import com.cloud.basicfun.daos.AddressItemDao;
import com.cloud.basicfun.daos.DaoManager;
import com.cloud.core.Action;
import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.resources.dialog.LoadingDialog;
import com.cloud.resources.picker.OptionsPickerView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/6
 * @Description:地区选择器
 * @Modifier:
 * @ModifyContent:
 */

public class AddressPickerUtils {

    private OptionsInitTask optionsInitTask = null;
    private UpdateTask updateTask = null;
    private LoadingDialog mloading = new LoadingDialog();
    private String province = null;
    private String city = null;
    private String region = null;
    private List<OptionsItem> optionsItems = new ArrayList<>();
    private List<ArrayList<OptionsItem>> options2Items = new ArrayList<>();
    private List<ArrayList<ArrayList<OptionsItem>>> options3Items = new ArrayList<>();
    private static AddressPickerUtils addressPickerUtils = null;

    public static AddressPickerUtils getInstance() {
        return addressPickerUtils == null ? addressPickerUtils = new AddressPickerUtils() : addressPickerUtils;
    }

    protected void onAddressPicked(OptionsItem province, OptionsItem city, OptionsItem region, View v) {

    }

    public OptionsPickerView.Builder getBuilder(Context context) {
        optionsItems.clear();
        options2Items.clear();
        options3Items.clear();
        OptionsPickerView.Builder builder = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                try {
                    OptionsItem province = new OptionsItem();
                    OptionsItem city = new OptionsItem();
                    OptionsItem region = new OptionsItem();
                    if (optionsItems.size() > options1) {
                        OptionsItem optionsItem = optionsItems.get(options1);
                        if (optionsItem != null) {
                            province.setId(optionsItem.getId());
                            province.setName(optionsItem.getName());
                        }
                    }
                    if (options2Items.size() > options1) {
                        ArrayList<OptionsItem> optionsItems = options2Items.get(options1);
                        if (!ObjectJudge.isNullOrEmpty(optionsItems) && optionsItems.size() > options2) {
                            OptionsItem optionsItem = optionsItems.get(options2);
                            if (optionsItem != null) {
                                city.setId(optionsItem.getId());
                                city.setName(optionsItem.getName());
                            }
                        }
                    }
                    if (options3Items.size() > options1) {
                        ArrayList<ArrayList<OptionsItem>> lists = options3Items.get(options1);
                        if (!ObjectJudge.isNullOrEmpty(lists) && lists.size() > options2) {
                            ArrayList<OptionsItem> optionsItems = lists.get(options2);
                            if (!ObjectJudge.isNullOrEmpty(optionsItems) && optionsItems.size() > options3) {
                                OptionsItem optionsItem = optionsItems.get(options3);
                                if (optionsItem != null) {
                                    region.setId(optionsItem.getId());
                                    region.setName(optionsItem.getName());
                                }
                            }
                        }
                    }
                    onAddressPicked(province, city, region, v);
                } catch (Exception e) {
                    Logger.L.error(e);
                }
            }
        });
        return builder;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void show(Context context,
                     OptionsPickerView.Builder builder,
                     String assetsFileName,
                     View v) {
        try {
            if (builder == null) {
                mloading.dismiss();
                return;
            }
            GlobalUtils.cancelTask(optionsInitTask);
            optionsInitTask = new OptionsInitTask(context, builder, assetsFileName, v);
            optionsInitTask.execute();
        } catch (Exception e) {
            mloading.dismiss();
            Logger.L.error(e);
        }
    }

    public void show(Context context,
                     OptionsPickerView.Builder builder,
                     String assetsFileName) {
        show(context, builder, assetsFileName, null);
    }

    private class OptionsInitTask extends AsyncTask<Void, Void, Void> {

        private OptionsPickerView.Builder builder = null;
        private Context context = null;
        private String assetsFileName = "";
        private View v = null;

        public OptionsInitTask(Context context,
                               OptionsPickerView.Builder builder,
                               String assetsFileName,
                               View v) {
            this.context = context;
            this.builder = builder;
            this.assetsFileName = assetsFileName;
            this.v = v;
        }

        @Override
        protected void onPreExecute() {
            mloading.showDialog(context, "正在初始化数据...", null);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            initData(context, assetsFileName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (builder == null) {
                    mloading.dismiss();
                    return;
                }
                OptionsPickerView build = builder.build();
                build.setPicker(optionsItems, options2Items, options3Items);
                if (v == null) {
                    build.show(true);
                } else {
                    build.show(v, true);
                }
                mloading.dismiss();
            } catch (Exception e) {
                mloading.dismiss();
                Logger.L.error(e);
            }
        }
    }

    private void initData(Context context, String assetsFileName) {
        if (!ObjectJudge.isNullOrEmpty(optionsItems)) {
            return;
        }
        if (!loadListInDb()) {
            saveToDb(context, assetsFileName);
        } else {
            GlobalUtils.cancelTask(updateTask);
            updateTask = new UpdateTask(context, assetsFileName);
            updateTask.execute();
        }
    }

    private class UpdateTask extends AsyncTask<Void, Void, Void> {

        private Context context = null;
        private String assetsFileName = "";

        public UpdateTask(Context context, String assetsFileName) {
            this.context = context;
            this.assetsFileName = assetsFileName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            updateToDb(context, assetsFileName);
            return null;
        }
    }

    private void updateToDb(Context context, String assetsFileName) {
        try {
            if (context == null || TextUtils.isEmpty(assetsFileName)) {
                return;
            }
            String json = StorageUtils.readAssetsFileContent(context, assetsFileName);
            if (TextUtils.isEmpty(json)) {
                return;
            }
            final List<OptionsItem> datalist = JsonUtils.parseArray(json, OptionsItem.class);
            if (ObjectJudge.isNullOrEmpty(datalist)) {
                return;
            }
            DaoManager.getInstance().getAddressItemDao(new Action<AddressItemDao>() {
                @Override
                public void call(AddressItemDao itemDao) {
                    QueryBuilder<AddressItem> builder = itemDao.queryBuilder();
                    List<AddressItem> list = builder.list();
                    HashMap<String, AddressItem> maps = new HashMap<String, AddressItem>();
                    if (!ObjectJudge.isNullOrEmpty(list)) {
                        for (AddressItem addressItem : list) {
                            maps.put(addressItem.getName(), addressItem);
                        }
                    }
                    List<AddressItem> insertList = new ArrayList<AddressItem>();
                    for (OptionsItem optionsItem : datalist) {
                        if (maps.containsKey(optionsItem.getName())) {
                            maps.remove(optionsItem.getName());
                        } else {
                            insertList.add(new AddressItem(optionsItem.getId(), optionsItem.getName(), "0"));
                        }
                        if (!ObjectJudge.isNullOrEmpty(optionsItem.getChildren())) {
                            for (OptionsItem item : optionsItem.getChildren()) {
                                if (maps.containsKey(item.getName())) {
                                    maps.remove(item.getName());
                                } else {
                                    insertList.add(new AddressItem(item.getId(), item.getName(), optionsItem.getId()));
                                }
                                if (!ObjectJudge.isNullOrEmpty(item.getChildren())) {
                                    for (OptionsItem regions : item.getChildren()) {
                                        if (maps.containsKey(regions.getName())) {
                                            maps.remove(regions.getName());
                                        } else {
                                            insertList.add(new AddressItem(regions.getId(), regions.getName(), item.getId()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    itemDao.insertInTx(insertList);
                    itemDao.deleteInTx(maps.values());
                }
            });
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void saveToDb(Context context, String assetsFileName) {
        try {
            if (context == null || TextUtils.isEmpty(assetsFileName)) {
                return;
            }
            String json = StorageUtils.readAssetsFileContent(context, assetsFileName);
            if (TextUtils.isEmpty(json)) {
                return;
            }
            final List<OptionsItem> datalist = JsonUtils.parseArray(json, OptionsItem.class);
            if (ObjectJudge.isNullOrEmpty(datalist)) {
                return;
            }
            DaoManager.getInstance().getAddressItemDao(new Action<AddressItemDao>() {
                @Override
                public void call(AddressItemDao itemDao) {
                    List<AddressItem> list = new ArrayList<>();
                    for (OptionsItem optionsItem : datalist) {
                        //添加省
                        optionsItems.add(new OptionsItem(optionsItem.getId(), optionsItem.getName()));
                        //该省的城市列表（第二级）
                        final ArrayList<OptionsItem> citylist = new ArrayList<>();
                        //该省的所有地区列表（第三极）
                        final ArrayList<ArrayList<OptionsItem>> provinceAreaList = new ArrayList<>();
                        list.add(new AddressItem(optionsItem.getId(), optionsItem.getName(), "0"));
                        if (!ObjectJudge.isNullOrEmpty(optionsItem.getChildren())) {
                            for (OptionsItem item : optionsItem.getChildren()) {
                                list.add(new AddressItem(item.getId(), item.getName(), optionsItem.getId()));
                                //添加城市
                                citylist.add(new OptionsItem(item.getId(), item.getName()));
                                //加载该城市所有地区
                                if (!ObjectJudge.isNullOrEmpty(item.getChildren())) {
                                    ArrayList<OptionsItem> cityAreaList = new ArrayList<>();
                                    for (OptionsItem regions : item.getChildren()) {
                                        list.add(new AddressItem(regions.getId(), regions.getName(), item.getId()));
                                        cityAreaList.add(new OptionsItem(regions.getId(), regions.getName()));
                                    }
                                    provinceAreaList.add(cityAreaList);
                                }
                            }
                        }
                        options2Items.add(citylist);
                        options3Items.add(provinceAreaList);
                    }
                    itemDao.insertInTx(list);
                }
            });
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void saveOrUpdateAssetsAddressToDb(Context context, String assetsFileName) {
        GlobalUtils.cancelTask(updateTask);
        updateTask = new UpdateTask(context, assetsFileName);
        updateTask.execute();
    }

    private boolean loadListInDb() {
        try {
            final boolean[] isLoadSuccess = {false};
            DaoManager.getInstance().getAddressItemDao(new Action<AddressItemDao>() {
                @Override
                public void call(AddressItemDao itemDao) {
                    QueryBuilder<AddressItem> builder = itemDao.queryBuilder();
                    QueryBuilder<AddressItem> where = builder.where(AddressItemDao.Properties.ParentId.eq("0"));
                    List<AddressItem> list = where.list();
                    if (!ObjectJudge.isNullOrEmpty(list)) {
                        isLoadSuccess[0] = true;
                        for (final AddressItem addressItem : list) {
                            //该省的城市列表（第二级）
                            final ArrayList<OptionsItem> citylist = new ArrayList<>();
                            //该省的所有地区列表（第三极）
                            final ArrayList<ArrayList<OptionsItem>> provinceAreaList = new ArrayList<>();
                            //添加省
                            optionsItems.add(new OptionsItem(addressItem.getId(), addressItem.getName()));
                            //加载该省的所有城市
                            QueryBuilder<AddressItem> cityBuilder = itemDao.queryBuilder();
                            QueryBuilder<AddressItem> cityWhere = cityBuilder.where(AddressItemDao.Properties.ParentId.eq(addressItem.getId()));
                            List<AddressItem> cityQueryList = cityWhere.list();
                            if (!ObjectJudge.isNullOrEmpty(cityQueryList)) {
                                for (AddressItem item : cityQueryList) {
                                    //添加城市
                                    citylist.add(new OptionsItem(item.getId(), item.getName()));
                                    //加载该城市所有地区
                                    ArrayList<OptionsItem> cityAreaList = new ArrayList<>();
                                    QueryBuilder<AddressItem> regionBuilder = itemDao.queryBuilder();
                                    QueryBuilder<AddressItem> regionWhere = regionBuilder.where(AddressItemDao.Properties.ParentId.eq(item.getId()));
                                    List<AddressItem> regionQueryList = regionWhere.list();
                                    for (AddressItem region : regionQueryList) {
                                        cityAreaList.add(new OptionsItem(region.getId(), region.getName()));
                                    }
                                    provinceAreaList.add(cityAreaList);
                                }
                            }
                            options2Items.add(citylist);
                            options3Items.add(provinceAreaList);
                        }
                    }
                }
            });
            return isLoadSuccess[0];
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return false;
    }
}
