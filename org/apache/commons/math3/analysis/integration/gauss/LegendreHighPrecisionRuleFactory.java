/*     */ package org.apache.commons.math3.analysis.integration.gauss;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.MathContext;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.util.Pair;
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
/*     */ public class LegendreHighPrecisionRuleFactory
/*     */   extends BaseRuleFactory<BigDecimal>
/*     */ {
/*     */   private final MathContext mContext;
/*     */   private final BigDecimal two;
/*     */   private final BigDecimal minusOne;
/*     */   private final BigDecimal oneHalf;
/*     */   
/*     */   public LegendreHighPrecisionRuleFactory() {
/*  49 */     this(MathContext.DECIMAL128);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendreHighPrecisionRuleFactory(MathContext mContext) {
/*  56 */     this.mContext = mContext;
/*  57 */     this.two = new BigDecimal("2", mContext);
/*  58 */     this.minusOne = new BigDecimal("-1", mContext);
/*  59 */     this.oneHalf = new BigDecimal("0.5", mContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Pair<BigDecimal[], BigDecimal[]> computeRule(int numberOfPoints) throws DimensionMismatchException {
/*  67 */     if (numberOfPoints == 1)
/*     */     {
/*  69 */       return new Pair(new BigDecimal[] { BigDecimal.ZERO }, new BigDecimal[] { this.two });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     BigDecimal[] previousPoints = (BigDecimal[])getRuleInternal(numberOfPoints - 1).getFirst();
/*     */ 
/*     */     
/*  79 */     BigDecimal[] points = new BigDecimal[numberOfPoints];
/*  80 */     BigDecimal[] weights = new BigDecimal[numberOfPoints];
/*     */ 
/*     */     
/*  83 */     int iMax = numberOfPoints / 2;
/*  84 */     for (int i = 0; i < iMax; i++) {
/*     */       
/*  86 */       BigDecimal a = (i == 0) ? this.minusOne : previousPoints[i - 1];
/*     */       
/*  88 */       BigDecimal b = (iMax == 1) ? BigDecimal.ONE : previousPoints[i];
/*     */       
/*  90 */       BigDecimal pma = BigDecimal.ONE;
/*     */       
/*  92 */       BigDecimal pa = a;
/*     */       
/*  94 */       BigDecimal pmb = BigDecimal.ONE;
/*     */       
/*  96 */       BigDecimal pb = b;
/*  97 */       for (int j = 1; j < numberOfPoints; j++) {
/*  98 */         BigDecimal b_two_j_p_1 = new BigDecimal(2 * j + 1, this.mContext);
/*  99 */         BigDecimal b_j = new BigDecimal(j, this.mContext);
/* 100 */         BigDecimal b_j_p_1 = new BigDecimal(j + 1, this.mContext);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 105 */         BigDecimal bigDecimal1 = a.multiply(b_two_j_p_1, this.mContext);
/* 106 */         bigDecimal1 = pa.multiply(bigDecimal1, this.mContext);
/* 107 */         BigDecimal bigDecimal2 = pma.multiply(b_j, this.mContext);
/*     */         
/* 109 */         BigDecimal ppa = bigDecimal1.subtract(bigDecimal2, this.mContext);
/* 110 */         ppa = ppa.divide(b_j_p_1, this.mContext);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 115 */         bigDecimal1 = b.multiply(b_two_j_p_1, this.mContext);
/* 116 */         bigDecimal1 = pb.multiply(bigDecimal1, this.mContext);
/* 117 */         bigDecimal2 = pmb.multiply(b_j, this.mContext);
/*     */         
/* 119 */         BigDecimal ppb = bigDecimal1.subtract(bigDecimal2, this.mContext);
/* 120 */         ppb = ppb.divide(b_j_p_1, this.mContext);
/*     */         
/* 122 */         pma = pa;
/* 123 */         pa = ppa;
/* 124 */         pmb = pb;
/* 125 */         pb = ppb;
/*     */       } 
/*     */ 
/*     */       
/* 129 */       BigDecimal c = a.add(b, this.mContext).multiply(this.oneHalf, this.mContext);
/*     */       
/* 131 */       BigDecimal pmc = BigDecimal.ONE;
/*     */       
/* 133 */       BigDecimal pc = c;
/* 134 */       boolean done = false;
/* 135 */       while (!done) {
/* 136 */         BigDecimal bigDecimal1 = b.subtract(a, this.mContext);
/* 137 */         BigDecimal bigDecimal2 = c.ulp().multiply(BigDecimal.TEN, this.mContext);
/* 138 */         done = (bigDecimal1.compareTo(bigDecimal2) <= 0);
/* 139 */         pmc = BigDecimal.ONE;
/* 140 */         pc = c;
/* 141 */         for (int k = 1; k < numberOfPoints; k++) {
/* 142 */           BigDecimal b_two_j_p_1 = new BigDecimal(2 * k + 1, this.mContext);
/* 143 */           BigDecimal b_j = new BigDecimal(k, this.mContext);
/* 144 */           BigDecimal b_j_p_1 = new BigDecimal(k + 1, this.mContext);
/*     */ 
/*     */           
/* 147 */           bigDecimal1 = c.multiply(b_two_j_p_1, this.mContext);
/* 148 */           bigDecimal1 = pc.multiply(bigDecimal1, this.mContext);
/* 149 */           bigDecimal2 = pmc.multiply(b_j, this.mContext);
/*     */           
/* 151 */           BigDecimal ppc = bigDecimal1.subtract(bigDecimal2, this.mContext);
/* 152 */           ppc = ppc.divide(b_j_p_1, this.mContext);
/*     */           
/* 154 */           pmc = pc;
/* 155 */           pc = ppc;
/*     */         } 
/*     */         
/* 158 */         if (!done) {
/* 159 */           if (pa.signum() * pc.signum() <= 0) {
/* 160 */             b = c;
/* 161 */             pmb = pmc;
/* 162 */             pb = pc;
/*     */           } else {
/* 164 */             a = c;
/* 165 */             pma = pmc;
/* 166 */             pa = pc;
/*     */           } 
/* 168 */           c = a.add(b, this.mContext).multiply(this.oneHalf, this.mContext);
/*     */         } 
/*     */       } 
/* 171 */       BigDecimal nP = new BigDecimal(numberOfPoints, this.mContext);
/* 172 */       BigDecimal tmp1 = pmc.subtract(c.multiply(pc, this.mContext), this.mContext);
/* 173 */       tmp1 = tmp1.multiply(nP);
/* 174 */       tmp1 = tmp1.pow(2, this.mContext);
/* 175 */       BigDecimal tmp2 = c.pow(2, this.mContext);
/* 176 */       tmp2 = BigDecimal.ONE.subtract(tmp2, this.mContext);
/* 177 */       tmp2 = tmp2.multiply(this.two, this.mContext);
/* 178 */       tmp2 = tmp2.divide(tmp1, this.mContext);
/*     */       
/* 180 */       points[i] = c;
/* 181 */       weights[i] = tmp2;
/*     */       
/* 183 */       int idx = numberOfPoints - i - 1;
/* 184 */       points[idx] = c.negate(this.mContext);
/* 185 */       weights[idx] = tmp2;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (numberOfPoints % 2 != 0) {
/* 192 */       BigDecimal pmc = BigDecimal.ONE;
/* 193 */       for (int j = 1; j < numberOfPoints; j += 2) {
/* 194 */         BigDecimal b_j = new BigDecimal(j, this.mContext);
/* 195 */         BigDecimal b_j_p_1 = new BigDecimal(j + 1, this.mContext);
/*     */ 
/*     */         
/* 198 */         pmc = pmc.multiply(b_j, this.mContext);
/* 199 */         pmc = pmc.divide(b_j_p_1, this.mContext);
/* 200 */         pmc = pmc.negate(this.mContext);
/*     */       } 
/*     */ 
/*     */       
/* 204 */       BigDecimal nP = new BigDecimal(numberOfPoints, this.mContext);
/* 205 */       BigDecimal tmp1 = pmc.multiply(nP, this.mContext);
/* 206 */       tmp1 = tmp1.pow(2, this.mContext);
/* 207 */       BigDecimal tmp2 = this.two.divide(tmp1, this.mContext);
/*     */       
/* 209 */       points[iMax] = BigDecimal.ZERO;
/* 210 */       weights[iMax] = tmp2;
/*     */     } 
/*     */     
/* 213 */     return new Pair(points, weights);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/gauss/LegendreHighPrecisionRuleFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */