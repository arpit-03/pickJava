/*     */ package org.apache.commons.math3.geometry.partitioning;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class AbstractSubHyperplane<S extends Space, T extends Space>
/*     */   implements SubHyperplane<S>
/*     */ {
/*     */   private final Hyperplane<S> hyperplane;
/*     */   private final Region<T> remainingRegion;
/*     */   
/*     */   protected AbstractSubHyperplane(Hyperplane<S> hyperplane, Region<T> remainingRegion) {
/*  53 */     this.hyperplane = hyperplane;
/*  54 */     this.remainingRegion = remainingRegion;
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
/*     */   public AbstractSubHyperplane<S, T> copySelf() {
/*  67 */     return buildNew(this.hyperplane.copySelf(), this.remainingRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hyperplane<S> getHyperplane() {
/*  74 */     return this.hyperplane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region<T> getRemainingRegion() {
/*  85 */     return this.remainingRegion;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSize() {
/*  90 */     return this.remainingRegion.getSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractSubHyperplane<S, T> reunite(SubHyperplane<S> other) {
/*  96 */     AbstractSubHyperplane<S, T> o = (AbstractSubHyperplane)other;
/*  97 */     return buildNew(this.hyperplane, (new RegionFactory<T>()).union(this.remainingRegion, o.remainingRegion));
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
/*     */   public AbstractSubHyperplane<S, T> applyTransform(Transform<S, T> transform) {
/* 112 */     Hyperplane<S> tHyperplane = transform.apply(this.hyperplane);
/*     */ 
/*     */     
/* 115 */     Map<BSPTree<T>, BSPTree<T>> map = new HashMap<BSPTree<T>, BSPTree<T>>();
/* 116 */     BSPTree<T> tTree = recurseTransform(this.remainingRegion.getTree(false), tHyperplane, transform, map);
/*     */ 
/*     */ 
/*     */     
/* 120 */     for (Map.Entry<BSPTree<T>, BSPTree<T>> entry : map.entrySet()) {
/* 121 */       if (((BSPTree)entry.getKey()).getCut() != null) {
/*     */         
/* 123 */         BoundaryAttribute<T> original = (BoundaryAttribute<T>)((BSPTree)entry.getKey()).getAttribute();
/* 124 */         if (original != null) {
/*     */           
/* 126 */           BoundaryAttribute<T> transformed = (BoundaryAttribute<T>)((BSPTree)entry.getValue()).getAttribute();
/* 127 */           for (BSPTree<T> splitter : original.getSplitters()) {
/* 128 */             transformed.getSplitters().add(map.get(splitter));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     return buildNew(tHyperplane, this.remainingRegion.buildNew(tTree));
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
/*     */   private BSPTree<T> recurseTransform(BSPTree<T> node, Hyperplane<S> transformed, Transform<S, T> transform, Map<BSPTree<T>, BSPTree<T>> map) {
/*     */     BSPTree<T> transformedNode;
/* 151 */     if (node.getCut() == null) {
/* 152 */       transformedNode = new BSPTree<T>(node.getAttribute());
/*     */     }
/*     */     else {
/*     */       
/* 156 */       BoundaryAttribute<T> attribute = (BoundaryAttribute<T>)node.getAttribute();
/* 157 */       if (attribute != null) {
/* 158 */         SubHyperplane<T> tPO = (attribute.getPlusOutside() == null) ? null : transform.apply(attribute.getPlusOutside(), this.hyperplane, transformed);
/*     */         
/* 160 */         SubHyperplane<T> tPI = (attribute.getPlusInside() == null) ? null : transform.apply(attribute.getPlusInside(), this.hyperplane, transformed);
/*     */ 
/*     */         
/* 163 */         attribute = new BoundaryAttribute<T>(tPO, tPI, new NodesSet<T>());
/*     */       } 
/*     */       
/* 166 */       transformedNode = new BSPTree<T>(transform.apply(node.getCut(), this.hyperplane, transformed), recurseTransform(node.getPlus(), transformed, transform, map), recurseTransform(node.getMinus(), transformed, transform, map), attribute);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     map.put(node, transformedNode);
/* 173 */     return transformedNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Side side(Hyperplane<S> hyper) {
/* 180 */     return split(hyper).getSide();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 188 */     return this.remainingRegion.isEmpty();
/*     */   }
/*     */   
/*     */   protected abstract AbstractSubHyperplane<S, T> buildNew(Hyperplane<S> paramHyperplane, Region<T> paramRegion);
/*     */   
/*     */   public abstract SubHyperplane.SplitSubHyperplane<S> split(Hyperplane<S> paramHyperplane);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/AbstractSubHyperplane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */