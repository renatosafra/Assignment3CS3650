package assignment2;

import java.awt.BorderLayout;
import javax.swing.JFrame;

// the main frame for the set up of the admin control panel
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    AdminControlPanel admin;
    UserViewPanel userView;
    User user;

    public MainFrame() {
        this.setTitle("Admin Control UI");
        this.pack();
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        admin = AdminControlPanel.getInstance(this); //single instance
        addAdminControlPanel();
    }

    public void addAdminControlPanel() {
        admin.setVisible(true);
        this.add(admin);
    }

    public void backToAdminControlPanel() {
        this.remove(userView);
        this.add(admin);
        this.repaint();
        this.revalidate();
        admin.getOpenUserViewButton().setEnabled(false);
    }

    public void addUserViewPanel(User user) {
        userView = new UserViewPanel();
        userView.setUser(user);
        userView.setTwitterUser(user);
        userView.setVisible(true);
        userView.setUserId(user.getUsername());

        admin.setUserView(userView);
        userView.setAdmin(admin);
    }
}