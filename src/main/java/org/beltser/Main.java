package org.beltser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.beltser.controller.FrontController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public static final boolean DEBUG_INPUT_MODE = true;
  public static final Scanner SCANNER = new Scanner(System.in);
  public static Stage stage;
  public static FrontController controller;

<<<<<<< HEAD
    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Start JavaFx application");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Mth lab");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        FrontController controller = (FrontController) fxmlLoader.getController();
        controller.setStage(primaryStage);
    }
=======
  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
    Parent root = fxmlLoader.load();
    primaryStage.setTitle("Mth lab");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    stage = primaryStage;
    controller = (FrontController) fxmlLoader.getController();
  }
>>>>>>> 97e17d377b1a149e0bbfa0f48f747dcb847a70b7


  public static void main(String[] args) {
    launch(args);
  }
}
