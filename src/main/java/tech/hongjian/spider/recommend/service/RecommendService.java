package tech.hongjian.spider.recommend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hongjian.spider.recommend.repository.RecommendRepository;

/** 
 * @author xiahongjian
 * @time   2019-12-18 19:49:26
 */
@Service
public class RecommendService {

    @Autowired
    private RecommendRepository recommendRepo;
    
    
}
