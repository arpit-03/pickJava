/*     */ package org.jfree.data.gantt;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.Series;
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
/*     */ public class TaskSeries
/*     */   extends Series
/*     */ {
/*     */   private List tasks;
/*     */   
/*     */   public TaskSeries(String name) {
/*  73 */     super(name);
/*  74 */     this.tasks = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Task task) {
/*  85 */     ParamChecks.nullNotPermitted(task, "task");
/*  86 */     this.tasks.add(task);
/*  87 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Task task) {
/*  98 */     this.tasks.remove(task);
/*  99 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 108 */     this.tasks.clear();
/* 109 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 119 */     return this.tasks.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Task get(int index) {
/* 130 */     return this.tasks.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Task get(String description) {
/* 141 */     Task result = null;
/* 142 */     int count = this.tasks.size();
/* 143 */     for (int i = 0; i < count; i++) {
/* 144 */       Task t = this.tasks.get(i);
/* 145 */       if (t.getDescription().equals(description)) {
/* 146 */         result = t;
/*     */         break;
/*     */       } 
/*     */     } 
/* 150 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getTasks() {
/* 159 */     return Collections.unmodifiableList(this.tasks);
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
/* 171 */     if (obj == this) {
/* 172 */       return true;
/*     */     }
/* 174 */     if (!(obj instanceof TaskSeries)) {
/* 175 */       return false;
/*     */     }
/* 177 */     if (!super.equals(obj)) {
/* 178 */       return false;
/*     */     }
/* 180 */     TaskSeries that = (TaskSeries)obj;
/* 181 */     if (!this.tasks.equals(that.tasks)) {
/* 182 */       return false;
/*     */     }
/* 184 */     return true;
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
/* 197 */     TaskSeries clone = (TaskSeries)super.clone();
/* 198 */     clone.tasks = (List)ObjectUtilities.deepClone(this.tasks);
/* 199 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/gantt/TaskSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */