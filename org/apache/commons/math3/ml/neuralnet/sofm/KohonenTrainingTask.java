/*    */ package org.apache.commons.math3.ml.neuralnet.sofm;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.math3.ml.neuralnet.Network;
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
/*    */ public class KohonenTrainingTask
/*    */   implements Runnable
/*    */ {
/*    */   private final Network net;
/*    */   private final Iterator<double[]> featuresIterator;
/*    */   private final KohonenUpdateAction updateAction;
/*    */   
/*    */   public KohonenTrainingTask(Network net, Iterator<double[]> featuresIterator, KohonenUpdateAction updateAction) {
/* 46 */     this.net = net;
/* 47 */     this.featuresIterator = featuresIterator;
/* 48 */     this.updateAction = updateAction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 55 */     while (this.featuresIterator.hasNext())
/* 56 */       this.updateAction.update(this.net, this.featuresIterator.next()); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/sofm/KohonenTrainingTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */