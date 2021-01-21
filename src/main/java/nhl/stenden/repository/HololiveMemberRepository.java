package nhl.stenden.repository;

import nhl.stenden.model.HololiveMember;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository that handles queries to the 'hololive_member' table in the database.
 */
@Repository
@Transactional
public class HololiveMemberRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Adds a hololive member to the 'hololive_member' table in the database.
     * @param hololiveMember the hololive member that should be added to the database
     */
    public void addMember(HololiveMember hololiveMember){
        em.persist(hololiveMember);
    }

    /**
     * Gets all hololive members from the database and initializes all of their videos.
     * @return a list of all hololive members in the database
     */
    public List<HololiveMember> getAllMembersWithVideos(){
        List<HololiveMember> members = em.createQuery("select m from HololiveMember m", HololiveMember.class).getResultList();
        for(HololiveMember member : members){
            Hibernate.initialize(member.getVideos());
        }
        return members;
    }

    /**
     * Gets a hololive member by it's ID from the 'hololive_member' table in the database.
     * @param memberId the ID of the hololive member
     * @return the hololive member
     */
    public HololiveMember getMemberById(Long memberId){
        return em.find(HololiveMember.class, memberId);
    }
}
