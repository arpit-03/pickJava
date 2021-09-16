/*    */ package org.jfree.chart.event;
/*    */ 
/*    */ import org.jfree.chart.annotations.Annotation;
/*    */ import org.jfree.chart.util.ParamChecks;
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
/*    */ public class AnnotationChangeEvent
/*    */   extends ChartChangeEvent
/*    */ {
/*    */   private Annotation annotation;
/*    */   
/*    */   public AnnotationChangeEvent(Object source, Annotation annotation) {
/* 67 */     super(source);
/* 68 */     ParamChecks.nullNotPermitted(annotation, "annotation");
/* 69 */     this.annotation = annotation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Annotation getAnnotation() {
/* 80 */     return this.annotation;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/event/AnnotationChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */