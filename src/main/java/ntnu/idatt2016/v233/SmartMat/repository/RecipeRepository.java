package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;

import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface defines the methods for the recipe repository
 * 
 * @author Stian Lyng
 * @version 1.0
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /**
     * Gets a recipe by its name
     * @param name the name of the recipe
     * @return an optional containing the recipe if it exists
     */
    List<Recipe> findAllByName(String name);

}
