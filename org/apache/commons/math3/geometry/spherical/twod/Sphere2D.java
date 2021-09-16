/*    */ package org.apache.commons.math3.geometry.spherical.twod;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*    */ import org.apache.commons.math3.geometry.Space;
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
/*    */ public class Sphere2D
/*    */   implements Serializable, Space
/*    */ {
/*    */   private static final long serialVersionUID = 20131218L;
/*    */   
/*    */   private Sphere2D() {}
/*    */   
/*    */   public static Sphere2D getInstance() {
/* 49 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDimension() {
/* 54 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Sphere1D getSubSpace() {
/* 59 */     return Sphere1D.getInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class LazyHolder
/*    */   {
/* 68 */     private static final Sphere2D INSTANCE = new Sphere2D();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 77 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/Sphere2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */