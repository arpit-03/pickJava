/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import com.jogamp.opengl.util.awt.TextRenderer;
/*     */ import edu.mines.jtk.util.MathPlus;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Annotation
/*     */   extends Handle
/*     */ {
/*     */   private Alignment _alignment;
/*     */   private Font _font;
/*     */   private TextRenderer _renderer;
/*     */   private String _text;
/*     */   private Color _color;
/*     */   
/*     */   public enum Alignment
/*     */   {
/*  40 */     NORTH,
/*  41 */     SOUTH,
/*  42 */     EAST,
/*  43 */     WEST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation(Point3 p) {
/*  53 */     this(p, "");
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
/*     */   public Annotation(float x, float y, float z) {
/*  65 */     this(new Point3(x, y, z));
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
/*     */   public Annotation(float x, float y, float z, String text) {
/*  78 */     this(new Point3(x, y, z), text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation(Point3 p, String text) {
/*  89 */     super(p);
/*  90 */     this._text = text;
/*  91 */     addChild(new AnnotationText());
/*  92 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 100 */     this._font = font;
/* 101 */     updateTextRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 109 */     return this._font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlignment(Alignment alignment) {
/* 118 */     this._alignment = alignment;
/* 119 */     updateTextRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Alignment getAlignment() {
/* 127 */     return this._alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 135 */     this._text = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 143 */     return this._text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 152 */     this._color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 160 */     return this._color;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 165 */     return getLocation().toString() + " " + this._text;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 170 */     if (this == o) return true; 
/* 171 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 173 */     Annotation that = (Annotation)o;
/*     */     
/* 175 */     if (this._alignment != that._alignment) return false; 
/* 176 */     if (!this._font.equals(that._font)) return false; 
/* 177 */     if (!this._renderer.equals(that._renderer)) return false; 
/* 178 */     if (!this._text.equals(that._text)) return false; 
/* 179 */     return this._color.equals(that._color);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 184 */     int result = this._alignment.hashCode();
/* 185 */     result = 31 * result + this._font.hashCode();
/* 186 */     result = 31 * result + this._renderer.hashCode();
/* 187 */     result = 31 * result + this._text.hashCode();
/* 188 */     result = 31 * result + this._color.hashCode();
/* 189 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class AnnotationText
/*     */     extends Node
/*     */   {
/*     */     protected void draw(DrawContext dc) {
/*     */       int x, y;
/* 199 */       Matrix44 localToPixel = dc.getLocalToPixel();
/*     */       
/* 201 */       Point3 p = localToPixel.times(new Point3(0.0D, 0.0D, 0.0D));
/* 202 */       int w = dc.getViewCanvas().getWidth();
/* 203 */       int h = dc.getViewCanvas().getHeight();
/*     */       
/* 205 */       Rectangle2D rect = Annotation.this._renderer.getBounds(Annotation.this._text);
/* 206 */       double rw = rect.getWidth();
/* 207 */       double rh = rect.getHeight();
/*     */       
/* 209 */       int nudge = 10;
/*     */       
/* 211 */       Annotation.this._renderer.beginRendering(w, h);
/* 212 */       Annotation.this._renderer.setColor(Annotation.this._color);
/*     */       
/* 214 */       switch (Annotation.this._alignment) {
/*     */         case NORTH:
/* 216 */           x = (int)(p.x - rw / 2.0D);
/* 217 */           y = (int)(h - p.y + rh / 4.0D) - nudge;
/*     */           break;
/*     */         case EAST:
/* 220 */           x = (int)p.x + nudge;
/* 221 */           y = (int)(h - p.y - rh / 4.0D);
/*     */           break;
/*     */         case WEST:
/* 224 */           x = (int)(p.x - rw) - nudge;
/* 225 */           y = (int)(h - p.y - rh / 4.0D);
/*     */           break;
/*     */         default:
/* 228 */           x = (int)(p.x - rw / 2.0D);
/* 229 */           y = (int)(h - p.y - rh / 2.0D) - nudge; break;
/*     */       } 
/* 231 */       Annotation.this._renderer.draw(Annotation.this._text, x, y);
/* 232 */       Annotation.this._renderer.endRendering();
/*     */     }
/*     */     protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 235 */       Rectangle2D rect = Annotation.this._renderer.getBounds(Annotation.this._text);
/* 236 */       double w = rect.getWidth();
/* 237 */       double h = rect.getHeight();
/* 238 */       return new BoundingSphere(0.0D, 0.0D, 0.0D, MathPlus.sqrt(w * w + h * h));
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateTextRenderer() {
/* 243 */     this._renderer = new TextRenderer(this._font);
/*     */   }
/*     */   
/*     */   private void init() {
/* 247 */     this._alignment = Alignment.EAST;
/* 248 */     this._font = new Font("SansSerif", 0, 18);
/* 249 */     this._color = Color.WHITE;
/* 250 */     updateTextRenderer();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Annotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */