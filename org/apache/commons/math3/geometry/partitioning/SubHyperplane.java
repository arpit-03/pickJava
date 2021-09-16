/*     */ package org.apache.commons.math3.geometry.partitioning;
/*     */ 
/*     */ import org.apache.commons.math3.geometry.Space;
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
/*     */ public interface SubHyperplane<S extends Space>
/*     */ {
/*     */   SubHyperplane<S> copySelf();
/*     */   
/*     */   Hyperplane<S> getHyperplane();
/*     */   
/*     */   boolean isEmpty();
/*     */   
/*     */   double getSize();
/*     */   
/*     */   @Deprecated
/*     */   Side side(Hyperplane<S> paramHyperplane);
/*     */   
/*     */   SplitSubHyperplane<S> split(Hyperplane<S> paramHyperplane);
/*     */   
/*     */   SubHyperplane<S> reunite(SubHyperplane<S> paramSubHyperplane);
/*     */   
/*     */   public static class SplitSubHyperplane<U extends Space>
/*     */   {
/*     */     private final SubHyperplane<U> plus;
/*     */     private final SubHyperplane<U> minus;
/*     */     
/*     */     public SplitSubHyperplane(SubHyperplane<U> plus, SubHyperplane<U> minus) {
/* 113 */       this.plus = plus;
/* 114 */       this.minus = minus;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SubHyperplane<U> getPlus() {
/* 121 */       return this.plus;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SubHyperplane<U> getMinus() {
/* 128 */       return this.minus;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Side getSide() {
/* 140 */       if (this.plus != null && !this.plus.isEmpty()) {
/* 141 */         if (this.minus != null && !this.minus.isEmpty()) {
/* 142 */           return Side.BOTH;
/*     */         }
/* 144 */         return Side.PLUS;
/*     */       } 
/* 146 */       if (this.minus != null && !this.minus.isEmpty()) {
/* 147 */         return Side.MINUS;
/*     */       }
/* 149 */       return Side.HYPER;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/SubHyperplane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */