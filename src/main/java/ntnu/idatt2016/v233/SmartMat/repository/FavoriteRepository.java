package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idatt2016.v233.SmartMat.entity.Favourite;

public interface FavoriteRepository extends JpaRepository<Favourite, Long> {

    /**
     * Gets all favorites by username
     * 
     * @param id the username
     * @return a list of all favorites if they exist
     */
    List<Favourite> getAllByUsername(String name);
   
    /**
     * Gets all favorites by recipe ID
     * 
     * @param id the ID of the recipe
     * @return a list of all favorites if they exist
     */
    List<Favourite> getAllByRecipeId(long id);

}
