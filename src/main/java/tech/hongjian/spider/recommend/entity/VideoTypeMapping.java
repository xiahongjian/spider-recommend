package tech.hongjian.spider.recommend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 
 * @author xiahongjian
 * @time   2019-12-21 16:25:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class VideoTypeMapping extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String videoName;
    
    @Column(nullable = false)
    private String videoType;
}
