package tech.hongjian.spider.recommend.repository;
/** 
 * @author xiahongjian
 * @time   2019-12-12 23:22:43
 */

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.hongjian.spider.recommend.TestCaseBase;
import tech.hongjian.spider.recommend.entity.Actor;
import tech.hongjian.spider.recommend.entity.Recommend;
import tech.hongjian.spider.recommend.entity.Video;
import tech.hongjian.spider.recommend.entity.enums.Platform;
import tech.hongjian.spider.recommend.entity.enums.VideoType;

public class TestRecommendRepo extends TestCaseBase {

    @Autowired
    private RecommendRepository recommendRepo;
    
    @Autowired
    private VideoRepository videoRepo;
    
    @Autowired
    private ActorRepository actorRepo;
    
    @Test
    @Transactional
    public void testInsert() {
        Actor he = new Actor();
        he.setName("何炅");
        Video video = new Video();
        video.setActors(Stream.of(he).collect(Collectors.toSet()));
        video.setName("令人心动的offer");
        video.setType(VideoType.VARIETY_SHOW);
        video.setDescription("desc");
        
        Recommend recommend = new Recommend();
        recommend.setPlatform(Platform.QQ);
        recommend.setVideo(video);
        
        recommendRepo.save(recommend);
        
        assertEquals(1, recommendRepo.findAll().size());
        assertEquals(1, actorRepo.findByName("何炅").size());
        assertEquals(1, videoRepo.findByName("令人心动的offer").size());
    }
}
