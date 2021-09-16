/*     */ package org.la4j;
/*     */ 
/*     */ import org.la4j.decomposition.CholeskyDecompositor;
/*     */ import org.la4j.decomposition.EigenDecompositor;
/*     */ import org.la4j.decomposition.LUDecompositor;
/*     */ import org.la4j.decomposition.MatrixDecompositor;
/*     */ import org.la4j.decomposition.QRDecompositor;
/*     */ import org.la4j.decomposition.RawLUDecompositor;
/*     */ import org.la4j.decomposition.RawQRDecompositor;
/*     */ import org.la4j.decomposition.SingularValueDecompositor;
/*     */ import org.la4j.inversion.GaussJordanInverter;
/*     */ import org.la4j.inversion.MatrixInverter;
/*     */ import org.la4j.inversion.NoPivotGaussInverter;
/*     */ import org.la4j.linear.ForwardBackSubstitutionSolver;
/*     */ import org.la4j.linear.GaussianSolver;
/*     */ import org.la4j.linear.JacobiSolver;
/*     */ import org.la4j.linear.LeastSquaresSolver;
/*     */ import org.la4j.linear.LinearSystemSolver;
/*     */ import org.la4j.linear.SeidelSolver;
/*     */ import org.la4j.linear.SquareRootSolver;
/*     */ import org.la4j.linear.SweepSolver;
/*     */ import org.la4j.operation.MatrixMatrixOperation;
/*     */ import org.la4j.operation.MatrixOperation;
/*     */ import org.la4j.operation.MatrixVectorOperation;
/*     */ import org.la4j.operation.VectorMatrixOperation;
/*     */ import org.la4j.operation.VectorVectorOperation;
/*     */ import org.la4j.operation.inplace.InPlaceCopyMatrixToMatrix;
/*     */ import org.la4j.operation.ooplace.OoPlaceInnerProduct;
/*     */ import org.la4j.operation.ooplace.OoPlaceKroneckerProduct;
/*     */ import org.la4j.operation.ooplace.OoPlaceMatricesAddition;
/*     */ import org.la4j.operation.ooplace.OoPlaceMatricesMultiplication;
/*     */ import org.la4j.operation.ooplace.OoPlaceMatricesSubtraction;
/*     */ import org.la4j.operation.ooplace.OoPlaceMatrixByItsTransposeMultiplication;
/*     */ import org.la4j.operation.ooplace.OoPlaceMatrixByVectorMultiplication;
/*     */ import org.la4j.operation.ooplace.OoPlaceMatrixHadamardProduct;
/*     */ import org.la4j.operation.ooplace.OoPlaceOuterProduct;
/*     */ import org.la4j.operation.ooplace.OoPlaceVectorByMatrixMultiplication;
/*     */ import org.la4j.operation.ooplace.OoPlaceVectorHadamardProduct;
/*     */ import org.la4j.operation.ooplace.OoPlaceVectorsAddition;
/*     */ import org.la4j.operation.ooplace.OoPlaceVectorsSubtraction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LinearAlgebra
/*     */ {
/*     */   public static final String VERSION = "0.5.5";
/*     */   public static final String NAME = "la4j";
/*     */   public static final String DATE = "March 2015";
/*     */   public static final String FULL_NAME = "la4j-0.5.5 (March 2015)";
/*     */   public static final double EPS;
/*     */   public static final int ROUND_FACTOR;
/*     */   
/*     */   static {
/* 100 */     int roundFactor = 0;
/* 101 */     double eps = 1.0D;
/* 102 */     while (1.0D + eps > 1.0D) {
/* 103 */       eps /= 2.0D;
/* 104 */       roundFactor++;
/*     */     } 
/* 106 */     EPS = eps * 100.0D;
/* 107 */     ROUND_FACTOR = roundFactor - 1;
/*     */   }
/*     */   
/*     */   public enum SolverFactory {
/* 111 */     GAUSSIAN
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix) {
/* 114 */         return (LinearSystemSolver)new GaussianSolver(matrix);
/*     */       }
/*     */     },
/* 117 */     JACOBI
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix) {
/* 120 */         return (LinearSystemSolver)new JacobiSolver(matrix);
/*     */       }
/*     */     },
/* 123 */     SEIDEL
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix) {
/* 126 */         return (LinearSystemSolver)new SeidelSolver(matrix);
/*     */       }
/*     */     },
/* 129 */     FORWARD_BACK_SUBSTITUTION
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix) {
/* 132 */         return (LinearSystemSolver)new ForwardBackSubstitutionSolver(matrix);
/*     */       }
/*     */     },
/* 135 */     LEAST_SQUARES
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix) {
/* 138 */         return (LinearSystemSolver)new LeastSquaresSolver(matrix);
/*     */       }
/*     */     },
/* 141 */     SQUARE_ROOT
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix) {
/* 144 */         return (LinearSystemSolver)new SquareRootSolver(matrix);
/*     */       }
/*     */     },
/* 147 */     SWEEP
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix) {
/* 150 */         return (LinearSystemSolver)new SweepSolver(matrix);
/*     */       }
/*     */     },
/* 153 */     SMART
/*     */     {
/*     */       public LinearSystemSolver create(Matrix matrix)
/*     */       {
/* 157 */         if (matrix.rows() == matrix.columns())
/* 158 */           return (LinearSystemSolver)new ForwardBackSubstitutionSolver(matrix); 
/* 159 */         if (matrix.rows() > matrix.columns()) {
/* 160 */           return (LinearSystemSolver)new LeastSquaresSolver(matrix);
/*     */         }
/*     */         
/* 163 */         throw new IllegalArgumentException("Underdetermined system of linear equations can not be solved.");
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract LinearSystemSolver create(Matrix param1Matrix);
/*     */   }
/*     */ 
/*     */   
/* 173 */   public static final SolverFactory GAUSSIAN = SolverFactory.GAUSSIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public static final SolverFactory JACOBI = SolverFactory.JACOBI;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public static final SolverFactory SEIDEL = SolverFactory.SEIDEL;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public static final SolverFactory LEAST_SQUARES = SolverFactory.LEAST_SQUARES;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static final SolverFactory FORWARD_BACK_SUBSTITUTION = SolverFactory.FORWARD_BACK_SUBSTITUTION;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public static final SolverFactory SQUARE_ROOT = SolverFactory.SQUARE_ROOT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public static final SolverFactory SOLVER = SolverFactory.SMART;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public static final SolverFactory SWEEP = SolverFactory.SWEEP;
/*     */   
/*     */   public enum InverterFactory {
/* 211 */     GAUSS_JORDAN
/*     */     {
/*     */       public MatrixInverter create(Matrix matrix) {
/* 214 */         return (MatrixInverter)new GaussJordanInverter(matrix);
/*     */       }
/*     */     },
/* 217 */     NO_PIVOT_GAUSS
/*     */     {
/*     */       public MatrixInverter create(Matrix matrix) {
/* 220 */         return (MatrixInverter)new NoPivotGaussInverter(matrix);
/*     */       }
/*     */     },
/* 223 */     SMART
/*     */     {
/*     */       public MatrixInverter create(Matrix matrix) {
/* 226 */         return (MatrixInverter)new GaussJordanInverter(matrix);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract MatrixInverter create(Matrix param1Matrix);
/*     */   }
/*     */ 
/*     */   
/* 236 */   public static final InverterFactory GAUSS_JORDAN = InverterFactory.GAUSS_JORDAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public static final InverterFactory NO_PIVOT_GAUSS = InverterFactory.NO_PIVOT_GAUSS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public static final InverterFactory INVERTER = InverterFactory.SMART;
/*     */   
/*     */   public enum DecompositorFactory {
/* 253 */     CHOLESKY
/*     */     {
/*     */       public MatrixDecompositor create(Matrix matrix) {
/* 256 */         return (MatrixDecompositor)new CholeskyDecompositor(matrix);
/*     */       }
/*     */     },
/* 259 */     EIGEN
/*     */     {
/*     */       public MatrixDecompositor create(Matrix matrix) {
/* 262 */         return (MatrixDecompositor)new EigenDecompositor(matrix);
/*     */       }
/*     */     },
/* 265 */     RAW_LU
/*     */     {
/*     */       public MatrixDecompositor create(Matrix matrix) {
/* 268 */         return (MatrixDecompositor)new RawLUDecompositor(matrix);
/*     */       }
/*     */     },
/* 271 */     LU
/*     */     {
/*     */       public MatrixDecompositor create(Matrix matrix) {
/* 274 */         return (MatrixDecompositor)new LUDecompositor(matrix);
/*     */       }
/*     */     },
/* 277 */     RAW_QR
/*     */     {
/*     */       public MatrixDecompositor create(Matrix matrix) {
/* 280 */         return (MatrixDecompositor)new RawQRDecompositor(matrix);
/*     */       }
/*     */     },
/* 283 */     QR
/*     */     {
/*     */       public MatrixDecompositor create(Matrix matrix) {
/* 286 */         return (MatrixDecompositor)new QRDecompositor(matrix);
/*     */       }
/*     */     },
/* 289 */     SVD
/*     */     {
/*     */       public MatrixDecompositor create(Matrix matrix) {
/* 292 */         return (MatrixDecompositor)new SingularValueDecompositor(matrix);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract MatrixDecompositor create(Matrix param1Matrix);
/*     */   }
/*     */ 
/*     */   
/* 302 */   public static final DecompositorFactory CHOLESKY = DecompositorFactory.CHOLESKY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public static final DecompositorFactory EIGEN = DecompositorFactory.EIGEN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   public static final DecompositorFactory RAW_LU = DecompositorFactory.RAW_LU;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public static final DecompositorFactory LU = DecompositorFactory.LU;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 322 */   public static final DecompositorFactory RAW_QR = DecompositorFactory.RAW_QR;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 327 */   public static final DecompositorFactory QR = DecompositorFactory.QR;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 332 */   public static final DecompositorFactory SVD = DecompositorFactory.SVD;
/*     */   
/* 334 */   public static final VectorVectorOperation<Double> OO_PLACE_INNER_PRODUCT = (VectorVectorOperation<Double>)new OoPlaceInnerProduct();
/*     */ 
/*     */   
/* 337 */   public static final VectorVectorOperation<Vector> OO_PLACE_VECTORS_ADDITION = (VectorVectorOperation<Vector>)new OoPlaceVectorsAddition();
/*     */ 
/*     */   
/* 340 */   public static final VectorVectorOperation<Vector> OO_PLACE_VECTOR_HADAMARD_PRODUCT = (VectorVectorOperation<Vector>)new OoPlaceVectorHadamardProduct();
/*     */ 
/*     */   
/* 343 */   public static final VectorVectorOperation<Vector> OO_PLACE_VECTORS_SUBTRACTION = (VectorVectorOperation<Vector>)new OoPlaceVectorsSubtraction();
/*     */ 
/*     */   
/* 346 */   public static final VectorMatrixOperation<Vector> OO_PLACE_VECTOR_BY_MATRIX_MULTIPLICATION = (VectorMatrixOperation<Vector>)new OoPlaceVectorByMatrixMultiplication();
/*     */ 
/*     */   
/* 349 */   public static final VectorVectorOperation<Matrix> OO_PLACE_OUTER_PRODUCT = (VectorVectorOperation<Matrix>)new OoPlaceOuterProduct();
/*     */ 
/*     */   
/* 352 */   public static final MatrixMatrixOperation<Matrix> IN_PLACE_COPY_MATRIX_TO_MATRIX = (MatrixMatrixOperation<Matrix>)new InPlaceCopyMatrixToMatrix();
/*     */ 
/*     */   
/* 355 */   public static final MatrixMatrixOperation<Matrix> OO_PLACE_MATRIX_ADDITION = (MatrixMatrixOperation<Matrix>)new OoPlaceMatricesAddition();
/*     */ 
/*     */   
/* 358 */   public static final MatrixVectorOperation<Vector> OO_PLACE_MATRIX_BY_VECTOR_MULTIPLICATION = (MatrixVectorOperation<Vector>)new OoPlaceMatrixByVectorMultiplication();
/*     */ 
/*     */   
/* 361 */   public static final MatrixMatrixOperation<Matrix> OO_PLACE_MATRICES_SUBTRACTION = (MatrixMatrixOperation<Matrix>)new OoPlaceMatricesSubtraction();
/*     */ 
/*     */   
/* 364 */   public static final MatrixMatrixOperation<Matrix> OO_PLACE_MATRIX_HADAMARD_PRODUCT = (MatrixMatrixOperation<Matrix>)new OoPlaceMatrixHadamardProduct();
/*     */ 
/*     */   
/* 367 */   public static final MatrixOperation<Matrix> OO_PLACE_MATRIX_BY_ITS_TRANSPOSE_MULTIPLICATION = (MatrixOperation<Matrix>)new OoPlaceMatrixByItsTransposeMultiplication();
/*     */ 
/*     */   
/* 370 */   public static final MatrixMatrixOperation<Matrix> OO_PLACE_KRONECKER_PRODUCT = (MatrixMatrixOperation<Matrix>)new OoPlaceKroneckerProduct();
/*     */ 
/*     */   
/* 373 */   public static final MatrixMatrixOperation<Matrix> OO_PLACE_MATRICES_MULTIPLICATION = (MatrixMatrixOperation<Matrix>)new OoPlaceMatricesMultiplication();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/LinearAlgebra.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */