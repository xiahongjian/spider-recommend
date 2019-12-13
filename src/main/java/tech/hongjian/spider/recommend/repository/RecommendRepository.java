package tech.hongjian.spider.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hongjian.spider.recommend.entity.Recommend;

/** 
 * @author xiahongjian
 * @time   2019-12-12 22:33:51
 */
public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

}
