package ntnu.idatt2016.v233.SmartMat.service;

import ntnu.idatt2016.v233.SmartMat.dto.response.RecipeFridgeMatch;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This class defines the methods for the recipe service
 * 
 * @author Stian Lyng
 * @version 1.0
 */
@Service
public class RecipeService {
    
    /**
     * The recipe repository
     */
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new recipe service
     * @param recipeRepository
     */
    public RecipeService (RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    
    /**
     * Gets a recipe by its id
     * 
     * @param id the id of the recipe
     * @return an optional containing the recipe if it exists
     */
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    } 
    
    /**
     * Gets all recipes with a given name
     * 
     * @param name the name of the recipe
     * @return a list of recipes with the given name
     */
    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findAllByName(name);
    }
    
    /**
     * Gets all recipes
     * 
     * @return a list of all recipes
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    
    /**
     * Saves a recipe
     * 
     * @param recipe the recipe to save
     * @return the saved recipe
     */
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    /**
     * Deletes a recipe
     * 
     * @param recipe a recipe object to delete
     */
    public void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    /**
     * Deletes a recipe by its id
     *
     * @param id the id of the recipe to delete
     */
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }


    /**
     * Adds a user to a recipe
     * used for adding favorite recipes
     * @param recipe recipe to add user to
     * @param user user to add to recipe
     */
    public void addUserToRecipe(Recipe recipe, User user){
        recipe.addUser(user);
        recipeRepository.save(recipe);
    }

    public List<RecipeFridgeMatch> getRecipeWithFridgeProductMatch(long fridgeId, long recipeId) {
    
        List<Object[]> rawData = recipeRepository.findRecipeWithMatchingProductsInFridge(fridgeId, recipeId);

        List<RecipeFridgeMatch> result = rawData.stream()
            .map(row -> new RecipeFridgeMatch(
                (Integer) row[0],
                (String) row[1],
                (String) row[2],
                (Long) row[3],
                (String) row[4],
                (String) row[5],
                (Boolean) row[6]
            ))
            .collect(Collectors.toList());
            
                return result;
            }

    /**
     * Adds a recipe to a users favorites
     * @param recipeId id of the recipe
     * @param name name of the user
     * @return ResponsEntity with succsess/fail message
     */
    public ResponseEntity<String> addRecipeToFavorites(Long recipeId, String name) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        Optional<User> user = userRepository.findByUsername(name);
        if (recipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find Recipe");
        } else if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find User");
        } else {
            if (user.get().getRecipes().contains(recipe.get())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Recipe already in favorites");
            }

            user.get().addRecipe(recipe.get());
            recipe.get().addUser(user.get());
            userRepository.save(user.get());
            return ResponseEntity.ok("Recipe added to favorites");
        }

    }

    /**
     * Removes a recipe from a users favorites
     * @param recipeId id of the recipe
     * @param name name of the user
     * @return ResponseEntity with succsess/fail message
     */
    public ResponseEntity<String> removeRecipeFromFavorites(Long recipeId, String name) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        Optional<User> user = userRepository.findByUsername(name);

        if (recipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find Recipe");
        } else if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find User");
        } else {
            if (!user.get().getRecipes().contains(recipe.get())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not in favorites");
            }

            user.get().getRecipes().remove(recipe.get());
            recipe.get().getUsers().remove(user.get());
            userRepository.save(user.get());
            return ResponseEntity.ok("Recipe deleted from favorites");
        }
    }
}