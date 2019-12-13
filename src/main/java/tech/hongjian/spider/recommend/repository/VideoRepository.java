package tech.hongjian.spider.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hongjian.spider.recommend.entity.Video;

/** 
 * @author xiahongjian
 * @time   2019-12-12 22:34:43
 */
public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByName(String name);
}
