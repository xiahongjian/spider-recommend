package tech.hongjian.spider.recommend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.hongjian.spider.recommend.TestCaseBase;
import tech.hongjian.spider.recommend.entity.Actor;
import tech.hongjian.spider.recommend.entity.Recommend;
import tech.hongjian.spider.recommend.entity.Video;
import tech.hongjian.spider.recommend.entity.enums.Platform;
import tech.hongjian.spider.recommend.repository.ActorRepository;

/** 
 * @author xiahongjian
 * @time   2019-12-23 20:47:07
 */
public class TestRecommendService extends TestCaseBase {

    @Autowired
    private RecommendService recommendService;
    @Autowired
    private ActorRepository actorRepo;
    
    
    @Transactional
    @Test
    public void testSaveParseData() {
        Actor a = new Actor("何炅");
        actorRepo.save(a);
        
        Video v = new Video();
        v.setName("Test");
        v.setDescription("desc");
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("何炅"));
        v.setActors(actors);
        Recommend recommend = new Recommend();
        recommend.setId(1);
        recommend.setPlatform(Platform.IQIYI);
        recommend.setVideo(v);
        recommendService.saveParsedData(recommend);
        
        assertEquals(1, actorRepo.findByName("何炅").size());
    }
}
