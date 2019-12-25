package tech.hongjian.spider.recommend.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hongjian.spider.recommend.entity.Actor;
import tech.hongjian.spider.recommend.entity.Recommend;
import tech.hongjian.spider.recommend.entity.Video;
import tech.hongjian.spider.recommend.entity.enums.Platform;
import tech.hongjian.spider.recommend.entity.enums.VideoType;
import tech.hongjian.spider.recommend.service.RecommendService;

/**
 * @author xiahongjian
 * @time 2019-12-18 18:51:59
 */
@Service
public class QQRecommendParser extends BaseRecommendParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(QQRecommendParser.class);
    private static final String MAIN_DOMAIN = "v.qq.com";
    private static final String INDEX_URL = "https://" + MAIN_DOMAIN;

    @Autowired
    private RecommendService recommendService;

    @Override
    public void parse() {
        boolean isFirst = true;
        Elements navs;
        try {
            navs = queryElements(getIndexUrl(), "div.slider_nav a");
        } catch (IOException e) {
            LOGGER.warn("Failed to parse index page recommends.", e);
            return;
        }
        for (Element e : navs) {
            // 跳过第一条
            if (isFirst) {
                isFirst = false;
                continue;
            }
            Recommend recommend = new Recommend();
            recommend.setPlatform(getPlatform());
            String[] items = e.attr("_stat").split(":");
            String name = items[3];
            String url = processUrl(e.attr("href"));
            LOGGER.info("[Recommend-{}] name: {}, URL: {}", getPlatform(), name, url);
            recommend.setIndex(Integer.valueOf(items[2]));
            try {
                Video video = getVideoInfo(url, name);
                recommend.setVideo(video);
            } catch (Exception exception) {
                LOGGER.warn("Failed to parse page, URL: {}.", url, exception);
            }
            recommendService.saveParsedData(recommend);
        }
    }

    private Document featchVideoPage(String url) throws IOException {
        Document doc = getDocument(url);
        String frameSrouce = getFrameSource(doc);
        return StringUtils.isNotBlank(frameSrouce) ? getDocument(frameSrouce) : doc;
    }


    private String getFrameSource(Document doc) {
        return processUrl(doc.select(".B_Video frame").attr("src"));
    }

    private Video getVideoInfo(String url, String name) throws IOException {
        Video video = new Video();
        Document doc = featchVideoPage(url);
        Element title = doc.select(".player_title a").first();
        if (title != null) {
            featchDetail(video, processUrl(title.attr("href")));
        } else {
            if (url.contains("/live")) {
                video.setType(VideoType.LIVE);
            } else {
                video.setType(videoType(video.getName(), null));
            }
        }
        return video;
    }

    private void featchDetail(Video video, String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        String title = doc.select(".video_title_cn a").text();
        String type = doc.select(".video_title_cn .type").text();
        String desc = doc.select(".video_desc span.desc_txt span").text();
        Elements actors = doc.select(".video_actor .actor_list .item .name");
        List<Actor> actorList = new ArrayList<>(actors.size());
        for (Element actor : actors) {
            if (StringUtils.isBlank(actor.text())) {
                continue;
            }
            Actor a = new Actor();
            a.setName(actor.text());
            actorList.add(a);
        }
        if (StringUtils.isNotBlank(title)) {
            video.setName(title);
        }
        video.setType(videoType(video.getName(), type));
        video.setActors(actorList);
        video.setDescription(desc);
    }

    @Override
    public String getIndexUrl() {
        return INDEX_URL;
    }

    @Override
    public String getMainDomain() {
        return MAIN_DOMAIN;
    }

    @Override
    public Platform getPlatform() {
        return Platform.QQ;
    }
}
