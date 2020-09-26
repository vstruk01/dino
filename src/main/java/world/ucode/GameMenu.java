package world.ucode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameMenu {
    public void showMenu(Stage primaryStage) {
            VBox vbox = new VBox();
            Button s = new Button("Start");
            Button r = new Button("Records");
            Button e = new Button("Exit");

            s.setStyle("-fx-font-size: 70px;");
            s.setMinHeight(150);
            s.setMinWidth(500);

            r.setStyle("-fx-font-size: 60px;");
            r.setMinHeight(150);
            r.setMinWidth(450);

            e.setStyle("-fx-font-size: 50px;");
            e.setMinHeight(150);
            e.setMinWidth(400);

            vbox.getChildren().addAll(s, r, e);
            vbox.setAlignment(Pos.BOTTOM_CENTER);

            s.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    GamePlay Play = new GamePlay();
                    Play.Play(primaryStage);
                }
            });

            e.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    System.exit(0);
                }
            });

            Scene scene = new Scene(vbox, 1000, 450);
            primaryStage.setTitle("Menu");
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        }
}
