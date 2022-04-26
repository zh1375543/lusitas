package com.wzx.tipcontent.bean;


import com.stx.xhb.xbanner.entity.BaseBannerInfo;

/**
 * on 2021/6/25
 *
 * @Author zhanghui
 * @Description
 */
public class imageDateBean implements BaseBannerInfo {


    private String url;

    public imageDateBean(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Object getXBannerUrl() {
        return null;
    }

    @Override
    public String getXBannerTitle() {
        return null;
    }
}
