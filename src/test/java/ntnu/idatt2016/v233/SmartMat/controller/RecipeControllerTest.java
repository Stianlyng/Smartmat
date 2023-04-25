package ntnu.idatt2016.v233.SmartMat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
