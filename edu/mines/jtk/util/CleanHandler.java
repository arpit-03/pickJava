/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogManager;
/*     */ import java.util.logging.LogRecord;
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
/*     */ public class CleanHandler
/*     */   extends Handler
/*     */ {
/*  33 */   private static Collection<PrintStream> s_printStreams = new LinkedList<>();
/*     */ 
/*     */   
/*     */   private static boolean s_setDefault = false;
/*     */ 
/*     */   
/*     */   public CleanHandler() {
/*  40 */     setFormatter(new CleanFormatter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addGlobalLogFile(String fileName) throws FileNotFoundException {
/*  50 */     s_printStreams.add(new PrintStream(new FileOutputStream(fileName), true));
/*     */   }
/*     */   
/*     */   public void publish(LogRecord record) {
/*  54 */     if (record == null || !isLoggable(record))
/*  55 */       return;  String message = getFormatter().format(record);
/*  56 */     if (message == null)
/*  57 */       return;  if (record.getLevel().intValue() > Level.INFO.intValue()) {
/*  58 */       System.err.print(message);
/*  59 */       System.err.flush();
/*     */     } else {
/*  61 */       System.out.print(message);
/*  62 */       System.out.flush();
/*     */     } 
/*  64 */     for (PrintStream ps : s_printStreams) {
/*  65 */       ps.print(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */   
/*     */   public void flush() {}
/*     */   
/*     */   public static void testLogger() {
/*  75 */     setDefaultHandler();
/*     */     
/*  77 */     assert null != CleanHandler.class
/*  78 */       .getResource("CleanHandler.properties");
/*     */     
/*  80 */     assert null != 
/*  81 */       ResourceBundle.getBundle("edu.mines.jtk.util.CleanHandler") : "can't find rb";
/*     */     
/*  83 */     Logger logger = Logger.getLogger("edu.mines.jtk.util", "edu.mines.jtk.util.CleanHandler");
/*     */ 
/*     */     
/*  86 */     logger.severe("test a severe");
/*  87 */     logger.warning("test a warning");
/*  88 */     logger.info("test an info");
/*  89 */     logger.info("test a\\");
/*  90 */     logger.info(" continued info");
/*  91 */     logger.config("test an config");
/*  92 */     logger.fine("test a fine");
/*  93 */     logger.finer("test a finer");
/*  94 */     logger.finest("test a finest");
/*  95 */     logger.info("testmessage");
/*  96 */     logger.info("Try this:>>${testmessage}<<");
/*  97 */     logger.info("Try this:>>${testmessage}<< >>${testmessage}<<");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 103 */     testLogger();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultHandler() {
/* 111 */     synchronized (CleanHandler.class) {
/* 112 */       if (s_setDefault)
/*     */         return; 
/* 114 */       if (System.getProperties()
/* 115 */         .getProperty("java.util.logging.config.file") == null && 
/* 116 */         System.getProperties()
/* 117 */         .getProperty("java.util.logging.config.class") == null) {
/* 118 */         overrideExistingHandlers(Level.INFO);
/*     */       }
/* 120 */       s_setDefault = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void overrideExistingHandlers(Level level) {
/*     */     try {
/* 129 */       LogManager.getLogManager()
/* 130 */         .readConfiguration(new ByteArrayInputStream(("handlers=edu.mines.jtk.util.CleanHandler\n.level=" + level
/* 131 */             .getName() + "\n")
/* 132 */             .getBytes()));
/* 133 */     } catch (IOException e) {
/* 134 */       throw new IllegalStateException("This should never fail with I/O from a byte array" + e
/* 135 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/CleanHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */