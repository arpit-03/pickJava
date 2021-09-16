/*     */ package org.jfree.chart.servlet;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServletUtilities
/*     */ {
/*  85 */   private static String tempFilePrefix = "jfreechart-";
/*     */ 
/*     */   
/*  88 */   private static String tempOneTimeFilePrefix = "jfreechart-onetime-";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTempFilePrefix() {
/*  96 */     return tempFilePrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setTempFilePrefix(String prefix) {
/* 105 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/* 106 */     tempFilePrefix = prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTempOneTimeFilePrefix() {
/* 116 */     return tempOneTimeFilePrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setTempOneTimeFilePrefix(String prefix) {
/* 126 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/* 127 */     tempOneTimeFilePrefix = prefix;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String saveChartAsPNG(JFreeChart chart, int width, int height, HttpSession session) throws IOException {
/* 148 */     return saveChartAsPNG(chart, width, height, null, session);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String saveChartAsPNG(JFreeChart chart, int width, int height, ChartRenderingInfo info, HttpSession session) throws IOException {
/* 175 */     ParamChecks.nullNotPermitted(chart, "chart");
/* 176 */     createTempDir();
/* 177 */     String prefix = tempFilePrefix;
/* 178 */     if (session == null) {
/* 179 */       prefix = tempOneTimeFilePrefix;
/*     */     }
/* 181 */     File tempFile = File.createTempFile(prefix, ".png", new File(
/* 182 */           System.getProperty("java.io.tmpdir")));
/* 183 */     ChartUtilities.saveChartAsPNG(tempFile, chart, width, height, info);
/* 184 */     if (session != null) {
/* 185 */       registerChartForDeletion(tempFile, session);
/*     */     }
/* 187 */     return tempFile.getName();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String saveChartAsJPEG(JFreeChart chart, int width, int height, HttpSession session) throws IOException {
/* 215 */     return saveChartAsJPEG(chart, width, height, null, session);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String saveChartAsJPEG(JFreeChart chart, int width, int height, ChartRenderingInfo info, HttpSession session) throws IOException {
/* 247 */     ParamChecks.nullNotPermitted(chart, "chart");
/* 248 */     createTempDir();
/* 249 */     String prefix = tempFilePrefix;
/* 250 */     if (session == null) {
/* 251 */       prefix = tempOneTimeFilePrefix;
/*     */     }
/* 253 */     File tempFile = File.createTempFile(prefix, ".jpeg", new File(
/* 254 */           System.getProperty("java.io.tmpdir")));
/* 255 */     ChartUtilities.saveChartAsJPEG(tempFile, chart, width, height, info);
/* 256 */     if (session != null) {
/* 257 */       registerChartForDeletion(tempFile, session);
/*     */     }
/* 259 */     return tempFile.getName();
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
/*     */   protected static void createTempDir() {
/* 273 */     String tempDirName = System.getProperty("java.io.tmpdir");
/* 274 */     if (tempDirName == null) {
/* 275 */       throw new RuntimeException("Temporary directory system property (java.io.tmpdir) is null.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 280 */     File tempDir = new File(tempDirName);
/* 281 */     if (!tempDir.exists()) {
/* 282 */       tempDir.mkdirs();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void registerChartForDeletion(File tempFile, HttpSession session) {
/* 298 */     if (session != null) {
/*     */       
/* 300 */       ChartDeleter chartDeleter = (ChartDeleter)session.getAttribute("JFreeChart_Deleter");
/* 301 */       if (chartDeleter == null) {
/* 302 */         chartDeleter = new ChartDeleter();
/* 303 */         session.setAttribute("JFreeChart_Deleter", chartDeleter);
/*     */       } 
/* 305 */       chartDeleter.addChart(tempFile.getName());
/*     */     } else {
/*     */       
/* 308 */       System.out.println("Session is null - chart will not be deleted");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendTempFile(String filename, HttpServletResponse response) throws IOException {
/* 324 */     File file = new File(System.getProperty("java.io.tmpdir"), filename);
/* 325 */     sendTempFile(file, response);
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
/*     */   public static void sendTempFile(File file, HttpServletResponse response) throws IOException {
/* 339 */     String mimeType = null;
/* 340 */     String filename = file.getName();
/* 341 */     if (filename.length() > 5) {
/* 342 */       if (filename.substring(filename.length() - 5, filename
/* 343 */           .length()).equals(".jpeg")) {
/* 344 */         mimeType = "image/jpeg";
/*     */       }
/* 346 */       else if (filename.substring(filename.length() - 4, filename
/* 347 */           .length()).equals(".png")) {
/* 348 */         mimeType = "image/png";
/*     */       } 
/*     */     }
/* 351 */     sendTempFile(file, response, mimeType);
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
/*     */   public static void sendTempFile(File file, HttpServletResponse response, String mimeType) throws IOException {
/* 366 */     if (file.exists()) {
/* 367 */       BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
/*     */ 
/*     */ 
/*     */       
/* 371 */       if (mimeType != null) {
/* 372 */         response.setHeader("Content-Type", mimeType);
/*     */       }
/* 374 */       response.setHeader("Content-Length", String.valueOf(file.length()));
/* 375 */       SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
/*     */       
/* 377 */       sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 378 */       response.setHeader("Last-Modified", sdf
/* 379 */           .format(new Date(file.lastModified())));
/*     */ 
/*     */       
/* 382 */       BufferedOutputStream bos = new BufferedOutputStream((OutputStream)response.getOutputStream());
/* 383 */       byte[] input = new byte[1024];
/* 384 */       boolean eof = false;
/* 385 */       while (!eof) {
/* 386 */         int length = bis.read(input);
/* 387 */         if (length == -1) {
/* 388 */           eof = true;
/*     */           continue;
/*     */         } 
/* 391 */         bos.write(input, 0, length);
/*     */       } 
/*     */       
/* 394 */       bos.flush();
/* 395 */       bis.close();
/* 396 */       bos.close();
/*     */     } else {
/*     */       
/* 399 */       throw new FileNotFoundException(file.getAbsolutePath());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String searchReplace(String inputString, String searchString, String replaceString) {
/* 417 */     int i = inputString.indexOf(searchString);
/* 418 */     if (i == -1) {
/* 419 */       return inputString;
/*     */     }
/*     */     
/* 422 */     String r = "";
/* 423 */     r = r + inputString.substring(0, i) + replaceString;
/* 424 */     if (i + searchString.length() < inputString.length()) {
/* 425 */       r = r + searchReplace(inputString.substring(i + searchString.length()), searchString, replaceString);
/*     */     }
/*     */ 
/*     */     
/* 429 */     return r;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/servlet/ServletUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */