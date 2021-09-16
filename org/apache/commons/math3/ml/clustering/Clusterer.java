/*    */ package org.apache.commons.math3.ml.clustering;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.apache.commons.math3.exception.ConvergenceException;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
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
/*    */ public abstract class Clusterer<T extends Clusterable>
/*    */ {
/*    */   private DistanceMeasure measure;
/*    */   
/*    */   protected Clusterer(DistanceMeasure measure) {
/* 43 */     this.measure = measure;
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
/*    */   public abstract List<? extends Cluster<T>> cluster(Collection<T> paramCollection) throws MathIllegalArgumentException, ConvergenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DistanceMeasure getDistanceMeasure() {
/* 65 */     return this.measure;
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
/*    */   protected double distance(Clusterable p1, Clusterable p2) {
/* 77 */     return this.measure.compute(p1.getPoint(), p2.getPoint());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/Clusterer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */