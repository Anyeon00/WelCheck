package K2LJ.WelCheck_Backend.memberpackage.controller;

import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.FindPasswordRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.FindUserIdRequestDTO;
import K2LJ.WelCheck_Backend.memberpackage.controller.requestdto.SignUpRequestDTO;
import org.springframework.validation.BindingResult;

public interface AuthController {

    //**회원가입**//
    //GetMapping
    //회원가입 -> 멤버타입선택페이지 -> 멤버별 회원가입 페이지
    public String signUp();
    public String GeneralMemberSignUp();
    public String disabledMemberSignUp();
    public String WelfareWorkerMemberSignUp();
    //PostMapping
    //각 멤버타입별 회원가입 페이지 모두 폼 제출시 아래 url로 매핑시키면 될 듯?
    public String signUp(SignUpRequestDTO signUpRequestDTO, BindingResult bindingResult);

    //**로그인**//
    //GetMapping
    public String signIn();
    //PostMapping
    //스프링시큐리티에서 구현됨

    //**아이디 찾기**//
    //GetMapping
    public String findUserIdPage();
    //PostMapping
    public String findUserIdPage(FindUserIdRequestDTO dto, BindingResult bindingResult);

    //**비밀번호 찾기**//
    //GetMapping
    public String findPasswordPage();

    //PostMapping
    public String findPasswordPage(FindPasswordRequestDTO dto, BindingResult bindingResult) throws Exception;

}
