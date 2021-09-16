/*     */ package org.jfree.data.io;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CSV
/*     */ {
/*     */   private char fieldDelimiter;
/*     */   private char textDelimiter;
/*     */   
/*     */   public CSV() {
/*  69 */     this(',', '"');
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
/*     */   public CSV(char fieldDelimiter, char textDelimiter) {
/*  81 */     this.fieldDelimiter = fieldDelimiter;
/*  82 */     this.textDelimiter = textDelimiter;
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
/*     */   public CategoryDataset readCategoryDataset(Reader in) throws IOException {
/*  96 */     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
/*  97 */     BufferedReader reader = new BufferedReader(in);
/*  98 */     List columnKeys = null;
/*  99 */     int lineIndex = 0;
/* 100 */     String line = reader.readLine();
/* 101 */     while (line != null) {
/* 102 */       if (lineIndex == 0) {
/* 103 */         columnKeys = extractColumnKeys(line);
/*     */       } else {
/*     */         
/* 106 */         extractRowKeyAndData(line, dataset, columnKeys);
/*     */       } 
/* 108 */       line = reader.readLine();
/* 109 */       lineIndex++;
/*     */     } 
/* 111 */     return (CategoryDataset)dataset;
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
/*     */   private List extractColumnKeys(String line) {
/* 123 */     List<String> keys = new ArrayList();
/* 124 */     int fieldIndex = 0;
/* 125 */     int start = 0;
/* 126 */     for (int i = 0; i < line.length(); i++) {
/* 127 */       if (line.charAt(i) == this.fieldDelimiter) {
/* 128 */         if (fieldIndex > 0) {
/*     */           
/* 130 */           String str = line.substring(start, i);
/* 131 */           keys.add(removeStringDelimiters(str));
/*     */         } 
/* 133 */         start = i + 1;
/* 134 */         fieldIndex++;
/*     */       } 
/*     */     } 
/* 137 */     String key = line.substring(start, line.length());
/* 138 */     keys.add(removeStringDelimiters(key));
/* 139 */     return keys;
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
/*     */   private void extractRowKeyAndData(String line, DefaultCategoryDataset dataset, List<Comparable> columnKeys) {
/* 152 */     Comparable rowKey = null;
/* 153 */     int fieldIndex = 0;
/* 154 */     int start = 0;
/* 155 */     for (int i = 0; i < line.length(); i++) {
/* 156 */       if (line.charAt(i) == this.fieldDelimiter) {
/* 157 */         if (fieldIndex == 0) {
/* 158 */           String key = line.substring(start, i);
/* 159 */           rowKey = removeStringDelimiters(key);
/*     */         } else {
/*     */           
/* 162 */           Double double_ = Double.valueOf(
/* 163 */               removeStringDelimiters(line.substring(start, i)));
/*     */           
/* 165 */           dataset.addValue(double_, rowKey, columnKeys
/*     */               
/* 167 */               .get(fieldIndex - 1));
/*     */         } 
/*     */         
/* 170 */         start = i + 1;
/* 171 */         fieldIndex++;
/*     */       } 
/*     */     } 
/* 174 */     Double value = Double.valueOf(
/* 175 */         removeStringDelimiters(line.substring(start, line.length())));
/*     */     
/* 177 */     dataset.addValue(value, rowKey, columnKeys
/* 178 */         .get(fieldIndex - 1));
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
/*     */   private String removeStringDelimiters(String key) {
/* 191 */     String k = key.trim();
/* 192 */     if (k.charAt(0) == this.textDelimiter) {
/* 193 */       k = k.substring(1);
/*     */     }
/* 195 */     if (k.charAt(k.length() - 1) == this.textDelimiter) {
/* 196 */       k = k.substring(0, k.length() - 1);
/*     */     }
/* 198 */     return k;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/io/CSV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */