package ntnu.idatt2016.v233.SmartMat.service.user;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.user.AuthorityTable;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.user.AuthoritesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * AuthoritesService is a service for Authorites.
 * It is used to get an authority by the authority.
 * If the authority does not exist, it will be created.
 * @author Birk
 * @version 1.0
 * @since 25.04.2023
 */
@Service
@AllArgsConstructor
public class AuthoritesService {
    private final AuthoritesRepository authoritesRepository;

    /**
     * Gets an authority by the authority.
     * @param auth The authority to get
     * @return The authority if it exists, otherwise it will be created and returned
     */
    public AuthorityTable getAuthorityByAuth(Authority auth){

        Optional<AuthorityTable> temp =  authoritesRepository.findById(auth);

        return temp.orElseGet(() -> authoritesRepository.save(new AuthorityTable(auth)));

    }

    /**
     * Adds a user to an authority.
     * @param auth The authority to add the user to
     * @param user The user to add to the authority
     */
    public void addUserToAuthoriy(AuthorityTable auth, User user){
        auth.addUser(user);
    }

}
