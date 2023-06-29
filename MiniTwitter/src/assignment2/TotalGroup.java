package assignment2;

import java.util.ArrayList;

// Visitor

public class TotalGroup implements AdminAnalytics {

    private ArrayList<UserGroup> userGroupList;

    public TotalGroup() {
        userGroupList = new ArrayList<UserGroup>();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void addGroup(UserGroup userGroup) {
        userGroupList.add(userGroup);
    }

    public int getGroupTotal() {
        return userGroupList.size();
    }

    public ArrayList<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(ArrayList<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

}
