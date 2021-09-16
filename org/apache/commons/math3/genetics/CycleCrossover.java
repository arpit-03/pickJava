/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class CycleCrossover<T>
/*     */   implements CrossoverPolicy
/*     */ {
/*     */   private final boolean randomStart;
/*     */   
/*     */   public CycleCrossover() {
/*  74 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CycleCrossover(boolean randomStart) {
/*  83 */     this.randomStart = randomStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRandomStart() {
/*  92 */     return this.randomStart;
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
/*     */   public ChromosomePair crossover(Chromosome first, Chromosome second) throws DimensionMismatchException, MathIllegalArgumentException {
/* 105 */     if (!(first instanceof AbstractListChromosome) || !(second instanceof AbstractListChromosome)) {
/* 106 */       throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
/*     */     }
/* 108 */     return mate((AbstractListChromosome<T>)first, (AbstractListChromosome<T>)second);
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
/* 122 */     int length = first.getLength();
/* 123 */     if (length != second.getLength()) {
/* 124 */       throw new DimensionMismatchException(second.getLength(), length);
/*     */     }
/*     */ 
/*     */     
/* 128 */     List<T> parent1Rep = first.getRepresentation();
/* 129 */     List<T> parent2Rep = second.getRepresentation();
/*     */     
/* 131 */     List<T> child1Rep = new ArrayList<T>(second.getRepresentation());
/* 132 */     List<T> child2Rep = new ArrayList<T>(first.getRepresentation());
/*     */ 
/*     */     
/* 135 */     Set<Integer> visitedIndices = new HashSet<Integer>(length);
/*     */     
/* 137 */     List<Integer> indices = new ArrayList<Integer>(length);
/*     */ 
/*     */     
/* 140 */     int idx = this.randomStart ? GeneticAlgorithm.getRandomGenerator().nextInt(length) : 0;
/* 141 */     int cycle = 1;
/*     */     
/* 143 */     while (visitedIndices.size() < length) {
/* 144 */       indices.add(Integer.valueOf(idx));
/*     */       
/* 146 */       T item = parent2Rep.get(idx);
/* 147 */       idx = parent1Rep.indexOf(item);
/*     */       
/* 149 */       while (idx != ((Integer)indices.get(0)).intValue()) {
/*     */         
/* 151 */         indices.add(Integer.valueOf(idx));
/*     */         
/* 153 */         item = parent2Rep.get(idx);
/*     */         
/* 155 */         idx = parent1Rep.indexOf(item);
/*     */       } 
/*     */ 
/*     */       
/* 159 */       if (cycle++ % 2 != 0) {
/* 160 */         for (Iterator<Integer> i$ = indices.iterator(); i$.hasNext(); ) { int i = ((Integer)i$.next()).intValue();
/* 161 */           T tmp = child1Rep.get(i);
/* 162 */           child1Rep.set(i, child2Rep.get(i));
/* 163 */           child2Rep.set(i, tmp); }
/*     */       
/*     */       }
/*     */       
/* 167 */       visitedIndices.addAll(indices);
/*     */       
/* 169 */       idx = (((Integer)indices.get(0)).intValue() + 1) % length;
/* 170 */       while (visitedIndices.contains(Integer.valueOf(idx)) && visitedIndices.size() < length) {
/* 171 */         idx++;
/* 172 */         if (idx >= length) {
/* 173 */           idx = 0;
/*     */         }
/*     */       } 
/* 176 */       indices.clear();
/*     */     } 
/*     */     
/* 179 */     return new ChromosomePair(first.newFixedLengthChromosome(child1Rep), second.newFixedLengthChromosome(child2Rep));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/CycleCrossover.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */