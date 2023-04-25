package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserGroupAssoRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserGroupAssoRepository userGroupAssoRepository;

    @BeforeEach
    void setUp() {
        UserGroupAsso tempAsso = new UserGroupAsso();

        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setUsername("test");

        entityManager.persist(user);

        Group tempgroup = new Group();
        tempgroup.setGroupName("test");

        entityManager.persist(tempgroup);

        tempAsso.setId(UserGroupId.builder().username("test").groupId(1L).build());
        tempAsso.setGroup(tempgroup);
        tempAsso.setUser(user);
        tempAsso.setPrimaryGroup(true);

        tempgroup.addUser(tempAsso);
        user.addGroup(tempAsso);


        entityManager.persist(tempAsso);

    }

    @Test
    void shouldFindByGroupGroupId() {
        Optional<UserGroupAsso> foundasso = userGroupAssoRepository.findById(UserGroupId.builder()
                .groupId(1L).username("test").build());

        assertTrue(foundasso.isPresent());

        assertEquals(foundasso.get().getGroup().getGroupId(), 1L);

        assertNotNull(foundasso.get().getUser());

    }

    @Test
    void shouldBeConnectionBetweenUserAndGroup(){
        assertNotNull(entityManager.find(User.class, "test").getGroup());

        entityManager.find(User.class, "test").getGroup().forEach(asso -> {
            assertEquals(asso.getGroup().getGroupId(), 1L);
        });

        entityManager.find(Group.class, 1L).getUser().forEach(asso -> {
            assertEquals(asso.getUser().getUsername(), "test");
        });
    }


}