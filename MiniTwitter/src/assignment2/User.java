package assignment2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// Observer & Composite

public class User {

    private String tweet;
    private String username;
    private PropertyChangeSupport support;
    private ChatFeed myFeed;
    private ArrayList<User> followers;
    private ArrayList<User> following;
    private ArrayList<String> tweetList;
    //A3
    private long creationTime;
    private long lastUpdateTime;


    public User() {

    }
   
    //constructor
    public User(String username) {
        setUsername(username);
        tweet = "";
        support = new PropertyChangeSupport(this);
        myFeed = new ChatFeed();
        myFeed.setUser(this);
        followers = new ArrayList<>();
        following = new ArrayList<>();
        tweetList = new ArrayList<>();
        addPropertyChangeListener(myFeed);
        //A3
        System.out.println("Creation time:");
        creationTime = System.currentTimeMillis();
        showCreationTime(creationTime);

    }

    //print out time on console
    private void showCreationTime(long creationTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(creationTime);
        System.out.println(sdf.format(resultdate));
    }
    
    private void showUpdateTime() {
        System.out.println("Last update: ");
        lastUpdateTime = System.currentTimeMillis();
        showCreationTime(lastUpdateTime);
    }

    // adds a property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) throws NullPointerException{
        support.addPropertyChangeListener(listener);
        showUpdateTime(); //A3
    }

    public void addFollowers(User newFollower) {
        followers.add(newFollower);
    }

    public void addFollowing(User newFollowing) {
        following.add(newFollowing);
        newFollowing.addFollowers(this);
    }

    // property change to all the listeners when a post is sent
    public void addTweet(String tweet) {
        support.firePropertyChange("tweet", this.tweet, tweet);
        tweetList.add(tweet);
        setTweet(tweet);
        showUpdateTime();
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public ChatFeed getMyFeed() {
        return myFeed;
    }

    public void setMyFeed(ChatFeed myFeed) {
        this.myFeed = myFeed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }
    
    //A3
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}