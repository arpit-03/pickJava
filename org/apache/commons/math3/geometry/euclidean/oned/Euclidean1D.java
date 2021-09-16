/*    */ package org.apache.commons.math3.geometry.euclidean.oned;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*    */ import org.apache.commons.math3.exception.util.Localizable;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*    */ import org.apache.commons.math3.geometry.Space;
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
/*    */ public class Euclidean1D
/*    */   implements Serializable, Space
/*    */ {
/*    */   private static final long serialVersionUID = -1178039568877797126L;
/*    */   
/*    */   private Euclidean1D() {}
/*    */   
/*    */   public static Euclidean1D getInstance() {
/* 44 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDimension() {
/* 49 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Space getSubSpace() throws NoSubSpaceException {
/* 61 */     throw new NoSubSpaceException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class LazyHolder
/*    */   {
/* 70 */     private static final Euclidean1D INSTANCE = new Euclidean1D();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 79 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class NoSubSpaceException
/*    */     extends MathUnsupportedOperationException
/*    */   {
/*    */     private static final long serialVersionUID = 20140225L;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public NoSubSpaceException() {
/* 95 */       super((Localizable)LocalizedFormats.NOT_SUPPORTED_IN_DIMENSION_N, new Object[] { Integer.valueOf(1) });
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/oned/Euclidean1D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */