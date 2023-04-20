package ntnu.idatt2016.v233.SmartMat.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class CategoryService {
    public static String getCategory(Long ean) throws URISyntaxException {
        HttpRequest getRequest = HttpRequest.newBuilder().uri(new URI("https://kassal.app/api/v1/products/ean/"+ean))
                .header("Authoriation").build();
    }
}
