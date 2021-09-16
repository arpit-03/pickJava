/*     */ package org.apache.commons.math3.geometry.partitioning;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BoundaryProjector<S extends Space, T extends Space>
/*     */   implements BSPTreeVisitor<S>
/*     */ {
/*     */   private final Point<S> original;
/*     */   private Point<S> projected;
/*     */   private BSPTree<S> leaf;
/*     */   private double offset;
/*     */   
/*     */   BoundaryProjector(Point<S> original) {
/*  50 */     this.original = original;
/*  51 */     this.projected = null;
/*  52 */     this.leaf = null;
/*  53 */     this.offset = Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTreeVisitor.Order visitOrder(BSPTree<S> node) {
/*  60 */     if (node.getCut().getHyperplane().getOffset(this.original) <= 0.0D) {
/*  61 */       return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
/*     */     }
/*  63 */     return BSPTreeVisitor.Order.PLUS_SUB_MINUS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInternalNode(BSPTree<S> node) {
/*  71 */     Hyperplane<S> hyperplane = node.getCut().getHyperplane();
/*  72 */     double signedOffset = hyperplane.getOffset(this.original);
/*  73 */     if (FastMath.abs(signedOffset) < this.offset) {
/*     */ 
/*     */       
/*  76 */       Point<S> regular = hyperplane.project(this.original);
/*     */ 
/*     */       
/*  79 */       List<Region<T>> boundaryParts = boundaryRegions(node);
/*     */ 
/*     */       
/*  82 */       boolean regularFound = false;
/*  83 */       for (Region<T> part : boundaryParts) {
/*  84 */         if (!regularFound && belongsToPart(regular, hyperplane, part)) {
/*     */           
/*  86 */           this.projected = regular;
/*  87 */           this.offset = FastMath.abs(signedOffset);
/*  88 */           regularFound = true;
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       if (!regularFound)
/*     */       {
/*     */ 
/*     */         
/*  96 */         for (Region<T> part : boundaryParts) {
/*  97 */           Point<S> spI = singularProjection(regular, hyperplane, part);
/*  98 */           if (spI != null) {
/*  99 */             double distance = this.original.distance(spI);
/* 100 */             if (distance < this.offset) {
/* 101 */               this.projected = spI;
/* 102 */               this.offset = distance;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLeafNode(BSPTree<S> node) {
/* 115 */     if (this.leaf == null)
/*     */     {
/*     */       
/* 118 */       this.leaf = node;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundaryProjection<S> getProjection() {
/* 128 */     this.offset = FastMath.copySign(this.offset, ((Boolean)this.leaf.getAttribute()).booleanValue() ? -1.0D : 1.0D);
/*     */     
/* 130 */     return new BoundaryProjection<S>(this.original, this.projected, this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Region<T>> boundaryRegions(BSPTree<S> node) {
/* 140 */     List<Region<T>> regions = new ArrayList<Region<T>>(2);
/*     */ 
/*     */     
/* 143 */     BoundaryAttribute<S> ba = (BoundaryAttribute<S>)node.getAttribute();
/* 144 */     addRegion(ba.getPlusInside(), regions);
/* 145 */     addRegion(ba.getPlusOutside(), regions);
/*     */     
/* 147 */     return regions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRegion(SubHyperplane<S> sub, List<Region<T>> list) {
/* 156 */     if (sub != null) {
/*     */       
/* 158 */       Region<T> region = ((AbstractSubHyperplane)sub).getRemainingRegion();
/* 159 */       if (region != null) {
/* 160 */         list.add(region);
/*     */       }
/*     */     } 
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
/*     */   private boolean belongsToPart(Point<S> point, Hyperplane<S> hyperplane, Region<T> part) {
/* 176 */     Embedding<S, T> embedding = (Embedding)hyperplane;
/* 177 */     return (part.checkPoint(embedding.toSubSpace(point)) != Region.Location.OUTSIDE);
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
/*     */   private Point<S> singularProjection(Point<S> point, Hyperplane<S> hyperplane, Region<T> part) {
/* 192 */     Embedding<S, T> embedding = (Embedding)hyperplane;
/* 193 */     BoundaryProjection<T> bp = part.projectToBoundary(embedding.toSubSpace(point));
/*     */ 
/*     */     
/* 196 */     return (bp.getProjected() == null) ? null : embedding.toSpace(bp.getProjected());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/BoundaryProjector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */