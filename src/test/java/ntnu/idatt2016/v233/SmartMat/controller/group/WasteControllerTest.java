package ntnu.idatt2016.v233.SmartMat.controller.group;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.*;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.ObjectMapper;

import ntnu.idatt2016.v233.SmartMat.dto.request.group.WasteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.group.WasteService;
import ntnu.idatt2016.v233.SmartMat.util.CategoryUtil;

public class WasteControllerTest {

    @Mock
    private WasteService wasteService;

    @Mock
    private GroupService groupService;

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
            return "regularUser";
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

    private WasteController wasteController;
    private ObjectMapper objectMapper;
    private Group group;
    private Product product;
    private WasteRequest wasteRequest;
    private Waste expectedWaste;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        wasteController = new WasteController(wasteService, groupService);
        objectMapper = new ObjectMapper();

        group = new Group();
        group.setGroupId(1L);
        group.setGroupName("TestGroup");

        user = User.builder()
                .username("regularUser")
                .password("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .user(user)
                .group(group)
                .id(new UserGroupId(user.getUsername(), group.getGroupId()))
                .build();

        group.addUser(userGroupAsso);
        user.addGroup(userGroupAsso);

        product = new Product();
        product.setEan(12345678L);

        wasteRequest = new WasteRequest(1L, 12345678L, 0.5, "kg");

        expectedWaste = new Waste();
        expectedWaste.setWasteId(1L);
        expectedWaste.setGroupId(group);
        expectedWaste.setEan(product);
        expectedWaste.setTimestamp(new Timestamp(System.currentTimeMillis()));
        expectedWaste.setAmount(0.5);
        expectedWaste.setUnit("kg");

    }

    @Nested
    class createWaste{
        @Test
        void asAdmin()  {
            when(wasteService.createWaste(eq(wasteRequest))).thenReturn(Optional.of(expectedWaste));

            ResponseEntity<Waste> result = wasteController.createWaste(wasteRequest, adminUser);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertNotNull(result.getBody());

            assertEquals(expectedWaste.getWasteId(), result.getBody().getWasteId());
            verify(wasteService, times(1)).createWaste(eq(wasteRequest));
        }

        @Test
        void asRegUser()  {
            when(wasteService.createWaste(eq(wasteRequest))).thenReturn(Optional.of(expectedWaste));
            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(true);

            ResponseEntity<Waste> result = wasteController.createWaste(wasteRequest, regularUser);


            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertNotNull(result.getBody());

            assertEquals(expectedWaste.getWasteId(), result.getBody().getWasteId());
            verify(wasteService, times(1)).createWaste(eq(wasteRequest));
        }

        @Test
        void notAuthorized(){
            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(false);

            ResponseEntity<Waste> result = wasteController.createWaste(wasteRequest, regularUser);


            assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
            assertNull(result.getBody());

            verify(wasteService, times(0)).createWaste(eq(wasteRequest));
        }
    }

    @Nested
    class getByCategoryAndGroup{
        @Test
        void testGetWasteOfCategoryByGroupId()  {
            int categoryNumber = 1;
            String categoryName = CategoryUtil.getCategoryName(categoryNumber);
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));

            when(wasteService.getWasteOfCategoryByGroupId(eq(group.getGroupId()), eq(categoryName)))
                    .thenReturn(Optional.of(expectedWastes));

            ResponseEntity<List<Waste>> result = wasteController.getWasteOfCategoryByGroupId(group.getGroupId(), categoryNumber, adminUser);


            assertEquals(HttpStatus.OK, result.getStatusCode());

            assertNotNull(result.getBody());
            assertEquals(expectedWastes, result.getBody());
            verify(wasteService, times(1)).getWasteOfCategoryByGroupId(eq(group.getGroupId()), eq(categoryName));
        }

        @Test
        public void testGetWasteOfCategoryByGroupIdAsRegularUser() {
            int categoryNumber = 1;
            String categoryName = CategoryUtil.getCategoryName(categoryNumber);
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));

            when(wasteService.getWasteOfCategoryByGroupId(eq(group.getGroupId()), eq(categoryName)))
                    .thenReturn(Optional.of(expectedWastes));

            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(true);

            ResponseEntity<List<Waste>> result = wasteController.getWasteOfCategoryByGroupId(group.getGroupId(), categoryNumber, regularUser);


            assertNotNull(result.getBody());
            assertEquals(expectedWastes, result.getBody());
            verify(wasteService, times(1)).getWasteOfCategoryByGroupId(eq(group.getGroupId()), eq(categoryName));
        }

        @Test
        void notAuthorized(){
            int categoryNumber = 1;
            String categoryName = CategoryUtil.getCategoryName(categoryNumber);
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));


            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(false);

            ResponseEntity<List<Waste>> result = wasteController.getWasteOfCategoryByGroupId(group.getGroupId(), categoryNumber, regularUser);


            assertNull(result.getBody());
            verify(wasteService, times(0)).getWasteOfCategoryByGroupId(eq(group.getGroupId()), eq(categoryName));
        }
    }




    @Nested
    class testGetByGroupId{
        @Test
        void getAsAdmin() {
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));

            when(wasteService.getWasteByGroupId(eq(group.getGroupId())))
                    .thenReturn(Optional.of(expectedWastes));

            ResponseEntity<List<Waste>> result = wasteController.getWasteByGroupId(group.getGroupId(), adminUser);

            assertEquals(HttpStatus.OK, result.getStatusCode());

            assertNotNull(result.getBody());

            assertEquals(expectedWastes, result.getBody());

            verify(wasteService, times(1)).getWasteByGroupId(eq(group.getGroupId()));

        }

        @Test
        void getAsRegUser() {
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));

            when(wasteService.getWasteByGroupId(eq(group.getGroupId())))
                    .thenReturn(Optional.of(expectedWastes));

            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(true);

            ResponseEntity<List<Waste>> result = wasteController.getWasteByGroupId(group.getGroupId(), regularUser);

            assertEquals(HttpStatus.OK, result.getStatusCode());

            assertNotNull(result.getBody());

            assertEquals(expectedWastes, result.getBody());

            verify(wasteService, times(1)).getWasteByGroupId(eq(group.getGroupId()));

        }

        @Test
        void notAuthorized(){

            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(false);

            ResponseEntity<List<Waste>> result = wasteController.getWasteByGroupId(group.getGroupId(), regularUser);

            assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());

            assertNull(result.getBody());

            verify(wasteService, times(0)).getWasteByGroupId(eq(group.getGroupId()));
        }
    }


    @Nested
    class testGetByWasteId{
        @Test
        void asAdmin() {
            when(wasteService.getWasteById(eq(expectedWaste.getWasteId())))
                    .thenReturn(Optional.of(expectedWaste));

            ResponseEntity<Waste> result = wasteController.getWasteById(expectedWaste.getWasteId(), adminUser);

            assertEquals(HttpStatus.OK, result.getStatusCode());

            assertNotNull(result.getBody());

            assertEquals(expectedWaste, result.getBody());

            verify(wasteService, times(1)).getWasteById(eq(expectedWaste.getWasteId()));
        }

        @Test
        void asRegUser(){
            when(wasteService.getWasteById(eq(expectedWaste.getWasteId())))
                    .thenReturn(Optional.of(expectedWaste));

            when(wasteService.isUserAssosiatedWithWaste(eq(regularUser.getName()), eq(expectedWaste.getWasteId())))
                    .thenReturn(true);

            ResponseEntity<Waste> result = wasteController.getWasteById(expectedWaste.getWasteId(), regularUser);

            assertEquals(HttpStatus.OK, result.getStatusCode());

            assertNotNull(result.getBody());

            assertEquals(expectedWaste, result.getBody());

            verify(wasteService, times(1)).getWasteById(eq(expectedWaste.getWasteId()));
        }

        @Test
        void notFound(){
            when(wasteService.getWasteById(eq(expectedWaste.getWasteId())))
                    .thenReturn(Optional.empty());

            ResponseEntity<Waste> result = wasteController.getWasteById(expectedWaste.getWasteId(), adminUser);

            assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

            assertNull(result.getBody());

            verify(wasteService, times(1)).getWasteById(eq(expectedWaste.getWasteId()));
        }

        @Test
        void notAuthorized(){

            when(wasteService.isUserAssosiatedWithWaste(eq(regularUser.getName()), eq(expectedWaste.getWasteId())))
                    .thenReturn(false);

            ResponseEntity<Waste> result = wasteController.getWasteById(expectedWaste.getWasteId(), regularUser);

            assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());

            assertNull(result.getBody());

            verify(wasteService, times(0)).getWasteById(eq(expectedWaste.getWasteId()));
        }
    }

    @Nested
    class getInformationOfCakeGraph{

        @Test
        void asAdmin() {
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));


            when(wasteService.getCakeDiagram(group.getGroupId())).thenReturn(Optional.of(new double[]{1, 2, 3}));

            ResponseEntity<double[]> result = wasteController.getInformationOfCakeGraph(group.getGroupId(), adminUser);

            assertEquals(HttpStatus.OK, result.getStatusCode());

            assertNotNull(result.getBody());

            assertTrue(result.getBody().length > 0);

            verify(wasteService, times(1)).getCakeDiagram(eq(group.getGroupId()));
        }

        @Test
        void asRegularUser()  {
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));


            when(wasteService.getCakeDiagram(group.getGroupId())).thenReturn(Optional.of(new double[]{1, 2, 3}));
            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(true);

            ResponseEntity<double[]> result = wasteController.getInformationOfCakeGraph(group.getGroupId(), regularUser);


            assertEquals(HttpStatus.OK, result.getStatusCode());

            assertNotNull(result.getBody());

            assertTrue(result.getBody().length > 0);

            verify(wasteService, times(1)).getCakeDiagram(eq(group.getGroupId()));
        }

        @Test
        void notFound()  {
            List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));


            when(wasteService.getCakeDiagram(group.getGroupId())).thenThrow(new NoSuchElementException());
            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(true);

            assertThrows(NoSuchElementException.class,
                    ()-> wasteController.getInformationOfCakeGraph(group.getGroupId(), regularUser));



            verify(wasteService, times(1)).getCakeDiagram(eq(group.getGroupId()));
        }

        @Test
        void notAuthorized() {


            when(groupService.isUserAssociatedWithGroup(eq(regularUser.getName()), eq(group.getGroupId())))
                    .thenReturn(false);

            ResponseEntity<double[]> result = wasteController
                    .getInformationOfCakeGraph(group.getGroupId(), regularUser);

            assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());

            assertNull(result.getBody());

            verify(wasteService, times(0)).getCakeDiagram(eq(group.getGroupId()));
        }

    }

}