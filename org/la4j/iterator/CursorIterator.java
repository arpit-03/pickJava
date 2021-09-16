/*     */ package org.la4j.iterator;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class CursorIterator
/*     */   implements Iterator<Double>
/*     */ {
/*     */   private enum IteratorState
/*     */   {
/*  32 */     TAKEN_FROM_THESE,
/*  33 */     TAKEN_FROM_THOSE,
/*  34 */     THESE_ARE_EMPTY,
/*  35 */     THOSE_ARE_EMPTY;
/*     */   }
/*     */   
/*  38 */   private static final Collection<IteratorState> TAKEN_FROM_BOTH = Arrays.asList(new IteratorState[] { IteratorState.TAKEN_FROM_THESE, IteratorState.TAKEN_FROM_THOSE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CursorIterator orElse(final CursorIterator those, final JoinFunction function) {
/*  49 */     final CursorIterator these = this;
/*  50 */     return new CursorIterator() {
/*  51 */         private final EnumSet<CursorIterator.IteratorState> state = EnumSet.copyOf(CursorIterator.TAKEN_FROM_BOTH);
/*     */ 
/*     */         
/*     */         public int cursor() {
/*  55 */           if (this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THESE)) {
/*  56 */             return these.cursor();
/*     */           }
/*  58 */           return those.cursor();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public double get() {
/*  64 */           if (this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THESE) && this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THOSE))
/*     */           {
/*     */             
/*  67 */             return function.apply(these.get(), those.get()); } 
/*  68 */           if (this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THESE)) {
/*  69 */             return function.apply(these.get(), 0.0D);
/*     */           }
/*  71 */           return function.apply(0.0D, those.get());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void set(double value) {
/*  77 */           throw new UnsupportedOperationException("Composed iterators are read-only for now.");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/*  83 */           if (these.hasNext() || those.hasNext()) {
/*  84 */             return true;
/*     */           }
/*     */           
/*  87 */           if (this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THESE) && this.state.contains(CursorIterator.IteratorState.THOSE_ARE_EMPTY)) {
/*  88 */             return false;
/*     */           }
/*     */           
/*  91 */           if (this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THOSE) && this.state.contains(CursorIterator.IteratorState.THESE_ARE_EMPTY)) {
/*  92 */             return false;
/*     */           }
/*     */           
/*  95 */           return !this.state.containsAll(CursorIterator.TAKEN_FROM_BOTH);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 100 */           if (this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THESE)) {
/* 101 */             if (these.hasNext()) {
/* 102 */               these.next();
/*     */             } else {
/* 104 */               this.state.add(CursorIterator.IteratorState.THESE_ARE_EMPTY);
/*     */             } 
/*     */           }
/*     */           
/* 108 */           if (this.state.contains(CursorIterator.IteratorState.TAKEN_FROM_THOSE)) {
/* 109 */             if (those.hasNext()) {
/* 110 */               those.next();
/*     */             } else {
/* 112 */               this.state.add(CursorIterator.IteratorState.THOSE_ARE_EMPTY);
/*     */             } 
/*     */           }
/*     */           
/* 116 */           this.state.remove(CursorIterator.IteratorState.TAKEN_FROM_THESE);
/* 117 */           this.state.remove(CursorIterator.IteratorState.TAKEN_FROM_THOSE);
/*     */           
/* 119 */           if (!this.state.contains(CursorIterator.IteratorState.THESE_ARE_EMPTY) && !this.state.contains(CursorIterator.IteratorState.THOSE_ARE_EMPTY)) {
/*     */ 
/*     */             
/* 122 */             if (these.cursor() < those.cursor()) {
/* 123 */               this.state.add(CursorIterator.IteratorState.TAKEN_FROM_THESE);
/* 124 */             } else if (these.cursor() > those.cursor()) {
/* 125 */               this.state.add(CursorIterator.IteratorState.TAKEN_FROM_THOSE);
/*     */             } else {
/* 127 */               this.state.add(CursorIterator.IteratorState.TAKEN_FROM_THESE);
/* 128 */               this.state.add(CursorIterator.IteratorState.TAKEN_FROM_THOSE);
/*     */             } 
/* 130 */           } else if (this.state.contains(CursorIterator.IteratorState.THESE_ARE_EMPTY)) {
/* 131 */             this.state.add(CursorIterator.IteratorState.TAKEN_FROM_THOSE);
/* 132 */           } else if (this.state.contains(CursorIterator.IteratorState.THOSE_ARE_EMPTY)) {
/* 133 */             this.state.add(CursorIterator.IteratorState.TAKEN_FROM_THESE);
/*     */           } 
/*     */           
/* 136 */           return Double.valueOf(get());
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected CursorIterator andAlso(final CursorIterator those, final JoinFunction function) {
/* 142 */     final CursorIterator these = this;
/* 143 */     return new CursorIterator()
/*     */       {
/*     */         private boolean hasNext;
/*     */         
/*     */         private double prevValue;
/*     */         
/*     */         private double currValue;
/*     */         private int prevCursor;
/*     */         private int currCursor;
/*     */         
/*     */         public int cursor() {
/* 154 */           return this.prevCursor;
/*     */         }
/*     */         
/*     */         private void doNext() {
/* 158 */           this.hasNext = false;
/*     */           
/* 160 */           this.prevValue = this.currValue;
/* 161 */           this.prevCursor = this.currCursor;
/*     */           
/* 163 */           if (these.hasNext() && those.hasNext()) {
/* 164 */             these.next();
/* 165 */             those.next();
/*     */             
/* 167 */             while (these.cursor() != those.cursor()) {
/* 168 */               if (these.cursor() < those.cursor() && these.hasNext()) {
/* 169 */                 these.next(); continue;
/* 170 */               }  if (these.cursor() > those.cursor() && those.hasNext()) {
/* 171 */                 those.next();
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               return;
/*     */             } 
/* 177 */             this.hasNext = true;
/*     */             
/* 179 */             this.currValue = function.apply(these.get(), those.get());
/* 180 */             this.currCursor = these.cursor();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 186 */           return this.prevValue;
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 191 */           throw new UnsupportedOperationException("Composed iterators are read-only for now.");
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 196 */           return this.hasNext;
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 201 */           doNext();
/* 202 */           return Double.valueOf(get());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 209 */     throw new UnsupportedOperationException("This will be supported in 0.6.0.");
/*     */   }
/*     */   
/*     */   public abstract double get();
/*     */   
/*     */   public abstract void set(double paramDouble);
/*     */   
/*     */   protected abstract int cursor();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/iterator/CursorIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */