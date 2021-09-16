/*    */ package Catalano.Statistics;
/*    */ 
/*    */ import Catalano.Core.ArraysUtil;
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
/*    */ public final class Correlations
/*    */ {
/*    */   public static double VectorInnerProduct(double[] p, double[] q) {
/* 44 */     double product = 0.0D;
/* 45 */     for (int i = 0; i < p.length; i++) {
/* 46 */       product += p[i] * q[i];
/*    */     }
/* 48 */     return product;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double Tanimoto(double[] p, double[] q) {
/* 58 */     double product = 0.0D;
/* 59 */     double x = 0.0D, y = 0.0D;
/* 60 */     for (int i = 0; i < p.length; i++) {
/* 61 */       product += p[i] * q[i];
/* 62 */       x += p[i] * p[i];
/* 63 */       y += q[i] * q[i];
/*    */     } 
/* 65 */     return product / (x + y - product);
/*    */   }
/*    */   
/*    */   public static double PearsonCorrelation(double[] x, double[] y) {
/* 69 */     double meanX = Tools.Mean(x);
/* 70 */     double meanY = Tools.Mean(y);
/* 71 */     return PearsonCorrelation(x, y, meanX, meanY);
/*    */   }
/*    */   
/*    */   public static double PearsonCorrelation(double[] x, double[] y, double meanX, double meanY) {
/* 75 */     double sumNum = 0.0D, sumDenX = 0.0D, sumDenY = 0.0D;
/* 76 */     for (int i = 0; i < x.length; i++) {
/* 77 */       sumNum += (x[i] - meanX) * (y[i] - meanY);
/* 78 */       sumDenX += Math.pow(x[i] - meanX, 2.0D);
/* 79 */       sumDenY += Math.pow(y[i] - meanY, 2.0D);
/*    */     } 
/* 81 */     double sumDen = Math.sqrt(sumDenX * sumDenY);
/* 82 */     return sumNum / sumDen;
/*    */   }
/*    */ 
/*    */   
/*    */   public static double SpearmanCorrelation(double[] x, double[] y) {
/* 87 */     int[] r1 = ArraysUtil.Argsort(x, true);
/* 88 */     int[] r2 = ArraysUtil.Argsort(y, true);
/*    */     
/* 90 */     double diff = 0.0D;
/* 91 */     for (int i = 0; i < r1.length; i++) {
/* 92 */       diff += (r1[i] - r2[i]);
/*    */     }
/* 94 */     diff *= 6.0D;
/*    */     
/* 96 */     double den = r1.length * (Math.pow(r1.length, 2.0D) - 1.0D);
/*    */     
/* 98 */     return 1.0D - diff / den;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Correlations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */