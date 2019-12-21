package tech.hongjian.spider.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hongjian.spider.recommend.entity.VideoTypeMapping;

/** 
 * @author xiahongjian
 * @time   2019-12-21 16:29:40
 */
public interface VideoTypeMappingRepository extends JpaRepository<VideoTypeMapping, Integer> {

    VideoTypeMapping findByVideoName(String name);
}
