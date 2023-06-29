package assignment2;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainDriver {
    public static void main(String[] args) {
     
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    
    }
}