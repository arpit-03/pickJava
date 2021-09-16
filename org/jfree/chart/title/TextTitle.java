/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.block.BlockResult;
/*     */ import org.jfree.chart.block.EntityBlockParams;
/*     */ import org.jfree.chart.block.LengthConstraintType;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.StandardEntityCollection;
/*     */ import org.jfree.chart.entity.TitleEntity;
/*     */ import org.jfree.chart.event.TitleChangeEvent;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.G2TextMeasurer;
/*     */ import org.jfree.text.TextBlock;
/*     */ import org.jfree.text.TextBlockAnchor;
/*     */ import org.jfree.text.TextMeasurer;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.ui.VerticalAlignment;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextTitle
/*     */   extends Title
/*     */   implements Serializable, Cloneable, PublicCloneable
/*     */ {
/*     */   private static final long serialVersionUID = 8372008692127477443L;
/* 134 */   public static final Font DEFAULT_FONT = new Font("SansSerif", 1, 12);
/*     */ 
/*     */ 
/*     */   
/* 138 */   public static final Paint DEFAULT_TEXT_PAINT = Color.black;
/*     */ 
/*     */ 
/*     */   
/*     */   private String text;
/*     */ 
/*     */ 
/*     */   
/*     */   private Font font;
/*     */ 
/*     */ 
/*     */   
/*     */   private HorizontalAlignment textAlignment;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint paint;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint backgroundPaint;
/*     */ 
/*     */ 
/*     */   
/*     */   private String toolTipText;
/*     */ 
/*     */ 
/*     */   
/*     */   private String urlText;
/*     */ 
/*     */   
/*     */   private TextBlock content;
/*     */ 
/*     */   
/*     */   private boolean expandToFitSpace = false;
/*     */ 
/*     */   
/* 175 */   private int maximumLinesToDisplay = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextTitle() {
/* 181 */     this("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextTitle(String text) {
/* 190 */     this(text, DEFAULT_FONT, DEFAULT_TEXT_PAINT, Title.DEFAULT_POSITION, Title.DEFAULT_HORIZONTAL_ALIGNMENT, Title.DEFAULT_VERTICAL_ALIGNMENT, Title.DEFAULT_PADDING);
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
/*     */   public TextTitle(String text, Font font) {
/* 202 */     this(text, font, DEFAULT_TEXT_PAINT, Title.DEFAULT_POSITION, Title.DEFAULT_HORIZONTAL_ALIGNMENT, Title.DEFAULT_VERTICAL_ALIGNMENT, Title.DEFAULT_PADDING);
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
/*     */   public TextTitle(String text, Font font, Paint paint, RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, RectangleInsets padding) {
/* 226 */     super(position, horizontalAlignment, verticalAlignment, padding);
/*     */     
/* 228 */     if (text == null) {
/* 229 */       throw new NullPointerException("Null 'text' argument.");
/*     */     }
/* 231 */     if (font == null) {
/* 232 */       throw new NullPointerException("Null 'font' argument.");
/*     */     }
/* 234 */     if (paint == null) {
/* 235 */       throw new NullPointerException("Null 'paint' argument.");
/*     */     }
/* 237 */     this.text = text;
/* 238 */     this.font = font;
/* 239 */     this.paint = paint;
/*     */ 
/*     */ 
/*     */     
/* 243 */     this.textAlignment = horizontalAlignment;
/* 244 */     this.backgroundPaint = null;
/* 245 */     this.content = null;
/* 246 */     this.toolTipText = null;
/* 247 */     this.urlText = null;
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
/*     */   public String getText() {
/* 259 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 269 */     ParamChecks.nullNotPermitted(text, "text");
/* 270 */     if (!this.text.equals(text)) {
/* 271 */       this.text = text;
/* 272 */       notifyListeners(new TitleChangeEvent(this));
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
/*     */   public HorizontalAlignment getTextAlignment() {
/* 285 */     return this.textAlignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextAlignment(HorizontalAlignment alignment) {
/* 295 */     ParamChecks.nullNotPermitted(alignment, "alignment");
/* 296 */     this.textAlignment = alignment;
/* 297 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 308 */     return this.font;
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
/*     */   public void setFont(Font font) {
/* 320 */     ParamChecks.nullNotPermitted(font, "font");
/* 321 */     if (!this.font.equals(font)) {
/* 322 */       this.font = font;
/* 323 */       notifyListeners(new TitleChangeEvent(this));
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
/*     */   public Paint getPaint() {
/* 335 */     return this.paint;
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
/*     */   public void setPaint(Paint paint) {
/* 347 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 348 */     if (!this.paint.equals(paint)) {
/* 349 */       this.paint = paint;
/* 350 */       notifyListeners(new TitleChangeEvent(this));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 360 */     return this.backgroundPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundPaint(Paint paint) {
/* 371 */     this.backgroundPaint = paint;
/* 372 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolTipText() {
/* 381 */     return this.toolTipText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setToolTipText(String text) {
/* 391 */     this.toolTipText = text;
/* 392 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURLText() {
/* 401 */     return this.urlText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURLText(String text) {
/* 411 */     this.urlText = text;
/* 412 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getExpandToFitSpace() {
/* 422 */     return this.expandToFitSpace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpandToFitSpace(boolean expand) {
/* 433 */     this.expandToFitSpace = expand;
/* 434 */     notifyListeners(new TitleChangeEvent(this));
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
/*     */   public int getMaximumLinesToDisplay() {
/* 447 */     return this.maximumLinesToDisplay;
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
/*     */   public void setMaximumLinesToDisplay(int max) {
/* 461 */     this.maximumLinesToDisplay = max;
/* 462 */     notifyListeners(new TitleChangeEvent(this));
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/* 476 */     RectangleConstraint cc = toContentConstraint(constraint);
/* 477 */     LengthConstraintType w = cc.getWidthConstraintType();
/* 478 */     LengthConstraintType h = cc.getHeightConstraintType();
/* 479 */     Size2D contentSize = null;
/* 480 */     if (w == LengthConstraintType.NONE) {
/* 481 */       if (h == LengthConstraintType.NONE) {
/* 482 */         contentSize = arrangeNN(g2);
/*     */       } else {
/* 484 */         if (h == LengthConstraintType.RANGE) {
/* 485 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/* 487 */         if (h == LengthConstraintType.FIXED) {
/* 488 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/*     */       } 
/* 491 */     } else if (w == LengthConstraintType.RANGE) {
/* 492 */       if (h == LengthConstraintType.NONE) {
/* 493 */         contentSize = arrangeRN(g2, cc.getWidthRange());
/*     */       }
/* 495 */       else if (h == LengthConstraintType.RANGE) {
/* 496 */         contentSize = arrangeRR(g2, cc.getWidthRange(), cc
/* 497 */             .getHeightRange());
/*     */       }
/* 499 */       else if (h == LengthConstraintType.FIXED) {
/* 500 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/*     */     
/* 503 */     } else if (w == LengthConstraintType.FIXED) {
/* 504 */       if (h == LengthConstraintType.NONE) {
/* 505 */         contentSize = arrangeFN(g2, cc.getWidth());
/*     */       } else {
/* 507 */         if (h == LengthConstraintType.RANGE) {
/* 508 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/* 510 */         if (h == LengthConstraintType.FIXED)
/* 511 */           throw new RuntimeException("Not yet implemented."); 
/*     */       } 
/*     */     } 
/* 514 */     assert contentSize != null;
/* 515 */     return new Size2D(calculateTotalWidth(contentSize.getWidth()), 
/* 516 */         calculateTotalHeight(contentSize.getHeight()));
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
/*     */   protected Size2D arrangeNN(Graphics2D g2) {
/* 532 */     Range max = new Range(0.0D, 3.4028234663852886E38D);
/* 533 */     return arrangeRR(g2, max, max);
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
/*     */   protected Size2D arrangeFN(Graphics2D g2, double w) {
/* 550 */     RectangleEdge position = getPosition();
/* 551 */     if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
/* 552 */       float maxWidth = (float)w;
/* 553 */       g2.setFont(this.font);
/* 554 */       this.content = TextUtilities.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, (TextMeasurer)new G2TextMeasurer(g2));
/*     */ 
/*     */       
/* 557 */       this.content.setLineAlignment(this.textAlignment);
/* 558 */       Size2D contentSize = this.content.calculateDimensions(g2);
/* 559 */       if (this.expandToFitSpace) {
/* 560 */         return new Size2D(maxWidth, contentSize.getHeight());
/*     */       }
/*     */       
/* 563 */       return contentSize;
/*     */     } 
/*     */     
/* 566 */     if (position == RectangleEdge.LEFT || position == RectangleEdge.RIGHT) {
/*     */       
/* 568 */       float maxWidth = Float.MAX_VALUE;
/* 569 */       g2.setFont(this.font);
/* 570 */       this.content = TextUtilities.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, (TextMeasurer)new G2TextMeasurer(g2));
/*     */ 
/*     */       
/* 573 */       this.content.setLineAlignment(this.textAlignment);
/* 574 */       Size2D contentSize = this.content.calculateDimensions(g2);
/*     */ 
/*     */       
/* 577 */       if (this.expandToFitSpace) {
/* 578 */         return new Size2D(contentSize.getHeight(), maxWidth);
/*     */       }
/*     */       
/* 581 */       return new Size2D(contentSize.height, contentSize.width);
/*     */     } 
/*     */ 
/*     */     
/* 585 */     throw new RuntimeException("Unrecognised exception.");
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
/*     */   protected Size2D arrangeRN(Graphics2D g2, Range widthRange) {
/* 603 */     Size2D s = arrangeNN(g2);
/* 604 */     if (widthRange.contains(s.getWidth())) {
/* 605 */       return s;
/*     */     }
/* 607 */     double ww = widthRange.constrain(s.getWidth());
/* 608 */     return arrangeFN(g2, ww);
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
/*     */   protected Size2D arrangeRR(Graphics2D g2, Range widthRange, Range heightRange) {
/* 624 */     RectangleEdge position = getPosition();
/* 625 */     if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
/* 626 */       float maxWidth = (float)widthRange.getUpperBound();
/* 627 */       g2.setFont(this.font);
/* 628 */       this.content = TextUtilities.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, (TextMeasurer)new G2TextMeasurer(g2));
/*     */ 
/*     */       
/* 631 */       this.content.setLineAlignment(this.textAlignment);
/* 632 */       Size2D contentSize = this.content.calculateDimensions(g2);
/* 633 */       if (this.expandToFitSpace) {
/* 634 */         return new Size2D(maxWidth, contentSize.getHeight());
/*     */       }
/*     */       
/* 637 */       return contentSize;
/*     */     } 
/*     */     
/* 640 */     if (position == RectangleEdge.LEFT || position == RectangleEdge.RIGHT) {
/*     */       
/* 642 */       float maxWidth = (float)heightRange.getUpperBound();
/* 643 */       g2.setFont(this.font);
/* 644 */       this.content = TextUtilities.createTextBlock(this.text, this.font, this.paint, maxWidth, this.maximumLinesToDisplay, (TextMeasurer)new G2TextMeasurer(g2));
/*     */ 
/*     */       
/* 647 */       this.content.setLineAlignment(this.textAlignment);
/* 648 */       Size2D contentSize = this.content.calculateDimensions(g2);
/*     */ 
/*     */       
/* 651 */       if (this.expandToFitSpace) {
/* 652 */         return new Size2D(contentSize.getHeight(), maxWidth);
/*     */       }
/*     */       
/* 655 */       return new Size2D(contentSize.height, contentSize.width);
/*     */     } 
/*     */ 
/*     */     
/* 659 */     throw new RuntimeException("Unrecognised exception.");
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 672 */     draw(g2, area, (Object)null);
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
/*     */     TitleEntity titleEntity;
/* 689 */     if (this.content == null) {
/* 690 */       return null;
/*     */     }
/* 692 */     area = trimMargin(area);
/* 693 */     drawBorder(g2, area);
/* 694 */     if (this.text.equals("")) {
/* 695 */       return null;
/*     */     }
/* 697 */     ChartEntity entity = null;
/* 698 */     if (params instanceof EntityBlockParams) {
/* 699 */       EntityBlockParams p = (EntityBlockParams)params;
/* 700 */       if (p.getGenerateEntities()) {
/* 701 */         titleEntity = new TitleEntity(area, this, this.toolTipText, this.urlText);
/*     */       }
/*     */     } 
/*     */     
/* 705 */     area = trimBorder(area);
/* 706 */     if (this.backgroundPaint != null) {
/* 707 */       g2.setPaint(this.backgroundPaint);
/* 708 */       g2.fill(area);
/*     */     } 
/* 710 */     area = trimPadding(area);
/* 711 */     RectangleEdge position = getPosition();
/* 712 */     if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
/* 713 */       drawHorizontal(g2, area);
/*     */     }
/* 715 */     else if (position == RectangleEdge.LEFT || position == RectangleEdge.RIGHT) {
/*     */       
/* 717 */       drawVertical(g2, area);
/*     */     } 
/* 719 */     BlockResult result = new BlockResult();
/* 720 */     if (titleEntity != null) {
/* 721 */       StandardEntityCollection sec = new StandardEntityCollection();
/* 722 */       sec.add((ChartEntity)titleEntity);
/* 723 */       result.setEntityCollection((EntityCollection)sec);
/*     */     } 
/* 725 */     return result;
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
/*     */   protected void drawHorizontal(Graphics2D g2, Rectangle2D area) {
/* 737 */     Rectangle2D titleArea = (Rectangle2D)area.clone();
/* 738 */     g2.setFont(this.font);
/* 739 */     g2.setPaint(this.paint);
/* 740 */     TextBlockAnchor anchor = null;
/* 741 */     float x = 0.0F;
/* 742 */     HorizontalAlignment horizontalAlignment = getHorizontalAlignment();
/* 743 */     if (horizontalAlignment == HorizontalAlignment.LEFT) {
/* 744 */       x = (float)titleArea.getX();
/* 745 */       anchor = TextBlockAnchor.TOP_LEFT;
/*     */     }
/* 747 */     else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
/* 748 */       x = (float)titleArea.getMaxX();
/* 749 */       anchor = TextBlockAnchor.TOP_RIGHT;
/*     */     }
/* 751 */     else if (horizontalAlignment == HorizontalAlignment.CENTER) {
/* 752 */       x = (float)titleArea.getCenterX();
/* 753 */       anchor = TextBlockAnchor.TOP_CENTER;
/*     */     } 
/* 755 */     float y = 0.0F;
/* 756 */     RectangleEdge position = getPosition();
/* 757 */     if (position == RectangleEdge.TOP) {
/* 758 */       y = (float)titleArea.getY();
/*     */     }
/* 760 */     else if (position == RectangleEdge.BOTTOM) {
/* 761 */       y = (float)titleArea.getMaxY();
/* 762 */       if (horizontalAlignment == HorizontalAlignment.LEFT) {
/* 763 */         anchor = TextBlockAnchor.BOTTOM_LEFT;
/*     */       }
/* 765 */       else if (horizontalAlignment == HorizontalAlignment.CENTER) {
/* 766 */         anchor = TextBlockAnchor.BOTTOM_CENTER;
/*     */       }
/* 768 */       else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
/* 769 */         anchor = TextBlockAnchor.BOTTOM_RIGHT;
/*     */       } 
/*     */     } 
/* 772 */     this.content.draw(g2, x, y, anchor);
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
/*     */   protected void drawVertical(Graphics2D g2, Rectangle2D area) {
/* 784 */     Rectangle2D titleArea = (Rectangle2D)area.clone();
/* 785 */     g2.setFont(this.font);
/* 786 */     g2.setPaint(this.paint);
/* 787 */     TextBlockAnchor anchor = null;
/* 788 */     float y = 0.0F;
/* 789 */     VerticalAlignment verticalAlignment = getVerticalAlignment();
/* 790 */     if (verticalAlignment == VerticalAlignment.TOP) {
/* 791 */       y = (float)titleArea.getY();
/* 792 */       anchor = TextBlockAnchor.TOP_RIGHT;
/*     */     }
/* 794 */     else if (verticalAlignment == VerticalAlignment.BOTTOM) {
/* 795 */       y = (float)titleArea.getMaxY();
/* 796 */       anchor = TextBlockAnchor.TOP_LEFT;
/*     */     }
/* 798 */     else if (verticalAlignment == VerticalAlignment.CENTER) {
/* 799 */       y = (float)titleArea.getCenterY();
/* 800 */       anchor = TextBlockAnchor.TOP_CENTER;
/*     */     } 
/* 802 */     float x = 0.0F;
/* 803 */     RectangleEdge position = getPosition();
/* 804 */     if (position == RectangleEdge.LEFT) {
/* 805 */       x = (float)titleArea.getX();
/*     */     }
/* 807 */     else if (position == RectangleEdge.RIGHT) {
/* 808 */       x = (float)titleArea.getMaxX();
/* 809 */       if (verticalAlignment == VerticalAlignment.TOP) {
/* 810 */         anchor = TextBlockAnchor.BOTTOM_RIGHT;
/*     */       }
/* 812 */       else if (verticalAlignment == VerticalAlignment.CENTER) {
/* 813 */         anchor = TextBlockAnchor.BOTTOM_CENTER;
/*     */       }
/* 815 */       else if (verticalAlignment == VerticalAlignment.BOTTOM) {
/* 816 */         anchor = TextBlockAnchor.BOTTOM_LEFT;
/*     */       } 
/*     */     } 
/* 819 */     this.content.draw(g2, x, y, anchor, x, y, -1.5707963267948966D);
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
/*     */   public boolean equals(Object obj) {
/* 831 */     if (obj == this) {
/* 832 */       return true;
/*     */     }
/* 834 */     if (!(obj instanceof TextTitle)) {
/* 835 */       return false;
/*     */     }
/* 837 */     TextTitle that = (TextTitle)obj;
/* 838 */     if (!ObjectUtilities.equal(this.text, that.text)) {
/* 839 */       return false;
/*     */     }
/* 841 */     if (!ObjectUtilities.equal(this.font, that.font)) {
/* 842 */       return false;
/*     */     }
/* 844 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 845 */       return false;
/*     */     }
/* 847 */     if (this.textAlignment != that.textAlignment) {
/* 848 */       return false;
/*     */     }
/* 850 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
/* 851 */       return false;
/*     */     }
/* 853 */     if (this.maximumLinesToDisplay != that.maximumLinesToDisplay) {
/* 854 */       return false;
/*     */     }
/* 856 */     if (this.expandToFitSpace != that.expandToFitSpace) {
/* 857 */       return false;
/*     */     }
/* 859 */     if (!ObjectUtilities.equal(this.toolTipText, that.toolTipText)) {
/* 860 */       return false;
/*     */     }
/* 862 */     if (!ObjectUtilities.equal(this.urlText, that.urlText)) {
/* 863 */       return false;
/*     */     }
/* 865 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 875 */     int result = super.hashCode();
/* 876 */     result = 29 * result + ((this.text != null) ? this.text.hashCode() : 0);
/* 877 */     result = 29 * result + ((this.font != null) ? this.font.hashCode() : 0);
/* 878 */     result = 29 * result + ((this.paint != null) ? this.paint.hashCode() : 0);
/*     */     
/* 880 */     result = 29 * result + ((this.backgroundPaint != null) ? this.backgroundPaint.hashCode() : 0);
/* 881 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 893 */     return super.clone();
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
/* 904 */     stream.defaultWriteObject();
/* 905 */     SerialUtilities.writePaint(this.paint, stream);
/* 906 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
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
/* 919 */     stream.defaultReadObject();
/* 920 */     this.paint = SerialUtilities.readPaint(stream);
/* 921 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/TextTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */