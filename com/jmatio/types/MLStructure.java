/*     */ package com.jmatio.types;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MLStructure
/*     */   extends MLArray
/*     */ {
/*     */   private Set<String> keys;
/*     */   private List<Map<String, MLArray>> mlStructArray;
/*  36 */   private int currentIndex = 0;
/*     */ 
/*     */   
/*     */   public MLStructure(String name, int[] dims) {
/*  40 */     this(name, dims, 2, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public MLStructure(String name, int[] dims, int type, int attributes) {
/*  45 */     super(name, dims, type, attributes);
/*     */     
/*  47 */     this.mlStructArray = new ArrayList<Map<String, MLArray>>(dims[0] * dims[1]);
/*  48 */     this.keys = new LinkedHashSet<String>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(String name, MLArray value) {
/*  59 */     setField(name, value, this.currentIndex);
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
/*     */   public void setField(String name, MLArray value, int m, int n) {
/*  71 */     setField(name, value, getIndex(m, n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(String name, MLArray value, int index) {
/*  82 */     this.keys.add(name);
/*  83 */     this.currentIndex = index;
/*     */     
/*  85 */     if (this.mlStructArray.isEmpty() || this.mlStructArray.size() <= index)
/*     */     {
/*  87 */       this.mlStructArray.add(index, new LinkedHashMap<String, MLArray>());
/*     */     }
/*  89 */     ((Map<String, MLArray>)this.mlStructArray.get(index)).put(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFieldLenth() {
/* 100 */     int maxLen = 0;
/* 101 */     for (String s : this.keys)
/*     */     {
/* 103 */       maxLen = (s.length() > maxLen) ? s.length() : maxLen;
/*     */     }
/* 105 */     return maxLen + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getKeySetToByteArray() {
/* 116 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 117 */     DataOutputStream dos = new DataOutputStream(baos);
/*     */     
/* 119 */     char[] buffer = new char[getMaxFieldLenth()];
/*     */ 
/*     */     
/*     */     try {
/* 123 */       for (String s : this.keys)
/*     */       {
/* 125 */         Arrays.fill(buffer, false);
/* 126 */         System.arraycopy(s.toCharArray(), 0, buffer, 0, s.length());
/* 127 */         dos.writeBytes(new String(buffer));
/*     */       }
/*     */     
/* 130 */     } catch (IOException e) {
/*     */       
/* 132 */       System.err.println("Could not write Structure key set to byte array: " + e);
/* 133 */       return new byte[0];
/*     */     } 
/* 135 */     return baos.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<MLArray> getAllFields() {
/* 145 */     ArrayList<MLArray> fields = new ArrayList<MLArray>();
/*     */     
/* 147 */     for (Map<String, MLArray> struct : this.mlStructArray)
/*     */     {
/* 149 */       fields.addAll(struct.values());
/*     */     }
/* 151 */     return fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getFieldNames() {
/* 159 */     Set<String> fieldNames = new LinkedHashSet<String>();
/*     */     
/* 161 */     fieldNames.addAll(this.keys);
/*     */     
/* 163 */     return fieldNames;
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
/*     */   public MLArray getField(String name) {
/* 175 */     return getField(name, this.currentIndex);
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
/*     */   public MLArray getField(String name, int m, int n) {
/* 188 */     return getField(name, getIndex(m, n));
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
/*     */   public MLArray getField(String name, int index) {
/* 200 */     if (this.mlStructArray.isEmpty()) {
/* 201 */       return null;
/*     */     }
/* 203 */     return (MLArray)((Map)this.mlStructArray.get(index)).get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String contentToString() {
/* 210 */     StringBuffer sb = new StringBuffer();
/* 211 */     sb.append(this.name + " = \n");
/*     */     
/* 213 */     if (getM() * getN() == 1) {
/*     */       
/* 215 */       for (String key : this.keys)
/*     */       {
/* 217 */         sb.append("\t" + key + " : " + getField(key) + "\n");
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 222 */       sb.append("\n");
/* 223 */       sb.append(getM() + "x" + getN());
/* 224 */       sb.append(" struct array with fields: \n");
/* 225 */       for (String key : this.keys)
/*     */       {
/* 227 */         sb.append("\t" + key + "\n");
/*     */       }
/*     */     } 
/* 230 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLStructure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */