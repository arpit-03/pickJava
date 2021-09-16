/*     */ package com.jmatio.types;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MLChar
/*     */   extends MLArray
/*     */   implements GenericArrayCreator<Character>
/*     */ {
/*     */   Character[] chars;
/*     */   
/*     */   public MLChar(String name, String value) {
/*  18 */     this(name, new int[] { 1, value.length() }, 4, 0);
/*  19 */     set(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLChar(String name, String[] values) {
/*  30 */     this(name, new int[] { values.length, (values.length > 0) ? getMaxLength(values) : 0 }, 4, 0);
/*     */     
/*  32 */     for (int i = 0; i < values.length; i++)
/*     */     {
/*  34 */       set(values[i], i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getMaxLength(String[] values) {
/*  44 */     int result = 0;
/*     */     
/*  46 */     for (int i = 0, curr = 0; i < values.length; i++) {
/*     */       
/*  48 */       if ((curr = values[i].length()) > result)
/*     */       {
/*  50 */         result = curr;
/*     */       }
/*     */     } 
/*  53 */     return result;
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
/*     */   public MLChar(String name, String[] values, int maxlen) {
/*  66 */     this(name, new int[] { values.length, maxlen }, 4, 0);
/*  67 */     int idx = 0;
/*  68 */     for (String v : values) {
/*     */       
/*  70 */       set(v, idx);
/*  71 */       idx++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MLChar(String name, int[] dims, int type, int attributes) {
/*  77 */     super(name, dims, type, attributes);
/*  78 */     this.chars = createArray(getM(), getN());
/*     */   }
/*     */ 
/*     */   
/*     */   public Character[] createArray(int m, int n) {
/*  83 */     return new Character[m * n];
/*     */   }
/*     */   
/*     */   public void setChar(char ch, int index) {
/*  87 */     this.chars[index] = Character.valueOf(ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String value) {
/*  95 */     char[] cha = value.toCharArray();
/*  96 */     for (int i = 0; i < getN() && i < value.length(); i++)
/*     */     {
/*  98 */       setChar(cha[i], i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String value, int idx) {
/* 110 */     int rowOffset = getM();
/*     */     
/* 112 */     for (int i = 0; i < getN(); i++) {
/*     */       
/* 114 */       if (i < value.length()) {
/*     */         
/* 116 */         setChar(value.charAt(i), idx + rowOffset * i);
/*     */       }
/*     */       else {
/*     */         
/* 120 */         setChar(' ', idx + rowOffset * i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Character getChar(int m, int n) {
/* 127 */     return this.chars[getIndex(m, n)];
/*     */   }
/*     */   
/*     */   public Character[] exportChar() {
/* 131 */     return this.chars;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 137 */     if (o instanceof MLChar)
/*     */     {
/* 139 */       return Arrays.equals((Object[])this.chars, (Object[])((MLChar)o).chars);
/*     */     }
/* 141 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(int m) {
/* 152 */     StringBuffer charbuff = new StringBuffer();
/*     */     
/* 154 */     for (int n = 0; n < getN(); n++)
/*     */     {
/* 156 */       charbuff.append(getChar(m, n));
/*     */     }
/*     */     
/* 159 */     return charbuff.toString().trim();
/*     */   }
/*     */ 
/*     */   
/*     */   public String contentToString() {
/* 164 */     StringBuffer sb = new StringBuffer();
/* 165 */     sb.append(this.name + " = \n");
/*     */     
/* 167 */     for (int m = 0; m < getM(); m++) {
/*     */       
/* 169 */       sb.append("\t");
/* 170 */       StringBuffer charbuff = new StringBuffer();
/* 171 */       charbuff.append("'");
/* 172 */       for (int n = 0; n < getN(); n++)
/*     */       {
/* 174 */         charbuff.append(getChar(m, n));
/*     */       }
/* 176 */       charbuff.append("'");
/* 177 */       sb.append(charbuff);
/* 178 */       sb.append("\n");
/*     */     } 
/* 180 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLChar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */