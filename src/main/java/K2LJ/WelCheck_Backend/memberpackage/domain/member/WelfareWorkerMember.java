package K2LJ.WelCheck_Backend.memberpackage.domain.member;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
public class WelfareWorkerMember extends Member{
    //변경필요
    private String workCertifed;    //재직증명서(또는 인증여부)

    //변경필요
    private String workSpace;   //근무지 수정필요 - address 상속
}
