/*     */ package org.jfree.chart.entity;
/*     */ 
/*     */ import java.awt.Shape;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.Axis;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
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
/*     */ public class AxisEntity
/*     */   extends ChartEntity
/*     */ {
/*     */   private static final long serialVersionUID = -4445994133561919083L;
/*     */   private Axis axis;
/*     */   
/*     */   public AxisEntity(Shape area, Axis axis) {
/*  78 */     this(area, axis, (String)null);
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
/*     */   public AxisEntity(Shape area, Axis axis, String toolTipText) {
/*  90 */     this(area, axis, toolTipText, (String)null);
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
/*     */   public AxisEntity(Shape area, Axis axis, String toolTipText, String urlText) {
/* 104 */     super(area, toolTipText, urlText);
/* 105 */     ParamChecks.nullNotPermitted(axis, "axis");
/* 106 */     this.axis = axis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Axis getAxis() {
/* 115 */     return this.axis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     StringBuilder sb = new StringBuilder("AxisEntity: ");
/* 127 */     sb.append("tooltip = ");
/* 128 */     sb.append(getToolTipText());
/* 129 */     return sb.toString();
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
/* 141 */     if (obj == this) {
/* 142 */       return true;
/*     */     }
/* 144 */     if (!(obj instanceof AxisEntity)) {
/* 145 */       return false;
/*     */     }
/* 147 */     AxisEntity that = (AxisEntity)obj;
/* 148 */     if (!getArea().equals(that.getArea())) {
/* 149 */       return false;
/*     */     }
/* 151 */     if (!ObjectUtilities.equal(getToolTipText(), that.getToolTipText())) {
/* 152 */       return false;
/*     */     }
/* 154 */     if (!ObjectUtilities.equal(getURLText(), that.getURLText())) {
/* 155 */       return false;
/*     */     }
/* 157 */     if (!this.axis.equals(that.axis)) {
/* 158 */       return false;
/*     */     }
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 170 */     int result = 39;
/* 171 */     result = HashUtilities.hashCode(result, getToolTipText());
/* 172 */     result = HashUtilities.hashCode(result, getURLText());
/* 173 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 186 */     return super.clone();
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
/* 197 */     stream.defaultWriteObject();
/* 198 */     SerialUtilities.writeShape(getArea(), stream);
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
/* 211 */     stream.defaultReadObject();
/* 212 */     setArea(SerialUtilities.readShape(stream));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/entity/AxisEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */