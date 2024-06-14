package K2LJ.WelCheck_Backend.facilitypackage.api.openApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@Transactional
@RequiredArgsConstructor
public class SclWlfrFcltInfoInqirServiceApi {

    @GetMapping("/openapi/facility/{pageNo}")
    public JSONPObject getFactilityInfo(@PathVariable("pageNo") Long pageNo/*@RequestBody @Valid GetFacilityInfoRequest request*/) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B554287/sclWlfrFcltInfoInqirService1/getFcltByBassInfoInqire"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=73iDZKFpqjBQWNBKlP5Ii5tFs3l%2BLY7tBRc70SqzSxPxjD3DDUBuLGLytfJeR%2FGiII26o74%2BvwsuDDSVeoYW4w%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        JSONPObject json = new JSONPObject("JSON.parse", sb.toString());
        String jsonStr = mapper.writeValueAsString(json);

        System.out.println(jsonStr);
        rd.close();
        conn.disconnect();
        return json;
    }

    @Data
    @AllArgsConstructor
    static class GetFactilityInfoResponse {
    }

    @Data
    static class GetFacilityInfoRequest {
    }
}
