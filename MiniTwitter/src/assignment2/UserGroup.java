package assignment2;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// Composite


public class UserGroup extends User {

    private ArrayList<User> users;
    private String groupName;
    long creationTime; //A3

    public UserGroup(String groupName) {
        super();
        users = new ArrayList<>();
        this.groupName = groupName;
        //A3
        creationTime = System.currentTimeMillis();
        showCreationTime(creationTime);
    }
    
    //displays time
    private void showCreationTime(long creationTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(creationTime);
        System.out.println("Creation time: " + sdf.format(resultdate));
    }

    void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}