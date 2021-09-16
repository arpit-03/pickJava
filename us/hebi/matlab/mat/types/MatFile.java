/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface MatFile
/*     */   extends Closeable
/*     */ {
/*     */   Matrix getMatrix(String paramString);
/*     */   
/*     */   Sparse getSparse(String paramString);
/*     */   
/*     */   Char getChar(String paramString);
/*     */   
/*     */   Struct getStruct(String paramString);
/*     */   
/*     */   ObjectStruct getObject(String paramString);
/*     */   
/*     */   Cell getCell(String paramString);
/*     */   
/*     */   Matrix getMatrix(int paramInt);
/*     */   
/*     */   Sparse getSparse(int paramInt);
/*     */   
/*     */   Char getChar(int paramInt);
/*     */   
/*     */   Struct getStruct(int paramInt);
/*     */   
/*     */   ObjectStruct getObject(int paramInt);
/*     */   
/*     */   Cell getCell(int paramInt);
/*     */   
/*     */   <T extends Array> T getArray(String paramString);
/*     */   
/*     */   <T extends Array> T getArray(int paramInt);
/*     */   
/*     */   MatFile addArray(String paramString, Array paramArray);
/*     */   
/*     */   MatFile addArray(String paramString, boolean paramBoolean, Array paramArray);
/*     */   
/*     */   MatFile addEntry(Entry paramEntry);
/*     */   
/*     */   int getNumEntries();
/*     */   
/*     */   Iterable<Entry> getEntries();
/*     */   
/*     */   void clear();
/*     */   
/*     */   void close() throws IOException;
/*     */   
/*     */   long getUncompressedSerializedSize();
/*     */   
/*     */   MatFile writeTo(Sink paramSink) throws IOException;
/*     */   
/*     */   public static final class Entry
/*     */   {
/*     */     private final String name;
/*     */     private final Array value;
/*     */     private final boolean isGlobal;
/*     */     
/*     */     public Entry(String name, boolean isGlobal, Array value) {
/* 122 */       this.name = (String)Preconditions.checkNotNull(name, "Name can't be null");
/* 123 */       this.value = (Array)Preconditions.checkNotNull(value, "Value can't be null");
/* 124 */       this.isGlobal = isGlobal;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 128 */       return this.name;
/*     */     }
/*     */     
/*     */     public Array getValue() {
/* 132 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean isGlobal() {
/* 136 */       return this.isGlobal;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 141 */       return String.valueOf(this.name) + (isGlobal() ? " (global)" : "") + " = " + this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return 31 * this.name.hashCode() + this.value.hashCode() + (this.isGlobal ? 1231 : 1237);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 155 */       if (this == obj)
/* 156 */         return true; 
/* 157 */       if (obj instanceof Entry) {
/* 158 */         Entry other = (Entry)obj;
/* 159 */         return (other.name.equals(this.name) && other.value.equals(this.value) && other.isGlobal == this.isGlobal);
/*     */       } 
/* 161 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/MatFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */