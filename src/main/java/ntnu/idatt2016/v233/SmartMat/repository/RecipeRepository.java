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

    /**
     * Finds the top 5 recipes with products that have a match with items in the given fridge.
     * Returns a list of Object arrays, where each array contains the recipe details and product information.
     *
     * @param fridgeId the ID of the fridge to use for matching products
     * @return a list of Object arrays with recipe and product details
     */
    @Query(value = """
        WITH fridge_products AS (
            SELECT ean
            FROM public.fridge_product
            WHERE fridge_id = :fridgeId
        ),
        matched_products_count AS (
            SELECT
                r.recipe_id,
                r.recipe_name,
                COUNT(*) AS product_count
            FROM
                public.recipe_product rp
                JOIN public.recipe r ON rp.recipe_id = r.recipe_id
                JOIN fridge_products fp ON rp.ean = fp.ean
            GROUP BY
                r.recipe_id,
                r.recipe_name
        ),
        top_5_recipes AS (
            SELECT
                recipe_id,
                recipe_name,
                product_count
            FROM
                matched_products_count
            ORDER BY
                product_count DESC,
                recipe_id
            LIMIT 5
        )
        SELECT
            t.recipe_id,
            t.recipe_name,
            p.ean,
            p.item_name,
            p.description,
            (rp.ean IN (SELECT ean FROM fridge_products)) AS in_fridge
        FROM
            top_5_recipes t
            JOIN public.recipe_product rp ON t.recipe_id = rp.recipe_id
            JOIN public.product p ON rp.ean = p.ean
        ORDER BY
            t.recipe_id,
            p.ean;
        """, nativeQuery = true)
    List<Object[]> findTop5RecipesWithProducts(@Param("fridgeId") long fridgeId);
}
