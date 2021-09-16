/*    */ package org.apache.commons.math3.genetics;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RandomKeyMutation
/*    */   implements MutationPolicy
/*    */ {
/*    */   public Chromosome mutate(Chromosome original) throws MathIllegalArgumentException {
/* 39 */     if (!(original instanceof RandomKey)) {
/* 40 */       throw new MathIllegalArgumentException(LocalizedFormats.RANDOMKEY_MUTATION_WRONG_CLASS, new Object[] { original.getClass().getSimpleName() });
/*    */     }
/*    */ 
/*    */     
/* 44 */     RandomKey<?> originalRk = (RandomKey)original;
/* 45 */     List<Double> repr = originalRk.getRepresentation();
/* 46 */     int rInd = GeneticAlgorithm.getRandomGenerator().nextInt(repr.size());
/*    */     
/* 48 */     List<Double> newRepr = new ArrayList<Double>(repr);
/* 49 */     newRepr.set(rInd, Double.valueOf(GeneticAlgorithm.getRandomGenerator().nextDouble()));
/*    */     
/* 51 */     return originalRk.newFixedLengthChromosome(newRepr);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/RandomKeyMutation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */