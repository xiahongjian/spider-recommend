package tech.hongjian.spider.recommend.entity.enums;
/** 
 * @author xiahongjian
 * @time   2019-12-12 21:19:59
 */
public enum VideoType implements HasCaption {
    OTHER("其他"),
    TV_SERIALS("电视剧"),
    VARIETY_SHOW("综艺"),
    MOVIE("电影"),
    LIFE("生活"),
    CARTOON("动漫"),
    CHILD("少儿"),
    LIVE("直播"),
    REALITY_TV_SHOW("真人秀");
    
    String caption;
    
    private VideoType(String caption) {
        this.caption = caption;
    }
    
    @Override
    public String getCaption() {
        return this.caption;
    }
    
    public static VideoType withCaption(String caption) {
        if (caption == null) {
            return OTHER;
        }
        for (VideoType type : values()) {
            if (type.getCaption().equals(caption)) {
                return type;
            }
        }
        if ("剧集".equals(caption)) {
            return TV_SERIALS;
        }
        return OTHER;
    }
}
