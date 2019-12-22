package tech.hongjian.spider.recommend.task;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tech.hongjian.spider.recommend.parser.IQiYiRecommendParser;
import tech.hongjian.spider.recommend.parser.QQRecommendParser;
import tech.hongjian.spider.recommend.parser.YoukuRecommendParser;

/** 
 * @author xiahongjian
 * @time   2019-12-21 21:09:08
 */
@Component
public class RecommendSpiderTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendSpiderTask.class);
    
    @Autowired
    private QQRecommendParser qqParser;
    @Autowired
    private IQiYiRecommendParser iqiyiParser;
    @Autowired
    private YoukuRecommendParser youkuParser;
    
    @Scheduled(cron = "${spider.cron:0 0 10,21 * * *}")
    public void doParse() {
        LOGGER.info("Begin to run recommend spider.");
        Instant start = Instant.now();
        qqParser.parse();
        Instant stage1 = Instant.now();
        LOGGER.info("[{}] finish. Total time costs: {}s.", qqParser.getIndexUrl(), Duration.between(start, stage1).getSeconds());
        
        
        iqiyiParser.parse();
        Instant stage2 = Instant.now();
        LOGGER.info("[{}] finish. Total time costs: {}s.", iqiyiParser.getIndexUrl(), Duration.between(stage1, stage2).getSeconds());
        
        youkuParser.parse();
        Instant stage3 = Instant.now();
        LOGGER.info("[{}] finish. Total time costs: {}s.", youkuParser.getIndexUrl(), Duration.between(stage2, stage3).getSeconds());
        
        LOGGER.info("Recommend spider finish task. Total time const: {}s.", Duration.between(start, stage3).getSeconds());
    }

    
}
