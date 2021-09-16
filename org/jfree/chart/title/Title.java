/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import org.jfree.chart.block.AbstractBlock;
/*     */ import org.jfree.chart.block.Block;
/*     */ import org.jfree.chart.event.TitleChangeEvent;
/*     */ import org.jfree.chart.event.TitleChangeListener;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.VerticalAlignment;
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
/*     */ public abstract class Title
/*     */   extends AbstractBlock
/*     */   implements Block, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6675162505277817221L;
/*  98 */   public static final RectangleEdge DEFAULT_POSITION = RectangleEdge.TOP;
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final HorizontalAlignment DEFAULT_HORIZONTAL_ALIGNMENT = HorizontalAlignment.CENTER;
/*     */ 
/*     */ 
/*     */   
/* 106 */   public static final VerticalAlignment DEFAULT_VERTICAL_ALIGNMENT = VerticalAlignment.CENTER;
/*     */ 
/*     */   
/* 109 */   public static final RectangleInsets DEFAULT_PADDING = new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visible;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleEdge position;
/*     */ 
/*     */ 
/*     */   
/*     */   private HorizontalAlignment horizontalAlignment;
/*     */ 
/*     */ 
/*     */   
/*     */   private VerticalAlignment verticalAlignment;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient EventListenerList listenerList;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notify;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Title() {
/* 140 */     this(DEFAULT_POSITION, DEFAULT_HORIZONTAL_ALIGNMENT, DEFAULT_VERTICAL_ALIGNMENT, DEFAULT_PADDING);
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
/*     */   protected Title(RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
/* 159 */     this(position, horizontalAlignment, verticalAlignment, DEFAULT_PADDING);
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
/*     */   protected Title(RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, RectangleInsets padding) {
/* 182 */     ParamChecks.nullNotPermitted(position, "position");
/* 183 */     ParamChecks.nullNotPermitted(horizontalAlignment, "horizontalAlignment");
/* 184 */     ParamChecks.nullNotPermitted(verticalAlignment, "verticalAlignment");
/* 185 */     ParamChecks.nullNotPermitted(padding, "padding");
/*     */     
/* 187 */     this.visible = true;
/* 188 */     this.position = position;
/* 189 */     this.horizontalAlignment = horizontalAlignment;
/* 190 */     this.verticalAlignment = verticalAlignment;
/* 191 */     setPadding(padding);
/* 192 */     this.listenerList = new EventListenerList();
/* 193 */     this.notify = true;
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
/*     */   public boolean isVisible() {
/* 207 */     return this.visible;
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
/*     */   public void setVisible(boolean visible) {
/* 221 */     this.visible = visible;
/* 222 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleEdge getPosition() {
/* 231 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(RectangleEdge position) {
/* 241 */     ParamChecks.nullNotPermitted(position, "position");
/* 242 */     if (this.position != position) {
/* 243 */       this.position = position;
/* 244 */       notifyListeners(new TitleChangeEvent(this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HorizontalAlignment getHorizontalAlignment() {
/* 254 */     return this.horizontalAlignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHorizontalAlignment(HorizontalAlignment alignment) {
/* 265 */     ParamChecks.nullNotPermitted(alignment, "alignment");
/* 266 */     if (this.horizontalAlignment != alignment) {
/* 267 */       this.horizontalAlignment = alignment;
/* 268 */       notifyListeners(new TitleChangeEvent(this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerticalAlignment getVerticalAlignment() {
/* 278 */     return this.verticalAlignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerticalAlignment(VerticalAlignment alignment) {
/* 289 */     ParamChecks.nullNotPermitted(alignment, "alignment");
/* 290 */     if (this.verticalAlignment != alignment) {
/* 291 */       this.verticalAlignment = alignment;
/* 292 */       notifyListeners(new TitleChangeEvent(this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getNotify() {
/* 303 */     return this.notify;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNotify(boolean flag) {
/* 314 */     this.notify = flag;
/* 315 */     if (flag) {
/* 316 */       notifyListeners(new TitleChangeEvent(this));
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
/*     */   public abstract void draw(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 345 */     Title duplicate = (Title)super.clone();
/* 346 */     duplicate.listenerList = new EventListenerList();
/*     */     
/* 348 */     return duplicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChangeListener(TitleChangeListener listener) {
/* 357 */     this.listenerList.add(TitleChangeListener.class, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeChangeListener(TitleChangeListener listener) {
/* 366 */     this.listenerList.remove(TitleChangeListener.class, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyListeners(TitleChangeEvent event) {
/* 377 */     if (this.notify) {
/* 378 */       Object[] listeners = this.listenerList.getListenerList();
/* 379 */       for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 380 */         if (listeners[i] == TitleChangeListener.class) {
/* 381 */           ((TitleChangeListener)listeners[i + 1]).titleChanged(event);
/*     */         }
/*     */       } 
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
/*     */   public boolean equals(Object obj) {
/* 397 */     if (obj == this) {
/* 398 */       return true;
/*     */     }
/* 400 */     if (!(obj instanceof Title)) {
/* 401 */       return false;
/*     */     }
/* 403 */     Title that = (Title)obj;
/* 404 */     if (this.visible != that.visible) {
/* 405 */       return false;
/*     */     }
/* 407 */     if (this.position != that.position) {
/* 408 */       return false;
/*     */     }
/* 410 */     if (this.horizontalAlignment != that.horizontalAlignment) {
/* 411 */       return false;
/*     */     }
/* 413 */     if (this.verticalAlignment != that.verticalAlignment) {
/* 414 */       return false;
/*     */     }
/* 416 */     if (this.notify != that.notify) {
/* 417 */       return false;
/*     */     }
/* 419 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 429 */     int result = 193;
/* 430 */     result = 37 * result + ObjectUtilities.hashCode(this.position);
/*     */     
/* 432 */     result = 37 * result + ObjectUtilities.hashCode(this.horizontalAlignment);
/* 433 */     result = 37 * result + ObjectUtilities.hashCode(this.verticalAlignment);
/* 434 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 445 */     stream.defaultWriteObject();
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 458 */     stream.defaultReadObject();
/* 459 */     this.listenerList = new EventListenerList();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/Title.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */