/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OnePointCrossover<T>
/*     */   implements CrossoverPolicy
/*     */ {
/*     */   public ChromosomePair crossover(Chromosome first, Chromosome second) throws DimensionMismatchException, MathIllegalArgumentException {
/*  81 */     if (!(first instanceof AbstractListChromosome) || !(second instanceof AbstractListChromosome)) {
/*  82 */       throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
/*     */     }
/*  84 */     return crossover((AbstractListChromosome<T>)first, (AbstractListChromosome<T>)second);
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
/*     */   private ChromosomePair crossover(AbstractListChromosome<T> first, AbstractListChromosome<T> second) throws DimensionMismatchException {
/*  98 */     int length = first.getLength();
/*  99 */     if (length != second.getLength()) {
/* 100 */       throw new DimensionMismatchException(second.getLength(), length);
/*     */     }
/*     */ 
/*     */     
/* 104 */     List<T> parent1Rep = first.getRepresentation();
/* 105 */     List<T> parent2Rep = second.getRepresentation();
/*     */     
/* 107 */     List<T> child1Rep = new ArrayList<T>(length);
/* 108 */     List<T> child2Rep = new ArrayList<T>(length);
/*     */ 
/*     */     
/* 111 */     int crossoverIndex = 1 + GeneticAlgorithm.getRandomGenerator().nextInt(length - 2);
/*     */     
/*     */     int i;
/* 114 */     for (i = 0; i < crossoverIndex; i++) {
/* 115 */       child1Rep.add(parent1Rep.get(i));
/* 116 */       child2Rep.add(parent2Rep.get(i));
/*     */     } 
/*     */     
/* 119 */     for (i = crossoverIndex; i < length; i++) {
/* 120 */       child1Rep.add(parent2Rep.get(i));
/* 121 */       child2Rep.add(parent1Rep.get(i));
/*     */     } 
/*     */     
/* 124 */     return new ChromosomePair(first.newFixedLengthChromosome(child1Rep), second.newFixedLengthChromosome(child2Rep));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/OnePointCrossover.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */