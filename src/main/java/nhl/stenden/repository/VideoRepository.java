package nhl.stenden.repository;

import nhl.stenden.model.Video;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository that handles queries to the 'video' table in the database.
 */
@Repository
@Transactional
public class VideoRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Adds a video to the 'video' table in the database.
     * @param video the video that needs to be added to the database
     */
    public void addVideo(Video video){
        em.persist(video);
    }

    /**
     * Adds a list of videos to the 'video' table in the database.
     * @param videos the videos that needs to be added to the database
     */
    public void addVideos(List<Video> videos){
        for (Video video : videos){
            addVideo(video);
        }
    }

    /**
     * Updates a video from the 'video' table in the database.
     * @param video the updated video that should be added to the database
     */
    public void updateVideo(Video video){
        em.merge(video);
    }

    /**
     * Gets all videos from the 'video' table in the database.
     * @return a list of all videos in the database
     */
    public List<Video> getAllVideos(){
        return em.createQuery("select v from Video v", Video.class).getResultList();
    }

    /**
     * Gets a video from the 'video' table in the database.
     * @param youtubeVideoId the youtube ID of the video
     * @return the video
     */
    public Video getVideoByYtId(String youtubeVideoId){
        return (Video) em.createQuery("select v from Video v where youtube_id = :youtubeId")
                .setParameter("youtubeId", youtubeVideoId).getSingleResult();
    }
}
