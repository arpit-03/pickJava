/*     */ package Catalano.Math.Transforms;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DiscreteHartleyTransform
/*     */ {
/*     */   public static void Forward(double[] data) {
/*  44 */     double[] result = new double[data.length];
/*     */     
/*  46 */     for (int k = 0; k < result.length; k++) {
/*  47 */       double sum = 0.0D;
/*  48 */       for (int n = 0; n < data.length; n++) {
/*  49 */         double theta = 6.283185307179586D / data.length * k * n;
/*  50 */         sum += data[n] * cas(theta);
/*     */       } 
/*  52 */       result[k] = 1.0D / Math.sqrt(data.length) * sum;
/*     */     } 
/*     */     
/*  55 */     for (int i = 0; i < result.length; i++) {
/*  56 */       data[i] = result[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Backward(double[] data) {
/*  66 */     Forward(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Forward(double[][] data) {
/*  74 */     double[][] result = new double[data.length][(data[0]).length];
/*     */     
/*  76 */     for (int m = 0; m < data.length; m++) {
/*  77 */       for (int n = 0; n < (data[0]).length; n++) {
/*  78 */         double sum = 0.0D;
/*  79 */         for (int j = 0; j < result.length; j++) {
/*  80 */           for (int k = 0; k < data.length; k++) {
/*  81 */             sum += data[j][k] * cas(6.283185307179586D / data.length * (j * m + k * n));
/*     */           }
/*  83 */           result[m][n] = 1.0D / data.length * sum;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     for (int i = 0; i < data.length; i++) {
/*  89 */       for (int j = 0; j < (data[0]).length; j++) {
/*  90 */         data[i][j] = result[i][j];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Backward(double[][] data) {
/* 100 */     Forward(data);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double cas(double theta) {
/* 116 */     return 1.4142135623730951D * Math.cos(theta - 0.7853981633974483D);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Transforms/DiscreteHartleyTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */