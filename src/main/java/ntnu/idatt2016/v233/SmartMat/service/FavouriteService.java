package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.dto.request.FavoriteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.Favourite;
import ntnu.idatt2016.v233.SmartMat.repository.FavoriteRepository;

/**
 * Service for the favorites enitity
 * 
 * @author Stian Lyng
 * @version 1.1
 */
@Service
public class FavouriteService {
    
    @Autowired
    FavoriteRepository favoriteRepository;
    
    /**
     * Create and save a shopping list to the database
     * @param shoppingList the shopping list to save
     * @return the saved shopping list
     */
    public Favourite addFav(FavoriteRequest request) {
        Favourite favourite = new Favourite();
        favourite.setRecipeId(request.getRecipeId());
        favourite.setUsername(request.getUsername());
        return favoriteRepository.save(favourite);
    }
    
    /**
     * Gets all favorites
     * 
     * @return a list of all favorites if they exist
     */
    public List<Favourite> getAllFavorites() {
        return favoriteRepository.findAll();
    } 
    
    /**
     * Gets all favorites by recipe ID
     * 
     * @param id the ID of the recipe 
     * @return a list of all favorites if they exist
     */
    public List<Favourite> getAllFavoritesByRecipeId(long id) {
        return favoriteRepository.getAllByRecipeId(id);
    }

    /**
     * Gets all favorites by username
     * 
     * @param id the username of the user
     * @return a list of all favorites if they exist
     */
    public List<Favourite> getAllFavoritesByUsername(String name) {
        return favoriteRepository.getAllByUsername(name);
    }

    /**
     * Deletes a favorite by its ID
     * 
     * @param id the ID of the favorite entry 
     */
    public void deleteFavoriteById(long id) {
        Optional<Favourite> favorite = favoriteRepository.findById(id);
        if (favorite.isPresent()) {
            favoriteRepository.deleteById(id);
        }
    }
}
