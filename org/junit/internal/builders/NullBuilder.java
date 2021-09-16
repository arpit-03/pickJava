/*   */ package org.junit.internal.builders;
/*   */ 
/*   */ import org.junit.runner.Runner;
/*   */ import org.junit.runners.model.RunnerBuilder;
/*   */ 
/*   */ public class NullBuilder
/*   */   extends RunnerBuilder {
/*   */   public Runner runnerForClass(Class<?> each) throws Throwable {
/* 9 */     return null;
/*   */   }
/*   */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/internal/builders/NullBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */