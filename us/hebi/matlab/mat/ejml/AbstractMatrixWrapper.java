/*     */ package us.hebi.matlab.mat.ejml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.ejml.data.Matrix;
/*     */ import us.hebi.matlab.mat.format.Mat5;
/*     */ import us.hebi.matlab.mat.format.Mat5Serializable;
/*     */ import us.hebi.matlab.mat.format.Mat5WriteUtil;
/*     */ import us.hebi.matlab.mat.types.AbstractArray;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractMatrixWrapper<M extends Matrix>
/*     */   extends AbstractArray
/*     */   implements Mat5Serializable, Mat5Serializable.Mat5Attributes
/*     */ {
/*     */   protected final M matrix;
/*     */   
/*     */   public int getMat5Size(String name) {
/*  46 */     return 8 + 
/*  47 */       Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this) + 
/*  48 */       getMat5DataSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/*  53 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/*  54 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/*  55 */     writeMat5Data(sink);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getMat5DataSize();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void writeMat5Data(Sink paramSink) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLogical() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplex() {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNzMax() {
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getDimensions() {
/*  85 */     this.dims[0] = this.matrix.getNumRows();
/*  86 */     this.dims[1] = this.matrix.getNumCols();
/*  87 */     return this.dims;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {}
/*     */ 
/*     */   
/*     */   protected int subHashCode() {
/*  96 */     return this.matrix.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 101 */     CMatrixWrapper other = (CMatrixWrapper)otherGuaranteedSameClass;
/* 102 */     return other.matrix.equals(this.matrix);
/*     */   }
/*     */   
/*     */   protected AbstractMatrixWrapper(M matrix) {
/* 106 */     super(Mat5.dims(matrix.getNumRows(), matrix.getNumCols()));
/* 107 */     this.matrix = matrix;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/AbstractMatrixWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */