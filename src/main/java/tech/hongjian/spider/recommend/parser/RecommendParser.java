package tech.hongjian.spider.recommend.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 
 * @author xiahongjian
 * @time   2019-12-14 13:30:59
 */
public interface RecommendParser {
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
    
    void parse();
    
    default Element queryElement(String url, String selector) throws IOException {
        return Jsoup.connect(url).userAgent(USER_AGENT).get().select(selector).first();
    }
    
    default Elements queryElements(String url, String selector) throws IOException {
        return Jsoup.connect(url).userAgent(USER_AGENT).get().select(selector);
    }
}
