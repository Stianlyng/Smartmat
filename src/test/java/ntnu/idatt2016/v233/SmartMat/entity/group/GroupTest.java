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
}