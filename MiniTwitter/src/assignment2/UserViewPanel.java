package assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Singleton

@SuppressWarnings("serial")
public class UserViewPanel extends JFrame implements ActionListener {
	
    // labels
    private JLabel userIdLabel;
    private JLabel followingLabel;
    private JLabel tweetLabel;
    private JLabel chatFeedLabel;
    private JLabel userIdLabel2;

    // texts
    private JTextArea tweetMessage;
    private JTextField userIdTextField;

    // buttons
    private JButton postTweet;
    private JButton followUser;

    // lists
    private JList<String> currentFollowing;
    private JList<String> chatFeed;
    private ArrayList<String> twitterFeed;

    // panels
    private JPanel userViewPanel;
    private JPanel topPanel;

    // objects
    private static MainFrame mainFrame;
    private ChatFeed userFeed;
    private User user;
    private AdminControlPanel admin;
    private TotalMessage totalMessage;

    public UserViewPanel() {
        initComponents();
    }

    private void initComponents() {

        Random randInt = new Random();
        int n = randInt.nextInt(500);
        twitterFeed = new ArrayList<>();

        this.setTitle("User View UI");
        this.pack();
        this.setBounds(n, n, 600, 450);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        // panel properties
        Color veryLightBlue = new Color(51, 169, 255);
        Color lightBlue = new Color(51, 156, 255);
        userViewPanel = new JPanel();
        userViewPanel.setBounds(0, 0, 600, 450);
        userViewPanel.setBackground(veryLightBlue);
        userViewPanel.setLayout(null);
        userViewPanel.setVisible(true);

        // initialize objects
        userFeed = new ChatFeed();
        totalMessage = new TotalMessage();
        user = new User();

        // initialize components
        userIdLabel = new JLabel();
        userIdLabel2 = new JLabel("User ID: ");
        followingLabel = new JLabel("Current Following");
        followingLabel.setForeground(Color.WHITE);
        tweetLabel = new JLabel("What's Happening?");
        tweetLabel.setForeground(Color.WHITE);
        chatFeedLabel = new JLabel("Chat Feed");
        chatFeedLabel.setForeground(Color.WHITE);

        tweetMessage = new JTextArea();
        userIdTextField = new JTextField();

        followUser = new JButton("Follow User");
        postTweet = new JButton("Tweet");

        currentFollowing = new JList<String>();
        chatFeed = new JList<String>();

        // JLabels
        followingLabel.setBounds(15, 55, 130, 20);
        tweetLabel.setBounds(15, 180, 150, 20);
        chatFeedLabel.setBounds(15, 255, 100, 20);
        userIdLabel.setBounds(15, 15, 150, 20);
        userIdLabel.setFont(new Font("Serif", Font.BOLD, 18));
        userIdLabel.setForeground(Color.WHITE);
        userIdLabel2.setBounds(300, 15, 75, 20);
        userIdLabel2.setForeground(Color.white);

        // JLists
        currentFollowing.setBounds(15, 75, 570, 100);
        chatFeed.setBounds(15, 275, 570, 105);

        // JTextArea
        tweetMessage.setBounds(15, 200, 350, 50);
        tweetMessage.setLineWrap(true);

        userIdTextField.setBounds(350, 15, 80, 20);
        userIdTextField.setText("@"); //like on twitter its a @ for user	

        // JButtons
        postTweet.setBounds(450, 200, 80, 20);
        postTweet.addActionListener(this);

        followUser.setBounds(450, 15, 80, 20);
        followUser.addActionListener(this);

        // JPanels
        topPanel = new JPanel();
        topPanel.setBounds(0, 0, 600, 50);
        topPanel.setBackground(lightBlue);
        topPanel.setLayout(null);

        // add components
        topPanel.add(userIdLabel);
        topPanel.add(userIdLabel2);
        topPanel.add(userIdTextField);
        topPanel.add(followUser);

        userViewPanel.add(topPanel);
        userViewPanel.add(followingLabel);
        userViewPanel.add(currentFollowing);
        userViewPanel.add(tweetLabel);
        userViewPanel.add(tweetMessage);
        userViewPanel.add(postTweet);
        userViewPanel.add(chatFeedLabel);
        userViewPanel.add(chatFeed);

        this.add(userViewPanel);
    }

    public void addToChatFeed(ChatFeed feed) {

        String[] tweets = new String[0];
        int size = feed.getTweets().size();

        twitterFeed.add(feed.getTweets().get(size - 1));

        for (int i = 0; i < twitterFeed.size(); i++) {
            System.out.println(twitterFeed.get(i));
        }

        tweets = new String[twitterFeed.size()];

        for (int i = 0; i < twitterFeed.size(); i++) {
            tweets[i] = twitterFeed.get(i);
        }

        chatFeed.setListData(tweets);
    }

    public void addToCurrentFollowing() {
        int size = user.getFollowing().size();
        ArrayList<User> username = user.getFollowing();
        String[] following = new String[size];

        for (int i = 0; i < size; i++) {
            following[i] = "- " + username.get(i).getUsername();
        }

        currentFollowing.setListData(following);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == postTweet) {
            System.out.println("Tweet Button Clicked");
            postTweet();
            updateFollowersFeed(tweetMessage.getText());

        } else if (e.getSource() == followUser) {
            System.out.println("Follow Button Clicked");
            System.out.println("Now Following: " + userIdTextField.getText());

            User following = new User();
            following = admin.findUser(userIdTextField.getText());

            System.out.println("Found: " + following.getUsername());

            addFollowing(following);

            userIdTextField.setText("");

        }
    }

    private void addFollowing(User following) {
        user.getFollowing().add(following);
        following.getFollowers().add(user);
        following.addPropertyChangeListener(user.getMyFeed());
        addToCurrentFollowing();
        following.getMyFeed().printTweets();
    }

    private void postTweet() {
        user.addTweet(user.getUsername() + ": " + tweetMessage.getText());
        totalMessage.addMessage(user.getTweet());
        admin.getMessageTotal().addMessage(user.getTweet());
        admin.getLastUpdatedUser().add(user);
        addToChatFeed(userFeed);
        userFeed.printTweets();
        tweetMessage.setText("");
        
    }

    private void updateFollowersFeed(String tweet) {
        for (int i = 0; i < user.getFollowers().size(); i++) {
            if (user.getFollowers().get(i) == admin.getUserPanels().get(i).getUser()) {
                admin.getUserPanels().get(i).getTwitterFeed().add(tweet);
                admin.getUserPanels().get(i).addToChatFeed(user.getFollowers().get(i).getMyFeed());
                updateFollowerChatFeeds(tweet);
            }
        }
    }
    
    private void updateFollowerChatFeeds(String tweet) {
        for (int i = 0; i < user.getFollowers().size(); i++) {
            UserViewPanel followerPanel = admin.getUserPanels().get(i);
            followerPanel.getFeed().addTweet(tweet); // Add the tweet to the follower's feed
            followerPanel.addToChatFeed(followerPanel.getFeed()); // Update the chat feed of the follower's panel
        }
    }


    public void setUserId(String userId) {
        this.userIdLabel.setText(userId);
    }

    public User getTwitterUser() {
        return user;
    }

    public void setTwitterUser(User twitterUser) {
        this.user = twitterUser;
        twitterUser.addPropertyChangeListener(userFeed);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame newMainFrame) {
        mainFrame = newMainFrame;
    }

    public ChatFeed getFeed() {
        return userFeed;
    }

    public void setFeed(ChatFeed feed) {
        this.userFeed = feed;
    }

    public AdminControlPanel getAdmin() {
        return admin;
    }

    public void setAdmin(AdminControlPanel admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getTwitterFeed() {
        return twitterFeed;
    }

    public void setTwitterFeed(ArrayList<String> twitterFeed) {
        this.twitterFeed = twitterFeed;
    }

}
