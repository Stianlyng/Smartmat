package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;

import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query( value = """
        SELECT r.recipe_id, r.recipe_name,r.recipe_description,r.image_url, COUNT(fp.ean) as product_count
        FROM recipe r
        LEFT JOIN recipe_product rp ON r.recipe_id = rp.recipe_id
        LEFT JOIN fridge_product fp ON rp.ean = fp.ean AND fp.fridge_id = :fridgeId
        GROUP BY r.recipe_id, r.recipe_name
        ORDER BY product_count DESC
        LIMIT 5;
        """, nativeQuery = true)
    List<Object[]> findWeeklyMenu(@Param("fridgeId") long fridgeId);
    
    @Query( value = """
        SELECT r.recipe_id, r.recipe_name, r.recipe_description, p.ean, p.item_name, p.description as product_description,
            CASE WHEN fp.fridge_id IS NOT NULL THEN TRUE ELSE FALSE END as in_fridge
        FROM recipe AS r
        JOIN recipe_product AS rp ON r.recipe_id = rp.recipe_id
        JOIN product AS p ON rp.ean = p.ean
        LEFT JOIN fridge_product AS fp ON p.ean = fp.ean AND fp.fridge_id = :fridgeId
        WHERE r.recipe_id = :recipeId ;
            """ , nativeQuery = true)
    List<Object[]> findRecipeWithMatchingProductsInFridge(long fridgeId, long recipeId);
    
}
