package com.dahoon.toy.artcollector.game.component;

import com.dahoon.toy.artcollector.game.document.GameDetail;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class SteamApiClient {
    private final WebClient webClient;

    public SteamApiClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://store.steampowered.com/api").build();
    }

    public GameDetail fetchGameDetail(Long appid) {
        String url = "/appdetails?appids=" + appid + "&l=korean";

        Map<String, Object> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        Map<String, Object> appData = (Map<String, Object>) response.get(String.valueOf(appid));
        boolean success = (boolean) appData.get("success");
        Map<String, Object> data = success ? (Map<String, Object>) appData.get("data") : null;

        return GameDetail.builder()
                .platform("Steam")
                .success(true)
                .data(data)
                .build();
    }
}
