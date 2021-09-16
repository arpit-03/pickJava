/*     */ package org.apache.commons.math3.exception;
/*     */ 
/*     */ import org.apache.commons.math3.exception.util.Localizable;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonMonotonicSequenceException
/*     */   extends MathIllegalNumberException
/*     */ {
/*     */   private static final long serialVersionUID = 3596849179428944575L;
/*     */   private final MathArrays.OrderDirection direction;
/*     */   private final boolean strict;
/*     */   private final int index;
/*     */   private final Number previous;
/*     */   
/*     */   public NonMonotonicSequenceException(Number wrong, Number previous, int index) {
/*  60 */     this(wrong, previous, index, MathArrays.OrderDirection.INCREASING, true);
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
/*     */   public NonMonotonicSequenceException(Number wrong, Number previous, int index, MathArrays.OrderDirection direction, boolean strict) {
/*  79 */     super((direction == MathArrays.OrderDirection.INCREASING) ? (strict ? (Localizable)LocalizedFormats.NOT_STRICTLY_INCREASING_SEQUENCE : (Localizable)LocalizedFormats.NOT_INCREASING_SEQUENCE) : (strict ? (Localizable)LocalizedFormats.NOT_STRICTLY_DECREASING_SEQUENCE : (Localizable)LocalizedFormats.NOT_DECREASING_SEQUENCE), wrong, new Object[] { previous, Integer.valueOf(index), Integer.valueOf(index - 1) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.direction = direction;
/*  89 */     this.strict = strict;
/*  90 */     this.index = index;
/*  91 */     this.previous = previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MathArrays.OrderDirection getDirection() {
/*  98 */     return this.direction;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getStrict() {
/* 104 */     return this.strict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 112 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getPrevious() {
/* 118 */     return this.previous;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/NonMonotonicSequenceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */