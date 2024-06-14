package K2LJ.WelCheck_Backend.kakaoapi;

import K2LJ.WelCheck_Backend.kakaoapi.local.KakaoAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class KakaoApiControllerImpl implements KakaoApiController{

    private final KakaoAddressService kakaoAddressService;
    @Override
    @GetMapping("/searchAddress")
    public String searchAddress(String query) {
        return kakaoAddressService.searchAddress(query);
    }
}
