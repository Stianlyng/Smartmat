package ntnu.idatt2016.v233.SmartMat.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductRequest(long ean, String name, String description, String image, double price, List<String> allergies) {
}
