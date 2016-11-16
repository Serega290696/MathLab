package org.beltser.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;

public class ChartController {

  @FXML
  public LineChart<Double, Double> mainChart;
  @FXML
  public Button buildButton;
  @FXML
  private void initialize() {
    System.out.println("ChartController.initialize !");
  }

  @FXML
  public void onBuild() {
  }
}
