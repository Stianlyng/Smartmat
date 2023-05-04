package ntnu.idatt2016.v233.SmartMat.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.service.RecipeService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is responsible for handling requests related to recipes
 * 
 * @author Stian Lyng
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    /**
     * The recipe service
     */
    private final RecipeService recipeService;   

    /**
     * Gets a recipe by its id
     * @param id the id of the recipe
     * @return the recipe if it exists, otherwise 404
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Gets all recipes with a given name
     *
     * @param name the name of the recipe
     * @return a list of recipes with the given name, otherwise 404
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Recipe>> getRecipeByName(@PathVariable("name") String name) {
        List<Recipe> recipes = recipeService.getRecipesByName(name);
        if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }


    /**
     * add recipe to favorites in user
     * @param recipeId the id of the recipe
     * @param authentication the authentication object
     * @return 200 if the recipe was added to favorites, otherwise 404
     */
    @PostMapping("/favorite/{recipeId}")
    public ResponseEntity<String> addRecipeToFavorites(@PathVariable("recipeId") Long recipeId,
                                                       Authentication authentication) {
        return recipeService.addRecipeToFavorites(recipeId, authentication.getName());
    }

    /**
     * remove recipe from favorites in user
     * @param recipeId the id of the recipe
     * @param authentication the authentication object
     * @return 200 if the recipe was removed from favorites, otherwise 404
     */
    @DeleteMapping("/favorite/{recipeId}")
    public ResponseEntity<String> removeRecipeFromFavorites(@PathVariable("recipeId") Long recipeId,
                                                            Authentication authentication) {
        return recipeService.removeRecipeFromFavorites(recipeId, authentication.getName());
    }

}
