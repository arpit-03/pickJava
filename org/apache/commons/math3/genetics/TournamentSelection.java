/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class TournamentSelection
/*     */   implements SelectionPolicy
/*     */ {
/*     */   private int arity;
/*     */   
/*     */   public TournamentSelection(int arity) {
/*  44 */     this.arity = arity;
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
/*     */   public ChromosomePair select(Population population) throws MathIllegalArgumentException {
/*  58 */     return new ChromosomePair(tournament((ListPopulation)population), tournament((ListPopulation)population));
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
/*     */   private Chromosome tournament(ListPopulation population) throws MathIllegalArgumentException {
/*  71 */     if (population.getPopulationSize() < this.arity) {
/*  72 */       throw new MathIllegalArgumentException(LocalizedFormats.TOO_LARGE_TOURNAMENT_ARITY, new Object[] { Integer.valueOf(this.arity), Integer.valueOf(population.getPopulationSize()) });
/*     */     }
/*     */ 
/*     */     
/*  76 */     ListPopulation tournamentPopulation = new ListPopulation(this.arity)
/*     */       {
/*     */         public Population nextGeneration()
/*     */         {
/*  80 */           return null;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  85 */     List<Chromosome> chromosomes = new ArrayList<Chromosome>(population.getChromosomes());
/*  86 */     for (int i = 0; i < this.arity; i++) {
/*     */       
/*  88 */       int rind = GeneticAlgorithm.getRandomGenerator().nextInt(chromosomes.size());
/*  89 */       tournamentPopulation.addChromosome(chromosomes.get(rind));
/*     */       
/*  91 */       chromosomes.remove(rind);
/*     */     } 
/*     */     
/*  94 */     return tournamentPopulation.getFittestChromosome();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArity() {
/* 103 */     return this.arity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArity(int arity) {
/* 112 */     this.arity = arity;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/TournamentSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */