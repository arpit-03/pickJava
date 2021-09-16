/*    */ package org.apache.commons.math3.geometry.euclidean.oned;
/*    */ 
/*    */ import org.apache.commons.math3.geometry.Point;
/*    */ import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
/*    */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*    */ import org.apache.commons.math3.geometry.partitioning.Region;
/*    */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubOrientedPoint
/*    */   extends AbstractSubHyperplane<Euclidean1D, Euclidean1D>
/*    */ {
/*    */   public SubOrientedPoint(Hyperplane<Euclidean1D> hyperplane, Region<Euclidean1D> remainingRegion) {
/* 37 */     super(hyperplane, remainingRegion);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getSize() {
/* 43 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractSubHyperplane<Euclidean1D, Euclidean1D> buildNew(Hyperplane<Euclidean1D> hyperplane, Region<Euclidean1D> remainingRegion) {
/* 56 */     return new SubOrientedPoint(hyperplane, remainingRegion);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SubHyperplane.SplitSubHyperplane<Euclidean1D> split(Hyperplane<Euclidean1D> hyperplane) {
/* 62 */     double global = hyperplane.getOffset((Point)((OrientedPoint)getHyperplane()).getLocation());
/* 63 */     if (global < -1.0E-10D)
/* 64 */       return new SubHyperplane.SplitSubHyperplane(null, (SubHyperplane)this); 
/* 65 */     if (global > 1.0E-10D) {
/* 66 */       return new SubHyperplane.SplitSubHyperplane((SubHyperplane)this, null);
/*    */     }
/* 68 */     return new SubHyperplane.SplitSubHyperplane(null, null);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/oned/SubOrientedPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */