/*     */ package us.hebi.matlab.mat.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndentingAppendable
/*     */   implements Appendable
/*     */ {
/*     */   private String indentString;
/*     */   private int numIndents;
/*     */   private int maxLineWidth;
/*     */   private int currentLine;
/*     */   private String overflowString;
/*     */   private final Appendable appendable;
/*     */   
/*     */   public IndentingAppendable(Appendable appendable) {
/* 129 */     this.indentString = "  ";
/* 130 */     this.numIndents = 0;
/* 131 */     this.maxLineWidth = 160;
/* 132 */     this.currentLine = 0;
/* 133 */     this.overflowString = "...";
/*     */     this.appendable = appendable;
/*     */   }
/*     */   
/*     */   public String getIndentString() {
/*     */     return this.indentString;
/*     */   }
/*     */   
/*     */   public void setIndentString(String indentString) {
/*     */     this.indentString = indentString;
/*     */   }
/*     */   
/*     */   public int getMaxLineWidth() {
/*     */     return this.maxLineWidth;
/*     */   }
/*     */   
/*     */   public void setMaxLineWidth(int maxLineWidth) {
/*     */     this.maxLineWidth = maxLineWidth;
/*     */   }
/*     */   
/*     */   public String getOverflowString() {
/*     */     return this.overflowString;
/*     */   }
/*     */   
/*     */   public void setOverflowString(String overflowString) {
/*     */     this.overflowString = overflowString;
/*     */   }
/*     */   
/*     */   public IndentingAppendable indent() {
/*     */     this.numIndents++;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public IndentingAppendable unindent() {
/*     */     this.numIndents--;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public IndentingAppendable append(Object object) {
/*     */     return append(String.valueOf(object));
/*     */   }
/*     */   
/*     */   public IndentingAppendable append(CharSequence csq) {
/*     */     append(csq, 0, csq.length());
/*     */     return this;
/*     */   }
/*     */   
/*     */   public IndentingAppendable append(CharSequence csq, int start, int end) {
/*     */     for (int i = start; i < end; i++)
/*     */       append(csq.charAt(i)); 
/*     */     return this;
/*     */   }
/*     */   
/*     */   public IndentingAppendable append(char c) {
/*     */     try {
/*     */       return append0(c);
/*     */     } catch (IOException ioe) {
/*     */       throw new RuntimeException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   private IndentingAppendable append0(char c) throws IOException {
/*     */     if (c == '\n') {
/*     */       this.appendable.append(c);
/*     */       this.currentLine = 0;
/*     */       for (int i = 0; i < this.numIndents; i++)
/*     */         append(this.indentString); 
/*     */       return this;
/*     */     } 
/*     */     if (this.currentLine >= this.maxLineWidth)
/*     */       return this; 
/*     */     if (this.currentLine > this.maxLineWidth - this.overflowString.length()) {
/*     */       this.currentLine += this.overflowString.length();
/*     */       this.appendable.append(this.overflowString);
/*     */       return this;
/*     */     } 
/*     */     this.currentLine++;
/*     */     this.appendable.append(c);
/*     */     return this;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/IndentingAppendable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */