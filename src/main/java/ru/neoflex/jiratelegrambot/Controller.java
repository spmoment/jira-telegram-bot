package ru.neoflex.jiratelegrambot;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class Controller {

    @GetMapping("/main")
    public String getAppRunMessage() {
        return "Application running!!!";
    }



    @PostMapping("/message")
    public void postMessage(@RequestBody String message) throws IOException, InterruptedException {
        System.out.println("message: " + message);
        JSONObject jsonObject = new JSONObject(message);
        String resultMessage = jsonObject.get("message").toString();
        System.out.println(resultMessage);

        final String URL = "https://api.telegram.org" +
                "/bot1881483631:AAFVTzoC0vATK7OLCnLLBwolAuRikmdR47Q" +
                "/sendMessage?chat_id=-527936555&text=" + resultMessage;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
    }
}
