package fmi.enroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KeycloakService {

    @Value("${keycloak.auth-server-url:http://localhost:8180}")
    private String keycloakUrl;

    @Value("${keycloak.realm:fmi-enroll}")
    private String realm;

    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final RestTemplate restTemplate;

    @Autowired
    public KeycloakService(OAuth2AuthorizedClientManager authorizedClientManager, RestTemplate restTemplate) {
        this.authorizedClientManager = authorizedClientManager;
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getUserInfo(String userId) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak")
                .principal("system")
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient != null) {
            String accessToken = authorizedClient.getAccessToken().getTokenValue();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            String url = String.format("%s/admin/realms/%s/users/%s", keycloakUrl, realm, userId);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {}
            );

            return response.getBody();
        }

        return null;
    }
}