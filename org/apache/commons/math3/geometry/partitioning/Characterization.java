/*     */ package org.apache.commons.math3.geometry.partitioning;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
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
/*     */ class Characterization<S extends Space>
/*     */ {
/*     */   private SubHyperplane<S> outsideTouching;
/*     */   private SubHyperplane<S> insideTouching;
/*     */   private final NodesSet<S> outsideSplitters;
/*     */   private final NodesSet<S> insideSplitters;
/*     */   
/*     */   Characterization(BSPTree<S> node, SubHyperplane<S> sub) {
/*  57 */     this.outsideTouching = null;
/*  58 */     this.insideTouching = null;
/*  59 */     this.outsideSplitters = new NodesSet<S>();
/*  60 */     this.insideSplitters = new NodesSet<S>();
/*  61 */     characterize(node, sub, new ArrayList<BSPTree<S>>());
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
/*     */   
/*     */   private void characterize(BSPTree<S> node, SubHyperplane<S> sub, List<BSPTree<S>> splitters) {
/*  79 */     if (node.getCut() == null) {
/*     */       
/*  81 */       boolean inside = ((Boolean)node.getAttribute()).booleanValue();
/*  82 */       if (inside) {
/*  83 */         addInsideTouching(sub, splitters);
/*     */       } else {
/*  85 */         addOutsideTouching(sub, splitters);
/*     */       } 
/*     */     } else {
/*  88 */       Hyperplane<S> hyperplane = node.getCut().getHyperplane();
/*  89 */       SubHyperplane.SplitSubHyperplane<S> split = sub.split(hyperplane);
/*  90 */       switch (split.getSide()) {
/*     */         case PLUS:
/*  92 */           characterize(node.getPlus(), sub, splitters);
/*     */           return;
/*     */         case MINUS:
/*  95 */           characterize(node.getMinus(), sub, splitters);
/*     */           return;
/*     */         case BOTH:
/*  98 */           splitters.add(node);
/*  99 */           characterize(node.getPlus(), split.getPlus(), splitters);
/* 100 */           characterize(node.getMinus(), split.getMinus(), splitters);
/* 101 */           splitters.remove(splitters.size() - 1);
/*     */           return;
/*     */       } 
/*     */       
/* 105 */       throw new MathInternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOutsideTouching(SubHyperplane<S> sub, List<BSPTree<S>> splitters) {
/* 116 */     if (this.outsideTouching == null) {
/* 117 */       this.outsideTouching = sub;
/*     */     } else {
/* 119 */       this.outsideTouching = this.outsideTouching.reunite(sub);
/*     */     } 
/* 121 */     this.outsideSplitters.addAll(splitters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addInsideTouching(SubHyperplane<S> sub, List<BSPTree<S>> splitters) {
/* 130 */     if (this.insideTouching == null) {
/* 131 */       this.insideTouching = sub;
/*     */     } else {
/* 133 */       this.insideTouching = this.insideTouching.reunite(sub);
/*     */     } 
/* 135 */     this.insideSplitters.addAll(splitters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean touchOutside() {
/* 142 */     return (this.outsideTouching != null && !this.outsideTouching.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubHyperplane<S> outsideTouching() {
/* 150 */     return this.outsideTouching;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodesSet<S> getOutsideSplitters() {
/* 161 */     return this.outsideSplitters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean touchInside() {
/* 168 */     return (this.insideTouching != null && !this.insideTouching.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubHyperplane<S> insideTouching() {
/* 176 */     return this.insideTouching;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodesSet<S> getInsideSplitters() {
/* 187 */     return this.insideSplitters;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/Characterization.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */