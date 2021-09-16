/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UniformCrossover<T>
/*     */   implements CrossoverPolicy
/*     */ {
/*     */   private final double ratio;
/*     */   
/*     */   public UniformCrossover(double ratio) throws OutOfRangeException {
/*  63 */     if (ratio < 0.0D || ratio > 1.0D) {
/*  64 */       throw new OutOfRangeException(LocalizedFormats.CROSSOVER_RATE, Double.valueOf(ratio), Double.valueOf(0.0D), Double.valueOf(1.0D));
/*     */     }
/*  66 */     this.ratio = ratio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRatio() {
/*  75 */     return this.ratio;
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
/*     */   public ChromosomePair crossover(Chromosome first, Chromosome second) throws DimensionMismatchException, MathIllegalArgumentException {
/*  89 */     if (!(first instanceof AbstractListChromosome) || !(second instanceof AbstractListChromosome)) {
/*  90 */       throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
/*     */     }
/*  92 */     return mate((AbstractListChromosome<T>)first, (AbstractListChromosome<T>)second);
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
/*     */   private ChromosomePair mate(AbstractListChromosome<T> first, AbstractListChromosome<T> second) throws DimensionMismatchException {
/* 105 */     int length = first.getLength();
/* 106 */     if (length != second.getLength()) {
/* 107 */       throw new DimensionMismatchException(second.getLength(), length);
/*     */     }
/*     */ 
/*     */     
/* 111 */     List<T> parent1Rep = first.getRepresentation();
/* 112 */     List<T> parent2Rep = second.getRepresentation();
/*     */     
/* 114 */     List<T> child1Rep = new ArrayList<T>(length);
/* 115 */     List<T> child2Rep = new ArrayList<T>(length);
/*     */     
/* 117 */     RandomGenerator random = GeneticAlgorithm.getRandomGenerator();
/*     */     
/* 119 */     for (int index = 0; index < length; index++) {
/*     */       
/* 121 */       if (random.nextDouble() < this.ratio) {
/*     */         
/* 123 */         child1Rep.add(parent2Rep.get(index));
/* 124 */         child2Rep.add(parent1Rep.get(index));
/*     */       } else {
/* 126 */         child1Rep.add(parent1Rep.get(index));
/* 127 */         child2Rep.add(parent2Rep.get(index));
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     return new ChromosomePair(first.newFixedLengthChromosome(child1Rep), second.newFixedLengthChromosome(child2Rep));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/UniformCrossover.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */