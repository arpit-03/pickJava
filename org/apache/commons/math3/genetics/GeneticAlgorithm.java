/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.JDKRandomGenerator;
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
/*     */ public class GeneticAlgorithm
/*     */ {
/*  38 */   private static RandomGenerator randomGenerator = (RandomGenerator)new JDKRandomGenerator();
/*     */ 
/*     */   
/*     */   private final CrossoverPolicy crossoverPolicy;
/*     */ 
/*     */   
/*     */   private final double crossoverRate;
/*     */ 
/*     */   
/*     */   private final MutationPolicy mutationPolicy;
/*     */ 
/*     */   
/*     */   private final double mutationRate;
/*     */ 
/*     */   
/*     */   private final SelectionPolicy selectionPolicy;
/*     */ 
/*     */   
/*  56 */   private int generationsEvolved = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneticAlgorithm(CrossoverPolicy crossoverPolicy, double crossoverRate, MutationPolicy mutationPolicy, double mutationRate, SelectionPolicy selectionPolicy) throws OutOfRangeException {
/*  73 */     if (crossoverRate < 0.0D || crossoverRate > 1.0D) {
/*  74 */       throw new OutOfRangeException(LocalizedFormats.CROSSOVER_RATE, Double.valueOf(crossoverRate), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/*  77 */     if (mutationRate < 0.0D || mutationRate > 1.0D) {
/*  78 */       throw new OutOfRangeException(LocalizedFormats.MUTATION_RATE, Double.valueOf(mutationRate), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/*  81 */     this.crossoverPolicy = crossoverPolicy;
/*  82 */     this.crossoverRate = crossoverRate;
/*  83 */     this.mutationPolicy = mutationPolicy;
/*  84 */     this.mutationRate = mutationRate;
/*  85 */     this.selectionPolicy = selectionPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setRandomGenerator(RandomGenerator random) {
/*  94 */     randomGenerator = random;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized RandomGenerator getRandomGenerator() {
/* 103 */     return randomGenerator;
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
/*     */   public Population evolve(Population initial, StoppingCondition condition) {
/* 117 */     Population current = initial;
/* 118 */     this.generationsEvolved = 0;
/* 119 */     while (!condition.isSatisfied(current)) {
/* 120 */       current = nextGeneration(current);
/* 121 */       this.generationsEvolved++;
/*     */     } 
/* 123 */     return current;
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
/*     */   public Population nextGeneration(Population current) {
/* 149 */     Population nextGeneration = current.nextGeneration();
/*     */     
/* 151 */     RandomGenerator randGen = getRandomGenerator();
/*     */     
/* 153 */     while (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
/*     */       
/* 155 */       ChromosomePair pair = getSelectionPolicy().select(current);
/*     */ 
/*     */       
/* 158 */       if (randGen.nextDouble() < getCrossoverRate())
/*     */       {
/* 160 */         pair = getCrossoverPolicy().crossover(pair.getFirst(), pair.getSecond());
/*     */       }
/*     */ 
/*     */       
/* 164 */       if (randGen.nextDouble() < getMutationRate())
/*     */       {
/* 166 */         pair = new ChromosomePair(getMutationPolicy().mutate(pair.getFirst()), getMutationPolicy().mutate(pair.getSecond()));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       nextGeneration.addChromosome(pair.getFirst());
/*     */       
/* 174 */       if (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit())
/*     */       {
/* 176 */         nextGeneration.addChromosome(pair.getSecond());
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return nextGeneration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrossoverPolicy getCrossoverPolicy() {
/* 188 */     return this.crossoverPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCrossoverRate() {
/* 196 */     return this.crossoverRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutationPolicy getMutationPolicy() {
/* 204 */     return this.mutationPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMutationRate() {
/* 212 */     return this.mutationRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectionPolicy getSelectionPolicy() {
/* 220 */     return this.selectionPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGenerationsEvolved() {
/* 230 */     return this.generationsEvolved;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/GeneticAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */