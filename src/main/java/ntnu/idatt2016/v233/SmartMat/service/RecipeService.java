package ntnu.idatt2016.v233.SmartMat.service;

import ntnu.idatt2016.v233.SmartMat.dto.response.RecipeDetails;
import ntnu.idatt2016.v233.SmartMat.dto.response.RecipeWithMatchCount;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * Returns a list of RecipeWithMatchCount objects representing the weekly menu based on ingredients in a fridge.
     * The list contains recipes with matched ingredients and additional recipes with no matching ingredients
     * (if there are less than 5 recipes with matching ingredients). Recipes with 0 match count are shuffled before returning.
     *
     * @param fridgeId The ID of the fridge to get the weekly menu for
     * @return A list of RecipeWithMatchCount objects representing the weekly menu
     */
    public List<RecipeWithMatchCount> getWeeklyMenu(Integer fridgeId) {
        // Get the results from both repository methods
        List<Object[]> weeklyMenu = recipeRepository.findWeeklyMenu(fridgeId);
        List<Object[]> recipeProducts = recipeRepository.findRecipeProductsWithName();
    
        // Prepare a map to store RecipeDetails with their match count
        Map<RecipeDetails, Integer> recipeMatchCountMap = new HashMap<>();
        
        // Compare the item_name on both lists
        for (Object[] menuRow : weeklyMenu) {
            String menuRowItemName = ((String) menuRow[0]).toLowerCase();
            for (Object[] recipeProductRow : recipeProducts) {
                String recipeProductRowItemName = ((String) recipeProductRow[4]).toLowerCase();
                List<String> recipeProductWords = Arrays.asList(recipeProductRowItemName.split("\\s+"));
    
                boolean allWordsContained = recipeProductWords.stream()
                        .allMatch(word -> menuRowItemName.contains(word));
    
                if (allWordsContained) {
                    Integer recipeId = ((Integer) recipeProductRow[0]).intValue();
                    String recipeName = (String) recipeProductRow[1];
                    String recipeDescription = (String) recipeProductRow[2];
                    String recipeImage = (String) recipeProductRow[3];
                    RecipeDetails recipeDetails = new RecipeDetails(recipeId, recipeName, recipeDescription, recipeImage);
    
                    recipeMatchCountMap.put(recipeDetails, recipeMatchCountMap.getOrDefault(recipeDetails, 0) + 1);
                }
            }
        }
    
        // Get a list of unique RecipeDetails from recipeProducts
        List<RecipeDetails> uniqueRecipeDetails = recipeProducts.stream()
            .map(row -> new RecipeDetails(((Integer) row[0]).intValue(), (String) row[1], (String) row[2], (String) row[3]))
            .distinct()
            .collect(Collectors.toList());
    
        // Convert the map to a list of RecipeWithMatchCount
        List<RecipeWithMatchCount> commonRecipes = recipeMatchCountMap.entrySet().stream()
                .map(entry -> new RecipeWithMatchCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    
        // Add additional recipes from uniqueRecipeDetails with a count of 0 if the list has less than 5 recipes
        List<RecipeWithMatchCount> zeroMatchRecipes = new ArrayList<>();
        for (RecipeDetails recipeDetails : uniqueRecipeDetails) {
            if (commonRecipes.size() < 5 && !recipeMatchCountMap.containsKey(recipeDetails)) {
                zeroMatchRecipes.add(new RecipeWithMatchCount(recipeDetails, 0));
            }
        }

    // Shuffle the zeroMatchRecipes list
    Collections.shuffle(zeroMatchRecipes);

    // Combine the commonRecipes and zeroMatchRecipes lists
    for (RecipeWithMatchCount zeroMatchRecipe : zeroMatchRecipes) {
        if (commonRecipes.size() < 5) {
            commonRecipes.add(zeroMatchRecipe);
        } else {
            break;
        }
    }
        return commonRecipes;
    }
}