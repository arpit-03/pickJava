/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*     */ public abstract class ListPopulation
/*     */   implements Population
/*     */ {
/*     */   private List<Chromosome> chromosomes;
/*     */   private int populationLimit;
/*     */   
/*     */   public ListPopulation(int populationLimit) throws NotPositiveException {
/*  51 */     this(Collections.emptyList(), populationLimit);
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
/*     */   public ListPopulation(List<Chromosome> chromosomes, int populationLimit) throws NullArgumentException, NotPositiveException, NumberIsTooLargeException {
/*  68 */     if (chromosomes == null) {
/*  69 */       throw new NullArgumentException();
/*     */     }
/*  71 */     if (populationLimit <= 0) {
/*  72 */       throw new NotPositiveException(LocalizedFormats.POPULATION_LIMIT_NOT_POSITIVE, Integer.valueOf(populationLimit));
/*     */     }
/*  74 */     if (chromosomes.size() > populationLimit) {
/*  75 */       throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE, Integer.valueOf(chromosomes.size()), Integer.valueOf(populationLimit), false);
/*     */     }
/*     */     
/*  78 */     this.populationLimit = populationLimit;
/*  79 */     this.chromosomes = new ArrayList<Chromosome>(populationLimit);
/*  80 */     this.chromosomes.addAll(chromosomes);
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
/*     */   @Deprecated
/*     */   public void setChromosomes(List<Chromosome> chromosomes) throws NullArgumentException, NumberIsTooLargeException {
/*  98 */     if (chromosomes == null) {
/*  99 */       throw new NullArgumentException();
/*     */     }
/* 101 */     if (chromosomes.size() > this.populationLimit) {
/* 102 */       throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE, Integer.valueOf(chromosomes.size()), Integer.valueOf(this.populationLimit), false);
/*     */     }
/*     */     
/* 105 */     this.chromosomes.clear();
/* 106 */     this.chromosomes.addAll(chromosomes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChromosomes(Collection<Chromosome> chromosomeColl) throws NumberIsTooLargeException {
/* 117 */     if (this.chromosomes.size() + chromosomeColl.size() > this.populationLimit) {
/* 118 */       throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE, Integer.valueOf(this.chromosomes.size()), Integer.valueOf(this.populationLimit), false);
/*     */     }
/*     */     
/* 121 */     this.chromosomes.addAll(chromosomeColl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Chromosome> getChromosomes() {
/* 129 */     return Collections.unmodifiableList(this.chromosomes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<Chromosome> getChromosomeList() {
/* 138 */     return this.chromosomes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChromosome(Chromosome chromosome) throws NumberIsTooLargeException {
/* 149 */     if (this.chromosomes.size() >= this.populationLimit) {
/* 150 */       throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE, Integer.valueOf(this.chromosomes.size()), Integer.valueOf(this.populationLimit), false);
/*     */     }
/*     */     
/* 153 */     this.chromosomes.add(chromosome);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chromosome getFittestChromosome() {
/* 162 */     Chromosome bestChromosome = this.chromosomes.get(0);
/* 163 */     for (Chromosome chromosome : this.chromosomes) {
/* 164 */       if (chromosome.compareTo(bestChromosome) > 0)
/*     */       {
/* 166 */         bestChromosome = chromosome;
/*     */       }
/*     */     } 
/* 169 */     return bestChromosome;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPopulationLimit() {
/* 177 */     return this.populationLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPopulationLimit(int populationLimit) throws NotPositiveException, NumberIsTooSmallException {
/* 188 */     if (populationLimit <= 0) {
/* 189 */       throw new NotPositiveException(LocalizedFormats.POPULATION_LIMIT_NOT_POSITIVE, Integer.valueOf(populationLimit));
/*     */     }
/* 191 */     if (populationLimit < this.chromosomes.size()) {
/* 192 */       throw new NumberIsTooSmallException(Integer.valueOf(populationLimit), Integer.valueOf(this.chromosomes.size()), true);
/*     */     }
/* 194 */     this.populationLimit = populationLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPopulationSize() {
/* 202 */     return this.chromosomes.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 210 */     return this.chromosomes.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Chromosome> iterator() {
/* 220 */     return getChromosomes().iterator();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/ListPopulation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */