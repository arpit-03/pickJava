/*    */ package org.apache.commons.math3.analysis.interpolation;
/*    */ 
/*    */ import org.apache.commons.math3.util.FastMath;
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
/*    */ public class InterpolatingMicrosphere2D
/*    */   extends InterpolatingMicrosphere
/*    */ {
/*    */   private static final int DIMENSION = 2;
/*    */   
/*    */   public InterpolatingMicrosphere2D(int size, double maxDarkFraction, double darkThreshold, double background) {
/* 57 */     super(2, size, maxDarkFraction, darkThreshold, background);
/*    */ 
/*    */     
/* 60 */     for (int i = 0; i < size; i++) {
/* 61 */       double angle = i * 6.283185307179586D / size;
/*    */       
/* 63 */       add(new double[] { FastMath.cos(angle), FastMath.sin(angle) }, false);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected InterpolatingMicrosphere2D(InterpolatingMicrosphere2D other) {
/* 75 */     super(other);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InterpolatingMicrosphere2D copy() {
/* 85 */     return new InterpolatingMicrosphere2D(this);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/InterpolatingMicrosphere2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */