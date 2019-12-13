package tech.hongjian.spider.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hongjian.spider.recommend.entity.Actor;

/** 
 * @author xiahongjian
 * @time   2019-12-12 23:29:02
 */
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByName(String name);
}
