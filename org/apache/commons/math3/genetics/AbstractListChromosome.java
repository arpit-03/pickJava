/*     */ package org.apache.commons.math3.genetics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractListChromosome<T>
/*     */   extends Chromosome
/*     */ {
/*     */   private final List<T> representation;
/*     */   
/*     */   public AbstractListChromosome(List<T> representation) throws InvalidRepresentationException {
/*  41 */     this(representation, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractListChromosome(T[] representation) throws InvalidRepresentationException {
/*  50 */     this(Arrays.asList(representation));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractListChromosome(List<T> representation, boolean copyList) {
/*  60 */     checkValidity(representation);
/*  61 */     this.representation = Collections.unmodifiableList(copyList ? new ArrayList<T>(representation) : representation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkValidity(List<T> paramList) throws InvalidRepresentationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<T> getRepresentation() {
/*  78 */     return this.representation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  86 */     return getRepresentation().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract AbstractListChromosome<T> newFixedLengthChromosome(List<T> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return String.format("(f=%s %s)", new Object[] { Double.valueOf(getFitness()), getRepresentation() });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/AbstractListChromosome.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */