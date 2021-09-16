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
/*    */ public class Tuple3
/*    */ {
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   
/*    */   public Tuple3() {}
/*    */   
/*    */   public Tuple3(double x, double y, double z) {
/* 52 */     this.x = x;
/* 53 */     this.y = y;
/* 54 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Tuple3(Tuple3 t) {
/* 62 */     this.x = t.x;
/* 63 */     this.y = t.y;
/* 64 */     this.z = t.z;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 68 */     if (this == obj)
/* 69 */       return true; 
/* 70 */     if (obj == null || getClass() != obj.getClass())
/* 71 */       return false; 
/* 72 */     Tuple3 that = (Tuple3)obj;
/* 73 */     return (this.x == that.x && this.y == that.y && this.z == that.z);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 77 */     long xbits = Double.doubleToLongBits(this.x);
/* 78 */     long ybits = Double.doubleToLongBits(this.y);
/* 79 */     long zbits = Double.doubleToLongBits(this.z);
/* 80 */     return (int)(xbits ^ xbits >>> 32L ^ ybits ^ ybits >>> 32L ^ zbits ^ zbits >>> 32L);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 86 */     return "(" + this.x + "," + this.y + "," + this.z + ")";
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Tuple3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */