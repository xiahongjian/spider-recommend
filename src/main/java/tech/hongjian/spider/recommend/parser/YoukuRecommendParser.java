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
import tech.hongjian.spider.recommend.service.RecommendService;

/** 
 * @author xiahongjian
 * @time   2019-12-21 19:32:08
 */
@Service
public class YoukuRecommendParser extends BaseRecommendParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(YoukuRecommendParser.class);
    
    private static final String MAIN_DOMAIN = "v.youku.com";
    private static final String INDEX_URL = "https://www.youku.com";
    
    @Autowired
    private RecommendService recommendService;

    @Override
    public void parse() {
        try {
            Document doc = getDocument(getIndexUrl());
            LOGGER.debug("Doc: {}", doc.html());
            Elements navs = doc.select(".focusswiper_focus_nav .swiper-container .swiper-wrapper a");
            LOGGER.debug("Nav: {}", navs.html());
            
            int index = 0;
            for (Element e : navs) {
                index++;
                Recommend recommend = new Recommend();
                recommend.setIndex(index);
                recommend.setPlatform(getPlatform());
                
                String name = e.select("h2").text().substring(String.valueOf(index).length());
                String url = e.attr("href");
                Video v = getVideoInfo(processUrl(url), name);
                recommend.setVideo(v);
                recommendService.saveParsedData(recommend);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to parse page.", e);
        }

    }

    private Video getVideoInfo(String url, String name) throws IOException {
        Video v = new Video();
        v.setName(name);
        if (StringUtils.isBlank(url)) {
            return v;
        }
        
        Document doc = getDocument(url);
        String detailUrl = doc.select(".title-wrap h1 span a").attr("href");
        if (StringUtils.isBlank(detailUrl)) {
            detailUrl = doc.select(".tvinfo h2 a").attr("href");
        }
        return featchDetail(processUrl(detailUrl), v);
    }

    private Video featchDetail(String detailUrl, Video v) throws IOException {
        if (StringUtils.isBlank(detailUrl)) {
            return v;
        }
        Document doc = getDocument(detailUrl);
        Elements info = doc.select(".p-base");
        List<Actor> actors = new ArrayList<>();
        info.select(".p-performer a").forEach(createActor(actors));
        v.setActors(actors);
        getIntroduction(info, v);
        getVideoType(info, v);
        return v;
    }

    private Video getVideoType(Elements info, Video v) {
        String type = info.select(".p-title a[target]").text();
        v.setType(videoType(v.getName(), type));
        return v;
    }

    private Video getIntroduction(Elements parent, Video v) {
        String intro = parent.select(".p-intro span.intro-more").text();
        if (StringUtils.isBlank(intro)) {
            intro = parent.select(".p-intro span.text").text();
        }
        if (StringUtils.startsWith(intro, "简介：")) {
            intro = intro.substring(3);
        }
        v.setDescription(intro);
        return v;
    }

    private Consumer<? super Element> createActor(List<Actor> actors) {
        return e -> {
            String name = e.text();
            if (StringUtils.isBlank(name)) {
                return;
            }
            actors.add(new Actor(name));
        };
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
        return Platform.YOUKU;
    }

}
