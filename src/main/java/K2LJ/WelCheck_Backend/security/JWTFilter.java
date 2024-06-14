package K2LJ.WelCheck_Backend.security;

import K2LJ.WelCheck_Backend.memberpackage.domain.MemberRole;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.DisabledMember;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.GeneralMember;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.Member;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.WelfareWorkerMember;
import K2LJ.WelCheck_Backend.memberpackage.service.MemberDetailsDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static K2LJ.WelCheck_Backend.memberpackage.domain.MemberRole.WelfareWorkerMember;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        //토큰이 비어있거나, 잘못된 토큰일 시
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 만료 검증
        String token = authorization.split(" ")[1];
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);   //현재 토큰 생성시 role에 null주입함

        Member member = GeneralMember.builder()
                .userId(username)
                .password("Tmppassword@@")
                .build();

/*
        현재 토큰에 role에 null 입력, role에 제대로 role이 들어간 경우의 로직
        Member member;      //member 타입에 맞춰서 생성했는데, 이렇게 생성해서 세션에 넣어도 괜찮은지?
        if (role.equals(MemberRole.DisabledMember.toString())) {
            member = DisabledMember.builder()
                    .userId(username)
                    .password("Tmppassword@@")
                    .memberRole(MemberRole.DisabledMember)
                    .build();
        } else if (role.equals(WelfareWorkerMember.toString())){   //이건 왜 Enum 클래스 명시 안해도 되는지
            //이건 왜 풀주소를 써야하는지
            member = K2LJ.WelCheck_Backend.memberpackage.domain.member.WelfareWorkerMember.builder()
                    .userId(username)
                    .password("Tmppassword@@")
                    .memberRole(WelfareWorkerMember)
                    .build();
        } else {
            //여기선 또 왜 다시 ?
            member = GeneralMember.builder()
                    .userId(username)
                    .password("Tmppassword@@")
                    .memberRole(MemberRole.GeneralMember)
                    .build();
        }
*/

        MemberDetailsDTO memberDetailsDTO = new MemberDetailsDTO(member);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(memberDetailsDTO, null, memberDetailsDTO.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
