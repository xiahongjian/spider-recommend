package tech.hongjian.spider.recommend.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 
 * @author xiahongjian
 * @time   2019-12-12 22:37:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Actor extends BaseEntity {
    private String name;
}
