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
     * Adds a hololive member to the database.
     * @param hololiveMember the hololive member that should be added to the database
     */
    public void addMember(HololiveMember hololiveMember){
        em.persist(hololiveMember);
    }

    /**
     * Updates a hololive member in the database.
     * @param member the hololive member, including updated data, that should be updated in the database
     */
    public void updateMember(HololiveMember member){
        em.merge(member);
    }

    /**
     * Deletes a hololive member from the database.
     * @param member the hololive member that should be deleted from the database
     */
    public void deleteMember(HololiveMember member){
        em.remove(member);
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
     * Gets a hololive member from the database by it's ID.
     * @param memberId the ID of the hololive member
     * @return the hololive member
     */
    public HololiveMember getMemberById(Long memberId){
        return em.find(HololiveMember.class, memberId);
    }
}
