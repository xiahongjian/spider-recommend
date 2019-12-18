package tech.hongjian.spider.recommend.parser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.hongjian.spider.recommend.TestCaseBase;

/** 
 * @author xiahongjian
 * @time   2019-12-18 20:45:45
 */
public class TestQQRecommendParser extends TestCaseBase {

    @Autowired
    private QQRecommendParser parser;
    
    @Test
    public void testParse() {
        parser.parse();
    }
}
