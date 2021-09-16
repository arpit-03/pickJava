/*     */ package org.apache.commons.math3.exception.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExceptionContext
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6024911025449780478L;
/*     */   private Throwable throwable;
/*     */   private List<Localizable> msgPatterns;
/*     */   private List<Object[]> msgArguments;
/*     */   private Map<String, Object> context;
/*     */   
/*     */   public ExceptionContext(Throwable throwable) {
/*  64 */     this.throwable = throwable;
/*  65 */     this.msgPatterns = new ArrayList<Localizable>();
/*  66 */     this.msgArguments = new ArrayList();
/*  67 */     this.context = new HashMap<String, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/*  74 */     return this.throwable;
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
/*     */   public void addMessage(Localizable pattern, Object... arguments) {
/*  86 */     this.msgPatterns.add(pattern);
/*  87 */     this.msgArguments.add(ArgUtils.flatten(arguments));
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
/*     */   public void setValue(String key, Object value) {
/*  99 */     this.context.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue(String key) {
/* 109 */     return this.context.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getKeys() {
/* 118 */     return this.context.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 127 */     return getMessage(Locale.US);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedMessage() {
/* 136 */     return getMessage(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage(Locale locale) {
/* 146 */     return buildMessage(locale, ": ");
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
/*     */   public String getMessage(Locale locale, String separator) {
/* 158 */     return buildMessage(locale, separator);
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
/*     */   private String buildMessage(Locale locale, String separator) {
/* 170 */     StringBuilder sb = new StringBuilder();
/* 171 */     int count = 0;
/* 172 */     int len = this.msgPatterns.size();
/* 173 */     for (int i = 0; i < len; i++) {
/* 174 */       Localizable pat = this.msgPatterns.get(i);
/* 175 */       Object[] args = this.msgArguments.get(i);
/* 176 */       MessageFormat fmt = new MessageFormat(pat.getLocalizedString(locale), locale);
/*     */       
/* 178 */       sb.append(fmt.format(args));
/* 179 */       if (++count < len)
/*     */       {
/* 181 */         sb.append(separator);
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 196 */     out.writeObject(this.throwable);
/* 197 */     serializeMessages(out);
/* 198 */     serializeContext(out);
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
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 210 */     this.throwable = (Throwable)in.readObject();
/* 211 */     deSerializeMessages(in);
/* 212 */     deSerializeContext(in);
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
/*     */   private void serializeMessages(ObjectOutputStream out) throws IOException {
/* 224 */     int len = this.msgPatterns.size();
/* 225 */     out.writeInt(len);
/*     */     
/* 227 */     for (int i = 0; i < len; i++) {
/* 228 */       Localizable pat = this.msgPatterns.get(i);
/*     */       
/* 230 */       out.writeObject(pat);
/* 231 */       Object[] args = this.msgArguments.get(i);
/* 232 */       int aLen = args.length;
/*     */       
/* 234 */       out.writeInt(aLen);
/* 235 */       for (int j = 0; j < aLen; j++) {
/* 236 */         if (args[j] instanceof Serializable) {
/*     */           
/* 238 */           out.writeObject(args[j]);
/*     */         } else {
/*     */           
/* 241 */           out.writeObject(nonSerializableReplacement(args[j]));
/*     */         } 
/*     */       } 
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
/*     */   
/*     */   private void deSerializeMessages(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 258 */     int len = in.readInt();
/* 259 */     this.msgPatterns = new ArrayList<Localizable>(len);
/* 260 */     this.msgArguments = new ArrayList(len);
/*     */     
/* 262 */     for (int i = 0; i < len; i++) {
/*     */       
/* 264 */       Localizable pat = (Localizable)in.readObject();
/* 265 */       this.msgPatterns.add(pat);
/*     */       
/* 267 */       int aLen = in.readInt();
/* 268 */       Object[] args = new Object[aLen];
/* 269 */       for (int j = 0; j < aLen; j++)
/*     */       {
/* 271 */         args[j] = in.readObject();
/*     */       }
/* 273 */       this.msgArguments.add(args);
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
/*     */   private void serializeContext(ObjectOutputStream out) throws IOException {
/* 286 */     int len = this.context.size();
/* 287 */     out.writeInt(len);
/* 288 */     for (Map.Entry<String, Object> entry : this.context.entrySet()) {
/*     */       
/* 290 */       out.writeObject(entry.getKey());
/* 291 */       Object value = entry.getValue();
/* 292 */       if (value instanceof Serializable) {
/*     */         
/* 294 */         out.writeObject(value);
/*     */         continue;
/*     */       } 
/* 297 */       out.writeObject(nonSerializableReplacement(value));
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
/*     */ 
/*     */   
/*     */   private void deSerializeContext(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 313 */     int len = in.readInt();
/* 314 */     this.context = new HashMap<String, Object>();
/* 315 */     for (int i = 0; i < len; i++) {
/*     */       
/* 317 */       String key = (String)in.readObject();
/*     */       
/* 319 */       Object value = in.readObject();
/* 320 */       this.context.put(key, value);
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
/*     */   private String nonSerializableReplacement(Object obj) {
/* 332 */     return "[Object could not be serialized: " + obj.getClass().getName() + "]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/util/ExceptionContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */