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
/*     */ public class BoundaryAttribute<S extends Space>
/*     */ {
/*     */   private final SubHyperplane<S> plusOutside;
/*     */   private final SubHyperplane<S> plusInside;
/*     */   private final NodesSet<S> splitters;
/*     */   
/*     */   @Deprecated
/*     */   public BoundaryAttribute(SubHyperplane<S> plusOutside, SubHyperplane<S> plusInside) {
/*  65 */     this(plusOutside, plusInside, null);
/*     */   }
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
/*     */   BoundaryAttribute(SubHyperplane<S> plusOutside, SubHyperplane<S> plusInside, NodesSet<S> splitters) {
/*  82 */     this.plusOutside = plusOutside;
/*  83 */     this.plusInside = plusInside;
/*  84 */     this.splitters = splitters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubHyperplane<S> getPlusOutside() {
/*  95 */     return this.plusOutside;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubHyperplane<S> getPlusInside() {
/* 106 */     return this.plusInside;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodesSet<S> getSplitters() {
/* 113 */     return this.splitters;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/BoundaryAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */