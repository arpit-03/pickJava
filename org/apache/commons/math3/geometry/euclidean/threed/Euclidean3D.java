/*    */ package org.apache.commons.math3.geometry.euclidean.threed;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*    */ import org.apache.commons.math3.geometry.Space;
/*    */ import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
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
/*    */ public class Euclidean3D
/*    */   implements Serializable, Space
/*    */ {
/*    */   private static final long serialVersionUID = 6249091865814886817L;
/*    */   
/*    */   private Euclidean3D() {}
/*    */   
/*    */   public static Euclidean3D getInstance() {
/* 43 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDimension() {
/* 48 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public Euclidean2D getSubSpace() {
/* 53 */     return Euclidean2D.getInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class LazyHolder
/*    */   {
/* 62 */     private static final Euclidean3D INSTANCE = new Euclidean3D();
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


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/Euclidean3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */