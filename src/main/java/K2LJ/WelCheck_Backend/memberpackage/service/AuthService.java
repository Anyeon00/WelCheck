package K2LJ.WelCheck_Backend.memberpackage.service;

import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.FindUserIdRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.FindPasswordRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.SignUpRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    //== 회원가입 ==//
    public Member saveMember(SignUpRequestDTO signUpRequestDTO);

    public boolean validateMemberId(String userId);

    public boolean validateUsername(String username);

    public boolean validateEmail(String email);

    //== 아이디, 비밀번호 찾기 ==//
    public String findUserId(FindUserIdRequestDTO dto);

    public String findPassword(FindPasswordRequestDTO dto) throws Exception;

}
