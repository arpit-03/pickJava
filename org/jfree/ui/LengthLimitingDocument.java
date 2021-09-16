/*     */ package org.jfree.ui;
/*     */ 
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.PlainDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LengthLimitingDocument
/*     */   extends PlainDocument
/*     */ {
/*     */   private int maxlen;
/*     */   
/*     */   public LengthLimitingDocument() {
/*  65 */     this(-1);
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
/*     */   public LengthLimitingDocument(int maxlen) {
/*  77 */     this.maxlen = maxlen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxLength(int maxlen) {
/*  87 */     this.maxlen = maxlen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLength() {
/*  95 */     return this.maxlen;
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
/*     */   public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
/* 109 */     if (str == null) {
/*     */       return;
/*     */     }
/*     */     
/* 113 */     if (this.maxlen < 0) {
/* 114 */       super.insertString(offs, str, a);
/*     */     }
/*     */     
/* 117 */     char[] numeric = str.toCharArray();
/* 118 */     StringBuffer b = new StringBuffer();
/* 119 */     b.append(numeric, 0, Math.min(this.maxlen, numeric.length));
/* 120 */     super.insertString(offs, b.toString(), a);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/LengthLimitingDocument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */