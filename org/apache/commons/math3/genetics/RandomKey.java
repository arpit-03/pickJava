/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public abstract class RandomKey<T>
/*     */   extends AbstractListChromosome<Double>
/*     */   implements PermutationChromosome<T>
/*     */ {
/*     */   private final List<Double> sortedRepresentation;
/*     */   private final List<Integer> baseSeqPermutation;
/*     */   
/*     */   public RandomKey(List<Double> representation) throws InvalidRepresentationException {
/*  73 */     super(representation);
/*     */     
/*  75 */     List<Double> sortedRepr = new ArrayList<Double>(getRepresentation());
/*  76 */     Collections.sort(sortedRepr);
/*  77 */     this.sortedRepresentation = Collections.unmodifiableList(sortedRepr);
/*     */     
/*  79 */     this.baseSeqPermutation = Collections.unmodifiableList(decodeGeneric(baseSequence(getLength()), getRepresentation(), this.sortedRepresentation));
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
/*     */   public RandomKey(Double[] representation) throws InvalidRepresentationException {
/*  91 */     this(Arrays.asList(representation));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> decode(List<T> sequence) {
/*  98 */     return decodeGeneric(sequence, getRepresentation(), this.sortedRepresentation);
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
/*     */   private static <S> List<S> decodeGeneric(List<S> sequence, List<Double> representation, List<Double> sortedRepr) throws DimensionMismatchException {
/* 117 */     int l = sequence.size();
/*     */ 
/*     */     
/* 120 */     if (representation.size() != l) {
/* 121 */       throw new DimensionMismatchException(representation.size(), l);
/*     */     }
/* 123 */     if (sortedRepr.size() != l) {
/* 124 */       throw new DimensionMismatchException(sortedRepr.size(), l);
/*     */     }
/*     */ 
/*     */     
/* 128 */     List<Double> reprCopy = new ArrayList<Double>(representation);
/*     */ 
/*     */     
/* 131 */     List<S> res = new ArrayList<S>(l);
/* 132 */     for (int i = 0; i < l; i++) {
/* 133 */       int index = reprCopy.indexOf(sortedRepr.get(i));
/* 134 */       res.add(sequence.get(index));
/* 135 */       reprCopy.set(index, null);
/*     */     } 
/* 137 */     return res;
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
/*     */   protected boolean isSame(Chromosome another) {
/* 150 */     if (!(another instanceof RandomKey)) {
/* 151 */       return false;
/*     */     }
/* 153 */     RandomKey<?> anotherRk = (RandomKey)another;
/*     */     
/* 155 */     if (getLength() != anotherRk.getLength()) {
/* 156 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 161 */     List<Integer> thisPerm = this.baseSeqPermutation;
/* 162 */     List<Integer> anotherPerm = anotherRk.baseSeqPermutation;
/*     */     
/* 164 */     for (int i = 0; i < getLength(); i++) {
/* 165 */       if (thisPerm.get(i) != anotherPerm.get(i)) {
/* 166 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkValidity(List<Double> chromosomeRepresentation) throws InvalidRepresentationException {
/* 180 */     for (Iterator<Double> i$ = chromosomeRepresentation.iterator(); i$.hasNext(); ) { double val = ((Double)i$.next()).doubleValue();
/* 181 */       if (val < 0.0D || val > 1.0D) {
/* 182 */         throw new InvalidRepresentationException(LocalizedFormats.OUT_OF_RANGE_SIMPLE, new Object[] { Double.valueOf(val), Integer.valueOf(0), Integer.valueOf(1) });
/*     */       } }
/*     */   
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
/*     */   public static final List<Double> randomPermutation(int l) {
/* 197 */     List<Double> repr = new ArrayList<Double>(l);
/* 198 */     for (int i = 0; i < l; i++) {
/* 199 */       repr.add(Double.valueOf(GeneticAlgorithm.getRandomGenerator().nextDouble()));
/*     */     }
/* 201 */     return repr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final List<Double> identityPermutation(int l) {
/* 212 */     List<Double> repr = new ArrayList<Double>(l);
/* 213 */     for (int i = 0; i < l; i++) {
/* 214 */       repr.add(Double.valueOf(i / l));
/*     */     }
/* 216 */     return repr;
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
/*     */   public static <S> List<Double> comparatorPermutation(List<S> data, Comparator<S> comparator) {
/* 234 */     List<S> sortedData = new ArrayList<S>(data);
/* 235 */     Collections.sort(sortedData, comparator);
/*     */     
/* 237 */     return inducedPermutation(data, sortedData);
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
/*     */   public static <S> List<Double> inducedPermutation(List<S> originalData, List<S> permutedData) throws DimensionMismatchException, MathIllegalArgumentException {
/* 261 */     if (originalData.size() != permutedData.size()) {
/* 262 */       throw new DimensionMismatchException(permutedData.size(), originalData.size());
/*     */     }
/* 264 */     int l = originalData.size();
/*     */     
/* 266 */     List<S> origDataCopy = new ArrayList<S>(originalData);
/*     */     
/* 268 */     Double[] res = new Double[l];
/* 269 */     for (int i = 0; i < l; i++) {
/* 270 */       int index = origDataCopy.indexOf(permutedData.get(i));
/* 271 */       if (index == -1) {
/* 272 */         throw new MathIllegalArgumentException(LocalizedFormats.DIFFERENT_ORIG_AND_PERMUTED_DATA, new Object[0]);
/*     */       }
/* 274 */       res[index] = Double.valueOf(i / l);
/* 275 */       origDataCopy.set(index, null);
/*     */     } 
/* 277 */     return Arrays.asList(res);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 283 */     return String.format("(f=%s pi=(%s))", new Object[] { Double.valueOf(getFitness()), this.baseSeqPermutation });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Integer> baseSequence(int l) {
/* 293 */     List<Integer> baseSequence = new ArrayList<Integer>(l);
/* 294 */     for (int i = 0; i < l; i++) {
/* 295 */       baseSequence.add(Integer.valueOf(i));
/*     */     }
/* 297 */     return baseSequence;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/RandomKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */