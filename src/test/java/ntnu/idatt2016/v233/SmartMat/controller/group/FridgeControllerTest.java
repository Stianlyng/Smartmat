package ntnu.idatt2016.v233.SmartMat.controller.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.product.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.group.FridgeService;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FridgeControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private FridgeController fridgeController;
    @Mock
    private FridgeService fridgeService;

    @Mock
    private GroupService groupService;


    private Fridge fridge;
    private Product product;
    private FridgeProductAsso fridgeProductAsso;
    private FridgeProductRequest fridgeProductRequest;


    private final Authentication regularUser = new Authentication() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(Authority.USER.name()));
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "test";
        }
    };

    private final Authentication adminUser = new Authentication() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(Authority.ADMIN.name()));
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "test";
        }
    };


    @BeforeEach
    public void setUp() {
        // Create and set up your test data here
        fridge = new Fridge();
        product = new Product();
        fridgeProductAsso = new FridgeProductAsso();
        fridgeProductRequest = new FridgeProductRequest(1L, 1L, 1L, 2, 7, 100);
    }

    @Test
    public void getFridgeByGroupIdAsAdmin() throws Exception {
        when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));


        ResponseEntity<Fridge> responseEntity = fridgeController.getFridgeByGroupId(1L, adminUser);


        verify(fridgeService).getFridgeByGroupId(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getFridgeByGroupIdAsUser() throws Exception {
        when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));

        ResponseEntity<Fridge> responseEntity = fridgeController.getFridgeByGroupId(1L, regularUser);

        verify(fridgeService).getFridgeByGroupId(1L);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);

    }

    @Test
    public void getFridgeByGroupId_notFound() throws Exception {
        when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Fridge> responseEntity = fridgeController.getFridgeByGroupId(1L, adminUser);


        verify(fridgeService).getFridgeByGroupId(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getFridgeByFridgeId() throws Exception {
        when(fridgeService.getFridgeByFridgeId(1L)).thenReturn(Optional.of(fridge));

        ResponseEntity<Fridge> responseEntity = fridgeController.getFridgeByFridgeId(1L, adminUser);


        verify(fridgeService).getFridgeByFridgeId(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getFridgeByFridgeIdAsUser() throws Exception {

        ResponseEntity<Fridge> responseEntity = fridgeController.getFridgeByFridgeId(1L, regularUser);

        verify(fridgeService).isUserInFridge("test",  1L);


        assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);

    }

    @Test
    public void getFridgeByFridgeId_notFound() throws Exception {
        when(fridgeService.getFridgeByFridgeId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Fridge> responseEntity = fridgeController.getFridgeByFridgeId(1L, adminUser);


        verify(fridgeService).getFridgeByFridgeId(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void addProductToFridge() throws Exception {
        when(fridgeService.addProductToFridge(any(FridgeProductRequest.class))).thenReturn(Optional.of(product));

        when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));

        ResponseEntity<Product> responseEntity = fridgeController.addProductToFridge(fridgeProductRequest, adminUser);


        verify(fridgeService).addProductToFridge(any(FridgeProductRequest.class));

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


    }


    @Nested
    class addProduct{
        @Test
        public void addProductToFridgeAsUserNotAutorized() throws Exception {

            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            when(fridgeService.isUserInFridge("test", 0L)).thenReturn(false);


            ResponseEntity<Product> responseEntity = fridgeController.addProductToFridge(fridgeProductRequest, regularUser);

            verify(fridgeService).isUserInFridge("test", 0L);

            assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);

        }

        @Test
        public void addProductToFridge_notFound() throws Exception {
            when(fridgeService.addProductToFridge(any(FridgeProductRequest.class))).thenReturn(Optional.empty( ));

            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            when(fridgeService.isUserInFridge("test", 0L)).thenReturn(true);


            ResponseEntity<Product> responseEntity = fridgeController.addProductToFridge(fridgeProductRequest, regularUser);

            verify(fridgeService).isUserInFridge("test", 0L);

            assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        }

        @Test
        public void addProductTOFridgeAsAdmin(){
            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));

            when(fridgeService.addProductToFridge(any(FridgeProductRequest.class))).thenReturn(Optional.of(product));

            ResponseEntity<Product> responseEntity = fridgeController.addProductToFridge(fridgeProductRequest, adminUser);


            assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

            verify(fridgeService).addProductToFridge(any(FridgeProductRequest.class));


        }

        @Test
        public void addProductTOFridgeAsRegUserAllowed(){
            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            when(fridgeService.isUserInFridge("test", 0L)).thenReturn(true);
            when(fridgeService.addProductToFridge(any(FridgeProductRequest.class))).thenReturn(Optional.of(product));

            ResponseEntity<Product> responseEntity = fridgeController.addProductToFridge(fridgeProductRequest, regularUser);


            assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

            verify(fridgeService).addProductToFridge(any(FridgeProductRequest.class));

        }



    }


    @Nested
    class updateProduct{
        @Test
        public void updateProductInFridge() throws Exception {
            when(fridgeService.updateProductInFridge(any(FridgeProductRequest.class))).thenReturn(Optional.of(fridgeProductAsso));

            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            ResponseEntity<FridgeProductAsso> responseEntity =
                    fridgeController.updateProductInFridge(fridgeProductRequest, adminUser);


            verify(fridgeService).updateProductInFridge(any(FridgeProductRequest.class));

            assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        }

        @Test
        public void updateProductInFridge_notFound() throws Exception {
            when(fridgeService.updateProductInFridge(any(FridgeProductRequest.class))).thenReturn(Optional.empty());

            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            ResponseEntity<FridgeProductAsso> responseEntity =
                    fridgeController.updateProductInFridge(fridgeProductRequest, adminUser);


            verify(fridgeService).updateProductInFridge(any(FridgeProductRequest.class));

            assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        }

        @Test
        public void updateNotAuthorized() throws Exception {
            when(fridgeService.isUserInFridge("test", 0L)).thenReturn(false);
            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            ResponseEntity<FridgeProductAsso> responseEntity =
                    fridgeController.updateProductInFridge(fridgeProductRequest, regularUser);

            verify(fridgeService, times(0)).updateProductInFridge(any(FridgeProductRequest.class));

            assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);

        }
        @Test
        public void updateNotAuthorizedRestricted() throws Exception {
            when(fridgeService.isUserInFridge("test", 0L)).thenReturn(true);
            when(groupService.getUserGroupAssoAuthority("test", 1L))
                    .thenReturn("restricted");

            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            ResponseEntity<FridgeProductAsso> responseEntity =
                    fridgeController.updateProductInFridge(fridgeProductRequest, regularUser);

            verify(fridgeService, times(0)).updateProductInFridge(any(FridgeProductRequest.class));

            assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);

        }
        @Test
        public void updateAuthorizedWhenNotRestricted(){
            when(fridgeService.isUserInFridge("test", 0L)).thenReturn(true);
            when(groupService.getUserGroupAssoAuthority("test", 1L))
                    .thenReturn("USER");
            when(fridgeService.updateProductInFridge(any(FridgeProductRequest.class)))
                    .thenReturn(Optional.of(fridgeProductAsso));

            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            ResponseEntity<FridgeProductAsso> responseEntity =
                    fridgeController.updateProductInFridge(fridgeProductRequest, regularUser);

            verify(fridgeService, times(1)).updateProductInFridge(any(FridgeProductRequest.class));

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
        @Test
        public void updateAuthorizedWhenAdmin(){
            when(fridgeService.isUserInFridge("test", 0L)).thenReturn(true);
            when(groupService.getUserGroupAssoAuthority("test", 1L))
                    .thenReturn("ADMIN");
            when(fridgeService.updateProductInFridge(any(FridgeProductRequest.class)))
                    .thenReturn(Optional.of(fridgeProductAsso));

            when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));
            ResponseEntity<FridgeProductAsso> responseEntity =
                    fridgeController.updateProductInFridge(fridgeProductRequest, regularUser);

            verify(fridgeService, times(1)).updateProductInFridge(any(FridgeProductRequest.class));

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }


    }


    @Nested
    class removeProductFromFridge {
        @Test
        public void removeProductFromFridge_success() throws Exception {
            when(fridgeService.removeProductFromFridge(1L)).thenReturn(true);


            ResponseEntity<String> responseEntity =
                    fridgeController.removeProductFromFridge(1L, adminUser);

            verify(fridgeService).removeProductFromFridge(1L);

            assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


        }

        @Test
        public void removeProductFromFridge_notFound() throws Exception {
            when(fridgeService.removeProductFromFridge(1L)).thenReturn(false);

            ResponseEntity<String> responseEntity =
                    fridgeController.removeProductFromFridge(1L, adminUser);

            verify(fridgeService).removeProductFromFridge(1L);

            assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        }

        @Test
        public void removeProductNotAuthorizedWhenRestricted(){

            when(fridgeService.isUserInGroupWithFridgeProduct("test", 1L))
                    .thenReturn(true);
            when(groupService.getUserGroupAssoAuthority(eq("test"), eq(1L)))
                    .thenReturn("restricted");

            when(fridgeService.getGroupIdFromFridgeProuctId(1L)).thenReturn(1L);

            ResponseEntity<String> responseEntity =
                    fridgeController.removeProductFromFridge(1L, regularUser);

            verify(fridgeService, times(0)).removeProductFromFridge(1L);

            assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
        }

        @Test
        public void removeProductAuthorizedWhenNotRestricted(){

            when(fridgeService.removeProductFromFridge(1L)).thenReturn(true );

            when(fridgeService.isUserInGroupWithFridgeProduct("test", 1L))
                    .thenReturn(true);
            when(groupService.getUserGroupAssoAuthority(eq("test"), eq(1L)))
                    .thenReturn("USER");

            when(fridgeService.getGroupIdFromFridgeProuctId(1L)).thenReturn(1L);

            ResponseEntity<String> responseEntity =
                    fridgeController.removeProductFromFridge(1L, regularUser);

            verify(fridgeService, times(1)).removeProductFromFridge(1L);

            assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        }

        @Test
        public void notRemoveProductWhenNotInGroup(){

            when(fridgeService.isUserInGroupWithFridgeProduct("test", 1L))
                    .thenReturn(false);

            ResponseEntity<String> responseEntity =
                    fridgeController.removeProductFromFridge(1L, regularUser);

            verify(fridgeService, times(0)).removeProductFromFridge(1L);

            assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
        }
    }


}
