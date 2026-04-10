package praktikum.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import praktikum.config.AppConfig;

public class RestClient {
    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(AppConfig.BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}