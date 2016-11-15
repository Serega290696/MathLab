package org.beltser;

import org.apache.commons.collections.map.HashedMap;
import org.beltser.ppz.labs.OperatorLab4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Test {
  @org.junit.Test
  public void test() throws Exception {
    Main main = new Main();
    Main.launch();
    Main.controller.showChart();
  }
  @org.junit.Test
  public void test2() throws Exception {
    OperatorLab4 lab = new OperatorLab4(10, TimeUnit.SECONDS);
    Map<String, Object> map = new HashedMap();
    BigDecimal answer = lab.launch(map);
    System.out.println("answer = " + answer.doubleValue());
  }

  @org.junit.Test
  public void test33() throws Exception {
    ArrayList<Integer> i = new ArrayList<>();
    i.add(1);
    i.add(2);
    i.add(3);
    i.add(4, 4);
    System.out.println(Arrays.toString(i.toArray()));
  }
}
