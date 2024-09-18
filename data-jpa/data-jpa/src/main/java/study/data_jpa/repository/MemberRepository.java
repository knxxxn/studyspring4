package study.data_jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
    Page<Member> findByAge(int age, Pageable pageable);

    //@Query(name = "Member.findByUsername") //주석 처리 가능
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //DTO로 직접 조회
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //파라미터 바인딩
    @Query("select m from Member m where m.username = :name")
    Member findMembers(@Param("name") String username);

    //컬렉션 파라이터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

//    //반환 타입
//    List<Member> findByUsername(String name); //컬렉션
//    Member findByUsername(String name); //단건
//    Optional<Member> findByUsername(String name); //단건 Optional

//    Slice<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m", countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberAllCountBy(Pageable pageable); //count 쿼리를 다음과 같이 분리할 수 있음

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    //entityGraph
    //공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();
    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
//    @EntityGraph("Member.all") //namedentitygraph
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();
    //메서드 이름으로 쿼리에서 특히 편리하다.
//    @EntityGraph(attributePaths = {"team"})
//    List<Member> findByUsername(String username)

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.readOnly",
            value = "true")},
            forCounting = true)
    Page<Member> findByUsername(String name, Pageable pageable);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    List<Member> findByUsername(String name);



}