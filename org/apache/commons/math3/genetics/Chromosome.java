/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Chromosome
/*     */   implements Comparable<Chromosome>, Fitness
/*     */ {
/*     */   private static final double NO_FITNESS = -InfinityD;
/*  32 */   private double fitness = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFitness() {
/*  42 */     if (this.fitness == Double.NEGATIVE_INFINITY)
/*     */     {
/*  44 */       this.fitness = fitness();
/*     */     }
/*  46 */     return this.fitness;
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
/*     */   public int compareTo(Chromosome another) {
/*  61 */     return Double.compare(getFitness(), another.getFitness());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSame(Chromosome another) {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Chromosome findSameChromosome(Population population) {
/*  83 */     for (Chromosome anotherChr : population) {
/*  84 */       if (isSame(anotherChr)) {
/*  85 */         return anotherChr;
/*     */       }
/*     */     } 
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void searchForFitnessUpdate(Population population) {
/*  98 */     Chromosome sameChromosome = findSameChromosome(population);
/*  99 */     if (sameChromosome != null)
/* 100 */       this.fitness = sameChromosome.getFitness(); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/Chromosome.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */