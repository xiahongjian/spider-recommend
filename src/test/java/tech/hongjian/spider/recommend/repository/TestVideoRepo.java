package tech.hongjian.spider.recommend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.hongjian.spider.recommend.TestCaseBase;
import tech.hongjian.spider.recommend.entity.Actor;
import tech.hongjian.spider.recommend.entity.Video;
import tech.hongjian.spider.recommend.entity.enums.VideoType;

/** 
 * @author xiahongjian
 * @time   2019-12-12 23:52:59
 */
public class TestVideoRepo extends TestCaseBase {

    @Autowired
    private VideoRepository videoRepo;
    
    @Autowired
    private ActorRepository actorRepo;
    
    private Actor createActor() {
        Actor he = new Actor();
        he.setName("何炅");
        return he;
    }
    
    private Video createVideo() {
        Video video = new Video();
        video.setName("令人心动的offer");
        video.setType(VideoType.VARIETY_SHOW);
        video.setDescription("desc");
        Actor actor = createActor();
        List<Actor> set = new ArrayList<>(1);
        set.add(actor);
        video.setActors(set);
        return video;
    }
    
    @Test
    @Transactional
    public void testInsert() {
        Video video = createVideo();
        videoRepo.save(video);
        
        assertEquals(1, videoRepo.findAll().size());
        assertEquals(1, actorRepo.findAll().size());
    }
    
    @Test
    @Transactional
    public void testFindByName() {
        Video v = createVideo();
        videoRepo.save(v);
        
        assertEquals(1, videoRepo.findByName("令人心动的offer").size());
    }
}
