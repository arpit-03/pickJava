/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractStruct
/*     */   extends AbstractStructBase
/*     */ {
/*     */   private final HashMap<String, Integer> indexMap;
/*     */   private final List<String> fields;
/*     */   private final List<Array[]> values;
/*     */   
/*     */   public List<String> getFieldNames() {
/*     */     return this.fields;
/*     */   }
/*     */   
/*     */   public Array[] remove(String field) {
/*     */     Integer fieldIndex = this.indexMap.remove(checkNonEmpty(field));
/*     */     if (fieldIndex == null)
/*     */       throw new IllegalArgumentException("A field named '" + field + "' doesn't exist."); 
/*     */     this.fields.remove(fieldIndex.intValue());
/*     */     return this.values.remove(fieldIndex.intValue());
/*     */   }
/*     */   
/*     */   public <T extends Array> T get(String field, int index) {
/*     */     Integer fieldIndex = this.indexMap.get(checkNonEmpty(field));
/*     */     if (fieldIndex == null)
/*     */       throw new IllegalArgumentException("Reference to non-existent field '" + field + "'"); 
/*     */     return (T)((Array[])this.values.get(fieldIndex.intValue()))[index];
/*     */   }
/*     */   
/*     */   protected AbstractStruct(int[] dims) {
/*  41 */     super(dims);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     this.indexMap = new HashMap<>();
/* 111 */     this.fields = new ArrayList<>();
/* 112 */     this.values = (List)new ArrayList<>();
/*     */   }
/*     */   
/*     */   protected int subHashCode() {
/* 116 */     int prime = 31;
/* 117 */     int result = 1;
/* 118 */     result = 31 * result + this.indexMap.hashCode();
/* 119 */     result = 31 * result + this.fields.hashCode();
/* 120 */     for (Array[] valueArray : this.values) {
/* 121 */       result = 31 * result + Arrays.hashCode((Object[])valueArray);
/*     */     }
/* 123 */     return result;
/*     */   } public Struct set(String field, int index, Array value) {
/*     */     getOrInitValues(field)[index] = value;
/*     */     return this;
/*     */   } protected abstract Array getEmptyValue(); protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 128 */     AbstractStruct other = (AbstractStruct)otherGuaranteedSameClass;
/* 129 */     if (other.indexMap.equals(this.indexMap) && 
/* 130 */       other.fields.equals(this.fields) && 
/* 131 */       other.values.size() == this.values.size()) {
/* 132 */       for (int i = 0; i < this.values.size(); i++) {
/* 133 */         if (!Arrays.equals((Object[])other.values.get(i), (Object[])this.values.get(i))) {
/* 134 */           return false;
/*     */         }
/*     */       } 
/* 137 */       return true;
/*     */     } 
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   protected Array[] getOrInitValues(String field) {
/*     */     Integer fieldIndex = this.indexMap.get(checkNonEmpty(field));
/*     */     if (fieldIndex != null)
/*     */       return this.values.get(fieldIndex.intValue()); 
/*     */     Array[] value = new Array[getNumElements()];
/*     */     Arrays.fill((Object[])value, getEmptyValue());
/*     */     this.indexMap.put(field, Integer.valueOf(this.values.size()));
/*     */     this.fields.add(field);
/*     */     this.values.add(value);
/*     */     return value;
/*     */   }
/*     */   
/*     */   protected static String checkNonEmpty(String field) {
/*     */     if (field == null || field.isEmpty())
/*     */       throw new IllegalArgumentException("Field name can't be empty."); 
/*     */     return field;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*     */     for (Array[] value : this.values) {
/*     */       byte b;
/*     */       int i;
/*     */       Array[] arrayOfArray1;
/*     */       for (i = (arrayOfArray1 = value).length, b = 0; b < i; ) {
/*     */         Array array = arrayOfArray1[b];
/*     */         array.close();
/*     */         b++;
/*     */       } 
/*     */     } 
/*     */     this.indexMap.clear();
/*     */     this.fields.clear();
/*     */     this.values.clear();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractStruct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */