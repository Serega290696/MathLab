package org.beltser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.beltser.controller.FrontController;

import java.util.Scanner;

public class Main extends Application {

  public static final boolean DEBUG_INPUT_MODE = true;
  public static final Scanner SCANNER = new Scanner(System.in);
  public static Stage stage;
  public static FrontController controller;

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


  public static void main(String[] args) {
    launch(args);
  }
}
