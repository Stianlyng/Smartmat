package ntnu.idatt2016.v233.SmartMat.util;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductUtilTest {

    private Product product;

    @Test @Order(1)
    void getVolumeFromProduct() {
        product = Product.builder()
                .ean(123456789)
                .name("Pepsi Original")
                .description("500 ml")
                .build();
        assertEquals(List.of("500.0", "ml"), ProductUtil.getVolumeFromProduct(product).get());
    }

    @Test @Order(2)
    void getVolumeFromProductMultipleUnits() {
        product = Product.builder()
                .ean(123456789)
                .name("Pepsi Original 24x500ml")
                .description("test")
                .build();
        assertEquals(List.of("12000.0", "ml"), ProductUtil.getVolumeFromProduct(product).get());
    }

    @Test @Order(3)
    void getVolumeFromProductCombined() {
        product = Product.builder()
                .ean(123456789)
                .name("test")
                .description("Pepsi Original 24x500 ml")
                .build();
        assertEquals(List.of("12000.0", "ml"), ProductUtil.getVolumeFromProduct(product).get());

        product = Product.builder()
                .ean(123456789)
                .name("test")
                .description("Pepsi Original 24x500ml")
                .build();
        assertEquals(List.of("12000.0", "ml"), ProductUtil.getVolumeFromProduct(product).get());
    }
}
