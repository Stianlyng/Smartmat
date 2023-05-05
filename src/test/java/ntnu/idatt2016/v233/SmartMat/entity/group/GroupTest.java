package ntnu.idatt2016.v233.SmartMat.entity.group;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    void testEquals() {
        Group group = Group.builder()
                .groupName("Test Group")
                .groupId(1L)
                .build();

        Group group2 = Group.builder()
                .groupId(2L)
                .groupName("Test Group2")
                .build();


        assertEquals(group, group);
        assertNotEquals(group, group2);
        assertNotEquals("hello", group);
    }

    @Test
    void testHashCode() {
        Group group = Group.builder()
                .groupName("Test Group")
                .groupId(1L)
                .build();

        Group group2 = Group.builder()
                .groupId(2L)
                .groupName("Test Group2")
                .build();

        assertEquals(group.hashCode(), group.hashCode());
        assertNotEquals(group.hashCode(), group2.hashCode());
    }

    @Test
    void testToString() {
        Group group = Group.builder()
                .groupName("Test Group")
                .groupId(1L)
                .build();

        assertEquals("Group(groupId=1, level=0, points=0, groupName=Test Group, linkCode=null, open=null, shoppingList=null, user=null, fridge=null, achievements=null)",
                group.toString());
    }

    @Test
    void builder() {
        Group.GroupBuilder builder = Group.builder();
        builder.groupName("Test Group");
        builder.groupId(1L);
        builder.user(null);
        builder.fridge(null);
        builder.shoppingList(null);
        builder.achievements(null);
        builder.level(0);
        builder.points(0);
        builder.linkCode(null);
        builder.open(null);

        assertNotNull(builder.toString());

        Group group = builder
                .build();

        assertEquals("Test Group", group.getGroupName());
        assertEquals(1L, group.getGroupId());

        assertNull(group.getUser());

        assertNull(group.getFridge());

        assertNull(group.getShoppingList());

        assertNull(group.getAchievements());

        assertEquals(0, group.getLevel());

        assertEquals(0, group.getPoints());

        assertNull(group.getLinkCode());

        assertNull(group.getOpen());

    }
}