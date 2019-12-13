package tech.hongjian.spider.recommend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.hongjian.spider.recommend.TestCaseBase;
import tech.hongjian.spider.recommend.entity.Actor;

/** 
 * @author xiahongjian
 * @time   2019-12-12 23:43:37
 */
public class TestActorRepo extends TestCaseBase {

    @Autowired
    private ActorRepository actorRepo;
    
    private Actor createActor() {
        Actor he = new Actor();
        he.setName("何炅");
        return he;
    }
    
    @Test
    @Transactional
    public void testInsert() {
        Actor he = createActor();
        actorRepo.save(he);
        assertEquals(1, actorRepo.findAll().size());
    }
    
    @Test
    @Transactional
    public void testFindByName() {
        Actor actor = createActor();
        actorRepo.save(actor);
        
        assertEquals(1, actorRepo.findByName("何炅").size());
    }
}
