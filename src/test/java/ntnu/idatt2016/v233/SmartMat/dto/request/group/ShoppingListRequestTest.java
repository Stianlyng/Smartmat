package ntnu.idatt2016.v233.SmartMat.dto.request.group;

import ntnu.idatt2016.v233.SmartMat.dto.request.group.ShoppingListRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingListRequestTest {

    @Test
    void testEquals() {
        ShoppingListRequest shoppingListRequest = ShoppingListRequest.builder()
                .groupID(1)
                .build();

        ShoppingListRequest shoppingListRequest1 = ShoppingListRequest.builder()
                .groupID(1)
                .build();

        assertEquals(shoppingListRequest, shoppingListRequest1);

        shoppingListRequest1.setGroupID(2);

        assertNotEquals(shoppingListRequest, shoppingListRequest1);

        assertEquals(shoppingListRequest, shoppingListRequest);


    }

    @Test
    void testHashCode() {
        ShoppingListRequest shoppingListRequest = ShoppingListRequest.builder()
                .groupID(1)
                .build();

        ShoppingListRequest shoppingListRequest1 = ShoppingListRequest.builder()
                .groupID(1)
                .build();

        assertEquals(shoppingListRequest.hashCode(), shoppingListRequest1.hashCode());

        shoppingListRequest1.setGroupID(2);

        assertNotEquals(shoppingListRequest.hashCode(), shoppingListRequest1.hashCode());
    }
}