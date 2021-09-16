/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoggerStream
/*     */   extends PrintStream
/*     */ {
/*  35 */   private Level _level = null;
/*  36 */   private Logger _logger = null;
/*  37 */   private ByteArrayOutputStream _baos = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerStream(Logger logger, Level level) {
/*  46 */     super(new ByteArrayOutputStream(), true);
/*  47 */     this._baos = (ByteArrayOutputStream)this.out;
/*  48 */     this._logger = logger;
/*  49 */     this._level = level;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/*  54 */     super.flush();
/*  55 */     if (this._baos.size() == 0)
/*  56 */       return;  String out1 = this._baos.toString();
/*     */     
/*  58 */     this._logger.log(this._level, out1);
/*     */     
/*  60 */     this._baos.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void println() {
/*  65 */     flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void println(Object x) {
/*  70 */     print(x);
/*  71 */     flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void println(String x) {
/*  76 */     print(x);
/*  77 */     flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/*  82 */     flush();
/*  83 */     super.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean checkError() {
/*  88 */     flush();
/*  89 */     return super.checkError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  96 */     Logger logger = Logger.getLogger("edu.mines.jtk.util");
/*  97 */     PrintStream psInfo = new LoggerStream(logger, Level.INFO);
/*     */     
/*  99 */     psInfo.print(3.0D);
/* 100 */     psInfo.println("*3.=9.");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     psInfo.print(3.0D);
/* 106 */     psInfo.flush();
/* 107 */     psInfo.println("*3.=9.");
/* 108 */     psInfo.println();
/* 109 */     psInfo.print("x");
/* 110 */     psInfo.close();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/LoggerStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */