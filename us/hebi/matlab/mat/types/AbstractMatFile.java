/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ public abstract class AbstractMatFile
/*     */   extends AbstractMatFileBase
/*     */ {
/*     */   public <T extends Array> T getArray(String name) {
/*  40 */     Array array = this.lookup.get(name);
/*  41 */     if (array != null) {
/*  42 */       return (T)array;
/*     */     }
/*     */     
/*  45 */     for (MatFile.Entry entry : this.entries) {
/*  46 */       if (name.equalsIgnoreCase(entry.getName())) {
/*  47 */         return (T)entry.getValue();
/*     */       }
/*     */     } 
/*  50 */     throw new IllegalArgumentException("Could not find array: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Array> T getArray(int index) {
/*  56 */     return (T)((MatFile.Entry)this.entries.get(index)).getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public MatFile addArray(String name, Array value) {
/*  61 */     return addArray(name, false, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public MatFile addArray(String name, boolean isGlobal, Array value) {
/*  66 */     return addEntry(new MatFile.Entry(name, isGlobal, value));
/*     */   }
/*     */ 
/*     */   
/*     */   public MatFile addEntry(MatFile.Entry entry) {
/*  71 */     this.entries.add(entry);
/*  72 */     this.lookup.put(entry.getName(), entry.getValue());
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     return StringHelper.toString(this.entries);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  87 */     IOException lastError = null;
/*  88 */     for (MatFile.Entry entry : this.entries) {
/*     */       try {
/*  90 */         entry.getValue().close();
/*  91 */       } catch (IOException ioe) {
/*  92 */         lastError = ioe;
/*     */       } 
/*     */     } 
/*  95 */     clear();
/*  96 */     if (lastError != null) {
/*  97 */       throw lastError;
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterable<MatFile.Entry> getEntries() {
/* 102 */     return this.entries;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumEntries() {
/* 107 */     return this.entries.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 112 */     this.lookup.clear();
/* 113 */     this.entries.clear();
/*     */   }
/*     */   
/* 116 */   protected final HashMap<String, Array> lookup = new HashMap<>();
/* 117 */   protected final List<MatFile.Entry> entries = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 121 */     return 31 * subHashCode() + this.entries.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object other) {
/* 126 */     if (other == this)
/* 127 */       return true; 
/* 128 */     if (other == null)
/* 129 */       return false; 
/* 130 */     if (other.getClass().equals(getClass())) {
/* 131 */       AbstractMatFile otherFile = (AbstractMatFile)other;
/* 132 */       return (subEqualsGuaranteedSameClass(other) && otherFile.entries.equals(this.entries));
/*     */     } 
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract int subHashCode();
/*     */   
/*     */   protected abstract boolean subEqualsGuaranteedSameClass(Object paramObject);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractMatFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */