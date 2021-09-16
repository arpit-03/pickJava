/*    */ package org.apache.commons.math3.geometry.spherical.oned;
/*    */ 
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
/*    */ public class SubLimitAngle
/*    */   extends AbstractSubHyperplane<Sphere1D, Sphere1D>
/*    */ {
/*    */   public SubLimitAngle(Hyperplane<Sphere1D> hyperplane, Region<Sphere1D> remainingRegion) {
/* 35 */     super(hyperplane, remainingRegion);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getSize() {
/* 41 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractSubHyperplane<Sphere1D, Sphere1D> buildNew(Hyperplane<Sphere1D> hyperplane, Region<Sphere1D> remainingRegion) {
/* 54 */     return new SubLimitAngle(hyperplane, remainingRegion);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SubHyperplane.SplitSubHyperplane<Sphere1D> split(Hyperplane<Sphere1D> hyperplane) {
/* 60 */     double global = hyperplane.getOffset(((LimitAngle)getHyperplane()).getLocation());
/* 61 */     return (global < -1.0E-10D) ? new SubHyperplane.SplitSubHyperplane(null, (SubHyperplane)this) : new SubHyperplane.SplitSubHyperplane((SubHyperplane)this, null);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/oned/SubLimitAngle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */