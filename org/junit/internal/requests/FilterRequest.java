/*    */ package org.junit.internal.requests;
/*    */ 
/*    */ import org.junit.internal.runners.ErrorReportingRunner;
/*    */ import org.junit.runner.Request;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runner.manipulation.Filter;
/*    */ import org.junit.runner.manipulation.NoTestsRemainException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FilterRequest
/*    */   extends Request
/*    */ {
/*    */   private final Request request;
/*    */   private final Filter fFilter;
/*    */   
/*    */   public FilterRequest(Request request, Filter filter) {
/* 29 */     this.request = request;
/* 30 */     this.fFilter = filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner getRunner() {
/*    */     try {
/* 36 */       Runner runner = this.request.getRunner();
/* 37 */       this.fFilter.apply(runner);
/* 38 */       return runner;
/* 39 */     } catch (NoTestsRemainException e) {
/* 40 */       return (Runner)new ErrorReportingRunner(Filter.class, new Exception(String.format("No tests found matching %s from %s", new Object[] { this.fFilter.describe(), this.request.toString() })));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/internal/requests/FilterRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */