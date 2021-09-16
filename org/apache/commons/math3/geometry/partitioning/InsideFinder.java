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
/*     */ class InsideFinder<S extends Space>
/*     */ {
/*     */   private final Region<S> region;
/*     */   private boolean plusFound;
/*     */   private boolean minusFound;
/*     */   
/*     */   InsideFinder(Region<S> region) {
/*  41 */     this.region = region;
/*  42 */     this.plusFound = false;
/*  43 */     this.minusFound = false;
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
/*     */   
/*     */   public void recurseSides(BSPTree<S> node, SubHyperplane<S> sub) {
/*  62 */     if (node.getCut() == null) {
/*  63 */       if (((Boolean)node.getAttribute()).booleanValue()) {
/*     */         
/*  65 */         this.plusFound = true;
/*  66 */         this.minusFound = true;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     Hyperplane<S> hyperplane = node.getCut().getHyperplane();
/*  72 */     SubHyperplane.SplitSubHyperplane<S> split = sub.split(hyperplane);
/*  73 */     switch (split.getSide()) {
/*     */       
/*     */       case PLUS:
/*  76 */         if (node.getCut().split(sub.getHyperplane()).getSide() == Side.PLUS) {
/*  77 */           if (!this.region.isEmpty(node.getMinus())) {
/*  78 */             this.plusFound = true;
/*     */           }
/*     */         }
/*  81 */         else if (!this.region.isEmpty(node.getMinus())) {
/*  82 */           this.minusFound = true;
/*     */         } 
/*     */         
/*  85 */         if (!this.plusFound || !this.minusFound) {
/*  86 */           recurseSides(node.getPlus(), sub);
/*     */         }
/*     */         return;
/*     */       
/*     */       case MINUS:
/*  91 */         if (node.getCut().split(sub.getHyperplane()).getSide() == Side.PLUS) {
/*  92 */           if (!this.region.isEmpty(node.getPlus())) {
/*  93 */             this.plusFound = true;
/*     */           }
/*     */         }
/*  96 */         else if (!this.region.isEmpty(node.getPlus())) {
/*  97 */           this.minusFound = true;
/*     */         } 
/*     */         
/* 100 */         if (!this.plusFound || !this.minusFound) {
/* 101 */           recurseSides(node.getMinus(), sub);
/*     */         }
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case BOTH:
/* 108 */         recurseSides(node.getPlus(), split.getPlus());
/*     */ 
/*     */         
/* 111 */         if (!this.plusFound || !this.minusFound) {
/* 112 */           recurseSides(node.getMinus(), split.getMinus());
/*     */         }
/*     */         return;
/*     */     } 
/*     */     
/* 117 */     if (node.getCut().getHyperplane().sameOrientationAs(sub.getHyperplane())) {
/* 118 */       if (node.getPlus().getCut() != null || ((Boolean)node.getPlus().getAttribute()).booleanValue()) {
/* 119 */         this.plusFound = true;
/*     */       }
/* 121 */       if (node.getMinus().getCut() != null || ((Boolean)node.getMinus().getAttribute()).booleanValue()) {
/* 122 */         this.minusFound = true;
/*     */       }
/*     */     } else {
/* 125 */       if (node.getPlus().getCut() != null || ((Boolean)node.getPlus().getAttribute()).booleanValue()) {
/* 126 */         this.minusFound = true;
/*     */       }
/* 128 */       if (node.getMinus().getCut() != null || ((Boolean)node.getMinus().getAttribute()).booleanValue()) {
/* 129 */         this.plusFound = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean plusFound() {
/* 140 */     return this.plusFound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean minusFound() {
/* 147 */     return this.minusFound;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/InsideFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */