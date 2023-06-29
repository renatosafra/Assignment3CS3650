package assignment2;

import java.util.ArrayList;

// Visitor

public class TotalUser implements AdminAnalytics {

    private ArrayList<User> userList;

    public TotalUser() {
        userList = new ArrayList<User>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getUserTotal() {
        return userList.size();
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}