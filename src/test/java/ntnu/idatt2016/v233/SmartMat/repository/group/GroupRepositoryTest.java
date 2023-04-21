package ntnu.idatt2016.v233.SmartMat.repository.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.repository.user.AchievementRepository;
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
    void shouldFindAllByAchievementsAchievementName() {
        // given
        Achievement achievement = Achievement.builder().achievementName("test achievement").build();
        Group group1 = Group.builder().groupName("group 1").achievements(List.of(achievement)).build();
        Group group2 = Group.builder().groupName("group 2").achievements(List.of(achievement)).build();
        Group group3 = Group.builder().groupName("group 3").build();

        achievementRepository.save(achievement);

        groupRepository.saveAll(List.of(group1, group2, group3));

        // when
        List<Group> result = groupRepository.findAllByAchievementsAchievementName("test achievement");

        // then
        assertThat(result).containsExactlyInAnyOrder(group1, group2);
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
        Optional<Group> tempGroupe = groupRepository.findByGroupName("test");


        assertTrue(tempGroupe.isPresent());

        Achievement tempAchievement = Achievement.builder().achievementName("test").build();

        achievementRepository.save(tempAchievement);
        Group tempGroup = tempGroupe.get();


        tempGroup.setAchievements(new ArrayList<>(Collections.singletonList(tempAchievement)));

        groupRepository.save(tempGroup);

        assertTrue(groupRepository.findByGroupName("test").isPresent());

        assertTrue(groupRepository.findByGroupName("test").get().getAchievements().contains(tempAchievement));

        assertNull(groupRepository.findByGroupName("test").get().getAchievements().get(0).getGroups());

    }

}