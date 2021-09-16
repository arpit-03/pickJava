/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BevelArrowIcon
/*     */   implements Icon
/*     */ {
/*     */   public static final int UP = 0;
/*     */   public static final int DOWN = 1;
/*     */   private static final int DEFAULT_SIZE = 11;
/*     */   private Color edge1;
/*     */   private Color edge2;
/*     */   private Color fill;
/*     */   private int size;
/*     */   private int direction;
/*     */   
/*     */   public BevelArrowIcon(int direction, boolean isRaisedView, boolean isPressedView) {
/*  96 */     if (isRaisedView) {
/*  97 */       if (isPressedView) {
/*  98 */         init(UIManager.getColor("controlLtHighlight"), 
/*  99 */             UIManager.getColor("controlDkShadow"), 
/* 100 */             UIManager.getColor("controlShadow"), 11, direction);
/*     */       }
/*     */       else {
/*     */         
/* 104 */         init(UIManager.getColor("controlHighlight"), 
/* 105 */             UIManager.getColor("controlShadow"), 
/* 106 */             UIManager.getColor("control"), 11, direction);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 111 */     else if (isPressedView) {
/* 112 */       init(UIManager.getColor("controlDkShadow"), 
/* 113 */           UIManager.getColor("controlLtHighlight"), 
/* 114 */           UIManager.getColor("controlShadow"), 11, direction);
/*     */     }
/*     */     else {
/*     */       
/* 118 */       init(UIManager.getColor("controlShadow"), 
/* 119 */           UIManager.getColor("controlHighlight"), 
/* 120 */           UIManager.getColor("control"), 11, direction);
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
/*     */ 
/*     */   
/*     */   public BevelArrowIcon(Color edge1, Color edge2, Color fill, int size, int direction) {
/* 140 */     init(edge1, edge2, fill, size, direction);
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
/*     */   public void paintIcon(Component c, Graphics g, int x, int y) {
/* 155 */     switch (this.direction) { case 1:
/* 156 */         drawDownArrow(g, x, y); break;
/* 157 */       case 0: drawUpArrow(g, x, y);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 167 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 175 */     return this.size;
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
/*     */   private void init(Color edge1, Color edge2, Color fill, int size, int direction) {
/* 192 */     this.edge1 = edge1;
/* 193 */     this.edge2 = edge2;
/* 194 */     this.fill = fill;
/* 195 */     this.size = size;
/* 196 */     this.direction = direction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawDownArrow(Graphics g, int xo, int yo) {
/* 207 */     g.setColor(this.edge1);
/* 208 */     g.drawLine(xo, yo, xo + this.size - 1, yo);
/* 209 */     g.drawLine(xo, yo + 1, xo + this.size - 3, yo + 1);
/* 210 */     g.setColor(this.edge2);
/* 211 */     g.drawLine(xo + this.size - 2, yo + 1, xo + this.size - 1, yo + 1);
/* 212 */     int x = xo + 1;
/* 213 */     int y = yo + 2;
/* 214 */     int dx = this.size - 6;
/* 215 */     while (y + 1 < yo + this.size) {
/* 216 */       g.setColor(this.edge1);
/* 217 */       g.drawLine(x, y, x + 1, y);
/* 218 */       g.drawLine(x, y + 1, x + 1, y + 1);
/* 219 */       if (0 < dx) {
/* 220 */         g.setColor(this.fill);
/* 221 */         g.drawLine(x + 2, y, x + 1 + dx, y);
/* 222 */         g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
/*     */       } 
/* 224 */       g.setColor(this.edge2);
/* 225 */       g.drawLine(x + dx + 2, y, x + dx + 3, y);
/* 226 */       g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
/* 227 */       x++;
/* 228 */       y += 2;
/* 229 */       dx -= 2;
/*     */     } 
/* 231 */     g.setColor(this.edge1);
/* 232 */     g.drawLine(xo + this.size / 2, yo + this.size - 1, xo + this.size / 2, yo + this.size - 1);
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
/*     */   private void drawUpArrow(Graphics g, int xo, int yo) {
/* 245 */     g.setColor(this.edge1);
/* 246 */     int x = xo + this.size / 2;
/* 247 */     g.drawLine(x, yo, x, yo);
/* 248 */     x--;
/* 249 */     int y = yo + 1;
/* 250 */     int dx = 0;
/* 251 */     while (y + 3 < yo + this.size) {
/* 252 */       g.setColor(this.edge1);
/* 253 */       g.drawLine(x, y, x + 1, y);
/* 254 */       g.drawLine(x, y + 1, x + 1, y + 1);
/* 255 */       if (0 < dx) {
/* 256 */         g.setColor(this.fill);
/* 257 */         g.drawLine(x + 2, y, x + 1 + dx, y);
/* 258 */         g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
/*     */       } 
/* 260 */       g.setColor(this.edge2);
/* 261 */       g.drawLine(x + dx + 2, y, x + dx + 3, y);
/* 262 */       g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
/* 263 */       x--;
/* 264 */       y += 2;
/* 265 */       dx += 2;
/*     */     } 
/* 267 */     g.setColor(this.edge1);
/* 268 */     g.drawLine(xo, yo + this.size - 3, xo + 1, yo + this.size - 3);
/* 269 */     g.setColor(this.edge2);
/* 270 */     g.drawLine(xo + 2, yo + this.size - 2, xo + this.size - 1, yo + this.size - 2);
/* 271 */     g.drawLine(xo, yo + this.size - 1, xo + this.size, yo + this.size - 1);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/BevelArrowIcon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */