package tech.hongjian.spider.recommend.entity.enums;
/** 
 * @author xiahongjian
 * @time   2019-12-12 20:10:39
 */
public enum Platform implements HasCaption {
    QQ("腾讯视频"), 
    YOUKU("优酷"), 
    IQIYI("爱奇艺");
    
    String caption;
    
    private Platform(String caption) {
        this.caption = caption;
    }

    @Override
    public String getCaption() {
        return this.caption;
    }
    
    
}
