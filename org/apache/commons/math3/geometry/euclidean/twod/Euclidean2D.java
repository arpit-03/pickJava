/*    */ package org.apache.commons.math3.geometry.euclidean.twod;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*    */ import org.apache.commons.math3.geometry.Space;
/*    */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
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
/*    */ public class Euclidean2D
/*    */   implements Serializable, Space
/*    */ {
/*    */   private static final long serialVersionUID = 4793432849757649566L;
/*    */   
/*    */   private Euclidean2D() {}
/*    */   
/*    */   public static Euclidean2D getInstance() {
/* 43 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDimension() {
/* 48 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Euclidean1D getSubSpace() {
/* 53 */     return Euclidean1D.getInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class LazyHolder
/*    */   {
/* 62 */     private static final Euclidean2D INSTANCE = new Euclidean2D();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 71 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/Euclidean2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */