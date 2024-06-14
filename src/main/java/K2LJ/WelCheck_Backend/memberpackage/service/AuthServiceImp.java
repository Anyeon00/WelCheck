package K2LJ.WelCheck_Backend.memberpackage.service;

import K2LJ.WelCheck_Backend.mail.MailService;
import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.FindUserIdRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.FindPasswordRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.SignUpRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.domain.MemberRole;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.DisabledMember;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.GeneralMember;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.Member;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.WelfareWorkerMember;
import K2LJ.WelCheck_Backend.memberpackage.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final MailService mailService;

    @Override
    public Member saveMember(SignUpRequestDTO signUpRequestDTO) {
        //Member Type 확인
        MemberRole memberRole;
        if (signUpRequestDTO.getMemberRole() == null) {
            memberRole = MemberRole.GeneralMember;
        }
        memberRole = signUpRequestDTO.getMemberRole(); // role & sex DTO에 변수타입 convert제대로 되어 넘어오는지 확인

        Member newMember;
        if (memberRole == MemberRole.DisabledMember) {
            newMember = getDisabledMember(signUpRequestDTO);
        } else if (memberRole == MemberRole.WelfareWorkerMember) {
            newMember = getWelfareWorkerMember(signUpRequestDTO);
        }else{
            newMember = getGeneralMember(signUpRequestDTO);
        }

        return memberRepository.save(newMember);
    }

    @Override
    public boolean validateMemberId(String userId) {
        //findAll()을 돌면서 각 userId와 비교
        List<Member> allMembers = memberRepository.findAll();
        for (Member m : allMembers) {
            if (userId.equals(m.getUserId())) {
                return true;    //중복 o
            }
        }
        return false;   //중복 x
    }

    @Override
    public boolean validateUsername(String username) {
        List<Member> allMembers = memberRepository.findAll();
        for (Member m : allMembers) {
            if (username.equals(m.getUsername())) {
                return true;    //중복 o
            }
        }
        return false;   //중복 x
    }
    @Override
    public boolean validateEmail(String email) {
        List<Member> allMembers = memberRepository.findAll();
        for (Member m : allMembers) {
            if (email.equals(m.getEmail())) {
                return true;    //중복 o
            }
        }
        return false;   //중복 x
    }

    @Override
    public String findUserId(FindUserIdRequestDTO dto) {
        //1.이메일로 회원을 찾기
        //2.이름이 맞는지 검증
        //3. 해당 id반환
        Member findMember = memberRepository.findByEmail(dto.getEmail());

        //이메일이 존재하지 않을 시
        if (findMember == null) {
            return "fail";
        }
        //이름이 다를 시
        if (!dto.getName().equals(findMember.getName())) {
            return "fail";
        }

        return findMember.getUserId();
    }

    @Transactional
    @Override
    public String findPassword(FindPasswordRequestDTO dto) throws Exception{
        Member findMember = memberRepository.findByUserId(dto.getUserId());
        //id가 존재하지 않을 시
        if (findMember == null) {
            return "fail";
        }
        //이메일이 다를 시
        if (!dto.getEmail().equals(findMember.getEmail())) {
            return "fail";
        }
        //이름이 다를 시
        if (!dto.getName().equals(findMember.getName())) {
            return "fail";
        }

        //1.랜덤비밀번호 생성
        String randomStr = UUID.randomUUID().toString();
        log.info("new password : " + randomStr);
        String tmpPassword = encodingPassword(randomStr);
        //2.유저의 비밀번호를 위 비밀번호로 변경
        findMember.changePassword(tmpPassword);
        //3.이메일로 위 비밀번호 전송
        mailService.sendTempPasswordMail(findMember.getEmail(), findMember.getName(), randomStr);

        return "success";
    }

    private DisabledMember getDisabledMember(SignUpRequestDTO signUpRequestDTO) {
        String encodedPassword = encodingPassword(signUpRequestDTO.getPassword());

        return DisabledMember.builder()
                .userId(signUpRequestDTO.getUserId())
                .password(encodedPassword)
                .name(signUpRequestDTO.getName())
                .username(signUpRequestDTO.getUsername())
                .address(signUpRequestDTO.getAddress())
                .gender(signUpRequestDTO.getGender())
                .email(signUpRequestDTO.getEmail())
                .memberRole(MemberRole.DisabledMember)
                .certified(signUpRequestDTO.getCertified())
                .disableCategory(signUpRequestDTO.getDisableCategory())
                .build();
    }

    private WelfareWorkerMember getWelfareWorkerMember(SignUpRequestDTO signUpRequestDTO) {
        String encodedPassword = encodingPassword(signUpRequestDTO.getPassword());

        return WelfareWorkerMember.builder()
                .userId(signUpRequestDTO.getUserId())
                .password(encodedPassword)
                .name(signUpRequestDTO.getName())
                .username(signUpRequestDTO.getUsername())
                .address(signUpRequestDTO.getAddress())
                .gender(signUpRequestDTO.getGender())
                .email(signUpRequestDTO.getEmail())
                .memberRole(MemberRole.WelfareWorkerMember)
                .workCertifed(signUpRequestDTO.getWorkCertified())
                .workSpace(signUpRequestDTO.getWorkSpace())
                .build();
    }

    private GeneralMember getGeneralMember(SignUpRequestDTO signUpRequestDTO) {
        {
            String encodedPassword = encodingPassword(signUpRequestDTO.getPassword());

            return GeneralMember.builder()
                    .userId(signUpRequestDTO.getUserId())
                    .password(encodedPassword)
                    .name(signUpRequestDTO.getName())
                    .username(signUpRequestDTO.getUsername())
                    .address(signUpRequestDTO.getAddress())
                    .gender(signUpRequestDTO.getGender())
                    .email(signUpRequestDTO.getEmail())
                    .memberRole(MemberRole.GeneralMember)
                    .build();
        }
    }

    private String encodingPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    //---------------------------------------------------------------회원가입
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByUserId(username);

        if (findMember == null) {
            //로그인 시도시, userId가 존재하지 않으면
            return null;
        }
        //로그인 시도시, userId가 존재하면 UserDetails객체를 만들어 반환
        //반환한 UserDetails객체로 로그인을 검증해줌
        return new MemberDetailsDTO(findMember);
    }

    //----------------------------------------------------------------로그인
}
