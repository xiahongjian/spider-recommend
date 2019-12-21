package tech.hongjian.spider.recommend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false)
    private String videoName;
    
    @Column(nullable = false)
    private String voideType;
}
