/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.jfree.ui.Drawable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExportUtils
/*     */ {
/*     */   public static boolean isJFreeSVGAvailable() {
/*  74 */     Class<?> svgClass = null;
/*     */     try {
/*  76 */       svgClass = Class.forName("org.jfree.graphics2d.svg.SVGGraphics2D");
/*  77 */     } catch (ClassNotFoundException e) {}
/*     */ 
/*     */     
/*  80 */     return (svgClass != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOrsonPDFAvailable() {
/*  91 */     Class<?> pdfDocumentClass = null;
/*     */     try {
/*  93 */       pdfDocumentClass = Class.forName("com.orsonpdf.PDFDocument");
/*  94 */     } catch (ClassNotFoundException e) {}
/*     */ 
/*     */     
/*  97 */     return (pdfDocumentClass != null);
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
/*     */   public static void writeAsSVG(Drawable drawable, int w, int h, File file) {
/* 113 */     if (!isJFreeSVGAvailable()) {
/* 114 */       throw new IllegalStateException("JFreeSVG is not present on the classpath.");
/*     */     }
/*     */     
/* 117 */     ParamChecks.nullNotPermitted(drawable, "drawable");
/* 118 */     ParamChecks.nullNotPermitted(file, "file");
/*     */     try {
/* 120 */       Class<?> svg2Class = Class.forName("org.jfree.graphics2d.svg.SVGGraphics2D");
/*     */       
/* 122 */       Constructor<?> c1 = svg2Class.getConstructor(new Class[] { int.class, int.class });
/* 123 */       Graphics2D svg2 = (Graphics2D)c1.newInstance(new Object[] { Integer.valueOf(w), Integer.valueOf(h) });
/* 124 */       Rectangle2D drawArea = new Rectangle2D.Double(0.0D, 0.0D, w, h);
/* 125 */       drawable.draw(svg2, drawArea);
/* 126 */       Class<?> svgUtilsClass = Class.forName("org.jfree.graphics2d.svg.SVGUtils");
/*     */       
/* 128 */       Method m1 = svg2Class.getMethod("getSVGElement", (Class[])null);
/* 129 */       String element = (String)m1.invoke(svg2, (Object[])null);
/* 130 */       Method m2 = svgUtilsClass.getMethod("writeToSVG", new Class[] { File.class, String.class });
/*     */       
/* 132 */       m2.invoke(svgUtilsClass, new Object[] { file, element });
/* 133 */     } catch (ClassNotFoundException ex) {
/* 134 */       throw new RuntimeException(ex);
/* 135 */     } catch (InstantiationException ex) {
/* 136 */       throw new RuntimeException(ex);
/* 137 */     } catch (IllegalAccessException ex) {
/* 138 */       throw new RuntimeException(ex);
/* 139 */     } catch (NoSuchMethodException ex) {
/* 140 */       throw new RuntimeException(ex);
/* 141 */     } catch (SecurityException ex) {
/* 142 */       throw new RuntimeException(ex);
/* 143 */     } catch (IllegalArgumentException ex) {
/* 144 */       throw new RuntimeException(ex);
/* 145 */     } catch (InvocationTargetException ex) {
/* 146 */       throw new RuntimeException(ex);
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
/*     */   public static final void writeAsPDF(Drawable drawable, int w, int h, File file) {
/* 163 */     if (!isOrsonPDFAvailable()) {
/* 164 */       throw new IllegalStateException("OrsonPDF is not present on the classpath.");
/*     */     }
/*     */     
/* 167 */     ParamChecks.nullNotPermitted(drawable, "drawable");
/* 168 */     ParamChecks.nullNotPermitted(file, "file");
/*     */     try {
/* 170 */       Class<?> pdfDocClass = Class.forName("com.orsonpdf.PDFDocument");
/* 171 */       Object pdfDoc = pdfDocClass.newInstance();
/* 172 */       Method m = pdfDocClass.getMethod("createPage", new Class[] { Rectangle2D.class });
/* 173 */       Rectangle2D rect = new Rectangle(w, h);
/* 174 */       Object page = m.invoke(pdfDoc, new Object[] { rect });
/* 175 */       Method m2 = page.getClass().getMethod("getGraphics2D", new Class[0]);
/* 176 */       Graphics2D g2 = (Graphics2D)m2.invoke(page, new Object[0]);
/* 177 */       Rectangle2D drawArea = new Rectangle2D.Double(0.0D, 0.0D, w, h);
/* 178 */       drawable.draw(g2, drawArea);
/* 179 */       Method m3 = pdfDocClass.getMethod("writeToFile", new Class[] { File.class });
/* 180 */       m3.invoke(pdfDoc, new Object[] { file });
/* 181 */     } catch (ClassNotFoundException ex) {
/* 182 */       throw new RuntimeException(ex);
/* 183 */     } catch (InstantiationException ex) {
/* 184 */       throw new RuntimeException(ex);
/* 185 */     } catch (IllegalAccessException ex) {
/* 186 */       throw new RuntimeException(ex);
/* 187 */     } catch (NoSuchMethodException ex) {
/* 188 */       throw new RuntimeException(ex);
/* 189 */     } catch (SecurityException ex) {
/* 190 */       throw new RuntimeException(ex);
/* 191 */     } catch (IllegalArgumentException ex) {
/* 192 */       throw new RuntimeException(ex);
/* 193 */     } catch (InvocationTargetException ex) {
/* 194 */       throw new RuntimeException(ex);
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
/*     */   public static void writeAsPNG(Drawable drawable, int w, int h, File file) throws FileNotFoundException, IOException {
/* 211 */     BufferedImage image = new BufferedImage(w, h, 2);
/*     */     
/* 213 */     Graphics2D g2 = image.createGraphics();
/* 214 */     drawable.draw(g2, new Rectangle(w, h));
/* 215 */     OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
/*     */     try {
/* 217 */       ImageIO.write(image, "png", out);
/*     */     } finally {
/*     */       
/* 220 */       out.close();
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
/*     */   public static void writeAsJPEG(Drawable drawable, int w, int h, File file) throws FileNotFoundException, IOException {
/* 237 */     BufferedImage image = new BufferedImage(w, h, 1);
/*     */     
/* 239 */     Graphics2D g2 = image.createGraphics();
/* 240 */     drawable.draw(g2, new Rectangle(w, h));
/* 241 */     OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
/*     */     try {
/* 243 */       ImageIO.write(image, "jpg", out);
/*     */     } finally {
/*     */       
/* 246 */       out.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/ExportUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */