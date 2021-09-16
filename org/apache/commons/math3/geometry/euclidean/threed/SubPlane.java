/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Line;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.PolygonsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.SubLine;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubPlane
/*     */   extends AbstractSubHyperplane<Euclidean3D, Euclidean2D>
/*     */ {
/*     */   public SubPlane(Hyperplane<Euclidean3D> hyperplane, Region<Euclidean2D> remainingRegion) {
/*  42 */     super(hyperplane, remainingRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSubHyperplane<Euclidean3D, Euclidean2D> buildNew(Hyperplane<Euclidean3D> hyperplane, Region<Euclidean2D> remainingRegion) {
/*  49 */     return new SubPlane(hyperplane, remainingRegion);
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
/*     */   public SubHyperplane.SplitSubHyperplane<Euclidean3D> split(Hyperplane<Euclidean3D> hyperplane) {
/*  61 */     Plane otherPlane = (Plane)hyperplane;
/*  62 */     Plane thisPlane = (Plane)getHyperplane();
/*  63 */     Line inter = otherPlane.intersection(thisPlane);
/*  64 */     double tolerance = thisPlane.getTolerance();
/*     */     
/*  66 */     if (inter == null) {
/*     */       
/*  68 */       double global = otherPlane.getOffset(thisPlane);
/*  69 */       if (global < -tolerance)
/*  70 */         return new SubHyperplane.SplitSubHyperplane(null, (SubHyperplane)this); 
/*  71 */       if (global > tolerance) {
/*  72 */         return new SubHyperplane.SplitSubHyperplane((SubHyperplane)this, null);
/*     */       }
/*  74 */       return new SubHyperplane.SplitSubHyperplane(null, null);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  79 */     Vector2D p = thisPlane.toSubSpace((Point<Euclidean3D>)inter.toSpace((Point<Euclidean1D>)Vector1D.ZERO));
/*  80 */     Vector2D q = thisPlane.toSubSpace((Point<Euclidean3D>)inter.toSpace((Point<Euclidean1D>)Vector1D.ONE));
/*  81 */     Vector3D crossP = Vector3D.crossProduct(inter.getDirection(), thisPlane.getNormal());
/*  82 */     if (crossP.dotProduct(otherPlane.getNormal()) < 0.0D) {
/*  83 */       Vector2D tmp = p;
/*  84 */       p = q;
/*  85 */       q = tmp;
/*     */     } 
/*  87 */     SubLine subLine1 = (new Line(p, q, tolerance)).wholeHyperplane();
/*     */     
/*  89 */     SubLine subLine2 = (new Line(q, p, tolerance)).wholeHyperplane();
/*     */ 
/*     */     
/*  92 */     BSPTree<Euclidean2D> splitTree = getRemainingRegion().getTree(false).split((SubHyperplane)subLine1);
/*  93 */     BSPTree<Euclidean2D> plusTree = getRemainingRegion().isEmpty(splitTree.getPlus()) ? new BSPTree(Boolean.FALSE) : new BSPTree((SubHyperplane)subLine2, new BSPTree(Boolean.FALSE), splitTree.getPlus(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     BSPTree<Euclidean2D> minusTree = getRemainingRegion().isEmpty(splitTree.getMinus()) ? new BSPTree(Boolean.FALSE) : new BSPTree((SubHyperplane)subLine1, new BSPTree(Boolean.FALSE), splitTree.getMinus(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     return new SubHyperplane.SplitSubHyperplane((SubHyperplane)new SubPlane(thisPlane.copySelf(), (Region<Euclidean2D>)new PolygonsSet(plusTree, tolerance)), (SubHyperplane)new SubPlane(thisPlane.copySelf(), (Region<Euclidean2D>)new PolygonsSet(minusTree, tolerance)));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/SubPlane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */