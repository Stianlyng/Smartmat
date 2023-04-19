package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.Recipe;

/**
 * This interface defines the methods for the recipe repository
 * 
 * @author Stian Lyng
 * @version 1.0
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /**
     * Gets a recipe by its name
     * @param name the name of the recipe
     * @return an optional containing the recipe if it exists
     */
    Optional<Recipe> getByName(String name);

}
