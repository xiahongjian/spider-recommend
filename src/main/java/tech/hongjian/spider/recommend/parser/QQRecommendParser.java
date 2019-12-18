package tech.hongjian.spider.recommend.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tech.hongjian.spider.recommend.entity.Actor;
import tech.hongjian.spider.recommend.entity.Recommend;
import tech.hongjian.spider.recommend.entity.Video;
import tech.hongjian.spider.recommend.entity.enums.VideoType;
import tech.hongjian.spider.recommend.util.JSONUtil;

/**
 * @author xiahongjian
 * @time 2019-12-18 18:51:59
 */
@Service
public class QQRecommendParser implements RecommendParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(QQRecommendParser.class);
    public static final String INDEX_URL = "https://v.qq.com";
    
    @Override
    public void parse() {
        try {
            boolean isFirst = true;
            for (Element e : queryElements(INDEX_URL, "div.slider_nav a")) {
                // 跳过第一条
               if (isFirst) {
                   isFirst = false;
                   continue;
               }
               Recommend recommend = new Recommend();
               Video video = new Video();
               String[] items = e.attr("_stat").split(":");
               recommend.setIndex(Integer.valueOf(items[2]));
               video.setName(items[3]);
               
               String url = processUrl(e.attr("href"));
               Element title = queryElement(url, ".player_title a");
               if (title != null) {
                   featchDetail(video, processUrl(title.attr("href")));
               } else {
                   if (url.contains("/live")) {
                       video.setType(VideoType.LIVE);
                   } else {
                       video.setType(VideoType.OTHER);
                   }
               }
               recommend.setVideo(video);
               LOGGER.info(JSONUtil.toJSON(recommend));
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to parse page.", e);
        }
    }
    
    private void featchDetail(Video video, String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        String type = doc.select(".video_title_cn .type").text();
        String desc = doc.select(".video_desc span.desc_txt").text();
        Elements actors = doc.select(".video_actor .actor_list .item .name");
        Set<Actor> actorList = new HashSet<>(actors.size());
        for (Element actor : actors) {
            Actor a = new Actor();
            a.setName(actor.text());
            actorList.add(a);
        }
        video.setType(VideoType.withCaption(type));
        video.setActors(actorList);
        video.setDescription(desc);
    }

    private String processUrl(String path) {
        if (!path.contains("v.qq.com")) {
            return INDEX_URL + (path.startsWith("/") ? "" : "/") + path;
        }
        return path;
    }
    
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(INDEX_URL).userAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                .get();
        doc.select("div.slider_nav a").forEach(e -> {
            String title = e.attr("_stat");
            String url = e.attr("href");
            System.out.println(title + ": " + url);
        });;
    }
}
