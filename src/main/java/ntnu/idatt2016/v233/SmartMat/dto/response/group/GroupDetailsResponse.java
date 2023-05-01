package ntnu.idatt2016.v233.SmartMat.dto.response.group;

import lombok.Data;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;

import java.util.List;

@Data
public class GroupDetailsResponse {
    private long groupId;
    private String linkCode;
    private List<UserAuthorityInfo> userAuthorityInfoList;

    public GroupDetailsResponse(Group group, List<UserAuthorityInfo> userAuthorityInfoList) {
        this.groupId = group.getGroupId();
        this.linkCode = group.getLinkCode();
        this.userAuthorityInfoList = userAuthorityInfoList;
    }
}

