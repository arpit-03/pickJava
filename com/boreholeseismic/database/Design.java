/*    */ package com.boreholeseismic.database;
/*    */ 
/*    */ public class Design
/*    */ {
/*    */   int id;
/*    */   String name;
/*    */   int noRec;
/*  8 */   double[] traceOrder = new double[3];
/*  9 */   double[] tracePolarity = new double[3];
/*    */ 
/*    */ 
/*    */   
/*    */   double[][][] rotationMatrix;
/*    */ 
/*    */ 
/*    */   
/*    */   int[] recInfo;
/*    */ 
/*    */   
/*    */   String[] toolstringInfo;
/*    */ 
/*    */ 
/*    */   
/*    */   public Design(int id, String name, int noRec, double[][][] rotationMatrix, double trc_pol_11, double trc_pol_12, double trc_pol_13, double trc_order_11, double trc_order_12, double trc_order_13, int[] recInfo, String[] toolstringInfo) {
/* 25 */     this.id = id;
/* 26 */     this.name = name;
/* 27 */     this.noRec = noRec;
/*    */     
/* 29 */     this.traceOrder[0] = trc_order_11;
/* 30 */     this.traceOrder[1] = trc_order_12;
/* 31 */     this.traceOrder[2] = trc_order_13;
/*    */     
/* 33 */     this.tracePolarity[0] = trc_pol_11;
/* 34 */     this.tracePolarity[1] = trc_pol_12;
/* 35 */     this.tracePolarity[2] = trc_pol_13;
/*    */     
/* 37 */     this.rotationMatrix = rotationMatrix;
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
/* 49 */     this.recInfo = recInfo;
/* 50 */     this.toolstringInfo = toolstringInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getTracePolarity() {
/* 56 */     return this.tracePolarity;
/*    */   }
/*    */   
/*    */   public double[] getTraceOrder() {
/* 60 */     return this.traceOrder;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 65 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getNoRec() {
/* 69 */     return this.noRec;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getRecInfo() {
/* 74 */     return this.recInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[][][] getRotationMatrix() {
/* 79 */     return this.rotationMatrix;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getToolstringInfo() {
/* 84 */     return this.toolstringInfo;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/database/Design.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */