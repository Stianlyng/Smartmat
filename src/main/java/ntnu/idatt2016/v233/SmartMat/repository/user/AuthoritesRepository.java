package ntnu.idatt2016.v233.SmartMat.repository.user;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.user.AuthorityTable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthoritesRepository is a repository for Authorites.
 */
public interface AuthoritesRepository extends JpaRepository<AuthorityTable, Authority> {
}
