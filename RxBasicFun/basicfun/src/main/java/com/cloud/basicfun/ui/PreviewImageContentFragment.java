package com.cloud.basicfun.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloud.basicfun.BaseFragment;
import com.cloud.basicfun.R;
import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;
import com.cloud.resources.PicasaView;
import com.cloud.resources.RedirectUtils;
import com.cloud.resources.beans.BaseImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/14
 * @Description:图片预览视图
 * @Modifier:
 * @ModifyContent:
 */
public class PreviewImageContentFragment extends BaseFragment {

    private PicasaView imgViewerPv;
    private List<BaseImageItem> imgUrls = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview_image_content_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        try {
            imgViewerPv = (PicasaView) findViewById(R.id.img_viewer_pv);
            int position = getIntBundle("POSITION");
            String imgUrlsJson = getStringBundle("IMG_URLS", "[]");
            imgUrls = JsonUtils.parseArray(imgUrlsJson, BaseImageItem.class);
            if (ObjectJudge.isNullOrEmpty(imgUrls)) {
                imgUrls = new ArrayList<BaseImageItem>();
                String sigImgUrlJson = getStringBundle("IMG_URL");
                BaseImageItem imageItem = JsonUtils.parseT(sigImgUrlJson, BaseImageItem.class);
                imgUrls.add(imageItem);
            }
            imgViewerPv.setImgUrls(imgUrls);
            imgViewerPv.setCurrentPosition(position);
            imgViewerPv.setDisplayDotView(getBooleanBundle("DISPLAY_DOT_VIEW"));
            imgViewerPv.setOnPhotoViewClickListener(photoViewClickListener);
            imgViewerPv.lazyLoad();
        } catch (Exception e) {
            Logger.L.error("preview image init error:", e);
        }
    }

    private PicasaView.OnPhotoViewClickListener photoViewClickListener = new PicasaView.OnPhotoViewClickListener() {
        @Override
        public void onPhotoViewClick() {
            RedirectUtils.finishActivity(getActivity());
        }

        @Override
        public void onOutsidePhotoTap() {
            RedirectUtils.finishActivity(getActivity());
        }
    };
}
