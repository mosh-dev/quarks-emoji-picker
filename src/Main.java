import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import others.WindowControl;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/emojiPicker.fxml"));
        root.getStylesheets().add("/css/style.css");

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/logo-emoji.png")));
        primaryStage.setTitle("Quarks Emoji Picker");
        primaryStage.show();

        new WindowControl(primaryStage);

    }


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        // Trick the JVM to set Encoding to UTF-8 so That emojis can be read as Unicode
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null,null);

        launch(args);
    }
}
