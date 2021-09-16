/*    */ package edu.mines.jtk.mosaic;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DPoint
/*    */ {
/*    */   public double x;
/*    */   public double y;
/*    */   
/*    */   public DPoint(double x, double y) {
/* 40 */     this.x = x;
/* 41 */     this.y = y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DPoint(DPoint p) {
/* 49 */     this(p.x, p.y);
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 53 */     if (this == obj)
/* 54 */       return true; 
/* 55 */     if (obj == null || getClass() != obj.getClass())
/* 56 */       return false; 
/* 57 */     DPoint that = (DPoint)obj;
/* 58 */     return (this.x == that.x && this.y == that.y);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 62 */     long xbits = Double.doubleToLongBits(this.x);
/* 63 */     long ybits = Double.doubleToLongBits(this.y);
/* 64 */     return (int)(xbits ^ xbits >>> 32L ^ ybits ^ ybits >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "(" + this.x + "," + this.y + ")";
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/DPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */