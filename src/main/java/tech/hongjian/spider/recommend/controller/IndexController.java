package tech.hongjian.spider.recommend.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.hongjian.spider.recommend.model.RestResponse;
import tech.hongjian.spider.recommend.task.RecommendSpiderTask;

/** 
 * @author xiahongjian
 * @time   2019-12-12 23:00:11
 */
@RestController
public class IndexController {
    @Value("${spider.user}")
    private String user;
    @Value("${spider.password}")
    private String password;
    
    @Autowired
    private RecommendSpiderTask task;
    
    
    @GetMapping("/parse")
    public RestResponse<?> parse(String user, String password) {
        if (StringUtils.equals(this.user, user) && StringUtils.equals(this.password, password)) {
            task.doParse();
            return RestResponse.ok("done");
        }
        return RestResponse.fail("No permission.");
    }
}
