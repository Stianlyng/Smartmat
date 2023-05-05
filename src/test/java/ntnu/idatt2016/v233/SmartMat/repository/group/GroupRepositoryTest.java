package ntnu.idatt2016.v233.SmartMat.repository.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ntnu.idatt2016.v233.SmartMat.entity.group.Achievement;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    private UserRepository userRepository;


    private Group group;

    @BeforeEach
    public void setUp() {
        group = Group.builder().groupName("test").build();
        entityManager.persist(group);
    }
    @Test
    void shouldFindByGroupName() {
        Optional<Group> result = groupRepository.findByGroupName("test");

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(group);
    }

    @Test
    void shouldFindById() {
        // given
        Group group = Group.builder().groupName("test group").build();
        groupRepository.save(group);

        // when
        Optional<Group> result = groupRepository.findById(group.getGroupId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(group);
    }

    @Test
    void shouldSave() {
        // given
        Group group = Group.builder().groupName("test group").build();

        // when
        groupRepository.save(group);

        // then
        assertThat(group.getGroupId()).isGreaterThan(0L);
    }

    @Test
    void shouldDelete() {
        // given
        Group group = Group.builder().groupName("test group").build();
        groupRepository.save(group);

        // when
        groupRepository.delete(group);

        // then
        assertThat(groupRepository.findById(group.getGroupId())).isEmpty();
    }

    @Test
    void shouldDeleteById() {
        // given
        Group group = Group.builder().groupName("test group").build();
        groupRepository.save(group);

        // when
        groupRepository.deleteById(group.getGroupId());

        // then
        assertThat(groupRepository.findById(group.getGroupId())).isEmpty();
    }

    @Test
    void groupShouldHaveAchivments(){

        Group tempgroup = Group.builder().groupName("testiossxjak").build();
        Achievement tempAchievement = Achievement.builder().achievementName("test2").build();

        tempgroup.setAchievements(new ArrayList<>(Collections.singletonList(tempAchievement)));
        groupRepository.save(tempgroup);

        Optional<Group> tempGroupe = groupRepository.findByGroupName("testiossxjak");


        assertTrue(tempGroupe.isPresent());


        assertTrue(tempGroupe.get().getAchievements().contains(tempAchievement));

        assertNull(tempGroupe.get().getAchievements().get(0).getGroups());

    }


}