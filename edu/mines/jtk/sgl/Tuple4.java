/*    */ package edu.mines.jtk.sgl;
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
/*    */ public class Tuple4
/*    */ {
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   public double w;
/*    */   
/*    */   public Tuple4() {}
/*    */   
/*    */   public Tuple4(double x, double y, double z, double w) {
/* 58 */     this.x = x;
/* 59 */     this.y = y;
/* 60 */     this.z = z;
/* 61 */     this.w = w;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Tuple4(Tuple4 t) {
/* 69 */     this.x = t.x;
/* 70 */     this.y = t.y;
/* 71 */     this.z = t.z;
/* 72 */     this.w = t.w;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 76 */     if (this == obj)
/* 77 */       return true; 
/* 78 */     if (obj == null || getClass() != obj.getClass())
/* 79 */       return false; 
/* 80 */     Tuple4 that = (Tuple4)obj;
/* 81 */     return (this.x == that.x && this.y == that.y && this.z == that.z && this.w == that.w);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 88 */     long xbits = Double.doubleToLongBits(this.x);
/* 89 */     long ybits = Double.doubleToLongBits(this.y);
/* 90 */     long zbits = Double.doubleToLongBits(this.z);
/* 91 */     long wbits = Double.doubleToLongBits(this.w);
/* 92 */     return (int)(xbits ^ xbits >>> 32L ^ ybits ^ ybits >>> 32L ^ zbits ^ zbits >>> 32L ^ wbits ^ wbits >>> 32L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "(" + this.x + "," + this.y + "," + this.z + "," + this.w + ")";
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Tuple4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */