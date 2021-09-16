/*     */ package com.boreholeseismic.imageproc;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Robot;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScreenImage
/*     */ {
/*  47 */   private static List<String> types = Arrays.asList(ImageIO.getWriterFileSuffixes());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage createImage(JComponent component) {
/*  58 */     Dimension d = component.getSize();
/*     */     
/*  60 */     if (d.width == 0 || d.height == 0) {
/*     */       
/*  62 */       d = component.getPreferredSize();
/*  63 */       component.setSize(d);
/*     */     } 
/*     */     
/*  66 */     Rectangle region = new Rectangle(0, 0, d.width, d.height);
/*  67 */     return createImage(component, region);
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
/*     */   public static BufferedImage createImage(JComponent component, Rectangle region) {
/*  83 */     if (!component.isDisplayable()) {
/*     */       
/*  85 */       Dimension d = component.getSize();
/*     */       
/*  87 */       if (d.width == 0 || d.height == 0) {
/*     */         
/*  89 */         d = component.getPreferredSize();
/*  90 */         component.setSize(d);
/*     */       } 
/*     */       
/*  93 */       layoutComponent(component);
/*     */     } 
/*     */     
/*  96 */     BufferedImage image = new BufferedImage(region.width, region.height, 1);
/*  97 */     Graphics2D g2d = image.createGraphics();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (!component.isOpaque()) {
/*     */       
/* 104 */       g2d.setColor(component.getBackground());
/* 105 */       g2d.fillRect(region.x, region.y, region.width, region.height);
/*     */     } 
/*     */     
/* 108 */     g2d.translate(-region.x, -region.y);
/* 109 */     component.paint(g2d);
/* 110 */     g2d.dispose();
/* 111 */     return image;
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
/*     */   public static BufferedImage createDesktopImage() throws AWTException, IOException {
/* 125 */     Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
/* 126 */     Rectangle region = new Rectangle(0, 0, d.width, d.height);
/* 127 */     return createImage(region);
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
/*     */   public static BufferedImage createImage(Component component) throws AWTException {
/* 140 */     Point p = new Point(0, 0);
/* 141 */     SwingUtilities.convertPointToScreen(p, component);
/* 142 */     Rectangle region = component.getBounds();
/* 143 */     region.x = p.x;
/* 144 */     region.y = p.y;
/* 145 */     return createImage(region);
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
/*     */   public static BufferedImage createImage(Rectangle region) throws AWTException {
/* 160 */     BufferedImage image = (new Robot()).createScreenCapture(region);
/* 161 */     return image;
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
/*     */   public static void writeImage(BufferedImage image, String fileName) throws IOException {
/* 174 */     if (fileName == null)
/*     */       return; 
/* 176 */     int offset = fileName.lastIndexOf(".");
/*     */     
/* 178 */     if (offset == -1) {
/*     */       
/* 180 */       String message = "file suffix was not specified";
/* 181 */       throw new IOException(message);
/*     */     } 
/*     */     
/* 184 */     String type = fileName.substring(offset + 1);
/*     */     
/* 186 */     if (types.contains(type)) {
/*     */       
/* 188 */       ImageIO.write(image, type, new File(fileName));
/*     */     }
/*     */     else {
/*     */       
/* 192 */       String message = "unknown writer file suffix (" + type + ")";
/* 193 */       throw new IOException(message);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void layoutComponent(Component component) {
/* 199 */     synchronized (component.getTreeLock()) {
/*     */       
/* 201 */       component.doLayout();
/*     */       
/* 203 */       if (component instanceof Container) {
/*     */         byte b; int i; Component[] arrayOfComponent;
/* 205 */         for (i = (arrayOfComponent = ((Container)component).getComponents()).length, b = 0; b < i; ) { Component child = arrayOfComponent[b];
/*     */           
/* 207 */           layoutComponent(child);
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/imageproc/ScreenImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */