package ntnu.idatt2016.v233.SmartMat.service;

import ntnu.idatt2016.v233.SmartMat.dto.response.RecipeWithMatchCount;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.RecipeRepository;
import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {


    @InjectMocks
    private RecipeService recipeService;
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;


    private Recipe recipe1;
    private Recipe recipe2;
    private User user;

    @BeforeEach
    void setUp() {
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

        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
    }

    @Test
    void getRecipeById() {
        when(recipeRepository.findById(recipe1.getId())).thenReturn(Optional.of(recipe1));

        Optional<Recipe> result = recipeService.getRecipeById(recipe1.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(recipe1);
        verify(recipeRepository).findById(recipe1.getId());
    }

    @Test
    void getRecipesByName() {
        List<Recipe> expectedRecipes = Arrays.asList(recipe1, recipe2);
        when(recipeRepository.findAllByName(recipe1.getName())).thenReturn(expectedRecipes);

        List<Recipe> result = recipeService.getRecipesByName(recipe1.getName());

        assertThat(result).isEqualTo(expectedRecipes);
        verify(recipeRepository).findAllByName(recipe1.getName());
    }

    @Test
    void getAllRecipes() {
        List<Recipe> expectedRecipes = Arrays.asList(recipe1, recipe2);
        when(recipeRepository.findAll()).thenReturn(expectedRecipes);

        List<Recipe> result = recipeService.getAllRecipes();

        assertThat(result).isEqualTo(expectedRecipes);
        verify(recipeRepository).findAll();
    }

    @Test
    void saveRecipe() {
        when(recipeRepository.save(recipe1)).thenReturn(recipe1);

        Recipe result = recipeService.saveRecipe(recipe1);

        assertThat(result).isEqualTo(recipe1);
        verify(recipeRepository).save(recipe1);
    }

    @Test
    void deleteRecipe() {
        doNothing().when(recipeRepository).delete(recipe1);

        recipeService.deleteRecipe(recipe1);

        verify(recipeRepository).delete(recipe1);
    }

    @Test
    void deleteRecipeById() {
        doNothing().when(recipeRepository).deleteById(recipe1.getId());

        recipeService.deleteRecipeById(recipe1.getId());

        verify(recipeRepository).deleteById(recipe1.getId());
    }

    @Test
    void addUserToRecipe() {
        when(recipeRepository.save(recipe1)).thenReturn(recipe1);

        recipeService.addUserToRecipe(recipe1, user);

        assertThat(recipe1.getUsers()).contains(user);
        verify(recipeRepository).save(recipe1);
    }


    

    @Test
    public void testGetWeeklyMenu() {
        // Mock the repository methods to return sample data
        List<Object[]> weeklyMenu = new ArrayList<>();
        weeklyMenu.add(new Object[]{"bread"});
        weeklyMenu.add(new Object[]{"milk"});
        weeklyMenu.add(new Object[]{"butter"});
        when(recipeRepository.findWeeklyMenu(1)).thenReturn(weeklyMenu);

        List<Object[]> recipeProducts = new ArrayList<>();
        recipeProducts.add(new Object[]{1, "Recipe 1", "Description 1", "Image 1", "bread milk"});
        recipeProducts.add(new Object[]{2, "Recipe 2", "Description 2", "Image 2", "bread butter cheese"});
        recipeProducts.add(new Object[]{3, "Recipe 3", "Description 3", "Image 3", "eggs milk cheese"});
        when(recipeRepository.findRecipeProductsWithName()).thenReturn(recipeProducts);

        // Call the service method
        List<RecipeWithMatchCount> weeklyMenuRecipes = recipeService.getWeeklyMenu(1);

        // Verify the expected output
        assertEquals(3, weeklyMenuRecipes.size());
        assertNotNull(weeklyMenuRecipes.get(0));
        // Verify that the repository methods were called with the expected parameter
        verify(recipeRepository).findWeeklyMenu(1);
        verify(recipeRepository).findRecipeProductsWithName();
    }
}
