package sparta.seed.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SchoolList {

    public void saveSchoolList() throws IOException {
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

        for (int i = 0; i < jsonArray.length(); i++) {
            System.out.println(jsonArray.getJSONObject(i).get("schoolName"));
        }


//		System.out.println(rjson.getJSONObject("response"));
//		System.out.println(rjson.getJSONObject("response").getJSONObject("body"));
//
//		System.out.println("---------------");
//		JSONArray jsonArray = rjson.getJSONObject("response").getJSONObject("body").getJSONArray("items");
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			System.out.println(jsonArray.getJSONObject(i));
//			System.out.println("-----");
    }
// 6개 -> 6
//	}
}