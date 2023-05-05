package ntnu.idatt2016.v233.SmartMat.repository.product;

import java.util.List;

import ntnu.idatt2016.v233.SmartMat.entity.product.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This interface defines the methods for the recipe repository
 * 
 * @author Stian Lyng
 * @version 2.0
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /**
     * Gets a recipe by its name
     * @param name the name of the recipe
     * @return an optional containing the recipe if it exists
     */
    List<Recipe> findAllByName(String name);

    /**
     * Gets a weekly menu for a fridge
     * @param fridgeId the id of the fridge
     * @return a list of objects containing the name of the product and the id of the fridge
     */
    @Query( value = """
        select p2.item_name, f.fridge_id  
        from fridge f 
        inner join fridge_product fp on f.fridge_id = fp.fridge_id and f.fridge_id = :fridgeId 
        inner join product p2 on p2.ean = fp.ean
        """, nativeQuery = true)
    List<Object[]> findWeeklyMenu(long fridgeId);

    /**
     * Gets a list of recipes with their products
     * @return a list of objects containing the recipe id, name, description, image url and product name
     */
    @Query( value = """
        SELECT r.recipe_id, r.recipe_name, r.recipe_description, r.image_url, p.item_name
        FROM recipe r
        inner JOIN recipe_product rp ON r.recipe_id = rp.recipe_id
        inner join product p on rp.ean = p.ean 
            """ , nativeQuery = true)
    List<Object[]> findRecipeProductsWithName();
    
}
