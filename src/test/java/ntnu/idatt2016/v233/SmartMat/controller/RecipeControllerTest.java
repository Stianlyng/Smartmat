package ntnu.idatt2016.v233.SmartMat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Recipe recipe1;
    private Recipe recipe2;

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
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        objectMapper = new ObjectMapper();

        recipe1 = Recipe.builder()
                .id(1L)
                .name("Recipe 1")
                .description("Recipe 1 description")
                .build();

        recipe2 = Recipe.builder()
                .id(2L)
                .name("Recipe 2")
                .description("Recipe 2 description")
                .build();
    }

    @Test
    void getRecipeById() throws Exception {
        when(recipeService.getRecipeById(recipe1.getId())).thenReturn(Optional.of(recipe1));

        mockMvc.perform(get("/api/recipe/id/{id}", recipe1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(recipe1)));
    }

    @Test
    void getRecipeById_NotFound() throws Exception {
        when(recipeService.getRecipeById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/recipe/id/{id}", 3L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRecipeByName() throws Exception {
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
        when(recipeService.getRecipesByName("Recipe")).thenReturn(recipes);

        mockMvc.perform(get("/api/recipe/name/{name}", "Recipe"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(recipes)));
    }

    @Test
    void getRecipeByName_NotFound() throws Exception {
        when(recipeService.getRecipesByName("Nonexistent")).thenReturn(List.of());

        mockMvc.perform(get("/api/recipe/name/{name}", "Nonexistent"))
                .andExpect(status().isNotFound());
    }


    @Test
    void addRecipeToFavorites_Success() throws Exception {
        Long recipeId = 1L;
        when(recipeService.addRecipeToFavorites(recipeId, regularUser.getName())).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = recipeController.addRecipeToFavorites(recipeId, regularUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addRecipeToFavorites_AdminUser() throws Exception {
        Long recipeId = 1L;
        when(recipeService.addRecipeToFavorites(recipeId, adminUser.getName())).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = recipeController.addRecipeToFavorites(recipeId, adminUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
