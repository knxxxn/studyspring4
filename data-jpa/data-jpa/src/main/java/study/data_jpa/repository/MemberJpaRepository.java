package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.data_jpa.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    public void delete(Member member) {
        em.remove(member);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }
    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    public List<Member> findByUsername(String username) {
        List<Member> resultList =
                em.createNamedQuery("Member.findByUsername", Member.class)
                        .setParameter("username", username)
                        .getResultList();
        return resultList;
    }

    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                        .setParameter("age", age)
                        .setFirstResult(offset) //어디서 부터 가져올지 정함
                        .setMaxResults(limit) // 개수 정하기
                        .getResultList();
    }
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age",
                        Long.class)
                .setParameter("age", age)
                .getSingleResult(); //count이기 때문에 한 개가 반환될 것임, sorting 필요 없음
    }

    public int bulkAgePlus(int age) {
        int resultCount = em.createQuery(
                        "update Member m set m.age = m.age + 1" +
                                "where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
        return resultCount;
    }


}