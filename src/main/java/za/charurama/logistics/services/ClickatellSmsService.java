package za.charurama.logistics.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.charurama.logistics.contracts.SmsService;
import za.charurama.logistics.models.SmsDetails;
import za.charurama.logistics.models.SmsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClickatellSmsService implements SmsService {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${clickatell.sms.api-key}")
    private String smsApiKey;


    @Override
    public SmsResponse sendSms(String destination, String message) {
        SmsResponse response;
        SmsDetails smsDetails = new SmsDetails();
        smsDetails.setContent(message);
        List<String> to = new ArrayList<>();
        to.add(destination);
        smsDetails.setTo(to);
        try {
            String json = post("https://platform.clickatell.com/messages",smsDetails.toString());
            response = mapper.readValue(json, SmsResponse.class);
        } catch (IOException e) {
            response = new SmsResponse();
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .header("Authorization", smsApiKey)
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
