package K2LJ.WelCheck_Backend.memberpackage.repository;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//SpringDataJpa 사용 Repository Test
@SpringBootTest
@Transactional
class MemberRepositoryTest {
/*

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        // userId, password, name, username, address, sex, email
        member.setUserId("yasuo123");
        member.setPassword("password99");
        member.setName("kimyounghan");
        member.setUsername("best yasuo");
        Address address1 = new Address("zipCode", "roadName", "streetNumber", "detail", "reference", "phoneNumber");
        member.setAddress(address1);
        member.setSex(Sex.MALE);
        member.setEmail("yasuo123@gmail.com");

        //when

        //then
        memberRepository.save(member);
    }
    @Test
    public void 회원가입후조회() throws Exception{
        //given
        Member member = new Member();
        // userId, password, name, username, address, sex, email
        member.setUserId("yasuo123");
        member.setPassword("password99");
        member.setName("kimyounghan");
        member.setUsername("best yasuo");
        //zipCode, roadName, streetNumber, detail, reference, phoneNumber
        Address address1 = new Address("zipCode", "roadName", "streetNumber", "detail", "reference", "phoneNumber");
        member.setAddress(address1);
        member.setSex(Sex.MALE);
        member.setEmail("yasuo123@gmail.com");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId());

        //then
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 전체회원조회() throws Exception{
        //given
        Member member1 = new Member();
        // userId, password, name, username, address, sex, email
        member1.setUserId("yasuo123");
        member1.setPassword("password99");
        member1.setName("kimyounghan");
        member1.setUsername("best yasuo");
        Address address1 = new Address("zipCode", "roadName", "streetNumber", "detail", "reference", "phoneNumber");
        member1.setAddress(address1);
        member1.setSex(Sex.MALE);
        member1.setEmail("yasuo123@gmail.com");
        memberRepository.save(member1);

        Member member2 = new Member();
        // userId, password, name, username, address, sex, email
        member2.setUserId("katarina123");
        member2.setPassword("password100");
        member2.setName("barkingdog");
        member2.setUsername("best katarina");
        Address address2 = new Address("zipCode", "roadName", "streetNumber", "detail", "reference", "phoneNumber");
        member2.setAddress(address2);
        member2.setSex(Sex.FEMALE);
        member2.setEmail("katarina123@gmail.com");
        memberRepository.save(member2);

        //when

        //then
        List<Member> members = memberRepository.findAll();

        assertThat(2).isEqualTo(members.size());
    }
*/
}