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
/*    */ 
/*    */ public class BinaryMutation
/*    */   implements MutationPolicy
/*    */ {
/*    */   public Chromosome mutate(Chromosome original) throws MathIllegalArgumentException {
/* 40 */     if (!(original instanceof BinaryChromosome)) {
/* 41 */       throw new MathIllegalArgumentException(LocalizedFormats.INVALID_BINARY_CHROMOSOME, new Object[0]);
/*    */     }
/*    */     
/* 44 */     BinaryChromosome origChrom = (BinaryChromosome)original;
/* 45 */     List<Integer> newRepr = new ArrayList<Integer>(origChrom.getRepresentation());
/*    */ 
/*    */     
/* 48 */     int geneIndex = GeneticAlgorithm.getRandomGenerator().nextInt(origChrom.getLength());
/*    */     
/* 50 */     newRepr.set(geneIndex, Integer.valueOf((((Integer)origChrom.getRepresentation().get(geneIndex)).intValue() == 0) ? 1 : 0));
/*    */     
/* 52 */     Chromosome<Integer> newChrom = origChrom.newFixedLengthChromosome(newRepr);
/* 53 */     return newChrom;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/BinaryMutation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */