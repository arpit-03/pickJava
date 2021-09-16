/*     */ package org.jfree.data.gantt;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.time.SimpleTimePeriod;
/*     */ import org.jfree.data.time.TimePeriod;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Task
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1094303785346988894L;
/*     */   private String description;
/*     */   private TimePeriod duration;
/*     */   private Double percentComplete;
/*     */   private List subtasks;
/*     */   
/*     */   public Task(String description, TimePeriod duration) {
/*  86 */     ParamChecks.nullNotPermitted(description, "description");
/*  87 */     this.description = description;
/*  88 */     this.duration = duration;
/*  89 */     this.percentComplete = null;
/*  90 */     this.subtasks = new ArrayList();
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
/*     */   public Task(String description, Date start, Date end) {
/* 102 */     this(description, (TimePeriod)new SimpleTimePeriod(start, end));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 111 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 120 */     ParamChecks.nullNotPermitted(description, "description");
/* 121 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimePeriod getDuration() {
/* 130 */     return this.duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDuration(TimePeriod duration) {
/* 139 */     this.duration = duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double getPercentComplete() {
/* 148 */     return this.percentComplete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPercentComplete(Double percent) {
/* 157 */     this.percentComplete = percent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPercentComplete(double percent) {
/* 166 */     setPercentComplete(new Double(percent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSubtask(Task subtask) {
/* 175 */     ParamChecks.nullNotPermitted(subtask, "subtask");
/* 176 */     this.subtasks.add(subtask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSubtask(Task subtask) {
/* 185 */     this.subtasks.remove(subtask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSubtaskCount() {
/* 194 */     return this.subtasks.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Task getSubtask(int index) {
/* 205 */     return this.subtasks.get(index);
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
/*     */   public boolean equals(Object object) {
/* 217 */     if (object == this) {
/* 218 */       return true;
/*     */     }
/* 220 */     if (!(object instanceof Task)) {
/* 221 */       return false;
/*     */     }
/* 223 */     Task that = (Task)object;
/* 224 */     if (!ObjectUtilities.equal(this.description, that.description)) {
/* 225 */       return false;
/*     */     }
/* 227 */     if (!ObjectUtilities.equal(this.duration, that.duration)) {
/* 228 */       return false;
/*     */     }
/* 230 */     if (!ObjectUtilities.equal(this.percentComplete, that.percentComplete))
/*     */     {
/* 232 */       return false;
/*     */     }
/* 234 */     if (!ObjectUtilities.equal(this.subtasks, that.subtasks)) {
/* 235 */       return false;
/*     */     }
/* 237 */     return true;
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
/* 250 */     Task clone = (Task)super.clone();
/* 251 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/gantt/Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */