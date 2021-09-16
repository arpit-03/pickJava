/*    */ package org.apache.commons.math3.genetics;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public abstract class BinaryChromosome
/*    */   extends AbstractListChromosome<Integer>
/*    */ {
/*    */   public BinaryChromosome(List<Integer> representation) throws InvalidRepresentationException {
/* 37 */     super(representation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BinaryChromosome(Integer[] representation) throws InvalidRepresentationException {
/* 46 */     super(representation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkValidity(List<Integer> chromosomeRepresentation) throws InvalidRepresentationException {
/* 54 */     for (Iterator<Integer> i$ = chromosomeRepresentation.iterator(); i$.hasNext(); ) { int i = ((Integer)i$.next()).intValue();
/* 55 */       if (i < 0 || i > 1) {
/* 56 */         throw new InvalidRepresentationException(LocalizedFormats.INVALID_BINARY_DIGIT, new Object[] { Integer.valueOf(i) });
/*    */       } }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<Integer> randomBinaryRepresentation(int length) {
/* 69 */     List<Integer> rList = new ArrayList<Integer>(length);
/* 70 */     for (int j = 0; j < length; j++) {
/* 71 */       rList.add(Integer.valueOf(GeneticAlgorithm.getRandomGenerator().nextInt(2)));
/*    */     }
/* 73 */     return rList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSame(Chromosome another) {
/* 80 */     if (!(another instanceof BinaryChromosome)) {
/* 81 */       return false;
/*    */     }
/* 83 */     BinaryChromosome anotherBc = (BinaryChromosome)another;
/*    */     
/* 85 */     if (getLength() != anotherBc.getLength()) {
/* 86 */       return false;
/*    */     }
/*    */     
/* 89 */     for (int i = 0; i < getRepresentation().size(); i++) {
/* 90 */       if (!((Integer)getRepresentation().get(i)).equals(anotherBc.getRepresentation().get(i))) {
/* 91 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/BinaryChromosome.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */