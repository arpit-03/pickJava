/*     */ package org.apache.commons.math3.geometry.partitioning;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractRegion<S extends Space, T extends Space>
/*     */   implements Region<S>
/*     */ {
/*     */   private BSPTree<S> tree;
/*     */   private final double tolerance;
/*     */   private double size;
/*     */   private Point<S> barycenter;
/*     */   
/*     */   protected AbstractRegion(double tolerance) {
/*  56 */     this.tree = new BSPTree<S>(Boolean.TRUE);
/*  57 */     this.tolerance = tolerance;
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
/*     */   protected AbstractRegion(BSPTree<S> tree, double tolerance) {
/*  74 */     this.tree = tree;
/*  75 */     this.tolerance = tolerance;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractRegion(Collection<SubHyperplane<S>> boundary, double tolerance) {
/* 100 */     this.tolerance = tolerance;
/*     */     
/* 102 */     if (boundary.size() == 0) {
/*     */ 
/*     */       
/* 105 */       this.tree = new BSPTree<S>(Boolean.TRUE);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 112 */       TreeSet<SubHyperplane<S>> ordered = new TreeSet<SubHyperplane<S>>(new Comparator<SubHyperplane<S>>()
/*     */           {
/*     */             public int compare(SubHyperplane<S> o1, SubHyperplane<S> o2) {
/* 115 */               double size1 = o1.getSize();
/* 116 */               double size2 = o2.getSize();
/* 117 */               return (size2 < size1) ? -1 : ((o1 == o2) ? 0 : 1);
/*     */             }
/*     */           });
/* 120 */       ordered.addAll(boundary);
/*     */ 
/*     */       
/* 123 */       this.tree = new BSPTree<S>();
/* 124 */       insertCuts(this.tree, ordered);
/*     */ 
/*     */       
/* 127 */       this.tree.visit(new BSPTreeVisitor<S>()
/*     */           {
/*     */             public BSPTreeVisitor.Order visitOrder(BSPTree<S> node)
/*     */             {
/* 131 */               return BSPTreeVisitor.Order.PLUS_SUB_MINUS;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void visitInternalNode(BSPTree<S> node) {}
/*     */ 
/*     */             
/*     */             public void visitLeafNode(BSPTree<S> node) {
/* 140 */               if (node.getParent() == null || node == node.getParent().getMinus()) {
/* 141 */                 node.setAttribute(Boolean.TRUE);
/*     */               } else {
/* 143 */                 node.setAttribute(Boolean.FALSE);
/*     */               } 
/*     */             }
/*     */           });
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
/*     */   public AbstractRegion(Hyperplane<S>[] hyperplanes, double tolerance) {
/* 158 */     this.tolerance = tolerance;
/* 159 */     if (hyperplanes == null || hyperplanes.length == 0) {
/* 160 */       this.tree = new BSPTree<S>(Boolean.FALSE);
/*     */     }
/*     */     else {
/*     */       
/* 164 */       this.tree = hyperplanes[0].wholeSpace().getTree(false);
/*     */ 
/*     */       
/* 167 */       BSPTree<S> node = this.tree;
/* 168 */       node.setAttribute(Boolean.TRUE);
/* 169 */       for (Hyperplane<S> hyperplane : hyperplanes) {
/* 170 */         if (node.insertCut(hyperplane)) {
/* 171 */           node.setAttribute(null);
/* 172 */           node.getPlus().setAttribute(Boolean.FALSE);
/* 173 */           node = node.getMinus();
/* 174 */           node.setAttribute(Boolean.TRUE);
/*     */         } 
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
/*     */   public double getTolerance() {
/* 189 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void insertCuts(BSPTree<S> node, Collection<SubHyperplane<S>> boundary) {
/* 200 */     Iterator<SubHyperplane<S>> iterator = boundary.iterator();
/*     */ 
/*     */     
/* 203 */     Hyperplane<S> inserted = null;
/* 204 */     while (inserted == null && iterator.hasNext()) {
/* 205 */       inserted = ((SubHyperplane<S>)iterator.next()).getHyperplane();
/* 206 */       if (!node.insertCut(inserted.copySelf())) {
/* 207 */         inserted = null;
/*     */       }
/*     */     } 
/*     */     
/* 211 */     if (!iterator.hasNext()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 216 */     ArrayList<SubHyperplane<S>> plusList = new ArrayList<SubHyperplane<S>>();
/* 217 */     ArrayList<SubHyperplane<S>> minusList = new ArrayList<SubHyperplane<S>>();
/* 218 */     while (iterator.hasNext()) {
/* 219 */       SubHyperplane<S> other = iterator.next();
/* 220 */       SubHyperplane.SplitSubHyperplane<S> split = other.split(inserted);
/* 221 */       switch (split.getSide()) {
/*     */         case PLUS:
/* 223 */           plusList.add(other);
/*     */         
/*     */         case MINUS:
/* 226 */           minusList.add(other);
/*     */         
/*     */         case BOTH:
/* 229 */           plusList.add(split.getPlus());
/* 230 */           minusList.add(split.getMinus());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 238 */     insertCuts(node.getPlus(), plusList);
/* 239 */     insertCuts(node.getMinus(), minusList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractRegion<S, T> copySelf() {
/* 245 */     return buildNew(this.tree.copySelf());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 250 */     return isEmpty(this.tree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(BSPTree<S> node) {
/* 260 */     if (node.getCut() == null)
/*     */     {
/* 262 */       return !((Boolean)node.getAttribute()).booleanValue();
/*     */     }
/*     */ 
/*     */     
/* 266 */     return (isEmpty(node.getMinus()) && isEmpty(node.getPlus()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 272 */     return isFull(this.tree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull(BSPTree<S> node) {
/* 282 */     if (node.getCut() == null)
/*     */     {
/* 284 */       return ((Boolean)node.getAttribute()).booleanValue();
/*     */     }
/*     */ 
/*     */     
/* 288 */     return (isFull(node.getMinus()) && isFull(node.getPlus()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Region<S> region) {
/* 294 */     return (new RegionFactory<S>()).difference(region, this).isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundaryProjection<S> projectToBoundary(Point<S> point) {
/* 301 */     BoundaryProjector<S, T> projector = new BoundaryProjector<S, T>(point);
/* 302 */     getTree(true).visit(projector);
/* 303 */     return projector.getProjection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region.Location checkPoint(Vector<S> point) {
/* 313 */     return checkPoint((Point<S>)point);
/*     */   }
/*     */ 
/*     */   
/*     */   public Region.Location checkPoint(Point<S> point) {
/* 318 */     return checkPoint(this.tree, point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Region.Location checkPoint(BSPTree<S> node, Vector<S> point) {
/* 329 */     return checkPoint(node, (Point<S>)point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Region.Location checkPoint(BSPTree<S> node, Point<S> point) {
/* 340 */     BSPTree<S> cell = node.getCell(point, this.tolerance);
/* 341 */     if (cell.getCut() == null)
/*     */     {
/* 343 */       return ((Boolean)cell.getAttribute()).booleanValue() ? Region.Location.INSIDE : Region.Location.OUTSIDE;
/*     */     }
/*     */ 
/*     */     
/* 347 */     Region.Location minusCode = checkPoint(cell.getMinus(), point);
/* 348 */     Region.Location plusCode = checkPoint(cell.getPlus(), point);
/* 349 */     return (minusCode == plusCode) ? minusCode : Region.Location.BOUNDARY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree<S> getTree(boolean includeBoundaryAttributes) {
/* 355 */     if (includeBoundaryAttributes && this.tree.getCut() != null && this.tree.getAttribute() == null)
/*     */     {
/* 357 */       this.tree.visit(new BoundaryBuilder<S>());
/*     */     }
/* 359 */     return this.tree;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getBoundarySize() {
/* 364 */     BoundarySizeVisitor<S> visitor = new BoundarySizeVisitor<S>();
/* 365 */     getTree(true).visit(visitor);
/* 366 */     return visitor.getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSize() {
/* 371 */     if (this.barycenter == null) {
/* 372 */       computeGeometricalProperties();
/*     */     }
/* 374 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setSize(double size) {
/* 381 */     this.size = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Point<S> getBarycenter() {
/* 386 */     if (this.barycenter == null) {
/* 387 */       computeGeometricalProperties();
/*     */     }
/* 389 */     return this.barycenter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setBarycenter(Vector<S> barycenter) {
/* 396 */     setBarycenter((Point<S>)barycenter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setBarycenter(Point<S> barycenter) {
/* 403 */     this.barycenter = barycenter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Side side(Hyperplane<S> hyperplane) {
/* 414 */     InsideFinder<S> finder = new InsideFinder<S>(this);
/* 415 */     finder.recurseSides(this.tree, hyperplane.wholeHyperplane());
/* 416 */     return finder.plusFound() ? (finder.minusFound() ? Side.BOTH : Side.PLUS) : (finder.minusFound() ? Side.MINUS : Side.HYPER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubHyperplane<S> intersection(SubHyperplane<S> sub) {
/* 423 */     return recurseIntersection(this.tree, sub);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SubHyperplane<S> recurseIntersection(BSPTree<S> node, SubHyperplane<S> sub) {
/* 434 */     if (node.getCut() == null) {
/* 435 */       return ((Boolean)node.getAttribute()).booleanValue() ? sub.copySelf() : null;
/*     */     }
/*     */     
/* 438 */     Hyperplane<S> hyperplane = node.getCut().getHyperplane();
/* 439 */     SubHyperplane.SplitSubHyperplane<S> split = sub.split(hyperplane);
/* 440 */     if (split.getPlus() != null) {
/* 441 */       if (split.getMinus() != null) {
/*     */         
/* 443 */         SubHyperplane<S> plus = recurseIntersection(node.getPlus(), split.getPlus());
/* 444 */         SubHyperplane<S> minus = recurseIntersection(node.getMinus(), split.getMinus());
/* 445 */         if (plus == null)
/* 446 */           return minus; 
/* 447 */         if (minus == null) {
/* 448 */           return plus;
/*     */         }
/* 450 */         return plus.reunite(minus);
/*     */       } 
/*     */ 
/*     */       
/* 454 */       return recurseIntersection(node.getPlus(), sub);
/*     */     } 
/* 456 */     if (split.getMinus() != null)
/*     */     {
/* 458 */       return recurseIntersection(node.getMinus(), sub);
/*     */     }
/*     */     
/* 461 */     return recurseIntersection(node.getPlus(), recurseIntersection(node.getMinus(), sub));
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
/*     */   public AbstractRegion<S, T> applyTransform(Transform<S, T> transform) {
/* 480 */     Map<BSPTree<S>, BSPTree<S>> map = new HashMap<BSPTree<S>, BSPTree<S>>();
/* 481 */     BSPTree<S> transformedTree = recurseTransform(getTree(false), transform, map);
/*     */ 
/*     */     
/* 484 */     for (Map.Entry<BSPTree<S>, BSPTree<S>> entry : map.entrySet()) {
/* 485 */       if (((BSPTree)entry.getKey()).getCut() != null) {
/*     */         
/* 487 */         BoundaryAttribute<S> original = (BoundaryAttribute<S>)((BSPTree)entry.getKey()).getAttribute();
/* 488 */         if (original != null) {
/*     */           
/* 490 */           BoundaryAttribute<S> transformed = (BoundaryAttribute<S>)((BSPTree)entry.getValue()).getAttribute();
/* 491 */           for (BSPTree<S> splitter : original.getSplitters()) {
/* 492 */             transformed.getSplitters().add(map.get(splitter));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 498 */     return buildNew(transformedTree);
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
/*     */   private BSPTree<S> recurseTransform(BSPTree<S> node, Transform<S, T> transform, Map<BSPTree<S>, BSPTree<S>> map) {
/*     */     BSPTree<S> transformedNode;
/* 513 */     if (node.getCut() == null) {
/* 514 */       transformedNode = new BSPTree<S>(node.getAttribute());
/*     */     } else {
/*     */       
/* 517 */       SubHyperplane<S> sub = node.getCut();
/* 518 */       SubHyperplane<S> tSub = ((AbstractSubHyperplane)sub).applyTransform(transform);
/* 519 */       BoundaryAttribute<S> attribute = (BoundaryAttribute<S>)node.getAttribute();
/* 520 */       if (attribute != null) {
/* 521 */         SubHyperplane<S> tPO = (attribute.getPlusOutside() == null) ? null : ((AbstractSubHyperplane)attribute.getPlusOutside()).applyTransform(transform);
/*     */         
/* 523 */         SubHyperplane<S> tPI = (attribute.getPlusInside() == null) ? null : ((AbstractSubHyperplane)attribute.getPlusInside()).applyTransform(transform);
/*     */ 
/*     */         
/* 526 */         attribute = new BoundaryAttribute<S>(tPO, tPI, new NodesSet<S>());
/*     */       } 
/*     */       
/* 529 */       transformedNode = new BSPTree<S>(tSub, recurseTransform(node.getPlus(), transform, map), recurseTransform(node.getMinus(), transform, map), attribute);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     map.put(node, transformedNode);
/* 536 */     return transformedNode;
/*     */   }
/*     */   
/*     */   public abstract AbstractRegion<S, T> buildNew(BSPTree<S> paramBSPTree);
/*     */   
/*     */   protected abstract void computeGeometricalProperties();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/AbstractRegion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */