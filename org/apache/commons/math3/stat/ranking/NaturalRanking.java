/*     */ package org.apache.commons.math3.stat.ranking;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NotANumberException;
/*     */ import org.apache.commons.math3.random.RandomDataGenerator;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class NaturalRanking
/*     */   implements RankingAlgorithm
/*     */ {
/*  73 */   public static final NaNStrategy DEFAULT_NAN_STRATEGY = NaNStrategy.FAILED;
/*     */ 
/*     */   
/*  76 */   public static final TiesStrategy DEFAULT_TIES_STRATEGY = TiesStrategy.AVERAGE;
/*     */ 
/*     */ 
/*     */   
/*     */   private final NaNStrategy nanStrategy;
/*     */ 
/*     */ 
/*     */   
/*     */   private final TiesStrategy tiesStrategy;
/*     */ 
/*     */   
/*     */   private final RandomDataGenerator randomData;
/*     */ 
/*     */ 
/*     */   
/*     */   public NaturalRanking() {
/*  92 */     this.tiesStrategy = DEFAULT_TIES_STRATEGY;
/*  93 */     this.nanStrategy = DEFAULT_NAN_STRATEGY;
/*  94 */     this.randomData = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NaturalRanking(TiesStrategy tiesStrategy) {
/* 104 */     this.tiesStrategy = tiesStrategy;
/* 105 */     this.nanStrategy = DEFAULT_NAN_STRATEGY;
/* 106 */     this.randomData = new RandomDataGenerator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NaturalRanking(NaNStrategy nanStrategy) {
/* 116 */     this.nanStrategy = nanStrategy;
/* 117 */     this.tiesStrategy = DEFAULT_TIES_STRATEGY;
/* 118 */     this.randomData = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NaturalRanking(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
/* 129 */     this.nanStrategy = nanStrategy;
/* 130 */     this.tiesStrategy = tiesStrategy;
/* 131 */     this.randomData = new RandomDataGenerator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NaturalRanking(RandomGenerator randomGenerator) {
/* 142 */     this.tiesStrategy = TiesStrategy.RANDOM;
/* 143 */     this.nanStrategy = DEFAULT_NAN_STRATEGY;
/* 144 */     this.randomData = new RandomDataGenerator(randomGenerator);
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
/*     */   public NaturalRanking(NaNStrategy nanStrategy, RandomGenerator randomGenerator) {
/* 158 */     this.nanStrategy = nanStrategy;
/* 159 */     this.tiesStrategy = TiesStrategy.RANDOM;
/* 160 */     this.randomData = new RandomDataGenerator(randomGenerator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NaNStrategy getNanStrategy() {
/* 169 */     return this.nanStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TiesStrategy getTiesStrategy() {
/* 178 */     return this.tiesStrategy;
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
/*     */   public double[] rank(double[] data) {
/* 194 */     IntDoublePair[] ranks = new IntDoublePair[data.length];
/* 195 */     for (int i = 0; i < data.length; i++) {
/* 196 */       ranks[i] = new IntDoublePair(data[i], i);
/*     */     }
/*     */ 
/*     */     
/* 200 */     List<Integer> nanPositions = null;
/* 201 */     switch (this.nanStrategy) {
/*     */       case AVERAGE:
/* 203 */         recodeNaNs(ranks, Double.POSITIVE_INFINITY);
/*     */         break;
/*     */       case MAXIMUM:
/* 206 */         recodeNaNs(ranks, Double.NEGATIVE_INFINITY);
/*     */         break;
/*     */       case MINIMUM:
/* 209 */         ranks = removeNaNs(ranks);
/*     */         break;
/*     */       case RANDOM:
/* 212 */         nanPositions = getNanPositions(ranks);
/*     */         break;
/*     */       case SEQUENTIAL:
/* 215 */         nanPositions = getNanPositions(ranks);
/* 216 */         if (nanPositions.size() > 0) {
/* 217 */           throw new NotANumberException();
/*     */         }
/*     */         break;
/*     */       default:
/* 221 */         throw new MathInternalError();
/*     */     } 
/*     */ 
/*     */     
/* 225 */     Arrays.sort((Object[])ranks);
/*     */ 
/*     */ 
/*     */     
/* 229 */     double[] out = new double[ranks.length];
/* 230 */     int pos = 1;
/* 231 */     out[ranks[0].getPosition()] = pos;
/* 232 */     List<Integer> tiesTrace = new ArrayList<Integer>();
/* 233 */     tiesTrace.add(Integer.valueOf(ranks[0].getPosition()));
/* 234 */     for (int j = 1; j < ranks.length; j++) {
/* 235 */       if (Double.compare(ranks[j].getValue(), ranks[j - 1].getValue()) > 0) {
/*     */         
/* 237 */         pos = j + 1;
/* 238 */         if (tiesTrace.size() > 1) {
/* 239 */           resolveTie(out, tiesTrace);
/*     */         }
/* 241 */         tiesTrace = new ArrayList<Integer>();
/* 242 */         tiesTrace.add(Integer.valueOf(ranks[j].getPosition()));
/*     */       } else {
/*     */         
/* 245 */         tiesTrace.add(Integer.valueOf(ranks[j].getPosition()));
/*     */       } 
/* 247 */       out[ranks[j].getPosition()] = pos;
/*     */     } 
/* 249 */     if (tiesTrace.size() > 1) {
/* 250 */       resolveTie(out, tiesTrace);
/*     */     }
/* 252 */     if (this.nanStrategy == NaNStrategy.FIXED) {
/* 253 */       restoreNaNs(out, nanPositions);
/*     */     }
/* 255 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IntDoublePair[] removeNaNs(IntDoublePair[] ranks) {
/* 266 */     if (!containsNaNs(ranks)) {
/* 267 */       return ranks;
/*     */     }
/* 269 */     IntDoublePair[] outRanks = new IntDoublePair[ranks.length];
/* 270 */     int j = 0;
/* 271 */     for (int i = 0; i < ranks.length; i++) {
/* 272 */       if (Double.isNaN(ranks[i].getValue())) {
/*     */         
/* 274 */         for (int k = i + 1; k < ranks.length; k++) {
/* 275 */           ranks[k] = new IntDoublePair(ranks[k].getValue(), ranks[k].getPosition() - 1);
/*     */         }
/*     */       } else {
/*     */         
/* 279 */         outRanks[j] = new IntDoublePair(ranks[i].getValue(), ranks[i].getPosition());
/*     */         
/* 281 */         j++;
/*     */       } 
/*     */     } 
/* 284 */     IntDoublePair[] returnRanks = new IntDoublePair[j];
/* 285 */     System.arraycopy(outRanks, 0, returnRanks, 0, j);
/* 286 */     return returnRanks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recodeNaNs(IntDoublePair[] ranks, double value) {
/* 296 */     for (int i = 0; i < ranks.length; i++) {
/* 297 */       if (Double.isNaN(ranks[i].getValue())) {
/* 298 */         ranks[i] = new IntDoublePair(value, ranks[i].getPosition());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean containsNaNs(IntDoublePair[] ranks) {
/* 311 */     for (int i = 0; i < ranks.length; i++) {
/* 312 */       if (Double.isNaN(ranks[i].getValue())) {
/* 313 */         return true;
/*     */       }
/*     */     } 
/* 316 */     return false;
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
/*     */   private void resolveTie(double[] ranks, List<Integer> tiesTrace) {
/*     */     Iterator<Integer> iterator;
/*     */     long f;
/*     */     int i;
/* 336 */     double c = ranks[((Integer)tiesTrace.get(0)).intValue()];
/*     */ 
/*     */     
/* 339 */     int length = tiesTrace.size();
/*     */     
/* 341 */     switch (this.tiesStrategy) {
/*     */       case AVERAGE:
/* 343 */         fill(ranks, tiesTrace, (2.0D * c + length - 1.0D) / 2.0D);
/*     */         return;
/*     */       case MAXIMUM:
/* 346 */         fill(ranks, tiesTrace, c + length - 1.0D);
/*     */         return;
/*     */       case MINIMUM:
/* 349 */         fill(ranks, tiesTrace, c);
/*     */         return;
/*     */       case RANDOM:
/* 352 */         iterator = tiesTrace.iterator();
/* 353 */         f = FastMath.round(c);
/* 354 */         while (iterator.hasNext())
/*     */         {
/* 356 */           ranks[((Integer)iterator.next()).intValue()] = this.randomData.nextLong(f, f + length - 1L);
/*     */         }
/*     */         return;
/*     */ 
/*     */       
/*     */       case SEQUENTIAL:
/* 362 */         iterator = tiesTrace.iterator();
/* 363 */         f = FastMath.round(c);
/* 364 */         i = 0;
/* 365 */         while (iterator.hasNext()) {
/* 366 */           ranks[((Integer)iterator.next()).intValue()] = (f + i++);
/*     */         }
/*     */         return;
/*     */     } 
/* 370 */     throw new MathInternalError();
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
/*     */   private void fill(double[] data, List<Integer> tiesTrace, double value) {
/* 382 */     Iterator<Integer> iterator = tiesTrace.iterator();
/* 383 */     while (iterator.hasNext()) {
/* 384 */       data[((Integer)iterator.next()).intValue()] = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void restoreNaNs(double[] ranks, List<Integer> nanPositions) {
/* 395 */     if (nanPositions.size() == 0) {
/*     */       return;
/*     */     }
/* 398 */     Iterator<Integer> iterator = nanPositions.iterator();
/* 399 */     while (iterator.hasNext()) {
/* 400 */       ranks[((Integer)iterator.next()).intValue()] = Double.NaN;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Integer> getNanPositions(IntDoublePair[] ranks) {
/* 412 */     ArrayList<Integer> out = new ArrayList<Integer>();
/* 413 */     for (int i = 0; i < ranks.length; i++) {
/* 414 */       if (Double.isNaN(ranks[i].getValue())) {
/* 415 */         out.add(Integer.valueOf(i));
/*     */       }
/*     */     } 
/* 418 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IntDoublePair
/*     */     implements Comparable<IntDoublePair>
/*     */   {
/*     */     private final double value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int position;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     IntDoublePair(double value, int position) {
/* 441 */       this.value = value;
/* 442 */       this.position = position;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(IntDoublePair other) {
/* 453 */       return Double.compare(this.value, other.value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getValue() {
/* 463 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getPosition() {
/* 471 */       return this.position;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/ranking/NaturalRanking.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */