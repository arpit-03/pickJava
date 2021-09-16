/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.client.utils.DateUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WarningValue
/*     */ {
/*     */   private int offs;
/*     */   private int init_offs;
/*     */   private final String src;
/*     */   private int warnCode;
/*     */   private String warnAgent;
/*     */   private String warnText;
/*     */   private Date warnDate;
/*     */   private static final String TOPLABEL = "\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?";
/*     */   private static final String DOMAINLABEL = "\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?";
/*     */   private static final String HOSTNAME = "(\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?";
/*     */   private static final String IPV4ADDRESS = "\\d+\\.\\d+\\.\\d+\\.\\d+";
/*     */   private static final String HOST = "((\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?)|(\\d+\\.\\d+\\.\\d+\\.\\d+)";
/*     */   private static final String PORT = "\\d*";
/*     */   private static final String HOSTPORT = "(((\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?)|(\\d+\\.\\d+\\.\\d+\\.\\d+))(\\:\\d*)?";
/*     */   
/*     */   WarningValue(String s) {
/*  54 */     this(s, 0);
/*     */   }
/*     */   
/*     */   WarningValue(String s, int offs) {
/*  58 */     this.offs = this.init_offs = offs;
/*  59 */     this.src = s;
/*  60 */     consumeWarnValue();
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
/*     */   public static WarningValue[] getWarningValues(Header h) {
/*  72 */     List<WarningValue> out = new ArrayList<WarningValue>();
/*  73 */     String src = h.getValue();
/*  74 */     int offs = 0;
/*  75 */     while (offs < src.length()) {
/*     */       try {
/*  77 */         WarningValue wv = new WarningValue(src, offs);
/*  78 */         out.add(wv);
/*  79 */         offs = wv.offs;
/*  80 */       } catch (IllegalArgumentException e) {
/*  81 */         int nextComma = src.indexOf(',', offs);
/*  82 */         if (nextComma == -1) {
/*     */           break;
/*     */         }
/*  85 */         offs = nextComma + 1;
/*     */       } 
/*     */     } 
/*  88 */     WarningValue[] wvs = new WarningValue[0];
/*  89 */     return out.<WarningValue>toArray(wvs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeLinearWhitespace() {
/*  97 */     while (this.offs < this.src.length()) {
/*  98 */       switch (this.src.charAt(this.offs)) {
/*     */         case '\r':
/* 100 */           if (this.offs + 2 >= this.src.length() || this.src.charAt(this.offs + 1) != '\n' || (this.src.charAt(this.offs + 2) != ' ' && this.src.charAt(this.offs + 2) != '\t')) {
/*     */             return;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 106 */           this.offs += 2;
/*     */           break;
/*     */         case '\t':
/*     */         case ' ':
/*     */           break;
/*     */         default:
/*     */           return;
/*     */       } 
/* 114 */       this.offs++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isChar(char c) {
/* 122 */     int i = c;
/* 123 */     return (i >= 0 && i <= 127);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isControl(char c) {
/* 131 */     int i = c;
/* 132 */     return (i == 127 || (i >= 0 && i <= 31));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSeparator(char c) {
/* 142 */     return (c == '(' || c == ')' || c == '<' || c == '>' || c == '@' || c == ',' || c == ';' || c == ':' || c == '\\' || c == '"' || c == '/' || c == '[' || c == ']' || c == '?' || c == '=' || c == '{' || c == '}' || c == ' ' || c == '\t');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeToken() {
/* 153 */     if (!isTokenChar(this.src.charAt(this.offs))) {
/* 154 */       parseError();
/*     */     }
/* 156 */     while (this.offs < this.src.length() && 
/* 157 */       isTokenChar(this.src.charAt(this.offs)))
/*     */     {
/*     */       
/* 160 */       this.offs++;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isTokenChar(char c) {
/* 165 */     return (isChar(c) && !isControl(c) && !isSeparator(c));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String MONTH = "Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec";
/*     */   
/*     */   private static final String WEEKDAY = "Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday";
/*     */   private static final String WKDAY = "Mon|Tue|Wed|Thu|Fri|Sat|Sun";
/*     */   private static final String TIME = "\\d{2}:\\d{2}:\\d{2}";
/*     */   private static final String DATE3 = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d";
/* 175 */   private static final Pattern HOSTPORT_PATTERN = Pattern.compile("(((\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?)|(\\d+\\.\\d+\\.\\d+\\.\\d+))(\\:\\d*)?"); private static final String DATE2 = "\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}";
/*     */   
/*     */   protected void consumeHostPort() {
/* 178 */     Matcher m = HOSTPORT_PATTERN.matcher(this.src.substring(this.offs));
/* 179 */     if (!m.find()) {
/* 180 */       parseError();
/*     */     }
/* 182 */     if (m.start() != 0) {
/* 183 */       parseError();
/*     */     }
/* 185 */     this.offs += m.end();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String DATE1 = "\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}";
/*     */   private static final String ASCTIME_DATE = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4}";
/*     */   private static final String RFC850_DATE = "(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT";
/*     */   
/*     */   protected void consumeWarnAgent() {
/* 194 */     int curr_offs = this.offs;
/*     */     try {
/* 196 */       consumeHostPort();
/* 197 */       this.warnAgent = this.src.substring(curr_offs, this.offs);
/* 198 */       consumeCharacter(' ');
/*     */       return;
/* 200 */     } catch (IllegalArgumentException e) {
/* 201 */       this.offs = curr_offs;
/*     */       
/* 203 */       consumeToken();
/* 204 */       this.warnAgent = this.src.substring(curr_offs, this.offs);
/* 205 */       consumeCharacter(' ');
/*     */       return;
/*     */     } 
/*     */   }
/*     */   private static final String RFC1123_DATE = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT"; private static final String HTTP_DATE = "((Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4})";
/*     */   private static final String WARN_DATE = "\"(((Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4}))\"";
/*     */   
/*     */   protected void consumeQuotedString() {
/* 213 */     if (this.src.charAt(this.offs) != '"') {
/* 214 */       parseError();
/*     */     }
/* 216 */     this.offs++;
/* 217 */     boolean foundEnd = false;
/* 218 */     while (this.offs < this.src.length() && !foundEnd) {
/* 219 */       char c = this.src.charAt(this.offs);
/* 220 */       if (this.offs + 1 < this.src.length() && c == '\\' && isChar(this.src.charAt(this.offs + 1))) {
/*     */         
/* 222 */         this.offs += 2; continue;
/* 223 */       }  if (c == '"') {
/* 224 */         foundEnd = true;
/* 225 */         this.offs++; continue;
/* 226 */       }  if (c != '"' && !isControl(c)) {
/* 227 */         this.offs++; continue;
/*     */       } 
/* 229 */       parseError();
/*     */     } 
/*     */     
/* 232 */     if (!foundEnd) {
/* 233 */       parseError();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeWarnText() {
/* 241 */     int curr = this.offs;
/* 242 */     consumeQuotedString();
/* 243 */     this.warnText = this.src.substring(curr, this.offs);
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
/* 258 */   private static final Pattern WARN_DATE_PATTERN = Pattern.compile("\"(((Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4}))\"");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeWarnDate() {
/* 264 */     int curr = this.offs;
/* 265 */     Matcher m = WARN_DATE_PATTERN.matcher(this.src.substring(this.offs));
/* 266 */     if (!m.lookingAt()) {
/* 267 */       parseError();
/*     */     }
/* 269 */     this.offs += m.end();
/* 270 */     this.warnDate = DateUtils.parseDate(this.src.substring(curr + 1, this.offs - 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeWarnValue() {
/* 277 */     consumeLinearWhitespace();
/* 278 */     consumeWarnCode();
/* 279 */     consumeWarnAgent();
/* 280 */     consumeWarnText();
/* 281 */     if (this.offs + 1 < this.src.length() && this.src.charAt(this.offs) == ' ' && this.src.charAt(this.offs + 1) == '"') {
/* 282 */       consumeCharacter(' ');
/* 283 */       consumeWarnDate();
/*     */     } 
/* 285 */     consumeLinearWhitespace();
/* 286 */     if (this.offs != this.src.length()) {
/* 287 */       consumeCharacter(',');
/*     */     }
/*     */   }
/*     */   
/*     */   protected void consumeCharacter(char c) {
/* 292 */     if (this.offs + 1 > this.src.length() || c != this.src.charAt(this.offs))
/*     */     {
/* 294 */       parseError();
/*     */     }
/* 296 */     this.offs++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeWarnCode() {
/* 303 */     if (this.offs + 4 > this.src.length() || !Character.isDigit(this.src.charAt(this.offs)) || !Character.isDigit(this.src.charAt(this.offs + 1)) || !Character.isDigit(this.src.charAt(this.offs + 2)) || this.src.charAt(this.offs + 3) != ' ')
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 308 */       parseError();
/*     */     }
/* 310 */     this.warnCode = Integer.parseInt(this.src.substring(this.offs, this.offs + 3));
/* 311 */     this.offs += 4;
/*     */   }
/*     */   
/*     */   private void parseError() {
/* 315 */     String s = this.src.substring(this.init_offs);
/* 316 */     throw new IllegalArgumentException("Bad warn code \"" + s + "\"");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWarnCode() {
/* 322 */     return this.warnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWarnAgent() {
/* 329 */     return this.warnAgent;
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
/*     */   public String getWarnText() {
/* 342 */     return this.warnText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getWarnDate() {
/* 349 */     return this.warnDate;
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
/*     */   public String toString() {
/* 362 */     if (this.warnDate != null) {
/* 363 */       return String.format("%d %s %s \"%s\"", new Object[] { Integer.valueOf(this.warnCode), this.warnAgent, this.warnText, DateUtils.formatDate(this.warnDate) });
/*     */     }
/*     */     
/* 366 */     return String.format("%d %s %s", new Object[] { Integer.valueOf(this.warnCode), this.warnAgent, this.warnText });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/WarningValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */