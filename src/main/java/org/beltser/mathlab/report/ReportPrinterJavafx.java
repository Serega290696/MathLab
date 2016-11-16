package org.beltser.mathlab.report;

import org.beltser.controller.FrontController;
import org.beltser.mathlab.matrix.Matrix;
import org.beltser.mathlab.utils.MathUtil;
import org.beltser.mathlab.linear_geometry.Point;

public class ReportPrinterJavafx<R> implements ReportPrinter<R> {
    private FrontController controller;

    public ReportPrinterJavafx(FrontController controller) {
        this.controller = controller;
    }

    public void print(Report<R> report) {
        StringBuilder answer = new StringBuilder();
        answer.append(" ======= Report ======= ").append("\n");
        if (report == null) {
            answer.append(" || Empty report.").append("\n");
        } else {
            if (report.isSuccessful()) {
                if (report.getResult() != null) {
                    if (report.getResult() instanceof Matrix) {
                        answer.append(" || Result:").append("\n");
                        Matrix result = (Matrix) report.getResult();
                        for (int i = 0; i < result.getHeight(); i++) {
                            for (int j = 0; j < result.getWidth(); j++) {
                                answer.append(" || X[").append((1 + i)).append("]");
                                if (result.getWidth() > 1) {
                                    answer.append("[").append((j + 1)).append("]").append("\n");

                                }
                                answer.append(" = ").append(result.get(i, j)).append("\n");
                            }
                        }
                    } else if (report.getResult() instanceof Point) {
                        Point p = (Point)report.getResult();
                        answer.append(" || Result: ").append(p).append(".").append("\n");
                    } else {
                        Number result = (Number) report.getResult();
                        answer.append(" || Result: ").append(MathUtil.round(result, 4)).append(".").append("\n");
                    }
                } else {
                    answer.append(" || Result is empty.").append("\n");
                }
                answer.append(" || Spent ").append(report.getElapsedTimeUnit().toSeconds(report.getSpentTime())).append(" seconds.").append("\n");
            } else {
                answer.append(" || Computing is failed").append("\n");
            }
        }
        answer.append(" =================== ").append("\n");
        if (controller != null) {
            controller.printAnswer(answer.toString());
        } else {
            System.err.println("Can't print report");
        }
    }

}
