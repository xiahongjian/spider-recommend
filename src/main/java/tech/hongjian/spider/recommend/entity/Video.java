package tech.hongjian.spider.recommend.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.hongjian.spider.recommend.entity.enums.VideoType;

/** 
 * @author xiahongjian
 * @time   2019-12-12 22:20:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Video extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private VideoType type;
    
    private String name;
    
    @Column(length = 2000)
    private String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "actor_id")}, joinColumns = {@JoinColumn(name = "video_id")})
    private List<Actor> actors = new ArrayList<>();
}
