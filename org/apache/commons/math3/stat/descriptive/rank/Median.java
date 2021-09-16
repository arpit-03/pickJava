/*    */ package org.apache.commons.math3.stat.descriptive.rank;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.exception.NullArgumentException;
/*    */ import org.apache.commons.math3.stat.ranking.NaNStrategy;
/*    */ import org.apache.commons.math3.util.KthSelector;
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
/*    */ public class Median
/*    */   extends Percentile
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3961477041290915687L;
/*    */   private static final double FIXED_QUANTILE_50 = 50.0D;
/*    */   
/*    */   public Median() {
/* 50 */     super(50.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Median(Median original) throws NullArgumentException {
/* 61 */     super(original);
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
/*    */ 
/*    */ 
/*    */   
/*    */   private Median(Percentile.EstimationType estimationType, NaNStrategy nanStrategy, KthSelector kthSelector) throws MathIllegalArgumentException {
/* 76 */     super(50.0D, estimationType, nanStrategy, kthSelector);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Median withEstimationType(Percentile.EstimationType newEstimationType) {
/* 82 */     return new Median(newEstimationType, getNaNStrategy(), getKthSelector());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Median withNaNStrategy(NaNStrategy newNaNStrategy) {
/* 88 */     return new Median(getEstimationType(), newNaNStrategy, getKthSelector());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Median withKthSelector(KthSelector newKthSelector) {
/* 94 */     return new Median(getEstimationType(), getNaNStrategy(), newKthSelector);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/rank/Median.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */