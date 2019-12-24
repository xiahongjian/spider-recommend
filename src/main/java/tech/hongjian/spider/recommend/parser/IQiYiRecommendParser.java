package tech.hongjian.spider.recommend.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
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
 * @time 2019-12-21 13:08:01
 */
@Service
public class IQiYiRecommendParser extends BaseRecommendParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(IQiYiRecommendParser.class);
    private static final String MAIN_DOMAIN = "www.iqiyi.com";
    private static final String INDEX_URL = "https://" + MAIN_DOMAIN;

    @Autowired
    private RecommendService recommendService;

    @Override
    public void parse() {
        Elements navs;
        try {
            navs = queryElements(getIndexUrl(), ".side-list .side-item a");
        } catch (IOException e) {
            LOGGER.warn("Failed to parse index page recommends.", e);
            return;
        }
        int index = 0;
        for (Element e : navs) {
            index++;

            String url = e.attr("href");
            String name = e.select("span.side-title").text();
            LOGGER.info("[Recommend-{}] name: {}, URL: {}", getPlatform(), name, url);
            Recommend recommend = new Recommend();
            recommend.setIndex(index);
            recommend.setPlatform(getPlatform());
            try {
                Video v = getVideoInfo(processUrl(url), name);
                recommend.setVideo(v);
            } catch (Exception exception) {
                LOGGER.warn("Failed to parse page, URL: {}", processUrl(url), exception);
            }
            recommendService.saveParsedData(recommend);
        }

    }

    private Video getVideoInfo(String url, String name) throws IOException {
        Video v = new Video();
        v.setName(name);
        if (StringUtils.isBlank(url)) {
            return v;
        }
        Document doc = getDocument(url);
        String detailUrl = doc.select(".player-title a.title-link").attr("href");
        // 没有详情页面链接则在当前页面查找Video相关信息
        if (StringUtils.isBlank(detailUrl)) {
            return getCurrentPageDetail(doc, v);
        }
        return featchDetail(detailUrl, v);
    }

    private Video getCurrentPageDetail(Document doc, Video v) {
        v.setType(videoType(v.getName(), null));
        Elements eles = doc.select(".intro-right .intro-detail .intro-detail-item");
        for (Element e : eles) {
            String title = StringUtils.trim(e.select(".item-title").text());
            if (title != null && title.contains("主演") || title.contains("嘉宾")) {
                List<Actor> actors = new ArrayList<>();
                e.select(".item-content .name-wrap").forEach(createActor(actors));
                v.setActors(actors);
                continue;
            }
            if (title != null && title.contains("简介")) {
                v.setDescription(e.select(".item-content").text());
            }
        }
        return v;
    }

    private Video featchDetail(String url, Video v) throws IOException {
        if (StringUtils.isBlank(url)) {
            return v;
        }
        Document doc = getDocument(url);
        Elements introduce = doc.select(".info-intro");
        if (!introduce.isEmpty()) {
            withIntroduceInfo(introduce, v);
        } else {
            withAblumInfo(doc, v);
        }
        VideoType type = videoType(v.getName(), doc.select("a.channelTag").text());
        List<Actor> actors = getActors(doc);
        v.setType(type);
        v.setActors(actors);
        return v;
    }

    private Video withAblumInfo(Document doc, Video v) {
        String title = doc.select(".album-head-title title-link").text();
        if (StringUtils.isNotBlank(title)) {
            v.setName(title);
        }
        String desc = doc.select(".album-head-des").attr("title");
        v.setDescription(desc);
        return v;
    }

    private Video withIntroduceInfo(Elements introduce, Video v) {
        String title = introduce.select(".info-intro-title").text();
        if (StringUtils.isNotBlank(title)) {
            v.setName(title);
        }
        String desc = getDescription(introduce);
        v.setDescription(desc);
        return v;
    }

    private List<Actor> getActors(Document doc) {
        List<Actor> actors = new ArrayList<>();
        doc.select(".actor-list a").forEach(createActor(actors));
        return actors;
    }

    private Consumer<Element> createActor(List<Actor> actors) {
        return (e) -> {
            String name = e.text();
            if (StringUtils.isNotBlank(name)) {
                Actor actor = new Actor();
                actor.setName(name.endsWith("/") ? name.substring(0, name.length() - 1) : name);
                actors.add(actor);
            }
        };
    }

    private String getDescription(Elements parent) {
        if (parent.isEmpty()) {
            return null;
        }
        String desc = parent.select(".episodeIntro-brief[data-moreorless=moreinfo] .briefIntroTxt")
                .text();
        if (StringUtils.isBlank(desc)) {
            desc = parent.select(".episodeIntro-brief[data-moreorless=lessinfo] .briefIntroTxt")
                    .text();
        }
        if (StringUtils.isBlank(desc)) {
            desc = parent.select(".episodeIntro").text();
        }
        return desc;
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
        return Platform.IQIYI;
    }

}
