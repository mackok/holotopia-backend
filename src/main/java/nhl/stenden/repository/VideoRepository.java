package nhl.stenden.repository;

import nhl.stenden.model.Video;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class VideoRepository {

    @PersistenceContext
    private EntityManager em;

    public Video findVideoById(Long id){
        return em.find(Video.class, id);
    }

    public void addVideo(Video hololiveMember){
        em.persist(hololiveMember);
    }

    public void addVideos(List<Video> videos){
        for (Video video : videos){
            addVideo(video);
        }
    }

    public void updateVideo(Video video){
        em.merge(video);
    }

    public List<Video> getAllVideos(){
        return em.createQuery("select v from Video v", Video.class).getResultList();
    }
}
