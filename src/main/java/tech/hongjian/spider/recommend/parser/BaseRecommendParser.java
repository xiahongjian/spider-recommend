package tech.hongjian.spider.recommend.parser;
/** 
 * @author xiahongjian
 * @time   2019-12-21 16:24:38
 */

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import tech.hongjian.spider.recommend.entity.VideoTypeMapping;
import tech.hongjian.spider.recommend.entity.enums.VideoType;
import tech.hongjian.spider.recommend.repository.VideoTypeMappingRepository;

public abstract class BaseRecommendParser implements RecommendParser {
    @Autowired
    protected VideoTypeMappingRepository videoTypeMappingRepo;

    public Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).userAgent(USER_AGENT).get();
    }
    
    public Element queryElement(String url, String selector) throws IOException {
        return Jsoup.connect(url).userAgent(USER_AGENT).get().select(selector).first();
    }
    
    public Elements queryElements(String url, String selector) throws IOException {
        return Jsoup.connect(url).userAgent(USER_AGENT).get().select(selector);
    }
    
    public String processUrl(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        String url = path;
        if (url.startsWith("//")) {
            return "https:" + url;
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }
        if (!url.contains(getMainDomain())) {
            return getIndexUrl() + (url.startsWith("/") ? "" : "/") + path;
        }
        return url;
    }
    
    public VideoType videoType(String name, String type) {
        VideoTypeMapping mapping = videoTypeMappingRepo.findByVideoName(name);
        if (mapping != null) {
            type = mapping.getVideoType();
        }
        return VideoType.withCaption(type);
    }
}
