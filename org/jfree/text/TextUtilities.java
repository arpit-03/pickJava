/*     */ package org.jfree.text;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.LineMetrics;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.text.AttributedString;
/*     */ import java.text.BreakIterator;
/*     */ import java.util.Map;
/*     */ import org.jfree.base.BaseBoot;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.Log;
/*     */ import org.jfree.util.LogContext;
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
/*     */ public class TextUtilities
/*     */ {
/*  92 */   protected static final LogContext logger = Log.createContext(TextUtilities.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean drawStringsWithFontAttributes = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean useDrawRotatedStringWorkaround;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean useFontMetricsGetStringBounds;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 119 */       boolean isJava14 = ObjectUtilities.isJDK14();
/*     */ 
/*     */       
/* 122 */       String configRotatedStringWorkaround = BaseBoot.getInstance().getGlobalConfig().getConfigProperty("org.jfree.text.UseDrawRotatedStringWorkaround", "auto");
/*     */       
/* 124 */       if (configRotatedStringWorkaround.equals("auto")) {
/* 125 */         useDrawRotatedStringWorkaround = !isJava14;
/*     */       }
/*     */       else {
/*     */         
/* 129 */         useDrawRotatedStringWorkaround = configRotatedStringWorkaround.equals("true");
/*     */       } 
/*     */ 
/*     */       
/* 133 */       String configFontMetricsStringBounds = BaseBoot.getInstance().getGlobalConfig().getConfigProperty("org.jfree.text.UseFontMetricsGetStringBounds", "auto");
/*     */       
/* 135 */       if (configFontMetricsStringBounds.equals("auto")) {
/* 136 */         useFontMetricsGetStringBounds = isJava14;
/*     */       } else {
/*     */         
/* 139 */         useFontMetricsGetStringBounds = configFontMetricsStringBounds.equals("true");
/*     */       }
/*     */     
/* 142 */     } catch (Exception e) {
/*     */       
/* 144 */       useDrawRotatedStringWorkaround = true;
/* 145 */       useFontMetricsGetStringBounds = true;
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
/*     */ 
/*     */   
/*     */   public static TextBlock createTextBlock(String text, Font font, Paint paint) {
/* 168 */     if (text == null) {
/* 169 */       throw new IllegalArgumentException("Null 'text' argument.");
/*     */     }
/* 171 */     TextBlock result = new TextBlock();
/* 172 */     String input = text;
/* 173 */     boolean moreInputToProcess = (text.length() > 0);
/* 174 */     int start = 0;
/* 175 */     while (moreInputToProcess) {
/* 176 */       int index = input.indexOf("\n");
/* 177 */       if (index > start) {
/* 178 */         String line = input.substring(start, index);
/* 179 */         if (index < input.length() - 1) {
/* 180 */           result.addLine(line, font, paint);
/* 181 */           input = input.substring(index + 1);
/*     */           continue;
/*     */         } 
/* 184 */         moreInputToProcess = false;
/*     */         continue;
/*     */       } 
/* 187 */       if (index == start) {
/* 188 */         if (index < input.length() - 1) {
/* 189 */           input = input.substring(index + 1);
/*     */           continue;
/*     */         } 
/* 192 */         moreInputToProcess = false;
/*     */         
/*     */         continue;
/*     */       } 
/* 196 */       result.addLine(input, font, paint);
/* 197 */       moreInputToProcess = false;
/*     */     } 
/*     */     
/* 200 */     return result;
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
/*     */   public static TextBlock createTextBlock(String text, Font font, Paint paint, float maxWidth, TextMeasurer measurer) {
/* 219 */     return createTextBlock(text, font, paint, maxWidth, 2147483647, measurer);
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
/*     */   public static TextBlock createTextBlock(String text, Font font, Paint paint, float maxWidth, int maxLines, TextMeasurer measurer) {
/* 240 */     TextBlock result = new TextBlock();
/* 241 */     BreakIterator iterator = BreakIterator.getLineInstance();
/* 242 */     iterator.setText(text);
/* 243 */     int current = 0;
/* 244 */     int lines = 0;
/* 245 */     int length = text.length();
/* 246 */     while (current < length && lines < maxLines) {
/* 247 */       int next = nextLineBreak(text, current, maxWidth, iterator, measurer);
/*     */       
/* 249 */       if (next == -1) {
/* 250 */         result.addLine(text.substring(current), font, paint);
/* 251 */         return result;
/* 252 */       }  if (next == current) {
/* 253 */         next++;
/*     */       }
/* 255 */       result.addLine(text.substring(current, next), font, paint);
/* 256 */       lines++;
/* 257 */       current = next;
/* 258 */       while (current < text.length() && text.charAt(current) == '\n') {
/* 259 */         current++;
/*     */       }
/*     */     } 
/* 262 */     if (current < length) {
/* 263 */       TextLine lastLine = result.getLastLine();
/* 264 */       TextFragment lastFragment = lastLine.getLastTextFragment();
/* 265 */       String oldStr = lastFragment.getText();
/* 266 */       String newStr = "...";
/* 267 */       if (oldStr.length() > 3) {
/* 268 */         newStr = oldStr.substring(0, oldStr.length() - 3) + "...";
/*     */       }
/*     */       
/* 271 */       lastLine.removeFragment(lastFragment);
/*     */       
/* 273 */       TextFragment newFragment = new TextFragment(newStr, lastFragment.getFont(), lastFragment.getPaint());
/* 274 */       lastLine.addFragment(newFragment);
/*     */     } 
/* 276 */     return result;
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
/*     */   private static int nextLineBreak(String text, int start, float width, BreakIterator iterator, TextMeasurer measurer) {
/* 297 */     int current = start;
/*     */     
/* 299 */     float x = 0.0F;
/* 300 */     boolean firstWord = true;
/* 301 */     int newline = text.indexOf('\n', start);
/* 302 */     if (newline < 0)
/* 303 */       newline = Integer.MAX_VALUE; 
/*     */     int end;
/* 305 */     while ((end = iterator.following(current)) != -1) {
/* 306 */       x += measurer.getStringWidth(text, current, end);
/* 307 */       if (x > width) {
/* 308 */         if (firstWord) {
/* 309 */           while (measurer.getStringWidth(text, start, end) > width) {
/* 310 */             end--;
/* 311 */             if (end <= start) {
/* 312 */               return end;
/*     */             }
/*     */           } 
/* 315 */           return end;
/*     */         } 
/*     */         
/* 318 */         end = iterator.previous();
/* 319 */         return end;
/*     */       } 
/*     */ 
/*     */       
/* 323 */       if (end > newline) {
/* 324 */         return newline;
/*     */       }
/*     */ 
/*     */       
/* 328 */       firstWord = false;
/* 329 */       current = end;
/*     */     } 
/* 331 */     return -1;
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
/*     */   public static Rectangle2D getTextBounds(String text, Graphics2D g2, FontMetrics fm) {
/*     */     Rectangle2D bounds;
/* 348 */     if (useFontMetricsGetStringBounds) {
/* 349 */       bounds = fm.getStringBounds(text, g2);
/*     */ 
/*     */ 
/*     */       
/* 353 */       LineMetrics lm = fm.getFont().getLineMetrics(text, g2
/* 354 */           .getFontRenderContext());
/* 355 */       bounds.setRect(bounds.getX(), bounds.getY(), bounds.getWidth(), lm
/* 356 */           .getHeight());
/*     */     } else {
/*     */       
/* 359 */       double width = fm.stringWidth(text);
/* 360 */       double height = fm.getHeight();
/* 361 */       if (logger.isDebugEnabled()) {
/* 362 */         logger.debug("Height = " + height);
/*     */       }
/* 364 */       bounds = new Rectangle2D.Double(0.0D, -fm.getAscent(), width, height);
/*     */     } 
/*     */     
/* 367 */     return bounds;
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
/*     */   public static Rectangle2D drawAlignedString(String text, Graphics2D g2, float x, float y, TextAnchor anchor) {
/* 385 */     Rectangle2D textBounds = new Rectangle2D.Double();
/* 386 */     float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor, textBounds);
/*     */ 
/*     */     
/* 389 */     textBounds.setRect((x + adjust[0]), (y + adjust[1] + adjust[2]), textBounds
/* 390 */         .getWidth(), textBounds.getHeight());
/* 391 */     if (!drawStringsWithFontAttributes) {
/* 392 */       g2.drawString(text, x + adjust[0], y + adjust[1]);
/*     */     } else {
/*     */       
/* 395 */       AttributedString as = new AttributedString(text, (Map)g2.getFont().getAttributes());
/* 396 */       g2.drawString(as.getIterator(), x + adjust[0], y + adjust[1]);
/*     */     } 
/* 398 */     return textBounds;
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
/*     */   private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2, String text, TextAnchor anchor, Rectangle2D textBounds) {
/* 420 */     float[] result = new float[3];
/* 421 */     FontRenderContext frc = g2.getFontRenderContext();
/* 422 */     Font f = g2.getFont();
/* 423 */     FontMetrics fm = g2.getFontMetrics(f);
/* 424 */     Rectangle2D bounds = getTextBounds(text, g2, fm);
/* 425 */     LineMetrics metrics = f.getLineMetrics(text, frc);
/* 426 */     float ascent = metrics.getAscent();
/* 427 */     result[2] = -ascent;
/* 428 */     float halfAscent = ascent / 2.0F;
/* 429 */     float descent = metrics.getDescent();
/* 430 */     float leading = metrics.getLeading();
/* 431 */     float xAdj = 0.0F;
/* 432 */     float yAdj = 0.0F;
/*     */     
/* 434 */     if (anchor.isHorizontalCenter()) {
/* 435 */       xAdj = (float)-bounds.getWidth() / 2.0F;
/*     */     }
/* 437 */     else if (anchor.isRight()) {
/* 438 */       xAdj = (float)-bounds.getWidth();
/*     */     } 
/*     */     
/* 441 */     if (anchor.isTop()) {
/* 442 */       yAdj = -descent - leading + (float)bounds.getHeight();
/*     */     }
/* 444 */     else if (anchor.isHalfAscent()) {
/* 445 */       yAdj = halfAscent;
/*     */     }
/* 447 */     else if (anchor.isVerticalCenter()) {
/* 448 */       yAdj = -descent - leading + (float)(bounds.getHeight() / 2.0D);
/*     */     }
/* 450 */     else if (anchor.isBaseline()) {
/* 451 */       yAdj = 0.0F;
/*     */     }
/* 453 */     else if (anchor.isBottom()) {
/* 454 */       yAdj = -metrics.getDescent() - metrics.getLeading();
/*     */     } 
/* 456 */     if (textBounds != null) {
/* 457 */       textBounds.setRect(bounds);
/*     */     }
/* 459 */     result[0] = xAdj;
/* 460 */     result[1] = yAdj;
/* 461 */     return result;
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
/*     */   public static void drawRotatedString(String text, Graphics2D g2, double angle, float x, float y) {
/* 479 */     drawRotatedString(text, g2, x, y, angle, x, y);
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
/*     */   public static void drawRotatedString(String text, Graphics2D g2, float textX, float textY, double angle, float rotateX, float rotateY) {
/* 500 */     if (text == null || text.equals("")) {
/*     */       return;
/*     */     }
/* 503 */     if (angle == 0.0D) {
/* 504 */       drawAlignedString(text, g2, textY, textY, TextAnchor.BASELINE_LEFT);
/*     */       
/*     */       return;
/*     */     } 
/* 508 */     AffineTransform saved = g2.getTransform();
/* 509 */     AffineTransform rotate = AffineTransform.getRotateInstance(angle, rotateX, rotateY);
/*     */     
/* 511 */     g2.transform(rotate);
/*     */     
/* 513 */     if (useDrawRotatedStringWorkaround) {
/*     */ 
/*     */       
/* 516 */       TextLayout tl = new TextLayout(text, g2.getFont(), g2.getFontRenderContext());
/* 517 */       tl.draw(g2, textX, textY);
/*     */     
/*     */     }
/* 520 */     else if (!drawStringsWithFontAttributes) {
/* 521 */       g2.drawString(text, textX, textY);
/*     */     } else {
/*     */       
/* 524 */       AttributedString as = new AttributedString(text, (Map)g2.getFont().getAttributes());
/* 525 */       g2.drawString(as.getIterator(), textX, textY);
/*     */     } 
/*     */     
/* 528 */     g2.setTransform(saved);
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
/*     */   public static void drawRotatedString(String text, Graphics2D g2, float x, float y, TextAnchor textAnchor, double angle, float rotationX, float rotationY) {
/* 549 */     if (text == null || text.equals("")) {
/*     */       return;
/*     */     }
/* 552 */     if (angle == 0.0D) {
/* 553 */       drawAlignedString(text, g2, x, y, textAnchor);
/*     */     } else {
/* 555 */       float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor);
/*     */       
/* 557 */       drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1], angle, rotationX, rotationY);
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
/*     */   public static void drawRotatedString(String text, Graphics2D g2, float x, float y, TextAnchor textAnchor, double angle, TextAnchor rotationAnchor) {
/* 578 */     if (text == null || text.equals("")) {
/*     */       return;
/*     */     }
/* 581 */     if (angle == 0.0D) {
/* 582 */       drawAlignedString(text, g2, x, y, textAnchor);
/*     */     } else {
/* 584 */       float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor);
/*     */       
/* 586 */       float[] rotateAdj = deriveRotationAnchorOffsets(g2, text, rotationAnchor);
/*     */       
/* 588 */       drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1], angle, x + textAdj[0] + rotateAdj[0], y + textAdj[1] + rotateAdj[1]);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Shape calculateRotatedStringBounds(String text, Graphics2D g2, float x, float y, TextAnchor textAnchor, double angle, TextAnchor rotationAnchor) {
/* 612 */     if (text == null || text.equals("")) {
/* 613 */       return null;
/*     */     }
/* 615 */     float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor);
/* 616 */     if (logger.isDebugEnabled()) {
/* 617 */       logger.debug("TextBoundsAnchorOffsets = " + textAdj[0] + ", " + textAdj[1]);
/*     */     }
/*     */     
/* 620 */     float[] rotateAdj = deriveRotationAnchorOffsets(g2, text, rotationAnchor);
/*     */     
/* 622 */     if (logger.isDebugEnabled()) {
/* 623 */       logger.debug("RotationAnchorOffsets = " + rotateAdj[0] + ", " + rotateAdj[1]);
/*     */     }
/*     */     
/* 626 */     Shape result = calculateRotatedStringBounds(text, g2, x + textAdj[0], y + textAdj[1], angle, x + textAdj[0] + rotateAdj[0], y + textAdj[1] + rotateAdj[1]);
/*     */ 
/*     */     
/* 629 */     return result;
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
/*     */   private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2, String text, TextAnchor anchor) {
/* 649 */     float[] result = new float[2];
/* 650 */     FontRenderContext frc = g2.getFontRenderContext();
/* 651 */     Font f = g2.getFont();
/* 652 */     FontMetrics fm = g2.getFontMetrics(f);
/* 653 */     Rectangle2D bounds = getTextBounds(text, g2, fm);
/* 654 */     LineMetrics metrics = f.getLineMetrics(text, frc);
/* 655 */     float ascent = metrics.getAscent();
/* 656 */     float halfAscent = ascent / 2.0F;
/* 657 */     float descent = metrics.getDescent();
/* 658 */     float leading = metrics.getLeading();
/* 659 */     float xAdj = 0.0F;
/* 660 */     float yAdj = 0.0F;
/*     */     
/* 662 */     if (anchor.isHorizontalCenter()) {
/* 663 */       xAdj = (float)-bounds.getWidth() / 2.0F;
/*     */     }
/* 665 */     else if (anchor.isRight()) {
/* 666 */       xAdj = (float)-bounds.getWidth();
/*     */     } 
/*     */     
/* 669 */     if (anchor.isTop()) {
/* 670 */       yAdj = -descent - leading + (float)bounds.getHeight();
/*     */     }
/* 672 */     else if (anchor.isHalfAscent()) {
/* 673 */       yAdj = halfAscent;
/*     */     }
/* 675 */     else if (anchor.isVerticalCenter()) {
/* 676 */       yAdj = -descent - leading + (float)(bounds.getHeight() / 2.0D);
/*     */     }
/* 678 */     else if (anchor.isBaseline()) {
/* 679 */       yAdj = 0.0F;
/*     */     }
/* 681 */     else if (anchor.isBottom()) {
/* 682 */       yAdj = -metrics.getDescent() - metrics.getLeading();
/*     */     } 
/* 684 */     result[0] = xAdj;
/* 685 */     result[1] = yAdj;
/* 686 */     return result;
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
/*     */   private static float[] deriveRotationAnchorOffsets(Graphics2D g2, String text, TextAnchor anchor) {
/* 704 */     float[] result = new float[2];
/* 705 */     FontRenderContext frc = g2.getFontRenderContext();
/* 706 */     LineMetrics metrics = g2.getFont().getLineMetrics(text, frc);
/* 707 */     FontMetrics fm = g2.getFontMetrics();
/* 708 */     Rectangle2D bounds = getTextBounds(text, g2, fm);
/* 709 */     float ascent = metrics.getAscent();
/* 710 */     float halfAscent = ascent / 2.0F;
/* 711 */     float descent = metrics.getDescent();
/* 712 */     float leading = metrics.getLeading();
/* 713 */     float xAdj = 0.0F;
/* 714 */     float yAdj = 0.0F;
/*     */     
/* 716 */     if (anchor.isLeft()) {
/* 717 */       xAdj = 0.0F;
/*     */     }
/* 719 */     else if (anchor.isHorizontalCenter()) {
/* 720 */       xAdj = (float)bounds.getWidth() / 2.0F;
/*     */     }
/* 722 */     else if (anchor.isRight()) {
/* 723 */       xAdj = (float)bounds.getWidth();
/*     */     } 
/*     */     
/* 726 */     if (anchor.isTop()) {
/* 727 */       yAdj = descent + leading - (float)bounds.getHeight();
/*     */     }
/* 729 */     else if (anchor.isVerticalCenter()) {
/* 730 */       yAdj = descent + leading - (float)(bounds.getHeight() / 2.0D);
/*     */     }
/* 732 */     else if (anchor.isHalfAscent()) {
/* 733 */       yAdj = -halfAscent;
/*     */     }
/* 735 */     else if (anchor.isBaseline()) {
/* 736 */       yAdj = 0.0F;
/*     */     }
/* 738 */     else if (anchor.isBottom()) {
/* 739 */       yAdj = metrics.getDescent() + metrics.getLeading();
/*     */     } 
/* 741 */     result[0] = xAdj;
/* 742 */     result[1] = yAdj;
/* 743 */     return result;
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
/*     */   public static Shape calculateRotatedStringBounds(String text, Graphics2D g2, float textX, float textY, double angle, float rotateX, float rotateY) {
/* 766 */     if (text == null || text.equals("")) {
/* 767 */       return null;
/*     */     }
/* 769 */     FontMetrics fm = g2.getFontMetrics();
/* 770 */     Rectangle2D bounds = getTextBounds(text, g2, fm);
/* 771 */     AffineTransform translate = AffineTransform.getTranslateInstance(textX, textY);
/*     */     
/* 773 */     Shape translatedBounds = translate.createTransformedShape(bounds);
/* 774 */     AffineTransform rotate = AffineTransform.getRotateInstance(angle, rotateX, rotateY);
/*     */     
/* 776 */     Shape result = rotate.createTransformedShape(translatedBounds);
/* 777 */     return result;
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
/*     */   public static boolean getUseFontMetricsGetStringBounds() {
/* 789 */     return useFontMetricsGetStringBounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUseFontMetricsGetStringBounds(boolean use) {
/* 800 */     useFontMetricsGetStringBounds = use;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUseDrawRotatedStringWorkaround() {
/* 810 */     return useDrawRotatedStringWorkaround;
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
/*     */   public static void setUseDrawRotatedStringWorkaround(boolean use) {
/* 823 */     useDrawRotatedStringWorkaround = use;
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
/*     */   public static boolean getDrawStringsWithFontAttributes() {
/* 836 */     return drawStringsWithFontAttributes;
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
/*     */   public static void setDrawStringsWithFontAttributes(boolean b) {
/* 850 */     drawStringsWithFontAttributes = b;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/text/TextUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */