package tech.hongjian.spider.recommend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hongjian.spider.recommend.entity.Actor;
import tech.hongjian.spider.recommend.entity.Recommend;
import tech.hongjian.spider.recommend.entity.Video;
import tech.hongjian.spider.recommend.repository.ActorRepository;
import tech.hongjian.spider.recommend.repository.RecommendRepository;
import tech.hongjian.spider.recommend.repository.VideoRepository;

/** 
 * @author xiahongjian
 * @time   2019-12-18 19:49:26
 */
@Service
public class RecommendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendService.class);

    @Autowired
    private RecommendRepository recommendRepo;
    
    @Autowired
    private VideoRepository videoRepo;
    
    @Autowired
    private ActorRepository actorRepo;
    
    
    public Recommend saveParsedData(Recommend entity) {
        Video video = entity.getVideo();
        if (video == null) {
            LOGGER.warn("No video contained in recommend.");
            return null;
        }
        List<Video> videos = videoRepo.findByName(video.getName());
        if (!videos.isEmpty()) {
            Video existed = videos.get(0);
            boolean isUpdate = false;
            if (!StringUtils.equals(existed.getDescription(), video.getDescription())) {
                existed.setDescription(video.getDescription());
                isUpdate = true;
            }
            if (existed.getType() != video.getType()) {
                existed.setType(video.getType());
                isUpdate = true;
            }
            if (isUpdate) {
                existed.setUpdateTime(LocalDateTime.now());
            }
            entity.setVideo(existed);
            return recommendRepo.save(entity);
        }
        List<Actor> actors = video.getActors();
        if (!actors.isEmpty()) {
            List<Actor> dbActors = new ArrayList<>(actors.size());
            actors.forEach(actor -> {
                List<Actor> result = actorRepo.findByName(actor.getName());
                if (result.isEmpty()) {
                    dbActors.add(actorRepo.save(actor));
                } else {
                    dbActors.add(result.get(0));
                }
            });
            video.setActors(dbActors);
        }
        return recommendRepo.save(entity);
    }
}
