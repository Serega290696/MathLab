package org.beltser.mathlab;

import org.beltser.Main;
import org.beltser.mathlab.exception.TimeLimitException;
import org.beltser.mathlab.report.Report;

import java.util.Map;
import java.util.Scanner;

public abstract class Operator<R> {

    public R launch(Map<String, Object> args) {
        long beginTime = System.currentTimeMillis();
        R result = null;
        boolean timeLimitExceed = false;
        boolean successful = true;
        try {
            result = compute(args);
        } catch (TimeLimitException e) {
            successful = false;
            timeLimitExceed = true;
        }
        Report<R> report = new Report.ReportBuilder<R>().setIsSuccessful(successful)
                .setIsTimeLimitExceed(timeLimitExceed)
                .setResult(result)
                .setElapsedTime(System.currentTimeMillis() - beginTime)
                .build();
        showResult(report);
        return report.getResult();
    }

    public R launch() {
        Map<String, Object> args = getInput();
        return launch(args);
    }

    protected abstract Map getInput();

    protected abstract R compute(Map inputtedData) throws TimeLimitException;

    protected abstract void showResult(Report<R> report);


    protected String read(String defaultString) {
        if (Main.DEBUG_INPUT_MODE) {
            System.out.print(defaultString);
            return defaultString;
        }
        return getReader().nextLine();
    }

    protected double read(double defaultReal) {
        if (Main.DEBUG_INPUT_MODE) {
            System.out.print(defaultReal);
            return defaultReal;
        }
        return getReader().nextDouble();
    }

    protected int read(int defaultInteger) {
        if (Main.DEBUG_INPUT_MODE) {
            System.out.print(defaultInteger);
            return defaultInteger;
        }
        return getReader().nextInt();
    }

    private Scanner getReader() {
        return Main.SCANNER;
    }
}