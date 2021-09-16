/*    */ package edu.mines.jtk.mosaic;
/*    */ 
/*    */ 
/*    */ public enum AxisScale
/*    */ {
/*  6 */   LINEAR,
/*  7 */   LOG10;
/*    */   
/*  9 */   public boolean isLinear() { return (this == LINEAR); } public boolean isLog() {
/* 10 */     return (this == LOG10);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/AxisScale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */