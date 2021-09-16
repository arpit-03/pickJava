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
/*     */ public class ConvexityDefect
/*     */ {
/*     */   private int start;
/*     */   private int end;
/*     */   private IntPoint point;
/*     */   private double depth;
/*     */   
/*     */   public ConvexityDefect(IntPoint point, int start, int end, double depth) {
/*  46 */     this.point = point;
/*  47 */     this.start = start;
/*  48 */     this.end = end;
/*  49 */     this.depth = depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDepth() {
/*  57 */     return this.depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDepth(double depth) {
/*  65 */     this.depth = depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnd() {
/*  73 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnd(int end) {
/*  81 */     this.end = end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint getPoint() {
/*  89 */     return this.point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPoint(IntPoint point) {
/*  97 */     this.point = point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStart() {
/* 105 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStart(int start) {
/* 113 */     this.start = start;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/ConvexityDefect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */