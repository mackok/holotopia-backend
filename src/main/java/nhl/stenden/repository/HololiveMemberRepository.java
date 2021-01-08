package nhl.stenden.repository;

import nhl.stenden.model.HololiveMember;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class HololiveMemberRepository {

    @PersistenceContext
    private EntityManager em;

    public HololiveMember findMemberById(Long id){
        return em.find(HololiveMember.class, id);
    }

    public void addMember(HololiveMember hololiveMember){
        em.persist(hololiveMember);
    }

    public List<HololiveMember> getAllMembers(){
       return em.createQuery("select m from HololiveMember m", HololiveMember.class).getResultList();
    }

    public List<HololiveMember> getAllMembersWithVideos(){
        List<HololiveMember> members = em.createQuery("select m from HololiveMember m", HololiveMember.class).getResultList();
        for(HololiveMember member : members){
            Hibernate.initialize(member.getVideos());
        }
        return members;
    }
}
