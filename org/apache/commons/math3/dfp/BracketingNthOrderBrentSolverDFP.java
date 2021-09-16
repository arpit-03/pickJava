/*     */ package org.apache.commons.math3.dfp;
/*     */ 
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.analysis.RealFieldUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.solvers.AllowedSolution;
/*     */ import org.apache.commons.math3.analysis.solvers.FieldBracketingNthOrderBrentSolver;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class BracketingNthOrderBrentSolverDFP
/*     */   extends FieldBracketingNthOrderBrentSolver<Dfp>
/*     */ {
/*     */   public BracketingNthOrderBrentSolverDFP(Dfp relativeAccuracy, Dfp absoluteAccuracy, Dfp functionValueAccuracy, int maximalOrder) throws NumberIsTooSmallException {
/*  60 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, maximalOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getAbsoluteAccuracy() {
/*  69 */     return (Dfp)super.getAbsoluteAccuracy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getRelativeAccuracy() {
/*  78 */     return (Dfp)super.getRelativeAccuracy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getFunctionValueAccuracy() {
/*  87 */     return (Dfp)super.getFunctionValueAccuracy();
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
/*     */   public Dfp solve(int maxEval, UnivariateDfpFunction f, Dfp min, Dfp max, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
/* 109 */     return solve(maxEval, f, min, max, min.add(max).divide(2), allowedSolution);
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
/*     */ 
/*     */   
/*     */   public Dfp solve(int maxEval, final UnivariateDfpFunction f, Dfp min, Dfp max, Dfp startValue, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
/* 135 */     MathUtils.checkNotNull(f);
/*     */ 
/*     */     
/* 138 */     RealFieldUnivariateFunction<Dfp> fieldF = new RealFieldUnivariateFunction<Dfp>()
/*     */       {
/*     */         public Dfp value(Dfp x)
/*     */         {
/* 142 */           return f.value(x);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 147 */     return (Dfp)solve(maxEval, fieldF, min, max, startValue, allowedSolution);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/dfp/BracketingNthOrderBrentSolverDFP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */