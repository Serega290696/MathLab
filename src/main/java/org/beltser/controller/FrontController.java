package org.beltser.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.beltser.CustomPrintStream;
import org.beltser.Main;
import org.beltser.mathlab.report.ReportPrinter;
import org.beltser.mathlab.report.ReportPrinterJavafx;
import org.beltser.ppz.labs.OperatorLab1;
import org.beltser.ppz.labs.OperatorLab2;
import org.beltser.ppz.labs.OperatorLab3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontController {

  private static final String CLOSE_CONSOLE__BUTTON_TEXT = "close";
  private static final String OPEN_CONSOLE__BUTTON_TEXT = "open";

  private CustomPrintStream printStream = new CustomPrintStream(this);
  private ReportPrinter reportPrinter = new ReportPrinterJavafx<>(this);

  private boolean trace = false;
  private boolean warn = false;
  private boolean error = true;


  @FXML
  public HBox ioBoxs;
  @FXML
  public TabPane tabs;

  @FXML
  public Tab msizeTab;
  @FXML
  public Tab systemTab;
  @FXML
  public Tab integralTab;
  @FXML
  public Tab difTab;

  @FXML
  public TextField matrixHeight;
  @FXML
  public TextField matrixWidth;


  @FXML
  public TextArea console;
  @FXML
  public HBox consoleBox;

  @FXML
  public Button closeConsoleButton;
  @FXML
  public CheckBox traceCheckBox;
  @FXML
  public CheckBox warnCheckBox;
  @FXML
  public CheckBox errorCheckBox;
  @FXML
  public CheckBox wrapText;


  public TextField matrixElementField11;
  public TextField matrixElementField12;
  public TextField matrixElementField13;
  public TextField matrixElementField14;

  public TextField matrixElementField15;
  public TextField matrixElementField16;

  public TextField matrixElementField21;
  public TextField matrixElementField22;
  public TextField matrixElementField23;
  public TextField matrixElementField24;
  public TextField matrixElementField25;
  public TextField matrixElementField26;

  public TextField matrixElementField31;
  public TextField matrixElementField32;
  public TextField matrixElementField33;
  public TextField matrixElementField34;
  public TextField matrixElementField35;
  public TextField matrixElementField36;

  public TextField matrixElementField41;
  public TextField matrixElementField42;
  public TextField matrixElementField43;
  public TextField matrixElementField44;
  public TextField matrixElementField45;
  public TextField matrixElementField46;

  public TextField matrixElementField51;
  public TextField matrixElementField52;
  public TextField matrixElementField53;
  public TextField matrixElementField54;
  public TextField matrixElementField55;
  public TextField matrixElementField56;

  public TextField integrandField;
  public TextField aField;
  public TextField bField;

  public TextField difExpressionField;
  public TextField xField;
  public TextField yField;
  public TextField aDifField;
  public TextField bDifField;

  public TextField[][] matrixElements;

  private OperatorLab1 operator1 = new OperatorLab1(reportPrinter);
  private OperatorLab2 operator2 = new OperatorLab2(reportPrinter);
  private OperatorLab3 operator3 = new OperatorLab3(reportPrinter);

  public TextArea answerField;
  public CheckBox isMatrixSymmetric;

  private boolean isMatrixElementsInit() {
    return matrixElements != null && matrixElements.length > 0 && matrixElements[0].length > 0
        && matrixElements[0][0] != null;
  }

  private void initMatrixElements() {
    if (!isMatrixElementsInit()) {
      matrixElements = new TextField[][] {
          {
              matrixElementField11,
              matrixElementField12,
              matrixElementField13,
              matrixElementField14,
              matrixElementField15,
              matrixElementField16
          },
          {
              matrixElementField21,
              matrixElementField22,
              matrixElementField23,
              matrixElementField24,
              matrixElementField25,
              matrixElementField26
          },
          {
              matrixElementField31,
              matrixElementField32,
              matrixElementField33,
              matrixElementField34,
              matrixElementField35,
              matrixElementField36
          },
          {
              matrixElementField41,
              matrixElementField42,
              matrixElementField43,
              matrixElementField44,
              matrixElementField45,
              matrixElementField46
          },
          {
              matrixElementField51,
              matrixElementField52,
              matrixElementField53,
              matrixElementField54,
              matrixElementField55,
              matrixElementField56
          }
      };
    }
  }

  public void compute() {
    System.out.println("Try computing. . .");
    if (msizeTab.isSelected()) {
      int height;
      try {
        height = Integer.parseInt(matrixHeight.getText());
      } catch (NumberFormatException e) {
        System.err.println("Wrong number");
        return;
      }
      int width;
      try {
        width = Integer.parseInt(matrixWidth.getText());
      } catch (NumberFormatException e) {
        System.err.println("Wrong number");
        return;
      }
      System.out.println("Height is " + height);
      System.out.println("Width is " + width);
      changeMatrixFormSize(width, height);
    } else if (systemTab.isSelected()) {
      if (isMatrixElementsInit()) {
        Map<String, Object> args = collectDataToTask1();
        if (args != null) {
          operator1.launch(args);
        }
      } else {
        System.err.println("Wrong matrix");
      }
    } else if (integralTab.isSelected()) {
      Map<String, Object> args = collectDataToTask2();
      if (args != null) {
        operator2.launch(args);
      } else {
        System.err.println("Wrong input");
      }
    } else if (difTab.isSelected()) {
      Map<String, Object> args = collectDataToTask3();
      if (args != null) {
        operator3.launch(args);
      } else {
        System.err.println("Wrong input");
      }
    }
  }

  private Map<String, Object> collectDataToTask3() {
    Map<String, Object> args = new HashMap<>();
    String expression = difExpressionField.getText();
    double x0;
    double y0;
    double a;
    double b;
    if (!"".equals(expression)) {
      args.put(OperatorLab3.EXPRESSION_FIELD_NAME, expression);
    } else {
      System.err.println("Wrong expression");
      return null;
    }
    try {
      x0 = Double.parseDouble(xField.getText());
    } catch (Exception e) {
      System.err.println("Wrong x0: " + xField.getText());
      return null;
    }
    try {
      y0 = Double.parseDouble(yField.getText());
    } catch (Exception e) {
      System.err.println("Wrong y0: " + yField.getText());
      return null;
    }
    try {
      a = Double.parseDouble(aDifField.getText());
    } catch (Exception e) {
      System.err.println("Wrong y0: " + aDifField.getText());
      return null;
    }
    try {
      b = Double.parseDouble(bDifField.getText());
    } catch (Exception e) {
      System.err.println("Wrong y0: " + bDifField.getText());
      return null;
    }
    args.put(OperatorLab3.X_ZERO_FIELD_NAME, x0);
    args.put(OperatorLab3.Y_ZERO_FIELD_NAME, y0);
    args.put(OperatorLab3.A_FIELD_NAME, a);
    args.put(OperatorLab3.B_FIELD_NAME, b);
    return args;
  }

  private Map<String, Object> collectDataToTask2() {
    Map<String, Object> args = new HashMap<>();
    String integrand = integrandField.getText();
    double a;
    double b;
    if (!"".equals(integrand)) {
      args.put(OperatorLab2.EXPRESSION_FIELD_NAME, integrand);
    } else {
      System.err.println("Wrong integrand");
      return null;
    }
    try {
      a = Double.parseDouble(aField.getText());
    } catch (Exception e) {
      System.err.println("Wrong lower limit: " + aField.getText());
      return null;
    }
    try {
      b = Double.parseDouble(bField.getText());
    } catch (Exception e) {
      System.err.println("Wrong upper limit: " + bField.getText());
      return null;
    }
    args.put(OperatorLab2.BEGIN_BOUNDARY_FIELD_NAME, a);
    args.put(OperatorLab2.END_BOUNDARY_FIELD_NAME, b);
    return args;
  }

  private Map<String, Object> collectDataToTask1() {
    Map<String, Object> args = new HashMap<>();
    int width = 0;
    int height = 0;
    for (int i = 0; i < matrixElements.length; i++) {
      for (int j = 0; j < matrixElements[0].length; j++) {
        if (matrixElements[i][j].isVisible()) {
          width = (1 + j) > width ? (1 + j) : width;
          height = (1 + i) > height ? (1 + i) : height;
        }
      }
    }
    double[][] matrix = new double[height][width - 1];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width - 1; j++) {
        double d;
        try {
          d = Double.parseDouble(matrixElements[i][j].getText());
        } catch (Exception e) {
          System.err.println("Wrong matrix element: " + i + "x" + j);
          return null;
        }
        matrix[i][j] = d;
      }
    }
    if (!isMatrixSymmetry(matrix)) {
      return null;
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width - 1; j++) {
        if (matrix[i][j] != matrix[j][i]) {
          System.err.println("Wrong matrix type! Matrix must be symmetry");
          return null;
        }
      }
    }
    args.put("matrix", matrix);
    double[][] b = new double[height][1];
    for (int i = 0; i < height; i++) {
      double d;
      try {
        d = Double.parseDouble(matrixElements[i][width - 1].getText());
      } catch (Exception e) {
        System.err.println("Wrong b vector element: " + i + "x" + (width - 1));
        return null;
      }
      b[i][0] = d;
    }
    args.put("b", b);
    return args;
  }

  private boolean isMatrixSymmetry(double[][] matrix) {
    if (matrix.length != matrix[0].length) {
      return false;
    }
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] != matrix[j][i]) {
          return false;
        }
      }
    }
    return true;
  }

  private void changeMatrixFormSize(int width, int height) {
    initMatrixElements();
    for (int i = 0; i < matrixElements.length; i++) {
      for (int j = 0; j < matrixElements[0].length; j++) {
        matrixElements[i][j].setVisible(i < height && j < width + 1);
      }
    }
  }

  public void wrapTextInConsole() {
    console.setWrapText(wrapText.isSelected());
  }

  public void errorInConsole() {
    error = errorCheckBox.isSelected();
    console.setText(printStream.getMsgs(trace, warn, error));
  }

  public void warnInConsole() {
    warn = warnCheckBox.isSelected();
    console.setText(printStream.getMsgs(trace, warn, error));
  }

  public void traceInConsole() {
    trace = traceCheckBox.isSelected();
    console.setText(printStream.getMsgs(trace, warn, error));
  }

  public void closeConsole() {
    if (CLOSE_CONSOLE__BUTTON_TEXT.equals(closeConsoleButton.getText())) {
      console.setMinHeight(0);
      console.setPrefHeight(0);
      consoleBox.setPrefHeight(0);
      ioBoxs.setMaxHeight(1000);
      closeConsoleButton.setText(OPEN_CONSOLE__BUTTON_TEXT);
    } else {
      closeConsoleButton.setText(CLOSE_CONSOLE__BUTTON_TEXT);
      consoleBox.setPrefHeight(140);
      ioBoxs.setMaxHeight(250);
    }
  }


  public void consolePrint() {
    if (console != null && printStream != null) {
      console.setText(printStream.getMsgs(trace, warn, error));
    }
  }

  public void printAnswer(String answer) {
    answerField.setText(answer);
  }

  public void autoFill() {
    if (!isMatrixSymmetric.isSelected()) {
      return;
    }
    for (int i = 0; i < matrixElements.length && matrixElements[i][0].isVisible(); i++) {
      for (int j = 0; j < matrixElements[0].length && matrixElements[0][j].isVisible() && i > j; j++) {
        if (
//                                "".equals(matrixElements[i][j].getText())
//                                matrixElements[i][j].getText() == null)
            j < matrixElements.length
                && i < matrixElements[0].length
                && !"".equals(matrixElements[j][i].getText())
            ) {
          matrixElements[i][j].setText(matrixElements[j][i].getText());
        }
      }
    }
  }

  public void clearConsole() {
    printStream.clearAll();
    console.setText(printStream.getMsgs(trace, warn, error));
    answerField.setText("");
  }

//  public void showChart() {
//    FXMLLoader loader = new FXMLLoader();
//    loader.setLocation(Main.class.getResource("fxml/chart.fxml"));
//    Pane pane = null;
//    try {
//      pane = loader.load();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    if (pane == null) {
//      throw new NullPointerException("pane is null");
//    }
//    // Создаём диалоговое окно Stage.
//    Stage dialogStage = new Stage();
//
//    Scene scene = new Scene(pane);
//    dialogStage.setScene(scene);
//    dialogStage.setTitle("Computing chart");
//    dialogStage.initModality(Modality.WINDOW_MODAL);
//    dialogStage.initOwner(Main.stage);
//
//    // Передаём адресата в контроллер.
//        ChartController controller = loader.getController();
//    // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
//    dialogStage.showAndWait();
//  }
}