package tech.hongjian.spider.recommend.entity.enums;
/** 
 * @author xiahongjian
 * @time   2019-12-12 21:19:59
 */
public enum VideoType implements HasCaption {
    TV_SERIAL("电视剧"),
    VARIETY_SHOW("综艺");
    
    String caption;
    
    private VideoType(String caption) {
        this.caption = caption;
    }
    
    @Override
    public String getCaption() {
        return this.caption;
    }
}
