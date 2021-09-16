/*     */ package edu.mines.jtk.bench;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AwtBench
/*     */ {
/*     */   public static void main(String[] args) {
/*  34 */     testImage();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void testImage() {
/*  39 */     Dimension panelSize = new Dimension(600, 600);
/*  40 */     ImagePanel panel = new ImagePanel();
/*  41 */     panel.setSize(panelSize);
/*  42 */     panel.setPreferredSize(panelSize);
/*  43 */     panel.setBackground(Color.WHITE);
/*  44 */     BufferedImage image = panel.paintToImage(300.0D, 3.0D);
/*  45 */     writeToPng(image, "ImageAWT.png");
/*  46 */     JFrame frame = new JFrame();
/*  47 */     frame.setDefaultCloseOperation(3);
/*  48 */     frame.add(panel);
/*  49 */     frame.pack();
/*  50 */     frame.setVisible(true);
/*     */   }
/*     */   private static class ImagePanel extends JPanel { private static final long serialVersionUID = 1L;
/*     */     
/*     */     public void paintComponent(Graphics g) {
/*  55 */       super.paintComponent(g);
/*  56 */       Graphics2D g2d = (Graphics2D)g;
/*  57 */       int width = getWidth();
/*  58 */       int height = getHeight();
/*  59 */       g2d.setColor(Color.RED); int i;
/*  60 */       for (i = 0; i < 3; i++)
/*  61 */         g2d.drawLine(0, 0, i * (width - 1) / 2, height - 1); 
/*  62 */       for (i = 0; i < 2; i++)
/*  63 */         g2d.drawLine(0, 0, width - 1, i * (height - 1) / 2); 
/*  64 */       g2d.setColor(Color.BLACK);
/*  65 */       Font font = new Font("SansSerif", 0, 18);
/*  66 */       g2d.setFont(font);
/*  67 */       FontMetrics fm = g2d.getFontMetrics();
/*  68 */       int fontAscent = fm.getAscent();
/*  69 */       int fontHeight = fm.getHeight();
/*  70 */       int x = width / 2;
/*  71 */       int y = height / 2;
/*  72 */       g2d.drawLine(x, 0, x, y);
/*  73 */       y += fontAscent;
/*  74 */       int sw = fm.stringWidth("Goodbye");
/*  75 */       g2d.drawString("Goodbye", x - sw / 2, y);
/*  76 */       y += fontHeight;
/*  77 */       sw = fm.stringWidth("Hello");
/*  78 */       g2d.drawString("Hello", x - sw / 2, y);
/*  79 */       y += fontHeight;
/*  80 */       String message = "abcdefghijklmnopqrstuvwxyz0123456789";
/*  81 */       sw = fm.stringWidth(message);
/*  82 */       g2d.drawString(message, x - sw / 2, y);
/*     */     } private ImagePanel() {}
/*     */     public BufferedImage paintToImage(double dpi, double winches) {
/*  85 */       int wpixels = getWidth();
/*  86 */       int hpixels = getHeight();
/*  87 */       double scale = dpi * winches / wpixels;
/*  88 */       int wimage = (int)(scale * (wpixels - 1) + 1.5D);
/*  89 */       int himage = (int)(scale * (hpixels - 1) + 1.5D);
/*  90 */       int type = 1;
/*  91 */       BufferedImage bi = new BufferedImage(wimage, himage, type);
/*  92 */       Graphics2D g2d = (Graphics2D)bi.getGraphics();
/*  93 */       g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */ 
/*     */       
/*  96 */       Color fg = getForeground();
/*  97 */       g2d.setColor(getBackground());
/*  98 */       g2d.fillRect(0, 0, wimage, himage);
/*  99 */       g2d.setColor(fg);
/* 100 */       g2d.scale(scale, scale);
/* 101 */       paintComponent(g2d);
/* 102 */       g2d.dispose();
/* 103 */       return bi;
/*     */     } }
/*     */   
/*     */   private static void writeToPng(BufferedImage image, String fileName) {
/*     */     try {
/* 108 */       File file = new File(fileName);
/* 109 */       ImageIO.write(image, "png", file);
/* 110 */     } catch (IOException ioe) {
/* 111 */       throw new RuntimeException(ioe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/AwtBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */