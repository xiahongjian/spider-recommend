package tech.hongjian.spider.recommend.entity;
/** 
 * @author xiahongjian
 * @time   2019-12-12 20:09:31
 */

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.hongjian.spider.recommend.entity.enums.Platform;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Recommend extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Platform platform;
    
    private Integer index;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id")
    private Video video;
}
