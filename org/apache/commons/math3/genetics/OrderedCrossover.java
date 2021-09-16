/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class OrderedCrossover<T>
/*     */   implements CrossoverPolicy
/*     */ {
/*     */   public ChromosomePair crossover(Chromosome first, Chromosome second) throws DimensionMismatchException, MathIllegalArgumentException {
/*  73 */     if (!(first instanceof AbstractListChromosome) || !(second instanceof AbstractListChromosome)) {
/*  74 */       throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
/*     */     }
/*  76 */     return mate((AbstractListChromosome<T>)first, (AbstractListChromosome<T>)second);
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
/*     */   protected ChromosomePair mate(AbstractListChromosome<T> first, AbstractListChromosome<T> second) throws DimensionMismatchException {
/*  90 */     int b, length = first.getLength();
/*  91 */     if (length != second.getLength()) {
/*  92 */       throw new DimensionMismatchException(second.getLength(), length);
/*     */     }
/*     */ 
/*     */     
/*  96 */     List<T> parent1Rep = first.getRepresentation();
/*  97 */     List<T> parent2Rep = second.getRepresentation();
/*     */     
/*  99 */     List<T> child1 = new ArrayList<T>(length);
/* 100 */     List<T> child2 = new ArrayList<T>(length);
/*     */     
/* 102 */     Set<T> child1Set = new HashSet<T>(length);
/* 103 */     Set<T> child2Set = new HashSet<T>(length);
/*     */     
/* 105 */     RandomGenerator random = GeneticAlgorithm.getRandomGenerator();
/*     */     
/* 107 */     int a = random.nextInt(length);
/*     */     
/*     */     do {
/* 110 */       b = random.nextInt(length);
/* 111 */     } while (a == b);
/*     */     
/* 113 */     int lb = FastMath.min(a, b);
/* 114 */     int ub = FastMath.max(a, b);
/*     */ 
/*     */     
/* 117 */     child1.addAll(parent1Rep.subList(lb, ub + 1));
/* 118 */     child1Set.addAll(child1);
/* 119 */     child2.addAll(parent2Rep.subList(lb, ub + 1));
/* 120 */     child2Set.addAll(child2);
/*     */ 
/*     */     
/* 123 */     for (int i = 1; i <= length; i++) {
/* 124 */       int idx = (ub + i) % length;
/*     */ 
/*     */       
/* 127 */       T item1 = parent1Rep.get(idx);
/* 128 */       T item2 = parent2Rep.get(idx);
/*     */ 
/*     */       
/* 131 */       if (!child1Set.contains(item2)) {
/* 132 */         child1.add(item2);
/* 133 */         child1Set.add(item2);
/*     */       } 
/*     */ 
/*     */       
/* 137 */       if (!child2Set.contains(item1)) {
/* 138 */         child2.add(item1);
/* 139 */         child2Set.add(item1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 144 */     Collections.rotate(child1, lb);
/* 145 */     Collections.rotate(child2, lb);
/*     */     
/* 147 */     return new ChromosomePair(first.newFixedLengthChromosome(child1), second.newFixedLengthChromosome(child2));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/OrderedCrossover.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */