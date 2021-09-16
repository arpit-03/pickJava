/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackableRuntimeException
/*     */   extends RuntimeException
/*     */ {
/*     */   private Exception parent;
/*     */   
/*     */   public StackableRuntimeException() {}
/*     */   
/*     */   public StackableRuntimeException(String message, Exception ex) {
/*  78 */     super(message);
/*  79 */     this.parent = ex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackableRuntimeException(String message) {
/*  88 */     super(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getParent() {
/*  97 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintStream stream) {
/* 106 */     super.printStackTrace(stream);
/* 107 */     if (getParent() != null) {
/* 108 */       stream.println("ParentException: ");
/* 109 */       getParent().printStackTrace(stream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintWriter writer) {
/* 119 */     super.printStackTrace(writer);
/* 120 */     if (getParent() != null) {
/* 121 */       writer.println("ParentException: ");
/* 122 */       getParent().printStackTrace(writer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/StackableRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */