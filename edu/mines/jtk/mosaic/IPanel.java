/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import javax.imageio.IIOImage;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.ImageWriteParam;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import javax.imageio.stream.ImageOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public void paintToRect(Graphics2D g2d, int x, int y, int w, int h) {
/*  77 */     g2d = createGraphics(g2d, x, y, w, h);
/*     */ 
/*     */     
/*  80 */     double ws = w / getWidth();
/*  81 */     double hs = h / getHeight();
/*  82 */     int nc = getComponentCount();
/*  83 */     for (int ic = 0; ic < nc; ic++) {
/*  84 */       Component c = getComponent(ic);
/*  85 */       int xc = c.getX();
/*  86 */       int yc = c.getY();
/*  87 */       int wc = c.getWidth();
/*  88 */       int hc = c.getHeight();
/*  89 */       xc = (int)Math.round(xc * ws);
/*  90 */       yc = (int)Math.round(yc * hs);
/*  91 */       wc = (int)Math.round(wc * ws);
/*  92 */       hc = (int)Math.round(hc * hs);
/*  93 */       if (c instanceof IPanel) {
/*  94 */         IPanel ip = (IPanel)c;
/*  95 */         ip.paintToRect(g2d, xc, yc, wc, hc);
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintToImage(BufferedImage image) {
/* 107 */     Graphics2D g2d = image.createGraphics();
/*     */ 
/*     */     
/* 110 */     g2d.setColor(getBackground());
/* 111 */     g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
/* 112 */     g2d.setColor(getForeground());
/* 113 */     g2d.setFont(getFont());
/*     */ 
/*     */     
/* 116 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     paintToRect(g2d, 0, 0, image.getWidth(), image.getHeight());
/*     */     
/* 123 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage paintToImage(int width) {
/* 134 */     int wpanel = getWidth();
/* 135 */     int hpanel = getHeight();
/* 136 */     double scale = width / wpanel;
/* 137 */     int wimage = (int)(scale * wpanel + 0.5D);
/* 138 */     int himage = (int)(scale * hpanel + 0.5D);
/* 139 */     int type = 1;
/* 140 */     BufferedImage image = new BufferedImage(wimage, himage, type);
/* 141 */     paintToImage(image);
/* 142 */     return image;
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
/*     */   public void paintToPng(double dpi, double win, String fileName) throws IOException {
/* 157 */     BufferedImage image = paintToImage((int)Math.ceil(dpi * win));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     Iterator<ImageWriter> i = ImageIO.getImageWritersBySuffix("png");
/* 163 */     if (!i.hasNext())
/* 164 */       throw new IOException("cannot get a PNG image writer"); 
/* 165 */     ImageWriter iw = i.next();
/* 166 */     FileOutputStream fos = new FileOutputStream(fileName);
/* 167 */     ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
/* 168 */     iw.setOutput(ios);
/* 169 */     ImageWriteParam iwp = iw.getDefaultWriteParam();
/* 170 */     ImageTypeSpecifier its = new ImageTypeSpecifier(image);
/* 171 */     IIOMetadata imd = iw.getDefaultImageMetadata(its, iwp);
/* 172 */     String format = "javax_imageio_png_1.0";
/* 173 */     IIOMetadataNode tree = (IIOMetadataNode)imd.getAsTree(format);
/* 174 */     IIOMetadataNode node = new IIOMetadataNode("pHYs");
/* 175 */     String dpm = Integer.toString((int)Math.ceil(dpi / 0.0254D));
/* 176 */     node.setAttribute("pixelsPerUnitXAxis", dpm);
/* 177 */     node.setAttribute("pixelsPerUnitYAxis", dpm);
/* 178 */     node.setAttribute("unitSpecifier", "meter");
/* 179 */     tree.appendChild(node);
/* 180 */     imd.setFromTree(format, tree);
/* 181 */     iw.write(new IIOImage(image, null, imd));
/* 182 */     ios.flush();
/* 183 */     ios.close();
/* 184 */     fos.flush();
/* 185 */     fos.close();
/* 186 */     iw.dispose();
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
/*     */   protected double computeScale(int w, int h) {
/* 201 */     double wscale = w / getWidth();
/* 202 */     double hscale = h / getHeight();
/* 203 */     double scale = Math.min(wscale, hscale);
/* 204 */     return scale;
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
/*     */   protected Graphics2D createGraphics(Graphics2D g2d, int x, int y, int w, int h) {
/* 235 */     g2d = (Graphics2D)g2d.create();
/*     */ 
/*     */     
/* 238 */     double scale = computeScale(w, h);
/*     */ 
/*     */     
/* 241 */     Rectangle clipRect = g2d.getClipBounds();
/* 242 */     Rectangle g2dRect = new Rectangle(x, y, w, h);
/* 243 */     g2d.setClip((clipRect == null) ? g2dRect : clipRect.intersection(g2dRect));
/*     */ 
/*     */     
/* 246 */     g2d.translate(x, y);
/*     */ 
/*     */     
/* 249 */     float lineWidth = (float)scale;
/* 250 */     g2d.setStroke(new BasicStroke(lineWidth));
/*     */ 
/*     */     
/* 253 */     Font font = getFont();
/* 254 */     float fontSize = (float)scale * font.getSize2D();
/* 255 */     g2d.setFont(font.deriveFont(fontSize));
/*     */     
/* 257 */     return g2d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getLineWidth(Graphics2D g2d) {
/* 266 */     float lineWidth = 1.0F;
/* 267 */     Stroke stroke = g2d.getStroke();
/* 268 */     if (stroke instanceof BasicStroke) {
/* 269 */       BasicStroke bs = (BasicStroke)stroke;
/* 270 */       lineWidth = bs.getLineWidth();
/*     */     } 
/* 272 */     return lineWidth;
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
/*     */   protected void scaleLineWidth(Graphics2D g2d, double scale) {
/* 285 */     float lineWidth = getLineWidth(g2d);
/* 286 */     lineWidth = (float)(lineWidth * scale);
/* 287 */     g2d.setStroke(new BasicStroke(lineWidth));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/IPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */