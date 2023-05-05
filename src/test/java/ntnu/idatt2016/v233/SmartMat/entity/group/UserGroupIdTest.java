package ntnu.idatt2016.v233.SmartMat.entity.group;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGroupIdTest {

    @Test
    void testEquals() {
        UserGroupId userGroupId = UserGroupId.builder()
                .groupId(1)
                .username("test")
                .build();

        UserGroupId userGroupId1 = UserGroupId.builder()
                .groupId(1)
                .username("test")
                .build();

        assertEquals(userGroupId, userGroupId1);

        userGroupId1.setGroupId(2);

        assertNotEquals(userGroupId, userGroupId1);

        userGroupId1.setGroupId(1);

        assertEquals(userGroupId, userGroupId);

        assertNotEquals(userGroupId, null);

        assertNotEquals(userGroupId, new Object());

        assertNotEquals(userGroupId, new UserGroupId());

    }

    @Test
    void testHashCode() {
        UserGroupId userGroupId = UserGroupId.builder()
                .groupId(1)
                .username("test")
                .build();

        UserGroupId userGroupId1 = UserGroupId.builder()
                .groupId(1)
                .username("test")
                .build();

        assertEquals(userGroupId.hashCode(), userGroupId1.hashCode());

        userGroupId1.setGroupId(2);

        assertNotEquals(userGroupId.hashCode(), userGroupId1.hashCode());
    }
}