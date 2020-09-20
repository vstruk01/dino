package world.ucode;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        GameMenu Menu = new GameMenu();
        Menu.showMenu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
