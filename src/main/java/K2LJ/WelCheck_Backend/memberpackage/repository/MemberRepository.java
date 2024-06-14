package K2LJ.WelCheck_Backend.memberpackage.repository;

import K2LJ.WelCheck_Backend.memberpackage.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//SpringDataJPA 사용
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{

    public Member findById(Long id);

    public Member findByUserId(String userId);

    public Member findByUsername(String username);

    public Member findByEmail(String email);
}
