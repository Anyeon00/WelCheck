package K2LJ.WelCheck_Backend.memberpackage.controller.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindUserIdRequestDTO {

    @NotBlank
    String name;

    @NotBlank
    String email;
}
