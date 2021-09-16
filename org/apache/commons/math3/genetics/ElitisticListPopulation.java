/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class ElitisticListPopulation
/*     */   extends ListPopulation
/*     */ {
/*  38 */   private double elitismRate = 0.9D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElitisticListPopulation(List<Chromosome> chromosomes, int populationLimit, double elitismRate) throws NullArgumentException, NotPositiveException, NumberIsTooLargeException, OutOfRangeException {
/*  55 */     super(chromosomes, populationLimit);
/*  56 */     setElitismRate(elitismRate);
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
/*     */   public ElitisticListPopulation(int populationLimit, double elitismRate) throws NotPositiveException, OutOfRangeException {
/*  70 */     super(populationLimit);
/*  71 */     setElitismRate(elitismRate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Population nextGeneration() {
/*  82 */     ElitisticListPopulation nextGeneration = new ElitisticListPopulation(getPopulationLimit(), getElitismRate());
/*     */ 
/*     */     
/*  85 */     List<Chromosome> oldChromosomes = getChromosomeList();
/*  86 */     Collections.sort(oldChromosomes);
/*     */ 
/*     */     
/*  89 */     int boundIndex = (int)FastMath.ceil((1.0D - getElitismRate()) * oldChromosomes.size());
/*  90 */     for (int i = boundIndex; i < oldChromosomes.size(); i++) {
/*  91 */       nextGeneration.addChromosome(oldChromosomes.get(i));
/*     */     }
/*  93 */     return nextGeneration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElitismRate(double elitismRate) throws OutOfRangeException {
/* 103 */     if (elitismRate < 0.0D || elitismRate > 1.0D) {
/* 104 */       throw new OutOfRangeException(LocalizedFormats.ELITISM_RATE, Double.valueOf(elitismRate), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 106 */     this.elitismRate = elitismRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getElitismRate() {
/* 114 */     return this.elitismRate;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/ElitisticListPopulation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */