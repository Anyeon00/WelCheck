package K2LJ.WelCheck_Backend.memberpackage.domain.member;

import K2LJ.WelCheck_Backend.memberpackage.domain.MemberRole;
import K2LJ.WelCheck_Backend.memberpackage.domain.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MTYPE")
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue @Column(name = "member_id")
    private Long id;

    private String userId;  //unique value

    private String password;

    private String name;

    private String username;    //unique value

    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    private String email;   //unique value

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    //== 데이터멤버 변경 로직 ==//
    //유저 닉네임 변경
    public void changeUsername(String newUsername) {
        this.username = newUsername;
    }
    //비밀번호 변경
    public void changePassword(String newPassword){
        this.password = newPassword;
    }
}
