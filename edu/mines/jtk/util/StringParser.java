/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringParser
/*     */ {
/*     */   private String _str;
/*     */   private int _pos;
/*     */   private int _end;
/*     */   private int _len;
/*     */   
/*     */   public StringParser(String s) {
/* 114 */     this._str = null;
/* 115 */     this._pos = 0;
/* 116 */     this._end = 0;
/* 117 */     this._len = 0;
/*     */     this._str = s;
/*     */     this._len = s.length();
/*     */     this._pos = 0;
/* 121 */     this._end = -1; } private String replaceEscapes(String s) { char backslash = '\\';
/* 122 */     if (s == null || s.indexOf('\\') < 0) return s; 
/* 123 */     StringBuilder sb = new StringBuilder(s.length());
/* 124 */     boolean in_escape = false;
/* 125 */     for (int i = 0; i < s.length(); i++) {
/* 126 */       char c = s.charAt(i);
/* 127 */       if (in_escape) {
/* 128 */         switch (c) {
/*     */           case '\\':
/* 130 */             sb.append('\\');
/*     */             break;
/*     */           case 't':
/* 133 */             sb.append('\t');
/*     */             break;
/*     */           case 'n':
/* 136 */             sb.append('\n');
/*     */             break;
/*     */           case 'r':
/* 139 */             sb.append('\r');
/*     */             break;
/*     */           case 'f':
/* 142 */             sb.append('\f');
/*     */             break;
/*     */           case 'b':
/* 145 */             sb.append('\b');
/*     */             break;
/*     */           default:
/* 148 */             sb.append(c); break;
/*     */         } 
/* 150 */         in_escape = false;
/* 151 */       } else if (c == '\\') {
/* 152 */         in_escape = true;
/*     */       } else {
/* 154 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 157 */     return sb.toString(); }
/*     */ 
/*     */   
/*     */   public boolean hasMoreStrings() {
/*     */     char space = ' ';
/*     */     char quote = '"';
/*     */     char backslash = '\\';
/*     */     if (this._pos <= this._end)
/*     */       return true; 
/*     */     if (this._pos > this._len || this._str == null)
/*     */       return false; 
/*     */     char c = Character.MIN_VALUE;
/*     */     while (this._pos < this._len) {
/*     */       c = this._str.charAt(this._pos);
/*     */       if (c > ' ')
/*     */         break; 
/*     */       this._pos++;
/*     */     } 
/*     */     if (this._pos >= this._len)
/*     */       return false; 
/*     */     if (c == '"') {
/*     */       this._pos++;
/*     */       boolean in_escape = false;
/*     */       for (this._end = this._pos; this._end < this._len; this._end++) {
/*     */         c = this._str.charAt(this._end);
/*     */         if (in_escape) {
/*     */           in_escape = false;
/*     */         } else if (c == '\\') {
/*     */           in_escape = true;
/*     */         } else if (c == '"') {
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       for (this._end = this._pos + 1; this._end < this._len; this._end++) {
/*     */         c = this._str.charAt(this._end);
/*     */         if (c <= ' ')
/*     */           break; 
/*     */       } 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public String nextString() {
/*     */     if (!hasMoreStrings())
/*     */       throw new NoSuchElementException("StringParser.nextString: no more strings in " + this._str + "."); 
/*     */     int pos = this._pos;
/*     */     this._pos = this._end + 1;
/*     */     return replaceEscapes(this._str.substring(pos, this._end));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/StringParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */