package K2LJ.WelCheck_Backend.memberpackage.controller.requestdto;

import K2LJ.WelCheck_Backend.memberpackage.domain.MemberRole;
import K2LJ.WelCheck_Backend.memberpackage.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequestDTO {
    @NotBlank
    String userId;
    @NotBlank
    String password;
    @NotBlank
    String name;
    @NotBlank
    String username;

    //주소
    String address;

    Gender gender;
    String email;
    MemberRole memberRole;    //if(role == null) role = GeneralMember

    //DisabledMember
    String certified;
    String disableCategory;

    //WelfareWorkerMember
    String workCertified;
    String workSpace;

    //GeneralMember
}
