/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayUtilities
/*     */ {
/*     */   public static float[][] clone(float[][] array) {
/*  70 */     if (array == null) {
/*  71 */       return (float[][])null;
/*     */     }
/*  73 */     float[][] result = new float[array.length][];
/*  74 */     System.arraycopy(array, 0, result, 0, array.length);
/*     */     
/*  76 */     for (int i = 0; i < array.length; i++) {
/*  77 */       float[] child = array[i];
/*  78 */       float[] copychild = new float[child.length];
/*  79 */       System.arraycopy(child, 0, copychild, 0, child.length);
/*  80 */       result[i] = copychild;
/*     */     } 
/*     */     
/*  83 */     return result;
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
/*     */   public static boolean equalReferencesInArrays(Object[] array1, Object[] array2) {
/*  99 */     if (array1 == null) {
/* 100 */       return (array2 == null);
/*     */     }
/* 102 */     if (array2 == null) {
/* 103 */       return false;
/*     */     }
/* 105 */     if (array1.length != array2.length) {
/* 106 */       return false;
/*     */     }
/* 108 */     for (int i = 0; i < array1.length; i++) {
/* 109 */       if (array1[i] == null && 
/* 110 */         array2[i] != null) {
/* 111 */         return false;
/*     */       }
/*     */       
/* 114 */       if (array2[i] == null && 
/* 115 */         array1[i] != null) {
/* 116 */         return false;
/*     */       }
/*     */       
/* 119 */       if (array1[i] != array2[i]) {
/* 120 */         return false;
/*     */       }
/*     */     } 
/* 123 */     return true;
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
/*     */   public static boolean equal(float[][] array1, float[][] array2) {
/* 136 */     if (array1 == null) {
/* 137 */       return (array2 == null);
/*     */     }
/*     */     
/* 140 */     if (array2 == null) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     if (array1.length != array2.length) {
/* 145 */       return false;
/*     */     }
/*     */     
/* 148 */     for (int i = 0; i < array1.length; i++) {
/* 149 */       if (!Arrays.equals(array1[i], array2[i])) {
/* 150 */         return false;
/*     */       }
/*     */     } 
/* 153 */     return true;
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
/*     */   public static boolean hasDuplicateItems(Object[] array) {
/* 165 */     for (int i = 0; i < array.length; i++) {
/* 166 */       for (int j = 0; j < i; j++) {
/* 167 */         Object o1 = array[i];
/* 168 */         Object o2 = array[j];
/* 169 */         if (o1 != null && o2 != null && 
/* 170 */           o1.equals(o2)) {
/* 171 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return false;
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
/*     */   public static int compareVersionArrays(Comparable[] a1, Comparable[] a2) {
/* 189 */     int length = Math.min(a1.length, a2.length);
/* 190 */     for (int i = 0; i < length; i++) {
/*     */       
/* 192 */       Comparable<Comparable> o1 = a1[i];
/* 193 */       Comparable o2 = a2[i];
/* 194 */       if (o1 != null || o2 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 199 */         if (o1 == null)
/*     */         {
/* 201 */           return 1;
/*     */         }
/* 203 */         if (o2 == null)
/*     */         {
/* 205 */           return -1;
/*     */         }
/* 207 */         int retval = o1.compareTo(o2);
/* 208 */         if (retval != 0)
/*     */         {
/* 210 */           return retval; } 
/*     */       } 
/*     */     } 
/* 213 */     return 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/ArrayUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */