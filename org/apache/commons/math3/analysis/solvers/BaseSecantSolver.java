/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseSecantSolver
/*     */   extends AbstractUnivariateSolver
/*     */   implements BracketedUnivariateSolver<UnivariateFunction>
/*     */ {
/*     */   protected static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   private AllowedSolution allowed;
/*     */   private final Method method;
/*     */   
/*     */   protected BaseSecantSolver(double absoluteAccuracy, Method method) {
/*  68 */     super(absoluteAccuracy);
/*  69 */     this.allowed = AllowedSolution.ANY_SIDE;
/*  70 */     this.method = method;
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
/*     */   protected BaseSecantSolver(double relativeAccuracy, double absoluteAccuracy, Method method) {
/*  83 */     super(relativeAccuracy, absoluteAccuracy);
/*  84 */     this.allowed = AllowedSolution.ANY_SIDE;
/*  85 */     this.method = method;
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
/*     */   protected BaseSecantSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy, Method method) {
/* 100 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
/* 101 */     this.allowed = AllowedSolution.ANY_SIDE;
/* 102 */     this.method = method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, UnivariateFunction f, double min, double max, AllowedSolution allowedSolution) {
/* 109 */     return solve(maxEval, f, min, max, min + 0.5D * (max - min), allowedSolution);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, UnivariateFunction f, double min, double max, double startValue, AllowedSolution allowedSolution) {
/* 116 */     this.allowed = allowedSolution;
/* 117 */     return super.solve(maxEval, f, min, max, startValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, UnivariateFunction f, double min, double max, double startValue) {
/* 124 */     return solve(maxEval, f, min, max, startValue, AllowedSolution.ANY_SIDE);
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
/*     */   protected final double doSolve() throws ConvergenceException {
/* 137 */     double x0 = getMin();
/* 138 */     double x1 = getMax();
/* 139 */     double f0 = computeObjectiveValue(x0);
/* 140 */     double f1 = computeObjectiveValue(x1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (f0 == 0.0D) {
/* 146 */       return x0;
/*     */     }
/* 148 */     if (f1 == 0.0D) {
/* 149 */       return x1;
/*     */     }
/*     */ 
/*     */     
/* 153 */     verifyBracketing(x0, x1);
/*     */ 
/*     */     
/* 156 */     double ftol = getFunctionValueAccuracy();
/* 157 */     double atol = getAbsoluteAccuracy();
/* 158 */     double rtol = getRelativeAccuracy();
/*     */ 
/*     */ 
/*     */     
/* 162 */     boolean inverted = false;
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 167 */       double x = x1 - f1 * (x1 - x0) / (f1 - f0);
/* 168 */       double fx = computeObjectiveValue(x);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       if (fx == 0.0D) {
/* 174 */         return x;
/*     */       }
/*     */ 
/*     */       
/* 178 */       if (f1 * fx < 0.0D) {
/*     */ 
/*     */         
/* 181 */         x0 = x1;
/* 182 */         f0 = f1;
/* 183 */         inverted = !inverted;
/*     */       } else {
/* 185 */         switch (this.method) {
/*     */           case ANY_SIDE:
/* 187 */             f0 *= 0.5D;
/*     */             break;
/*     */           case LEFT_SIDE:
/* 190 */             f0 *= f1 / (f1 + fx);
/*     */             break;
/*     */ 
/*     */           
/*     */           case RIGHT_SIDE:
/* 195 */             if (x == x1) {
/* 196 */               throw new ConvergenceException();
/*     */             }
/*     */             break;
/*     */           
/*     */           default:
/* 201 */             throw new MathInternalError();
/*     */         } 
/*     */       
/*     */       } 
/* 205 */       x1 = x;
/* 206 */       f1 = fx;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       if (FastMath.abs(f1) > ftol)
/* 212 */         continue;  switch (this.allowed) {
/*     */         case ANY_SIDE:
/* 214 */           return x1;
/*     */         case LEFT_SIDE:
/* 216 */           if (inverted) {
/* 217 */             return x1;
/*     */           }
/*     */           break;
/*     */         case RIGHT_SIDE:
/* 221 */           if (!inverted) {
/* 222 */             return x1;
/*     */           }
/*     */           break;
/*     */         case BELOW_SIDE:
/* 226 */           if (f1 <= 0.0D) {
/* 227 */             return x1;
/*     */           }
/*     */           break;
/*     */         case ABOVE_SIDE:
/* 231 */           if (f1 >= 0.0D) {
/* 232 */             return x1;
/*     */           }
/*     */           break;
/*     */         default:
/* 236 */           throw new MathInternalError();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     } while (FastMath.abs(x1 - x0) >= FastMath.max(rtol * FastMath.abs(x1), atol));
/*     */     
/* 244 */     switch (this.allowed) {
/*     */       case ANY_SIDE:
/* 246 */         return x1;
/*     */       case LEFT_SIDE:
/* 248 */         return inverted ? x1 : x0;
/*     */       case RIGHT_SIDE:
/* 250 */         return inverted ? x0 : x1;
/*     */       case BELOW_SIDE:
/* 252 */         return (f1 <= 0.0D) ? x1 : x0;
/*     */       case ABOVE_SIDE:
/* 254 */         return (f1 >= 0.0D) ? x1 : x0;
/*     */     } 
/* 256 */     throw new MathInternalError();
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
/*     */   protected enum Method
/*     */   {
/* 269 */     REGULA_FALSI,
/*     */ 
/*     */     
/* 272 */     ILLINOIS,
/*     */ 
/*     */     
/* 275 */     PEGASUS;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BaseSecantSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */