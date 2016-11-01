package org.beltser;

import org.beltser.controller.FrontController;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CustomPrintStream {

    private List<Message> msgs = new ArrayList<>();
    private FrontController frontController;

    public CustomPrintStream(FrontController frontController) {
        this.frontController = frontController;
        init();
    }

    public void init() {
        PrintStream traceAndWarns = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                super.println(x);
                if (x.contains("[WARN]")) {
                    msgs.add(new Message(MessageLevel.WARN, x));
                } else {
                    msgs.add(new Message(MessageLevel.TRACE, " [TRACE] " + x));
                }
                frontController.consolePrint();
            }

            @Override
            public void println(Object x) {
                super.println(x);
                msgs.add(new Message(MessageLevel.TRACE, " [TRACE] " + x.toString()));
                frontController.consolePrint();
            }

            @Override
            public void println(double x) {
                super.println(x);
                msgs.add(new Message(MessageLevel.TRACE, " [TRACE] " + x));
                frontController.consolePrint();
            }
        };
        PrintStream errOutput = new PrintStream(System.err) {
            @Override
            public void println(String x) {
                super.println(x);
                msgs.add(new Message(MessageLevel.ERROR, " [ERROR] " + x));
                frontController.consolePrint();
            }

            @Override
            public void println(Object x) {
                super.println(x);
                msgs.add(new Message(MessageLevel.ERROR, " [ERROR] " + x.toString()));
                frontController.consolePrint();
            }

            @Override
            public void println(double x) {
                super.println(x);
                msgs.add(new Message(MessageLevel.ERROR, " [ERROR] " + x));
                frontController.consolePrint();
            }
        };
        System.setOut(traceAndWarns);
        System.setErr(errOutput);
    }

    public String getMsgs(boolean trace, boolean warn, boolean error) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Message msg : msgs) {
            if (msg.getLevel() == MessageLevel.TRACE) {
                if (trace) {
                    stringBuilder.append(msg.getMsg()).append("\n");
                }
            } else if (msg.getLevel() == MessageLevel.WARN) {
                if (warn) {
                    stringBuilder.append(msg.getMsg()).append("\n");
                }
            } else if (msg.getLevel() == MessageLevel.ERROR) {
                if (error) {
                    stringBuilder.append(msg.getMsg()).append("\n");
                }
            }
        }
        return stringBuilder.toString();
    }

    public void clearAll() {
        msgs.clear();
    }


    class Message {

        private MessageLevel level;
        private String msg;

        public Message(MessageLevel level, String msg) {
            this.level = level;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public MessageLevel getLevel() {
            return level;
        }
    }

    enum MessageLevel {
        TRACE,
        WARN,
        ERROR;
    }

}
