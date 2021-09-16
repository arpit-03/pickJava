/*    */ package org.apache.commons.math3.geometry.spherical.twod;
/*    */ 
/*    */ import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
/*    */ import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
/*    */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*    */ import org.apache.commons.math3.geometry.partitioning.Region;
/*    */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*    */ import org.apache.commons.math3.geometry.spherical.oned.Arc;
/*    */ import org.apache.commons.math3.geometry.spherical.oned.ArcsSet;
/*    */ import org.apache.commons.math3.geometry.spherical.oned.Sphere1D;
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
/*    */ public class SubCircle
/*    */   extends AbstractSubHyperplane<Sphere2D, Sphere1D>
/*    */ {
/*    */   public SubCircle(Hyperplane<Sphere2D> hyperplane, Region<Sphere1D> remainingRegion) {
/* 39 */     super(hyperplane, remainingRegion);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractSubHyperplane<Sphere2D, Sphere1D> buildNew(Hyperplane<Sphere2D> hyperplane, Region<Sphere1D> remainingRegion) {
/* 46 */     return new SubCircle(hyperplane, remainingRegion);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SubHyperplane.SplitSubHyperplane<Sphere2D> split(Hyperplane<Sphere2D> hyperplane) {
/* 53 */     Circle thisCircle = (Circle)getHyperplane();
/* 54 */     Circle otherCircle = (Circle)hyperplane;
/* 55 */     double angle = Vector3D.angle(thisCircle.getPole(), otherCircle.getPole());
/*    */     
/* 57 */     if (angle < thisCircle.getTolerance() || angle > Math.PI - thisCircle.getTolerance())
/*    */     {
/* 59 */       return new SubHyperplane.SplitSubHyperplane(null, null);
/*    */     }
/*    */     
/* 62 */     Arc arc = thisCircle.getInsideArc(otherCircle);
/* 63 */     ArcsSet.Split split = ((ArcsSet)getRemainingRegion()).split(arc);
/* 64 */     ArcsSet plus = split.getPlus();
/* 65 */     ArcsSet minus = split.getMinus();
/* 66 */     return new SubHyperplane.SplitSubHyperplane((plus == null) ? null : (SubHyperplane)new SubCircle(thisCircle.copySelf(), (Region<Sphere1D>)plus), (minus == null) ? null : (SubHyperplane)new SubCircle(thisCircle.copySelf(), (Region<Sphere1D>)minus));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/SubCircle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */