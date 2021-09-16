/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Formatter;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CleanFormatter
/*     */   extends Formatter
/*     */ {
/*  29 */   private static String s_prefix = "";
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final String NL = System.getProperty("line.separator");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setWarningPrefix(String prefix) {
/*  45 */     s_prefix = prefix;
/*     */   }
/*     */   
/*  48 */   private Level lastLevel = Level.INFO;
/*     */ 
/*     */   
/*     */   public synchronized String format(LogRecord record) {
/*  52 */     String message = formatMessage(record);
/*  53 */     if (message == null) return null; 
/*  54 */     if (message.length() == 0) return message;
/*     */ 
/*     */     
/*  57 */     message = Localize.filter(message, record.getResourceBundle());
/*     */     
/*  59 */     if (message.endsWith("\\")) {
/*  60 */       message = message.substring(0, message.length() - 1);
/*  61 */     } else if (!message.matches("^\\s*(" + NL + ")?$")) {
/*     */       
/*  63 */       message = message + NL;
/*     */     } 
/*  65 */     Level level = record.getLevel();
/*  66 */     if (!level.equals(Level.INFO))
/*     */     {
/*  68 */       if (level.equals(Level.WARNING)) {
/*  69 */         if (!message.contains("WARNING")) {
/*  70 */           message = prependToLines(s_prefix + level + ": ", message);
/*     */         } else {
/*  72 */           message = s_prefix + message;
/*     */         } 
/*  74 */       } else if (level.equals(Level.SEVERE)) {
/*  75 */         message = prependToLines(level + ": ", message);
/*  76 */         if (!this.lastLevel.equals(Level.SEVERE))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*  81 */           message = s_prefix + "**** SEVERE WARNING **** (" + record.getSourceClassName() + "." + record.getSourceMethodName() + " " + getTimeStamp(record.getMillis()) + " #" + record.getThreadID() + ")" + NL + message;
/*     */         }
/*  83 */       } else if (level.equals(Level.FINE) || level
/*  84 */         .equals(Level.FINER) || level
/*  85 */         .equals(Level.FINEST)) {
/*  86 */         String shortPackage = record.getLoggerName();
/*  87 */         int index = shortPackage.lastIndexOf('.');
/*  88 */         if (index > 0) shortPackage = shortPackage.substring(index + 1); 
/*  89 */         message = prependToLines(level + " " + shortPackage + ": ", message);
/*     */       } else {
/*     */         
/*  92 */         message = prependToLines(level + " " + s_time_formatter.format(new Date()) + " " + record
/*  93 */             .getLoggerName() + ": ", message);
/*     */       } 
/*     */     }
/*  96 */     if (record.getThrown() != null) {
/*  97 */       StringWriter sw = new StringWriter();
/*  98 */       PrintWriter pw = new PrintWriter(sw);
/*  99 */       record.getThrown().printStackTrace(pw);
/* 100 */       pw.close();
/* 101 */       message = message + sw.toString();
/*     */     } 
/* 103 */     this.lastLevel = level;
/* 104 */     return message;
/*     */   }
/* 106 */   private static DateFormat s_time_formatter = new SimpleDateFormat("HH:mm:ss.SSS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String prependToLines(String prepend, String lines) {
/* 116 */     if (lines == null) return null; 
/* 117 */     if (prepend == null) return lines; 
/* 118 */     StringBuilder result = new StringBuilder();
/* 119 */     boolean hasFinalNL = lines.endsWith(NL);
/* 120 */     StringTokenizer divided = new StringTokenizer(lines, NL);
/* 121 */     while (divided.hasMoreTokens()) {
/* 122 */       result.append(prepend);
/* 123 */       result.append(divided.nextToken());
/* 124 */       if (divided.hasMoreTokens() || hasFinalNL) result.append(NL); 
/*     */     } 
/* 126 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getTimeStamp(Date date) {
/* 135 */     synchronized (TIMESTAMP_FORMAT) {
/* 136 */       return TIMESTAMP_FORMAT.format(date);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getTimeStamp(long date) {
/* 146 */     return getTimeStamp(new Date(date));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/CleanFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */