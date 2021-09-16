/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ public class KeyToGroupMap
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2228169345475318082L;
/*     */   private Comparable defaultGroup;
/*     */   private List groups;
/*     */   private Map keyToGroupMap;
/*     */   
/*     */   public KeyToGroupMap() {
/*  83 */     this("Default Group");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyToGroupMap(Comparable defaultGroup) {
/*  92 */     ParamChecks.nullNotPermitted(defaultGroup, "defaultGroup");
/*  93 */     this.defaultGroup = defaultGroup;
/*  94 */     this.groups = new ArrayList();
/*  95 */     this.keyToGroupMap = new HashMap<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGroupCount() {
/* 104 */     return this.groups.size() + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getGroups() {
/* 115 */     List<Comparable> result = new ArrayList();
/* 116 */     result.add(this.defaultGroup);
/* 117 */     Iterator<Comparable> iterator = this.groups.iterator();
/* 118 */     while (iterator.hasNext()) {
/* 119 */       Comparable group = iterator.next();
/* 120 */       if (!result.contains(group)) {
/* 121 */         result.add(group);
/*     */       }
/*     */     } 
/* 124 */     return result;
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
/*     */   public int getGroupIndex(Comparable group) {
/* 136 */     int result = this.groups.indexOf(group);
/* 137 */     if (result < 0) {
/* 138 */       if (this.defaultGroup.equals(group)) {
/* 139 */         result = 0;
/*     */       }
/*     */     } else {
/*     */       
/* 143 */       result++;
/*     */     } 
/* 145 */     return result;
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
/*     */   public Comparable getGroup(Comparable key) {
/* 157 */     ParamChecks.nullNotPermitted(key, "key");
/* 158 */     Comparable result = this.defaultGroup;
/* 159 */     Comparable group = (Comparable)this.keyToGroupMap.get(key);
/* 160 */     if (group != null) {
/* 161 */       result = group;
/*     */     }
/* 163 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mapKeyToGroup(Comparable key, Comparable group) {
/* 174 */     ParamChecks.nullNotPermitted(key, "key");
/* 175 */     Comparable currentGroup = getGroup(key);
/* 176 */     if (!currentGroup.equals(this.defaultGroup) && 
/* 177 */       !currentGroup.equals(group)) {
/* 178 */       int count = getKeyCount(currentGroup);
/* 179 */       if (count == 1) {
/* 180 */         this.groups.remove(currentGroup);
/*     */       }
/*     */     } 
/*     */     
/* 184 */     if (group == null) {
/* 185 */       this.keyToGroupMap.remove(key);
/*     */     } else {
/*     */       
/* 188 */       if (!this.groups.contains(group) && 
/* 189 */         !this.defaultGroup.equals(group)) {
/* 190 */         this.groups.add(group);
/*     */       }
/*     */       
/* 193 */       this.keyToGroupMap.put(key, group);
/*     */     } 
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
/*     */   public int getKeyCount(Comparable group) {
/* 207 */     ParamChecks.nullNotPermitted(group, "group");
/* 208 */     int result = 0;
/* 209 */     Iterator<Comparable> iterator = this.keyToGroupMap.values().iterator();
/* 210 */     while (iterator.hasNext()) {
/* 211 */       Comparable g = iterator.next();
/* 212 */       if (group.equals(g)) {
/* 213 */         result++;
/*     */       }
/*     */     } 
/* 216 */     return result;
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
/* 228 */     if (obj == this) {
/* 229 */       return true;
/*     */     }
/* 231 */     if (!(obj instanceof KeyToGroupMap)) {
/* 232 */       return false;
/*     */     }
/* 234 */     KeyToGroupMap that = (KeyToGroupMap)obj;
/* 235 */     if (!ObjectUtilities.equal(this.defaultGroup, that.defaultGroup)) {
/* 236 */       return false;
/*     */     }
/* 238 */     if (!this.keyToGroupMap.equals(that.keyToGroupMap)) {
/* 239 */       return false;
/*     */     }
/* 241 */     return true;
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
/* 254 */     KeyToGroupMap result = (KeyToGroupMap)super.clone();
/* 255 */     result
/* 256 */       .defaultGroup = (Comparable)clone(this.defaultGroup);
/* 257 */     result.groups = (List)clone(this.groups);
/* 258 */     result.keyToGroupMap = (Map)clone(this.keyToGroupMap);
/* 259 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object clone(Object object) {
/* 270 */     if (object == null) {
/* 271 */       return null;
/*     */     }
/* 273 */     Class<?> c = object.getClass();
/* 274 */     Object result = null;
/*     */     try {
/* 276 */       Method m = c.getMethod("clone", (Class[])null);
/* 277 */       if (Modifier.isPublic(m.getModifiers())) {
/*     */         try {
/* 279 */           result = m.invoke(object, (Object[])null);
/*     */         }
/* 281 */         catch (Exception e) {
/* 282 */           e.printStackTrace();
/*     */         }
/*     */       
/*     */       }
/* 286 */     } catch (NoSuchMethodException e) {
/* 287 */       result = object;
/*     */     } 
/* 289 */     return result;
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
/*     */   private static Collection clone(Collection list) throws CloneNotSupportedException {
/* 303 */     Collection<Object> result = null;
/* 304 */     if (list != null) {
/*     */       try {
/* 306 */         List<Object> clone = (List)list.getClass().newInstance();
/* 307 */         Iterator iterator = list.iterator();
/* 308 */         while (iterator.hasNext()) {
/* 309 */           clone.add(clone(iterator.next()));
/*     */         }
/* 311 */         result = clone;
/*     */       }
/* 313 */       catch (Exception e) {
/* 314 */         throw new CloneNotSupportedException("Exception.");
/*     */       } 
/*     */     }
/* 317 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/KeyToGroupMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */