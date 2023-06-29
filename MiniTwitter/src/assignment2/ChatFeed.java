package assignment2;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

// Observer 

public class ChatFeed implements PropertyChangeListener {

    private ArrayList<String> tweets;
    private User user;

    public ChatFeed() {
        tweets = new ArrayList<String>();
    }

    public void printTweets() {
        System.out.println("Chat Feed:");
        tweets.forEach(System.out::println);
    }

    // listen for change when user posts
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setUser((User) evt.getSource());
        tweets.add((String) evt.getNewValue());
    }

    public ArrayList<String> getTweets() {
        return tweets;
    }
    
    public void addTweet(String tweet) {
        tweets.add(tweet);
    }

    public void setTweets(ArrayList<String> tweets) {
        this.tweets = tweets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}