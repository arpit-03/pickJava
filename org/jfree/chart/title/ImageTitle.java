/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.chart.event.TitleChangeEvent;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.Size2D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageTitle
/*     */   extends Title
/*     */ {
/*     */   private Image image;
/*     */   
/*     */   public ImageTitle(Image image) {
/* 100 */     this(image, image.getHeight(null), image.getWidth(null), Title.DEFAULT_POSITION, Title.DEFAULT_HORIZONTAL_ALIGNMENT, Title.DEFAULT_VERTICAL_ALIGNMENT, Title.DEFAULT_PADDING);
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
/*     */   public ImageTitle(Image image, RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
/* 117 */     this(image, image.getHeight(null), image.getWidth(null), position, horizontalAlignment, verticalAlignment, Title.DEFAULT_PADDING);
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
/*     */   public ImageTitle(Image image, int height, int width, RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, RectangleInsets padding) {
/* 141 */     super(position, horizontalAlignment, verticalAlignment, padding);
/* 142 */     if (image == null) {
/* 143 */       throw new NullPointerException("Null 'image' argument.");
/*     */     }
/* 145 */     this.image = image;
/* 146 */     setHeight(height);
/* 147 */     setWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 157 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImage(Image image) {
/* 167 */     if (image == null) {
/* 168 */       throw new NullPointerException("Null 'image' argument.");
/*     */     }
/* 170 */     this.image = image;
/* 171 */     notifyListeners(new TitleChangeEvent(this));
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/* 186 */     Size2D s = new Size2D(this.image.getWidth(null), this.image.getHeight(null));
/* 187 */     return new Size2D(calculateTotalWidth(s.getWidth()), 
/* 188 */         calculateTotalHeight(s.getHeight()));
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 200 */     RectangleEdge position = getPosition();
/* 201 */     if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
/* 202 */       drawHorizontal(g2, area);
/*     */     }
/* 204 */     else if (position == RectangleEdge.LEFT || position == RectangleEdge.RIGHT) {
/*     */       
/* 206 */       drawVertical(g2, area);
/*     */     } else {
/*     */       
/* 209 */       throw new RuntimeException("Invalid title position.");
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
/*     */   
/*     */   protected Size2D drawHorizontal(Graphics2D g2, Rectangle2D chartArea) {
/* 230 */     double startY, w = getWidth();
/* 231 */     double h = getHeight();
/* 232 */     RectangleInsets padding = getPadding();
/* 233 */     double topSpace = padding.calculateTopOutset(h);
/* 234 */     double bottomSpace = padding.calculateBottomOutset(h);
/* 235 */     double leftSpace = padding.calculateLeftOutset(w);
/* 236 */     double rightSpace = padding.calculateRightOutset(w);
/*     */     
/* 238 */     if (getPosition() == RectangleEdge.TOP) {
/* 239 */       startY = chartArea.getY() + topSpace;
/*     */     } else {
/*     */       
/* 242 */       startY = chartArea.getY() + chartArea.getHeight() - bottomSpace - h;
/*     */     } 
/*     */ 
/*     */     
/* 246 */     HorizontalAlignment horizontalAlignment = getHorizontalAlignment();
/* 247 */     double startX = 0.0D;
/* 248 */     if (horizontalAlignment == HorizontalAlignment.CENTER) {
/* 249 */       startX = chartArea.getX() + leftSpace + chartArea.getWidth() / 2.0D - w / 2.0D;
/*     */     
/*     */     }
/* 252 */     else if (horizontalAlignment == HorizontalAlignment.LEFT) {
/* 253 */       startX = chartArea.getX() + leftSpace;
/*     */     }
/* 255 */     else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
/* 256 */       startX = chartArea.getX() + chartArea.getWidth() - rightSpace - w;
/*     */     } 
/* 258 */     g2.drawImage(this.image, (int)startX, (int)startY, (int)w, (int)h, null);
/*     */ 
/*     */     
/* 261 */     return new Size2D(chartArea.getWidth() + leftSpace + rightSpace, h + topSpace + bottomSpace);
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
/*     */   protected Size2D drawVertical(Graphics2D g2, Rectangle2D chartArea) {
/* 279 */     double startX, topSpace = 0.0D;
/* 280 */     double bottomSpace = 0.0D;
/* 281 */     double leftSpace = 0.0D;
/* 282 */     double rightSpace = 0.0D;
/*     */     
/* 284 */     double w = getWidth();
/* 285 */     double h = getHeight();
/*     */     
/* 287 */     RectangleInsets padding = getPadding();
/* 288 */     if (padding != null) {
/* 289 */       topSpace = padding.calculateTopOutset(h);
/* 290 */       bottomSpace = padding.calculateBottomOutset(h);
/* 291 */       leftSpace = padding.calculateLeftOutset(w);
/* 292 */       rightSpace = padding.calculateRightOutset(w);
/*     */     } 
/*     */     
/* 295 */     if (getPosition() == RectangleEdge.LEFT) {
/* 296 */       startX = chartArea.getX() + leftSpace;
/*     */     } else {
/*     */       
/* 299 */       startX = chartArea.getMaxX() - rightSpace - w;
/*     */     } 
/*     */ 
/*     */     
/* 303 */     VerticalAlignment alignment = getVerticalAlignment();
/* 304 */     double startY = 0.0D;
/* 305 */     if (alignment == VerticalAlignment.CENTER) {
/*     */       
/* 307 */       startY = chartArea.getMinY() + topSpace + chartArea.getHeight() / 2.0D - h / 2.0D;
/*     */     }
/* 309 */     else if (alignment == VerticalAlignment.TOP) {
/* 310 */       startY = chartArea.getMinY() + topSpace;
/*     */     }
/* 312 */     else if (alignment == VerticalAlignment.BOTTOM) {
/* 313 */       startY = chartArea.getMaxY() - bottomSpace - h;
/*     */     } 
/*     */     
/* 316 */     g2.drawImage(this.image, (int)startX, (int)startY, (int)w, (int)h, null);
/*     */ 
/*     */     
/* 319 */     return new Size2D(chartArea.getWidth() + leftSpace + rightSpace, h + topSpace + bottomSpace);
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
/*     */   public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
/* 335 */     draw(g2, area);
/* 336 */     return null;
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
/*     */   public boolean equals(Object obj) {
/* 355 */     if (obj == this) {
/* 356 */       return true;
/*     */     }
/* 358 */     if (!(obj instanceof ImageTitle)) {
/* 359 */       return false;
/*     */     }
/* 361 */     ImageTitle that = (ImageTitle)obj;
/* 362 */     if (!ObjectUtilities.equal(this.image, that.image)) {
/* 363 */       return false;
/*     */     }
/* 365 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/ImageTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */