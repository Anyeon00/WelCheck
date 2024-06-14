package K2LJ.WelCheck_Backend.memberpackage.service;

import K2LJ.WelCheck_Backend.memberpackage.domain.MemberRole;
import K2LJ.WelCheck_Backend.memberpackage.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class MemberDetailsDTO implements UserDetails {

    private Member member;

    public MemberDetailsDTO(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                MemberRole memberRole = member.getMemberRole();
                if (memberRole == MemberRole.DisabledMember) {
                    return "DisableMember";
                } else if (memberRole == MemberRole.WelfareWorkerMember) {
                    return "WelfareWorkerMember";
                } else if (memberRole == MemberRole.GeneralMember) {
                    return "GeneralMember";
                }
                return null;
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
