package sparta.seed.util;

import org.json.JSONArray;
import org.json.JSONObject;
import sparta.seed.school.domain.School;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SchoolList {

    public List<School> getSchoolList() throws IOException {
        StringBuilder result = new StringBuilder();

        String zzz = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=8b6987e3437f2f606c0b32a837986a81&svcType=api&svcCode=SCHOOL&contentType=json&gubun=high_list&perPage=2378&searchSchulNm=%EA%B3%A0%EB%93%B1%ED%95%99%EA%B5%90";


        URL url = new URL(zzz);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n");
        }
        urlConnection.disconnect();

        JSONObject rjson = new JSONObject(result.toString());
        //		System.out.println(rjson);
        JSONArray jsonArray = rjson.getJSONObject("dataSearch").getJSONArray("content");

//		System.out.println(jsonArray);
        List<School> schoolList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            schoolList.add(School.builder()
                    .schoolName(jsonArray.getJSONObject(i).get("schoolName").toString())
                    .address(jsonArray.getJSONObject(i).get("adres").toString())
                    .build());
        }
        return schoolList;
    }
}