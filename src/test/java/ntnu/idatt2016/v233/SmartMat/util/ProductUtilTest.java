package ntnu.idatt2016.v233.SmartMat.util;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductUtilTest {

    Product product;
    @BeforeEach
    void setUp() {
        this.product = Product.builder()
                .ean(123456789)
                .name("Pepsi Original 24x500 ml")
                .description("Pepsi Original 24x500 ml")
                .build();
    }

    @Test
    void getVolumeFromProduct() {
        assertEquals(List.of("24x500", "ml"), ProductUtil.getVolumeFromProduct(product).get());

        this.product = Product.builder()
                .ean(123456789)
                .name("test")
                .description("Pepsi Original 24x500 ml")
                .build();

        assertEquals(List.of("24x500", "ml"), ProductUtil.getVolumeFromProduct(product).get());

        this.product = Product.builder()
                .ean(123456789)
                .name("Pepsi Original 24x500ml")
                .description("test")
                .build();


        assertEquals(List.of("24x500", "ml"), ProductUtil.getVolumeFromProduct(product).get());

        this.product = Product.builder()
                .ean(123456789)
                .name("test")
                .description("Pepsi Original 24x500ml")
                .build();


        assertEquals(List.of("24x500", "ml"), ProductUtil.getVolumeFromProduct(product).get());
    }
}