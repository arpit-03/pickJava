/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.awt.Paint;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaintList
/*     */   extends AbstractObjectList
/*     */ {
/*     */   private static final long serialVersionUID = -708669381577938219L;
/*     */   
/*     */   public Paint getPaint(int index) {
/*  76 */     return (Paint)get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaint(int index, Paint paint) {
/*  86 */     set(index, paint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  97 */     if (obj == null) {
/*  98 */       return false;
/*     */     }
/* 100 */     if (obj == this) {
/* 101 */       return true;
/*     */     }
/* 103 */     if (!(obj instanceof PaintList)) {
/* 104 */       return false;
/*     */     }
/* 106 */     PaintList that = (PaintList)obj;
/* 107 */     int listSize = size();
/* 108 */     for (int i = 0; i < listSize; i++) {
/* 109 */       if (!PaintUtilities.equal(getPaint(i), that.getPaint(i))) {
/* 110 */         return false;
/*     */       }
/*     */     } 
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return super.hashCode();
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
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 134 */     stream.defaultWriteObject();
/* 135 */     int count = size();
/* 136 */     stream.writeInt(count);
/* 137 */     for (int i = 0; i < count; i++) {
/* 138 */       Paint paint = getPaint(i);
/* 139 */       if (paint != null) {
/* 140 */         stream.writeInt(i);
/* 141 */         SerialUtilities.writePaint(paint, stream);
/*     */       } else {
/*     */         
/* 144 */         stream.writeInt(-1);
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
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 160 */     stream.defaultReadObject();
/* 161 */     int count = stream.readInt();
/* 162 */     for (int i = 0; i < count; i++) {
/* 163 */       int index = stream.readInt();
/* 164 */       if (index != -1)
/* 165 */         setPaint(index, SerialUtilities.readPaint(stream)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/PaintList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */