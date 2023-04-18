package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.model.Recipe;

/**
 * This interface defines the methods for the recipe repository
 * 
 * @author Stian Lyng
 * @version 1.0
 */
public interface RecipeRepository {

    /**
     * Saves a recipe to the database
     * 
     * @param recipe the recipe to save
     */
    Recipe save (Recipe recipe);

    /**
     * Gets a recipe by its ID
     * 
     * @param id the ID of the recipe
     * @return an optional containing the recipe if it exists
     */
    Optional<Recipe> getById(long id);

    /**
     * Gets a recipe by its name
     * @param name the name of the recipe
     * @return an optional containing the recipe if it exists
     */
    Optional<Recipe> getByName(String name);

    /**
     * Gets all recipes
     * 
     * @return an optional containing a list of all recipes
     */
    Optional<List<Recipe>> getAll();
    
    /**
     * Deletes a recipe by its ID
     * 
     * @param id the ID of the recipe
     */
    void deleteById(int id);
    
}
