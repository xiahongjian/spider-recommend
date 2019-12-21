package tech.hongjian.spider.recommend.parser;

import tech.hongjian.spider.recommend.entity.enums.Platform;

/** 
 * @author xiahongjian
 * @time   2019-12-14 13:30:59
 */
public interface RecommendParser {
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
    
    void parse();
    
    String getIndexUrl();
    
    String getMainDomain();
    
    Platform getPlatform();
}
