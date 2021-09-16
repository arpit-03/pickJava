/*     */ package Catalano.Statistics.Regression;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class RegressionAnalysis
/*     */ {
/*  18 */   private int maxDegree = 2;
/*  19 */   private int usedDegree = 0;
/*     */   private boolean usePolynomial = true;
/*     */   
/*     */   public int getMaxDegree() {
/*  23 */     return this.maxDegree;
/*     */   }
/*     */   
/*     */   public void setMaxDegree(int maxDegree) {
/*  27 */     this.maxDegree = Math.max(maxDegree, 2);
/*     */   }
/*     */   
/*     */   public int getUsedDegree() {
/*  31 */     return this.usedDegree;
/*     */   }
/*     */   
/*     */   public boolean isUsePolynomial() {
/*  35 */     return this.usePolynomial;
/*     */   }
/*     */   
/*     */   public void setUsePolynomial(boolean usePolynomial) {
/*  39 */     this.usePolynomial = usePolynomial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegressionAnalysis() {
/*  47 */     this(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegressionAnalysis(int maxDegree) {
/*  55 */     this.usePolynomial = true;
/*  56 */     setMaxDegree(maxDegree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegressionAnalysis(boolean usePolynomial) {
/*  64 */     this.usePolynomial = usePolynomial;
/*  65 */     setMaxDegree(2);
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
/*     */   public ISimpleRegression BestFit(double[] x, double[] y) {
/*  77 */     List<ISimpleRegression> lst = new ArrayList<>();
/*  78 */     lst.add(new LinearRegression(x, y));
/*  79 */     lst.add(new LogarithmicRegression(x, y));
/*  80 */     lst.add(new ExponentialRegression(x, y));
/*  81 */     lst.add(new PowerRegression(x, y));
/*     */     
/*  83 */     ISimpleRegression bestRegression = null;
/*  84 */     double bestFit = 0.0D;
/*     */     
/*  86 */     for (ISimpleRegression r : lst) {
/*  87 */       double f = r.CoefficientOfDetermination();
/*  88 */       if (f == 1.0D) return r; 
/*  89 */       if (f > bestFit) {
/*  90 */         bestFit = f;
/*  91 */         bestRegression = r;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (this.usePolynomial) {
/*     */ 
/*     */       
/* 100 */       ISimpleRegression r = new PolynomialRegression(x, y, 2);
/* 101 */       double f = r.CoefficientOfDetermination();
/* 102 */       if (f == 1.0D) return r; 
/* 103 */       if (f > bestFit) {
/* 104 */         bestFit = f;
/* 105 */         bestRegression = r;
/* 106 */         this.usedDegree = 2;
/*     */       } 
/*     */ 
/*     */       
/* 110 */       for (int i = 3; i <= this.maxDegree; i++) {
/* 111 */         r = new PolynomialRegression(x, y, i);
/* 112 */         f = r.CoefficientOfDetermination();
/* 113 */         if (f == 1.0D) return r; 
/* 114 */         if (f > bestFit) {
/* 115 */           bestFit = f;
/* 116 */           bestRegression = r;
/* 117 */           this.usedDegree = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return bestRegression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISimpleRegression BestFit(List<ISimpleRegression> regressions) {
/* 132 */     ISimpleRegression reg = null;
/* 133 */     double best = 0.0D;
/* 134 */     for (ISimpleRegression r : regressions) {
/* 135 */       double c = r.CoefficientOfDetermination();
/* 136 */       if (c > best) {
/* 137 */         best = c;
/* 138 */         reg = r;
/*     */       } 
/*     */     } 
/* 141 */     return reg;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/RegressionAnalysis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */