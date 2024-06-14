package K2LJ.WelCheck_Backend.kakaoapi;

import org.springframework.stereotype.Controller;

public interface KakaoApiController {
    //검색어(query)를 전달받아서,검색된 실제주소 리스트를 응답하는 api
    public String searchAddress(String query);
}
