package ntnu.idatt2016.v233.SmartMat.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ntnu.idatt2016.v233.SmartMat.entity.Recipe;

@DataJpaTest
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testGetByName() {
        Recipe recipe = Recipe.builder()
                .name("Pizza Margherita")
                .description("The classic Italian pizza")
                .build();
        recipeRepository.save(recipe);

        List<Recipe> foundRecipes = recipeRepository.findAllByName("Pizza Margherita");
        assertFalse(foundRecipes.isEmpty());
        assertEquals(1, foundRecipes.size());
        assertEquals(recipe.getName(), foundRecipes.get(0).getName());
    }

    @Test
    public void testDeleteById() {
        Recipe recipe = Recipe.builder()
                .name("Pizza Margherita")
                .description("The classic Italian pizza")
                .build();
        recipeRepository.save(recipe);
        long id = recipe.getId();

        recipeRepository.deleteById(id);
        assertFalse(recipeRepository.findById(id).isPresent());
    }

    @Test
    public void testSave() {
        Recipe recipe = Recipe.builder()
                .name("Pizza Margherita")
                .description("The classic Italian pizza")
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);

        assertEquals(recipe.getName(), savedRecipe.getName());
        assertEquals(recipe.getDescription(), savedRecipe.getDescription());
    }

    @Test
    public void testFindAll() {
        Recipe recipe1 = Recipe.builder()
                .name("Pizza Margherita")
                .description("The classic Italian pizza")
                .build();
        Recipe recipe2 = Recipe.builder()
                .name("Lasagna Bolognese")
                .description("The classic Italian pasta dish")
                .build();
        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        List<Recipe> foundRecipes = recipeRepository.findAll();
        assertEquals(2, foundRecipes.size());
        assertEquals(recipe1.getName(), foundRecipes.get(0).getName());
        assertEquals(recipe2.getName(), foundRecipes.get(1).getName());
    }

}
