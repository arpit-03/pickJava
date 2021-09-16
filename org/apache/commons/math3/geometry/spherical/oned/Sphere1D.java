/*     */ package org.apache.commons.math3.geometry.spherical.oned;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.util.Localizable;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class Sphere1D
/*     */   implements Serializable, Space
/*     */ {
/*     */   private static final long serialVersionUID = 20131218L;
/*     */   
/*     */   private Sphere1D() {}
/*     */   
/*     */   public static Sphere1D getInstance() {
/*  50 */     return LazyHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDimension() {
/*  55 */     return 1;
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
/*     */   public Space getSubSpace() throws NoSubSpaceException {
/*  67 */     throw new NoSubSpaceException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LazyHolder
/*     */   {
/*  76 */     private static final Sphere1D INSTANCE = new Sphere1D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/*  85 */     return LazyHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NoSubSpaceException
/*     */     extends MathUnsupportedOperationException
/*     */   {
/*     */     private static final long serialVersionUID = 20140225L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public NoSubSpaceException() {
/* 101 */       super((Localizable)LocalizedFormats.NOT_SUPPORTED_IN_DIMENSION_N, new Object[] { Integer.valueOf(1) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/oned/Sphere1D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */