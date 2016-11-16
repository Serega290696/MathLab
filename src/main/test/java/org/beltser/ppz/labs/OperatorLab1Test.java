package org.beltser.ppz.labs;

import java.nio.ByteBuffer;

public class OperatorLab1Test {

  @org.junit.Test
  public void testGetInput() throws Exception {

  }

  @org.junit.Test
  public void testCompute() throws Exception {

  }

  @org.junit.Test
  public void testShowResult() throws Exception {
    ByteBuffer bs = ByteBuffer.wrap(new byte[] {1, 2, 3, 49, 50});
    System.out.println(bs.toString());
  }
}