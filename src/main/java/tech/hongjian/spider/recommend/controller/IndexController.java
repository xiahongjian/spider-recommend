package tech.hongjian.spider.recommend.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.hongjian.spider.recommend.entity.Actor;
import tech.hongjian.spider.recommend.entity.Recommend;
import tech.hongjian.spider.recommend.entity.Video;
import tech.hongjian.spider.recommend.entity.enums.Platform;
import tech.hongjian.spider.recommend.entity.enums.VideoType;
import tech.hongjian.spider.recommend.model.RestResponse;

/** 
 * @author xiahongjian
 * @time   2019-12-12 23:00:11
 */
@RestController
public class IndexController {

    
    @GetMapping("/create")
    public RestResponse<?> test() {
        Actor he = new Actor();
        he.setName("何炅");
        Video video = new Video();
        video.setActors(Stream.of(he).collect(Collectors.toSet()));
        video.setName("Test name");
        video.setType(VideoType.VARIETY_SHOW);
        video.setDescription("desc");
        
        Recommend recommend = new Recommend();
        recommend.setPlatform(Platform.QQ);
        recommend.setVideo(video);
        
        return RestResponse.ok("done");
    }
}
