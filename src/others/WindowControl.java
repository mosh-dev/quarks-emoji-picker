package others;

import javafx.application.Platform;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Galib on 2/27/2017.
 * UNDER QUARKS (An Initiative of TWIINED STUDIO)
 * www.twiined.com
 *
 *
 */
public class WindowControl {

    private TrayIcon trayIcon;
    private boolean firstTime = true;
    private Stage stage;
    private static WindowControl instance;



    public WindowControl(Stage stage) {
        this.stage = stage;
        createTrayIcon();
        setFocusListener();
        instance = this;

    }

    public static WindowControl getInstance() {
        return instance;
    }

    // CHANGING VISIBILITY ON THE WINDOW BASED ON FOCUS.
    private void setFocusListener() {
        stage.focusedProperty().addListener((ov, oldVal, newVal) -> {
            if (newVal) {
                stage.setOpacity(1);
            } else {
                stage.setOpacity(0.9);
            }
        });
    }


    private void createTrayIcon() {

        if (SystemTray.isSupported()) {
            // GET THE SYSTEMTRAY INSTANCE
            SystemTray tray = SystemTray.getSystemTray();

            // LOAD AN IMAGE
            java.awt.Image image = new ImageIcon(this.getClass().getResource("/icons/logo-emoji-small.png")).getImage();

            stage.setOnCloseRequest(t -> {
                        t.consume();
                        hideWindow();
                    }
            );

            // CREATE A ACTION LISTENER TO LISTEN FOR DEFAULT ACTION EXECUTED ON THE TRAY ICON.
            ActionListener closeListener = e -> System.exit(0);
            ActionListener showListener = e -> showWindow();

            // CREATE A POPUP MENU
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);

            // ADD OTHER ITEMS
            // CONSTRUCT A TRAYICON
            trayIcon = new TrayIcon(image, "Quarks Emoji Picker", popup);

            // set the TrayIcon properties
            trayIcon.addActionListener(showListener);

            // ADD THE TRAY IMAGE
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
    }

    // SHOWING PROGRAM IS MINIMIZED BUT STILL RUNNING ON SYSTEM TRAY.
    private void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("Quarks Emoji Picker is Still Running.",
                    "Show or Close it from system tray.",
                    TrayIcon.MessageType.NONE);

            firstTime = false;
        }
    }

    // HIDING APPLICATION WINDOW.
    public void hideWindow() {
        Platform.runLater(() -> {
            if (SystemTray.isSupported()) {
                Platform.setImplicitExit(false);
                stage.hide();
                showProgramIsMinimizedMsg();
            } else {
                System.exit(0);
            }
        });
    }

    // SHOWING APPLICATION WINDOW.
    private void showWindow() {
        Platform.runLater(stage::show);
    }


}
