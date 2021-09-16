/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPointCrossover<T>
/*     */   implements CrossoverPolicy
/*     */ {
/*     */   private final int crossoverPoints;
/*     */   
/*     */   public NPointCrossover(int crossoverPoints) throws NotStrictlyPositiveException {
/*  67 */     if (crossoverPoints <= 0) {
/*  68 */       throw new NotStrictlyPositiveException(Integer.valueOf(crossoverPoints));
/*     */     }
/*  70 */     this.crossoverPoints = crossoverPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCrossoverPoints() {
/*  79 */     return this.crossoverPoints;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChromosomePair crossover(Chromosome first, Chromosome second) throws DimensionMismatchException, MathIllegalArgumentException {
/* 110 */     if (!(first instanceof AbstractListChromosome) || !(second instanceof AbstractListChromosome)) {
/* 111 */       throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
/*     */     }
/* 113 */     return mate((AbstractListChromosome<T>)first, (AbstractListChromosome<T>)second);
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
/*     */   private ChromosomePair mate(AbstractListChromosome<T> first, AbstractListChromosome<T> second) throws DimensionMismatchException, NumberIsTooLargeException {
/* 129 */     int length = first.getLength();
/* 130 */     if (length != second.getLength()) {
/* 131 */       throw new DimensionMismatchException(second.getLength(), length);
/*     */     }
/* 133 */     if (this.crossoverPoints >= length) {
/* 134 */       throw new NumberIsTooLargeException(Integer.valueOf(this.crossoverPoints), Integer.valueOf(length), false);
/*     */     }
/*     */ 
/*     */     
/* 138 */     List<T> parent1Rep = first.getRepresentation();
/* 139 */     List<T> parent2Rep = second.getRepresentation();
/*     */     
/* 141 */     List<T> child1Rep = new ArrayList<T>(length);
/* 142 */     List<T> child2Rep = new ArrayList<T>(length);
/*     */     
/* 144 */     RandomGenerator random = GeneticAlgorithm.getRandomGenerator();
/*     */     
/* 146 */     List<T> c1 = child1Rep;
/* 147 */     List<T> c2 = child2Rep;
/*     */     
/* 149 */     int remainingPoints = this.crossoverPoints;
/* 150 */     int lastIndex = 0;
/* 151 */     for (int i = 0; i < this.crossoverPoints; i++, remainingPoints--) {
/*     */       
/* 153 */       int crossoverIndex = 1 + lastIndex + random.nextInt(length - lastIndex - remainingPoints);
/*     */ 
/*     */       
/* 156 */       for (int k = lastIndex; k < crossoverIndex; k++) {
/* 157 */         c1.add(parent1Rep.get(k));
/* 158 */         c2.add(parent2Rep.get(k));
/*     */       } 
/*     */ 
/*     */       
/* 162 */       List<T> tmp = c1;
/* 163 */       c1 = c2;
/* 164 */       c2 = tmp;
/*     */       
/* 166 */       lastIndex = crossoverIndex;
/*     */     } 
/*     */ 
/*     */     
/* 170 */     for (int j = lastIndex; j < length; j++) {
/* 171 */       c1.add(parent1Rep.get(j));
/* 172 */       c2.add(parent2Rep.get(j));
/*     */     } 
/*     */     
/* 175 */     return new ChromosomePair(first.newFixedLengthChromosome(child1Rep), second.newFixedLengthChromosome(child2Rep));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/NPointCrossover.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */