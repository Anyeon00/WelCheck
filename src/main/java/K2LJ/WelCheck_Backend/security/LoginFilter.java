package K2LJ.WelCheck_Backend.security;

import K2LJ.WelCheck_Backend.memberpackage.domain.MemberRole;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.Member;
import K2LJ.WelCheck_Backend.memberpackage.repository.MemberRepository;
import K2LJ.WelCheck_Backend.memberpackage.service.MemberDetailsDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);      //username -> userId로 검증되어야하는데 제대로 되는지
        String password = obtainPassword(request);

/*
        토큰에 role 넣는 방법?
        아래 토큰생성 파라미터의 role에 Collection<? extends GrantedAuthorities 타입으로 넣어야하는데 어떻게 하는건지
        -> 나중에 MemberDetailsDTO클래스 확인후 참고하기
        Member findMember = memberRepository.findByUserId(username);
        MemberRole memberRole = findMember.getMemberRole();
*/

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        //3번째 파라미터 : role _일단 null

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MemberDetailsDTO memberDetailsDTO = (MemberDetailsDTO) authResult.getPrincipal();

        String username = memberDetailsDTO.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //세번째 파라미터 : 토큰 만료 기간
        // 1초 * 60 * 60 = 1시간
        String token = jwtUtil.createJwt(username, role, 1000L * 60 * 60);


        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //-Error _nikip
        response.setStatus(401);
    }
}
