/*    */ package org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullOutputStream
/*    */   extends OutputStream
/*    */ {
/* 35 */   public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();
/*    */   
/*    */   public void write(byte[] b, int off, int len) {}
/*    */   
/*    */   public void write(int b) {}
/*    */   
/*    */   public void write(byte[] b) throws IOException {}
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/io/output/NullOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */