/*     */ package us.hebi.matlab.mat.ejml;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.ejml.data.BMatrixRMaj;
/*     */ import org.ejml.data.CMatrix;
/*     */ import org.ejml.data.DMatrix;
/*     */ import org.ejml.data.DMatrixD1;
/*     */ import org.ejml.data.DMatrixSparseCSC;
/*     */ import org.ejml.data.DMatrixSparseTriplet;
/*     */ import org.ejml.data.FMatrix;
/*     */ import org.ejml.data.FMatrixSparseCSC;
/*     */ import org.ejml.data.FMatrixSparseTriplet;
/*     */ import org.ejml.data.Matrix;
/*     */ import org.ejml.data.MatrixSparse;
/*     */ import org.ejml.data.ReshapeMatrix;
/*     */ import org.ejml.data.ZMatrix;
/*     */ import org.ejml.data.ZMatrixD1;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ import us.hebi.matlab.mat.types.Sparse;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mat5Ejml
/*     */ {
/*     */   public static Array asArray(Matrix matrix) {
/*  49 */     Preconditions.checkNotNull(matrix, "Input matrix can't be null");
/*     */ 
/*     */     
/*  52 */     if (matrix instanceof DMatrixSparseCSC)
/*  53 */       return (Array)new DMatrixSparseCSCWrapper((DMatrixSparseCSC)matrix); 
/*  54 */     if (matrix instanceof FMatrixSparseCSC)
/*  55 */       return (Array)new FMatrixSparseCSCWrapper((FMatrixSparseCSC)matrix); 
/*  56 */     if (matrix instanceof MatrixSparse) {
/*  57 */       throw new IllegalArgumentException("Unsupported Sparse Matrix Type: " + matrix.getClass().getSimpleName());
/*     */     }
/*     */     
/*  60 */     if (matrix instanceof DMatrix)
/*  61 */       return (Array)new DMatrixWrapper((DMatrix)matrix); 
/*  62 */     if (matrix instanceof FMatrix) {
/*  63 */       return (Array)new FMatrixWrapper((FMatrix)matrix);
/*     */     }
/*     */     
/*  66 */     if (matrix instanceof ZMatrix)
/*  67 */       return (Array)new ZMatrixWrapper((ZMatrix)matrix); 
/*  68 */     if (matrix instanceof CMatrix) {
/*  69 */       return (Array)new CMatrixWrapper((CMatrix)matrix);
/*     */     }
/*     */     
/*  72 */     if (matrix instanceof BMatrixRMaj) {
/*  73 */       return (Array)new BMatrixRMajWrapper((BMatrixRMaj)matrix);
/*     */     }
/*  75 */     throw new IllegalArgumentException("Unsupported Dense Matrix Type: " + matrix.getClass().getSimpleName());
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
/*     */   public static <T extends Matrix> T convert(Array input, T output) {
/*  93 */     Preconditions.checkNotNull(input, "Input array can't be null");
/*  94 */     Preconditions.checkNotNull(output, "Output matrix can't be null");
/*  95 */     Preconditions.checkArgument(input instanceof Matrix, "Input Array is not a Matrix type");
/*  96 */     Matrix array = (Matrix)input;
/*     */ 
/*     */     
/*  99 */     reshapeOutputSize(array, (Matrix)output);
/*     */ 
/*     */     
/* 102 */     if (array instanceof Sparse && output instanceof MatrixSparse) {
/*     */ 
/*     */       
/* 105 */       if (output instanceof DMatrixSparseCSC) {
/* 106 */         convertToDMatrixSparseCSC((Sparse)array, (DMatrixSparseCSC)output);
/* 107 */       } else if (output instanceof FMatrixSparseCSC) {
/* 108 */         convertToFMatrixSparseCSC((Sparse)array, (FMatrixSparseCSC)output);
/*     */       
/*     */       }
/* 111 */       else if (output instanceof DMatrixSparseTriplet) {
/* 112 */         convertToDMatrixSparseTriplet((Sparse)array, (DMatrixSparseTriplet)output);
/* 113 */       } else if (output instanceof FMatrixSparseTriplet) {
/* 114 */         convertToFMatrixSparseTriplet((Sparse)array, (FMatrixSparseTriplet)output);
/*     */       } else {
/* 116 */         throw new IllegalArgumentException("Unsupported sparse output type: " + output.getClass());
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 121 */     else if (array instanceof Sparse && output instanceof DMatrixD1) {
/* 122 */       convertToDMatrix((Sparse)array, (DMatrixD1)output);
/* 123 */     } else if (array instanceof Sparse && output instanceof ZMatrixD1) {
/* 124 */       convertToZMatrix((Sparse)array, (ZMatrixD1)output);
/*     */     
/*     */     }
/* 127 */     else if (output instanceof DMatrix) {
/* 128 */       convertToDMatrix(array, (DMatrix)output);
/* 129 */     } else if (output instanceof ZMatrix) {
/* 130 */       convertToZMatrix(array, (ZMatrix)output);
/*     */     
/*     */     }
/* 133 */     else if (output instanceof FMatrix) {
/* 134 */       convertToFMatrix(array, (FMatrix)output);
/* 135 */     } else if (output instanceof CMatrix) {
/* 136 */       convertToCMatrix(array, (CMatrix)output);
/*     */     
/*     */     }
/* 139 */     else if (output instanceof BMatrixRMaj) {
/* 140 */       convertToBMatrixRMaj(array, (BMatrixRMaj)output);
/*     */     } else {
/*     */       
/* 143 */       throw new IllegalArgumentException("Unsupported dense output type: " + output.getClass());
/*     */     } 
/*     */ 
/*     */     
/* 147 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void reshapeOutputSize(Matrix input, Matrix output) {
/* 152 */     Preconditions.checkNotNull(input);
/* 153 */     Preconditions.checkNotNull(output);
/* 154 */     Preconditions.checkArgument((input.getNumDimensions() == 2), "EJML only supports 2D matrices");
/* 155 */     if (input.getNumRows() != output.getNumRows() || input.getNumCols() != output.getNumCols())
/*     */     {
/* 157 */       if (input instanceof Sparse && output instanceof MatrixSparse) {
/* 158 */         ((MatrixSparse)output).reshape(input.getNumRows(), input.getNumCols(), ((Sparse)input).getNzMax());
/* 159 */       } else if (output instanceof ReshapeMatrix) {
/* 160 */         ((ReshapeMatrix)output).reshape(input.getNumRows(), input.getNumCols());
/*     */       } else {
/* 162 */         throw new IllegalArgumentException("Output matrix has incorrect size and can't be reshaped");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void convertToBMatrixRMaj(Matrix input, BMatrixRMaj output) {
/* 169 */     reshapeOutputSize(input, (Matrix)output);
/* 170 */     int rows = input.getNumRows();
/* 171 */     int cols = input.getNumCols();
/* 172 */     for (int col = 0; col < cols; col++) {
/* 173 */       for (int row = 0; row < rows; row++) {
/* 174 */         output.unsafe_set(row, col, input.getBoolean(row, col));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void convertToFMatrix(Matrix input, FMatrix output) {
/* 180 */     reshapeOutputSize(input, (Matrix)output);
/* 181 */     int rows = input.getNumRows();
/* 182 */     int cols = input.getNumCols();
/* 183 */     for (int col = 0; col < cols; col++) {
/* 184 */       for (int row = 0; row < rows; row++) {
/* 185 */         output.unsafe_set(row, col, input.getFloat(row, col));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void convertToDMatrix(Matrix input, DMatrix output) {
/* 191 */     reshapeOutputSize(input, (Matrix)output);
/* 192 */     int rows = input.getNumRows();
/* 193 */     int cols = input.getNumCols();
/* 194 */     for (int col = 0; col < cols; col++) {
/* 195 */       for (int row = 0; row < rows; row++) {
/* 196 */         output.unsafe_set(row, col, input.getDouble(row, col));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void convertToCMatrix(Matrix input, CMatrix output) {
/* 202 */     reshapeOutputSize(input, (Matrix)output);
/* 203 */     int rows = input.getNumRows();
/* 204 */     int cols = input.getNumCols();
/* 205 */     for (int col = 0; col < cols; col++) {
/* 206 */       for (int row = 0; row < rows; row++) {
/* 207 */         output.setReal(row, col, input.getFloat(row, col));
/* 208 */         output.setImag(row, col, input.getImaginaryFloat(row, col));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void convertToZMatrix(Matrix input, ZMatrix output) {
/* 214 */     reshapeOutputSize(input, (Matrix)output);
/* 215 */     int rows = input.getNumRows();
/* 216 */     int cols = input.getNumCols();
/* 217 */     for (int col = 0; col < cols; col++) {
/* 218 */       for (int row = 0; row < rows; row++) {
/* 219 */         output.setReal(row, col, input.getDouble(row, col));
/* 220 */         output.setImag(row, col, input.getImaginaryDouble(row, col));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void convertToDMatrix(Sparse input, final DMatrixD1 output) {
/* 226 */     reshapeOutputSize((Matrix)input, (Matrix)output);
/* 227 */     Arrays.fill(output.data, 0, output.getNumElements(), 0.0D);
/* 228 */     input.forEach(new Sparse.SparseConsumer()
/*     */         {
/*     */           public void accept(int row, int col, double real, double imaginary) {
/* 231 */             output.unsafe_set(row, col, real);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void convertToZMatrix(Sparse input, final ZMatrixD1 output) {
/* 237 */     reshapeOutputSize((Matrix)input, (Matrix)output);
/* 238 */     Arrays.fill(output.data, 0, output.getDataLength(), 0.0D);
/* 239 */     input.forEach(new Sparse.SparseConsumer()
/*     */         {
/*     */           public void accept(int row, int col, double real, double imaginary) {
/* 242 */             output.setReal(row, col, real);
/* 243 */             output.setImag(row, col, imaginary);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void convertToFMatrixSparseTriplet(Sparse input, final FMatrixSparseTriplet output) {
/* 249 */     reshapeOutputSize((Matrix)input, (Matrix)output);
/* 250 */     output.zero();
/* 251 */     input.forEach(new Sparse.SparseConsumer()
/*     */         {
/*     */           public void accept(int row, int col, double real, double imaginary) {
/* 254 */             output.addItem(row, col, (float)real);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void convertToDMatrixSparseTriplet(Sparse input, final DMatrixSparseTriplet output) {
/* 260 */     reshapeOutputSize((Matrix)input, (Matrix)output);
/* 261 */     output.zero();
/* 262 */     input.forEach(new Sparse.SparseConsumer()
/*     */         {
/*     */           public void accept(int row, int col, double real, double imaginary) {
/* 265 */             output.addItem(row, col, real);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void convertToFMatrixSparseCSC(Sparse input, FMatrixSparseCSC output) {
/* 271 */     SparseToCscConverter.convertToFMatrixSparseCSC(input, output);
/*     */   }
/*     */   
/*     */   private static void convertToDMatrixSparseCSC(Sparse input, DMatrixSparseCSC output) {
/* 275 */     SparseToCscConverter.convertToDMatrixSparseCSC(input, output);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/Mat5Ejml.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */