package tech.hongjian.spider.recommend.parser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.hongjian.spider.recommend.TestCaseBase;

/** 
 * @author xiahongjian
 * @time   2019-12-21 15:00:02
 */
public class TestIQiYiRecommendParser extends TestCaseBase {
    @Autowired
    private IQiYiRecommendParser recommendParser;
    
    @Test
    public void testParse() {
        recommendParser.parse();
    }
}
