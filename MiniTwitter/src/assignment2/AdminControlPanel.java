package assignment2;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

// Singleton
// main program

@SuppressWarnings("serial")
public class AdminControlPanel extends JPanel implements ActionListener, MouseListener {

    // singleton instance
    private static final AdminControlPanel adminControl = new AdminControlPanel();
    private static boolean initialized = false;

    // text fields
    private JTextField userIdTextField;
    private JTextField groupIdTextField;

    // buttons
    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton openUserViewButton;
    private JButton verificationButton; //A3
    private JButton lastUpdatedButton; //A3
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showMessageTotalButton;
    private JButton showPositivePercentageButton;

    // panels
    private JPanel bottomRightPanel;

    // labels
    private JLabel userIdLabel;
    private JLabel groupIdLabel;

    // objects
    private static MainFrame mainFrame;
    private User user;
    private TreeView treeView;
    private UserViewPanel userView;
    private TotalUser totalUser;
    private TotalGroup totalGroup;
    private UserVisitor visitor;
    private TotalMessage totalMessage;
    private PositiveMessage positiveMessage;

    // lists
    private ArrayList<User> userList;
    private ArrayList<UserGroup> groupList;
    private ArrayList<String> tweetList;
    private ArrayList<String> positiveWordsList;
    private ArrayList<UserViewPanel> userPanels;
    private ArrayList<User> lastUpdated;

    // tree node
    private DefaultMutableTreeNode selectedNode;

    public AdminControlPanel() {
    }

    // singleton getInstance method
    public static synchronized AdminControlPanel getInstance(MainFrame mainFrame) {

        setMainFrame(mainFrame);

        if (initialized) {
            return adminControl;
        } else {
            adminControl.initComponents();
            initialized = true;
            return adminControl;
        }

    }

    private void initComponents() {

        // initialize admin control panel
    	Color veryLightBlue = new Color(51, 169, 255);
        this.setBackground(veryLightBlue);
        this.setBounds(0, 0, 600, 400);
        this.setLayout(null);

        // initialize class objects
        totalUser = new TotalUser();
        totalGroup = new TotalGroup();
        totalMessage = new TotalMessage();
        positiveMessage = new PositiveMessage(totalMessage);
        visitor = new UserVisitor();
        user = new User();

        // initialize array lists
        userList = new ArrayList<User>();
        groupList = new ArrayList<UserGroup>();
        tweetList = new ArrayList<String>();
        positiveWordsList = new ArrayList<String>();
        userPanels = new ArrayList<>();
        lastUpdated = new ArrayList<User>();

        // initialize text areas
        userIdTextField = new JTextField();
        groupIdTextField = new JTextField();

        // initialize labels
        userIdLabel = new JLabel("User ID");
        userIdLabel.setForeground(Color.WHITE);
        groupIdLabel = new JLabel("Group ID");
        groupIdLabel.setForeground(Color.WHITE);

        // initialize buttons
        addUserButton = new JButton("Add User");
        addUserButton.addActionListener(this);

        addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(this);

        openUserViewButton = new JButton("Open User View");
      
        openUserViewButton.addActionListener(this);

        showUserTotalButton = new JButton("Show User Total");
        showGroupTotalButton = new JButton("Show Group Total");
        showMessageTotalButton = new JButton("Show Message Total");
        showPositivePercentageButton = new JButton("Show Positive Percentage");
        
        //A3
        verificationButton = new JButton("User ID Verification");
        verificationButton.addActionListener(this);

        lastUpdatedButton = new JButton("Last Updated User");
        lastUpdatedButton.addActionListener(this);


        // initialize tree components

        treeView = new TreeView();
        treeView.setEditable(true);
        treeView.setBounds(0, 0, 150, 400);
        treeView.addMouseListener(this);

        this.add(treeView);

        // panels
        bottomRightPanel = new JPanel();
        addBottomRightPanel();

        // labels
        userIdLabel.setBounds(240, 15, 75, 15);
        groupIdLabel.setBounds(240, 75, 75, 15);

        // text fields
        userIdTextField.setBounds(238, 30, 125, 25);
        groupIdTextField.setBounds(238, 90, 125, 25);

        // buttons
        addUserButton.setBounds(400, 30, 100, 25);
        addGroupButton.setBounds(400, 90, 100, 25);
        openUserViewButton.setBounds(250, 150, 250, 25);
        openUserViewButton.setEnabled(false);
        //A3
        verificationButton.setBounds(250, 180, 250, 25);
        lastUpdatedButton.setBounds(250, 210, 250, 25);

        // add components
        this.add(userIdTextField);
        this.add(groupIdTextField);
        this.add(userIdLabel);
        this.add(groupIdLabel);
        this.add(addUserButton);
        this.add(addGroupButton);
        this.add(openUserViewButton);
        //A3
        this.add(verificationButton);
        this.add(lastUpdatedButton);

    }

    //constructs the panel for the bottom right of admin control panel
    public void addBottomRightPanel() {

        Color lightBlue = new Color(51, 156, 255);
        bottomRightPanel.setBackground(lightBlue);
        bottomRightPanel.setBounds(150, 250, 450, 125);
        bottomRightPanel.setLayout(null);

        showUserTotalButton.setBounds(25, 20, 175, 35);
        showUserTotalButton.addActionListener(this);

        showGroupTotalButton.setBounds(250, 20, 175, 35);
        showGroupTotalButton.addActionListener(this);

        showMessageTotalButton.setBounds(25, 70, 175, 35);
        showMessageTotalButton.addActionListener(this);

        showPositivePercentageButton.setBounds(250, 70, 175, 35);
        showPositivePercentageButton.addActionListener(this);

        // initialize buttons
        bottomRightPanel.add(showUserTotalButton);
        bottomRightPanel.add(showGroupTotalButton);
        bottomRightPanel.add(showMessageTotalButton);
        bottomRightPanel.add(showPositivePercentageButton);

        this.add(bottomRightPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == openUserViewButton) {

            // open user view
            openUserView();

        } else if (e.getSource() == addUserButton) {

            if (selectedNode == null) {
                String message = "Choose node to add user/group";
                String labelString = "Error: ";
                createDialogBox(message, labelString);
            } else {
                // adds user
                createUser(userIdTextField.getText());
                System.out.println("Added user: " + userIdTextField.getText());
            }

            userIdTextField.setText("");

        } else if (e.getSource() == addGroupButton) {

            // creates group, adds it to the tree, and adds to group total
            createGroup();
            groupIdTextField.setText("");

        } else if (e.getSource() == showUserTotalButton) {

            String message = visitor.visit(totalUser);
            String labelString = "Total Users: ";
            createDialogBox(message, labelString);

        } else if (e.getSource() == showGroupTotalButton) {

            String message = visitor.visit(totalGroup);
            String labelString = "Total Groups: ";
            createDialogBox(message, labelString);

        } else if (e.getSource() == showMessageTotalButton) {

            String message = visitor.visit(totalMessage);
            String labeString = "Total Messages: ";
            createDialogBox(message, labeString);

        } else if (e.getSource() == showPositivePercentageButton) {

            positiveMessage.findPositiveWords(totalMessage.getMessageList());
            String message = visitor.visit(positiveMessage);
            String labelString = "Positive Message %: ";
            createDialogBox(message, labelString);
            
        }  else if (e.getSource() == verificationButton) {

            String invalidMessage = "User IDs are invalid";
            String validMessage = "User IDs are valid";
            String errorLabel = "Error: ";
            String messageLabel = "Message: ";
            userList = totalUser.getUserList();

            if (checkDuplicates(userList) && checkSpaces(userList)) {
                createDialogBox(validMessage, messageLabel);
            } else {
                createDialogBox(invalidMessage, errorLabel);
            }
        } else if (e.getSource() == lastUpdatedButton) {
            String message = lastUpdated.get(lastUpdated.size() - 1).getUsername();
            String labelString = "Last update: ";
            createDialogBox(message, labelString);
        }
    }
    
    public ArrayList<User> getLastUpdatedUser() {
        return lastUpdated;
    }

    private boolean checkSpaces(ArrayList<User> userList) {
        for (User user : userList) {
            for (int j = 0; j < user.getUsername().length(); j++) {
                if (user.getUsername().charAt(j) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkDuplicates(ArrayList<User> userList) {
        for (int i = 0; i < userList.size() - 1; i++) {
            if (userList.size() > 1 && userList.get(i).getUsername().equals(userList.get(i + 1).getUsername())) {
                return false;
            }
        }
        return true;
    }

    private void createGroup() {
        UserGroup newGroup = new UserGroup(groupIdTextField.getText());
        DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(newGroup.getGroupName());
        totalGroup.getUserGroupList().add(newGroup);

        treeView.getTreeModel().insertNodeInto(newGroupNode,
                selectedNode, 0);
    }

    private void createDialogBox(String message, String labelString) {

        JDialog dialog = new JDialog(mainFrame, "Message");
        JLabel label = new JLabel(labelString + message);
        dialog.setBounds(100, 100, 250, 100);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        dialog.add(label);
        dialog.setVisible(true);
    }

    // opens user view of user from tree
    private void openUserView() {
        String username = selectedNode.getUserObject().toString();
        user = findUser(username);
        mainFrame.addUserViewPanel(user);

        System.out.println("Opened user view for: " + user.getUsername());
    }

    // creates user, adds it to the tree, and adds to user total
    private void createUser(String username) {

        int index;
        user = new User("@" + username);
        DefaultMutableTreeNode newUserNode = new DefaultMutableTreeNode(user.getUsername());

        totalUser.getUserList().add(user);
        index = selectedNode.getChildCount();
        selectedNode.insert(newUserNode, index);
        treeView.getTreeModel().insertNodeInto(newUserNode, selectedNode, 0);

    }

    // finds users
    public User findUser(String username) {

        User user = new User();
        Boolean found = false;
        userList = totalUser.getUserList();

        System.out.println("User list: ");

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).getUsername());
        }

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                user = userList.get(i);
                System.out.println("Found: " + user.getUsername());
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("User Found");
            return user;
        } else {
            System.out.println("User Not Found");
            return null;
        }

    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == treeView) {
            selectedNode = (DefaultMutableTreeNode) treeView.getSelectionPath().getLastPathComponent();
            openUserViewButton.setEnabled(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public JButton getOpenUserViewButton() {
        return openUserViewButton;
    }

    public void setOpenUserViewButton(JButton openUserViewButton) {
        this.openUserViewButton = openUserViewButton;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame newMainFrame) {
        mainFrame = newMainFrame;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<UserGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<UserGroup> groupList) {
        this.groupList = groupList;
    }

    public ArrayList<String> getTweetList() {
        return tweetList;
    }

    public void setTweetList(ArrayList<String> tweetList) {
        this.tweetList = tweetList;
    }

    public ArrayList<String> getPositiveWordsList() {
        return positiveWordsList;
    }

    public void setPositiveWordsList(ArrayList<String> positiveWordsList) {
        this.positiveWordsList = positiveWordsList;
    }

    public UserViewPanel getUserView() {
        return userView;
    }

    public void setUserView(UserViewPanel userView) {
        this.userView = userView;
        userPanels.add(userView);
    }

    public TotalMessage getMessageTotal() {
        return totalMessage;
    }

    public void setMessageTotal(TotalMessage totalMessage) {
        this.totalMessage = totalMessage;
    }

    public ArrayList<UserViewPanel> getUserPanels() {
        return userPanels;
    }

    public void setUserPanels(ArrayList<UserViewPanel> userPanels) {
        this.userPanels = userPanels;
    }

}