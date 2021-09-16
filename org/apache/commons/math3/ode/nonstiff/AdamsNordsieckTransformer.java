/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.fraction.BigFraction;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.ArrayFieldVector;
/*     */ import org.apache.commons.math3.linear.FieldDecompositionSolver;
/*     */ import org.apache.commons.math3.linear.FieldLUDecomposition;
/*     */ import org.apache.commons.math3.linear.FieldMatrix;
/*     */ import org.apache.commons.math3.linear.FieldVector;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.QRDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
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
/*     */ 
/*     */ public class AdamsNordsieckTransformer
/*     */ {
/* 138 */   private static final Map<Integer, AdamsNordsieckTransformer> CACHE = new HashMap<Integer, AdamsNordsieckTransformer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Array2DRowRealMatrix update;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] c1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AdamsNordsieckTransformer(int n) {
/* 153 */     int rows = n - 1;
/*     */ 
/*     */     
/* 156 */     FieldMatrix<BigFraction> bigP = buildP(rows);
/* 157 */     FieldDecompositionSolver<BigFraction> pSolver = (new FieldLUDecomposition(bigP)).getSolver();
/*     */ 
/*     */     
/* 160 */     BigFraction[] u = new BigFraction[rows];
/* 161 */     Arrays.fill((Object[])u, BigFraction.ONE);
/* 162 */     BigFraction[] bigC1 = (BigFraction[])pSolver.solve((FieldVector)new ArrayFieldVector((FieldElement[])u, false)).toArray();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     BigFraction[][] shiftedP = (BigFraction[][])bigP.getData();
/* 168 */     for (int i = shiftedP.length - 1; i > 0; i--)
/*     */     {
/* 170 */       shiftedP[i] = shiftedP[i - 1];
/*     */     }
/* 172 */     shiftedP[0] = new BigFraction[rows];
/* 173 */     Arrays.fill((Object[])shiftedP[0], BigFraction.ZERO);
/* 174 */     FieldMatrix<BigFraction> bigMSupdate = pSolver.solve((FieldMatrix)new Array2DRowFieldMatrix((FieldElement[][])shiftedP, false));
/*     */ 
/*     */ 
/*     */     
/* 178 */     this.update = MatrixUtils.bigFractionMatrixToRealMatrix(bigMSupdate);
/* 179 */     this.c1 = new double[rows];
/* 180 */     for (int j = 0; j < rows; j++) {
/* 181 */       this.c1[j] = bigC1[j].doubleValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AdamsNordsieckTransformer getInstance(int nSteps) {
/* 192 */     synchronized (CACHE) {
/* 193 */       AdamsNordsieckTransformer t = CACHE.get(Integer.valueOf(nSteps));
/* 194 */       if (t == null) {
/* 195 */         t = new AdamsNordsieckTransformer(nSteps);
/* 196 */         CACHE.put(Integer.valueOf(nSteps), t);
/*     */       } 
/* 198 */       return t;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getNSteps() {
/* 210 */     return this.c1.length;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private FieldMatrix<BigFraction> buildP(int rows) {
/* 229 */     BigFraction[][] pData = new BigFraction[rows][rows];
/*     */     
/* 231 */     for (int i = 1; i <= pData.length; i++) {
/*     */       
/* 233 */       BigFraction[] pI = pData[i - 1];
/* 234 */       int factor = -i;
/* 235 */       int aj = factor;
/* 236 */       for (int j = 1; j <= pI.length; j++) {
/* 237 */         pI[j - 1] = new BigFraction(aj * (j + 1));
/* 238 */         aj *= factor;
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     return (FieldMatrix<BigFraction>)new Array2DRowFieldMatrix((FieldElement[][])pData, false);
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
/*     */   public Array2DRowRealMatrix initializeHighOrderDerivatives(double h, double[] t, double[][] y, double[][] yDot) {
/* 268 */     double[][] a = new double[this.c1.length + 1][this.c1.length + 1];
/* 269 */     double[][] b = new double[this.c1.length + 1][(y[0]).length];
/* 270 */     double[] y0 = y[0];
/* 271 */     double[] yDot0 = yDot[0];
/* 272 */     for (int i = 1; i < y.length; i++) {
/*     */       
/* 274 */       double di = t[i] - t[0];
/* 275 */       double ratio = di / h;
/* 276 */       double dikM1Ohk = 1.0D / h;
/*     */ 
/*     */ 
/*     */       
/* 280 */       double[] aI = a[2 * i - 2];
/* 281 */       double[] aDotI = (2 * i - 1 < a.length) ? a[2 * i - 1] : null;
/* 282 */       for (int k = 0; k < aI.length; k++) {
/* 283 */         dikM1Ohk *= ratio;
/* 284 */         aI[k] = di * dikM1Ohk;
/* 285 */         if (aDotI != null) {
/* 286 */           aDotI[k] = (k + 2) * dikM1Ohk;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 291 */       double[] yI = y[i];
/* 292 */       double[] yDotI = yDot[i];
/* 293 */       double[] bI = b[2 * i - 2];
/* 294 */       double[] bDotI = (2 * i - 1 < b.length) ? b[2 * i - 1] : null;
/* 295 */       for (int m = 0; m < yI.length; m++) {
/* 296 */         bI[m] = yI[m] - y0[m] - di * yDot0[m];
/* 297 */         if (bDotI != null) {
/* 298 */           bDotI[m] = yDotI[m] - yDot0[m];
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     QRDecomposition decomposition = new QRDecomposition((RealMatrix)new Array2DRowRealMatrix(a, false));
/* 307 */     RealMatrix x = decomposition.getSolver().solve((RealMatrix)new Array2DRowRealMatrix(b, false));
/*     */ 
/*     */     
/* 310 */     Array2DRowRealMatrix truncatedX = new Array2DRowRealMatrix(x.getRowDimension() - 1, x.getColumnDimension());
/* 311 */     for (int j = 0; j < truncatedX.getRowDimension(); j++) {
/* 312 */       for (int k = 0; k < truncatedX.getColumnDimension(); k++) {
/* 313 */         truncatedX.setEntry(j, k, x.getEntry(j, k));
/*     */       }
/*     */     } 
/* 316 */     return truncatedX;
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
/*     */   public Array2DRowRealMatrix updateHighOrderDerivativesPhase1(Array2DRowRealMatrix highOrder) {
/* 332 */     return this.update.multiply(highOrder);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateHighOrderDerivativesPhase2(double[] start, double[] end, Array2DRowRealMatrix highOrder) {
/* 351 */     double[][] data = highOrder.getDataRef();
/* 352 */     for (int i = 0; i < data.length; i++) {
/* 353 */       double[] dataI = data[i];
/* 354 */       double c1I = this.c1[i];
/* 355 */       for (int j = 0; j < dataI.length; j++)
/* 356 */         dataI[j] = dataI[j] + c1I * (start[j] - end[j]); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsNordsieckTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */