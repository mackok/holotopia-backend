package nhl.stenden.repository;

import nhl.stenden.model.HololiveMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
}
