package com.wills.help.home.view;

import com.wills.help.home.model.Banner;
import com.wills.help.home.model.News;

import java.util.List;

/**
 * com.wills.help.home.view
 * Created by lizhaoyong
 * 2017/1/6.
 */

public interface HomeView {
    void setBanner(List<Banner.BannerInfo> banner);
    void setNews(List<News.NewsInfo> news);
}
