/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.IntPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PointToProcess
/*     */   implements Comparable<PointToProcess>
/*     */ {
/*     */   public int x;
/*     */   public int y;
/*     */   public float k;
/*     */   public float distance;
/*     */   
/*     */   public PointToProcess(IntPoint p) {
/* 145 */     this.x = p.x;
/* 146 */     this.y = p.y;
/* 147 */     this.k = 0.0F;
/* 148 */     this.distance = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(PointToProcess o) {
/* 153 */     if (this.k == o.k) {
/* 154 */       return 0;
/*     */     }
/* 156 */     if (this.k > o.k) {
/* 157 */       return 1;
/*     */     }
/*     */     
/* 160 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntPoint toPoint() {
/* 165 */     return new IntPoint(this.x, this.y);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/PointToProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */