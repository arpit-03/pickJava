/*    */ package org.apache.commons.math3.geometry.euclidean.threed;
/*    */ 
/*    */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*    */ import org.apache.commons.math3.exception.util.Localizable;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*    */ public class CardanEulerSingularityException
/*    */   extends MathIllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = -1360952845582206770L;
/*    */   
/*    */   public CardanEulerSingularityException(boolean isCardan) {
/* 41 */     super(isCardan ? (Localizable)LocalizedFormats.CARDAN_ANGLES_SINGULARITY : (Localizable)LocalizedFormats.EULER_ANGLES_SINGULARITY, new Object[0]);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/CardanEulerSingularityException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */