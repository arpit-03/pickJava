/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.ExceptionContext;
/*     */ import org.apache.commons.math3.util.IterationManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConjugateGradient
/*     */   extends PreconditionedIterativeLinearSolver
/*     */ {
/*     */   public static final String OPERATOR = "operator";
/*     */   public static final String VECTOR = "vector";
/*     */   private boolean check;
/*     */   private final double delta;
/*     */   
/*     */   public ConjugateGradient(int maxIterations, double delta, boolean check) {
/* 107 */     super(maxIterations);
/* 108 */     this.delta = delta;
/* 109 */     this.check = check;
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
/*     */   public ConjugateGradient(IterationManager manager, double delta, boolean check) throws NullArgumentException {
/* 125 */     super(manager);
/* 126 */     this.delta = delta;
/* 127 */     this.check = check;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getCheck() {
/* 137 */     return this.check;
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
/*     */   public RealVector solveInPlace(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x0) throws NullArgumentException, NonPositiveDefiniteOperatorException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
/* 154 */     checkParameters(a, m, b, x0);
/* 155 */     IterationManager manager = getIterationManager();
/*     */     
/* 157 */     manager.resetIterationCount();
/* 158 */     double rmax = this.delta * b.getNorm();
/* 159 */     RealVector bro = RealVector.unmodifiableRealVector(b);
/*     */ 
/*     */     
/* 162 */     manager.incrementIterationCount();
/*     */ 
/*     */ 
/*     */     
/* 166 */     RealVector x = x0;
/* 167 */     RealVector xro = RealVector.unmodifiableRealVector(x);
/* 168 */     RealVector p = x.copy();
/* 169 */     RealVector q = a.operate(p);
/*     */     
/* 171 */     RealVector r = b.combine(1.0D, -1.0D, q);
/* 172 */     RealVector rro = RealVector.unmodifiableRealVector(r);
/* 173 */     double rnorm = r.getNorm();
/*     */     
/* 175 */     if (m == null) {
/* 176 */       RealVector z = r;
/*     */     } else {
/* 178 */       RealVector z = null;
/*     */     } 
/*     */     
/* 181 */     IterativeLinearSolverEvent evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
/*     */     
/* 183 */     manager.fireInitializationEvent(evt);
/* 184 */     if (rnorm <= rmax) {
/* 185 */       manager.fireTerminationEvent(evt);
/* 186 */       return x;
/*     */     } 
/* 188 */     double rhoPrev = 0.0D; while (true) {
/*     */       RealVector realVector;
/* 190 */       manager.incrementIterationCount();
/* 191 */       evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
/*     */       
/* 193 */       manager.fireIterationStartedEvent(evt);
/* 194 */       if (m != null) {
/* 195 */         realVector = m.operate(r);
/*     */       }
/* 197 */       double rhoNext = r.dotProduct(realVector);
/* 198 */       if (this.check && rhoNext <= 0.0D) {
/*     */         
/* 200 */         NonPositiveDefiniteOperatorException e = new NonPositiveDefiniteOperatorException();
/* 201 */         ExceptionContext context = e.getContext();
/* 202 */         context.setValue("operator", m);
/* 203 */         context.setValue("vector", r);
/* 204 */         throw e;
/*     */       } 
/* 206 */       if (manager.getIterations() == 2) {
/* 207 */         p.setSubVector(0, realVector);
/*     */       } else {
/* 209 */         p.combineToSelf(rhoNext / rhoPrev, 1.0D, realVector);
/*     */       } 
/* 211 */       q = a.operate(p);
/* 212 */       double pq = p.dotProduct(q);
/* 213 */       if (this.check && pq <= 0.0D) {
/*     */         
/* 215 */         NonPositiveDefiniteOperatorException e = new NonPositiveDefiniteOperatorException();
/* 216 */         ExceptionContext context = e.getContext();
/* 217 */         context.setValue("operator", a);
/* 218 */         context.setValue("vector", p);
/* 219 */         throw e;
/*     */       } 
/* 221 */       double alpha = rhoNext / pq;
/* 222 */       x.combineToSelf(1.0D, alpha, p);
/* 223 */       r.combineToSelf(1.0D, -alpha, q);
/* 224 */       rhoPrev = rhoNext;
/* 225 */       rnorm = r.getNorm();
/* 226 */       evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
/*     */       
/* 228 */       manager.fireIterationPerformedEvent(evt);
/* 229 */       if (rnorm <= rmax) {
/* 230 */         manager.fireTerminationEvent(evt);
/* 231 */         return x;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/ConjugateGradient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */