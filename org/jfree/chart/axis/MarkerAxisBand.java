/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.font.LineMetrics;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.plot.IntervalMarker;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MarkerAxisBand
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1729482413886398919L;
/*     */   private NumberAxis axis;
/*     */   private double topOuterGap;
/*     */   private double topInnerGap;
/*     */   private double bottomOuterGap;
/*     */   private double bottomInnerGap;
/*     */   private Font font;
/*     */   private List markers;
/*     */   
/*     */   public MarkerAxisBand(NumberAxis axis, double topOuterGap, double topInnerGap, double bottomOuterGap, double bottomInnerGap, Font font) {
/* 110 */     this.axis = axis;
/* 111 */     this.topOuterGap = topOuterGap;
/* 112 */     this.topInnerGap = topInnerGap;
/* 113 */     this.bottomOuterGap = bottomOuterGap;
/* 114 */     this.bottomInnerGap = bottomInnerGap;
/* 115 */     this.font = font;
/* 116 */     this.markers = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMarker(IntervalMarker marker) {
/* 125 */     this.markers.add(marker);
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
/*     */   public double getHeight(Graphics2D g2) {
/* 137 */     double result = 0.0D;
/* 138 */     if (this.markers.size() > 0) {
/* 139 */       LineMetrics metrics = this.font.getLineMetrics("123g", g2
/* 140 */           .getFontRenderContext());
/*     */       
/* 142 */       result = this.topOuterGap + this.topInnerGap + metrics.getHeight() + this.bottomInnerGap + this.bottomOuterGap;
/*     */     } 
/*     */     
/* 145 */     return result;
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
/*     */   private void drawStringInRect(Graphics2D g2, Rectangle2D bounds, Font font, String text) {
/* 160 */     g2.setFont(font);
/* 161 */     FontMetrics fm = g2.getFontMetrics(font);
/* 162 */     Rectangle2D r = TextUtilities.getTextBounds(text, g2, fm);
/* 163 */     double x = bounds.getX();
/* 164 */     if (r.getWidth() < bounds.getWidth()) {
/* 165 */       x += (bounds.getWidth() - r.getWidth()) / 2.0D;
/*     */     }
/* 167 */     LineMetrics metrics = font.getLineMetrics(text, g2
/* 168 */         .getFontRenderContext());
/*     */     
/* 170 */     g2.drawString(text, (float)x, 
/*     */         
/* 172 */         (float)(bounds.getMaxY() - this.bottomInnerGap - metrics.getDescent()));
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
/*     */   public void draw(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, double x, double y) {
/* 188 */     double h = getHeight(g2);
/* 189 */     Iterator<IntervalMarker> iterator = this.markers.iterator();
/* 190 */     while (iterator.hasNext()) {
/* 191 */       IntervalMarker marker = iterator.next();
/* 192 */       double start = Math.max(marker
/* 193 */           .getStartValue(), this.axis.getRange().getLowerBound());
/*     */       
/* 195 */       double end = Math.min(marker
/* 196 */           .getEndValue(), this.axis.getRange().getUpperBound());
/*     */       
/* 198 */       double s = this.axis.valueToJava2D(start, dataArea, RectangleEdge.BOTTOM);
/*     */ 
/*     */       
/* 201 */       double e = this.axis.valueToJava2D(end, dataArea, RectangleEdge.BOTTOM);
/*     */ 
/*     */       
/* 204 */       Rectangle2D r = new Rectangle2D.Double(s, y + this.topOuterGap, e - s, h - this.topOuterGap - this.bottomOuterGap);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       Composite originalComposite = g2.getComposite();
/* 210 */       g2.setComposite(AlphaComposite.getInstance(3, marker
/* 211 */             .getAlpha()));
/*     */       
/* 213 */       g2.setPaint(marker.getPaint());
/* 214 */       g2.fill(r);
/* 215 */       g2.setPaint(marker.getOutlinePaint());
/* 216 */       g2.draw(r);
/* 217 */       g2.setComposite(originalComposite);
/*     */       
/* 219 */       g2.setPaint(Color.black);
/* 220 */       drawStringInRect(g2, r, this.font, marker.getLabel());
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
/*     */   public boolean equals(Object obj) {
/* 235 */     if (obj == this) {
/* 236 */       return true;
/*     */     }
/* 238 */     if (!(obj instanceof MarkerAxisBand)) {
/* 239 */       return false;
/*     */     }
/* 241 */     MarkerAxisBand that = (MarkerAxisBand)obj;
/* 242 */     if (this.topOuterGap != that.topOuterGap) {
/* 243 */       return false;
/*     */     }
/* 245 */     if (this.topInnerGap != that.topInnerGap) {
/* 246 */       return false;
/*     */     }
/* 248 */     if (this.bottomInnerGap != that.bottomInnerGap) {
/* 249 */       return false;
/*     */     }
/* 251 */     if (this.bottomOuterGap != that.bottomOuterGap) {
/* 252 */       return false;
/*     */     }
/* 254 */     if (!ObjectUtilities.equal(this.font, that.font)) {
/* 255 */       return false;
/*     */     }
/* 257 */     if (!ObjectUtilities.equal(this.markers, that.markers)) {
/* 258 */       return false;
/*     */     }
/* 260 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 270 */     int result = 37;
/* 271 */     result = 19 * result + this.font.hashCode();
/* 272 */     result = 19 * result + this.markers.hashCode();
/* 273 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/MarkerAxisBand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */