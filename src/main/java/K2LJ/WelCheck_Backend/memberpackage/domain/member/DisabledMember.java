package K2LJ.WelCheck_Backend.memberpackage.domain.member;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
public class DisabledMember extends Member{
    //변경필요
    private String certified;   //장애인 등록증(또는 인증여부)

    //변경필요
    private String disableCategory; //장애 분류

}
