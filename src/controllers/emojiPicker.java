package controllers;

import javafx.event.*;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import others.EmojiDir;
import others.SingleEmojiPack;
import others.WindowControl;


/**
 * Created by MOSHIOUR RAHMAN(Tushar) on 3/30/2017.
 * UNDER QUARKS (An Initiative of TWIINED STUDIO)
 * www.twiined.com
 *
 *
 */

public class emojiPicker implements Initializable {


    // FXML ELEMENTS .
    public Pane pane;
    public ScrollPane scroll_pane;
    public Text pack_name;
    public Text msg;

    // FIELD VARIABLES .
    private String[] pack_names;
    private int pack_pointer;
    private int number_of_emoji_pack;

    private ClipboardContent content;
    private double xOffset;
    private double yOffset;


    private void init() {
        new EmojiDir(); // LOADS THE EMOJI DIRECTORY INFORMATION BEFORE EVERYTHING  .

        content = new ClipboardContent();
        pack_pointer = 0;
        pack_names = EmojiDir.emoji_pack_list;
        number_of_emoji_pack = pack_names.length;

        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }


    // NAVIGATION LOGIC FOR CHANGING THE SET OF EMOJIS .
    public void navRight() throws Exception {
        if(pack_pointer < number_of_emoji_pack-1){
            pack_pointer++;
            loadEmoji();
        }
    }

    public void navLeft() throws Exception {
        if(pack_pointer > 0){
            pack_pointer--;
            loadEmoji();
        }
    }


    private void loadEmoji() throws Exception {


        int emoji_size = 24;                // PIXEL SIXE OF THE EMOJI.

        // GRIDPANE SETUP FOR LODING EMOJIS INTO THEM .
        GridPane emoji_grid = new GridPane();
        emoji_grid.setPadding(new Insets(6));
        emoji_grid.setCursor(Cursor.HAND);
        emoji_grid.setVgap(16);
        emoji_grid.setHgap(8);

        emoji_grid.getChildren().clear();   // EVERYTIME NEW EMOJI PACK IS LOADED THIS MAKES SURE EXISTING ONES ARE CLEARD FIRST .
        int row = 0;                        // ROW POINTER .
        int column = 0;                     // COLUMN POINTER .

        // INSTANSE OF A SINGLE EMOJI PACK .
        SingleEmojiPack s = new SingleEmojiPack(pack_names[pack_pointer]);

        for (int i = 0; i<s.number_of_emoji; i++) {
            ImageView image = new ImageView(s.emojis_image.get(i));
            image.setFitHeight(emoji_size);
            image.setFitWidth(emoji_size);
            image.setId(s.emojis_code.get(i));
            image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClicked(event));
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> onHoverd(event));

            emoji_grid.add(image, row, column);

            if (row < 11) { row++; }
            if (row == 11){
                column++;
                row = 0;
            }
        }

        pack_name.setText(s.name);
        scroll_pane.setContent(emoji_grid); // FINALLY ADDING THE GRIDPANE TO THE SCROLLPANE
    }

    // CLICKING ON THE EMOJI WILL PUT THE CODE TO SYSTEM CLIPBOARD
    private void onClicked(Event event){
        content.putString(((ImageView) event.getSource()).getId());
        Clipboard.getSystemClipboard().setContent(content);
        msg.setText("copied to clipboard ");
    }

    private void onHoverd(Event event){
        msg.setText("click to copy " + ((ImageView) event.getSource()).getId());
    }


    // APP COLISING FUNCTIONALITY .
    public void closeApp(MouseEvent evt) {

        // SHOWING A DIALOG TO CONFIRM USER ACTION WHEN USER TRYING TO CLOSE THE APPLICATION.
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Quarks Emoji Picker is Closing");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to Exit ?");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(pane.getScene().getWindow());
        alert.setOnCloseRequest(event -> alert.hide());

        ButtonType buttonSystemTray = new ButtonType("System Tray");
        ButtonType buttonClose = new ButtonType("Close");
        ButtonType buttonCancel = new ButtonType("Cancel");

        //ADDING BUTTONS.
        alert.getButtonTypes().setAll(buttonSystemTray, buttonClose,buttonCancel);

        // USER CAN CHOOSE WHAT THEY WANT TO DO, CLOSE OR MINIMIZE TO SYSTEM TRAY.
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonSystemTray){
            WindowControl.getInstance().hideWindow();
        } else if (result.get() == buttonClose) {
            System.exit(0);
        } else if (result.get() == buttonCancel) {
            alert.close();
        }

    }


    // MINIMIZE BUTTON FUNCTION
    public void minApp() {
        WindowControl.getInstance().hideWindow();
    }


    //PRESSING AND DRAGGING WINDOW , SINCE WE ARE USING CUSTOM WINDOW WE HAVE TO DO IT MANUALLY.
    public void onPressed(MouseEvent event) {
        xOffset = pane.getScene().getWindow().getX() - event.getScreenX();
        yOffset = pane.getScene().getWindow().getY() - event.getScreenY();
    }

    public void onDrag(MouseEvent event) {
        pane.getScene().getWindow().setX(event.getScreenX() + xOffset);
        pane.getScene().getWindow().setY(event.getScreenY() + yOffset);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)   {
        try {
            init();
            loadEmoji();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
