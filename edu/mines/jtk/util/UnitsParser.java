/*      */ package edu.mines.jtk.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class UnitsParser
/*      */ {
/*      */   static synchronized Units parse(String definition) throws ParseException {
/*   51 */     ReInit(new StringReader(definition));
/*   52 */     return units();
/*      */   }
/*      */ 
/*      */   
/*      */   public static final Units units() throws ParseException {
/*   57 */     Units e = expr();
/*   58 */     jj_consume_token(0);
/*   59 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Units expr() throws ParseException {
/*      */     Token n;
/*   67 */     Units t = term();
/*   68 */     switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
/*      */     { case 3:
/*      */       case 4:
/*   71 */         switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
/*      */         { case 3:
/*   73 */             jj_consume_token(3);
/*   74 */             switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
/*      */               case 12:
/*   76 */                 n = jj_consume_token(12);
/*      */                 break;
/*      */               case 11:
/*   79 */                 n = jj_consume_token(11);
/*      */                 break;
/*      */               default:
/*   82 */                 jj_la1[0] = jj_gen;
/*   83 */                 jj_consume_token(-1);
/*   84 */                 throw new ParseException();
/*      */             } 
/*   86 */             t.shift(Double.valueOf(n.image).doubleValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  114 */             return t;case 4: jj_consume_token(4); switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) { case 12: n = jj_consume_token(12); break;case 11: n = jj_consume_token(11); break;default: jj_la1[1] = jj_gen; jj_consume_token(-1); throw new ParseException(); }  t.shift(-Double.valueOf(n.image).doubleValue()); return t; }  jj_la1[2] = jj_gen; jj_consume_token(-1); throw new ParseException(); }  jj_la1[3] = jj_gen; return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Units term() throws ParseException {
/*  121 */     Units f = factor();
/*      */     while (true) {
/*      */       Units fb;
/*  124 */       switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
/*      */         case 5:
/*      */         case 6:
/*      */         case 8:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */           break;
/*      */         
/*      */         default:
/*  134 */           jj_la1[4] = jj_gen;
/*      */           break;
/*      */       } 
/*  137 */       switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
/*      */         case 5:
/*      */         case 8:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*  143 */           switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
/*      */             case 5:
/*  145 */               jj_consume_token(5);
/*      */               break;
/*      */             default:
/*  148 */               jj_la1[5] = jj_gen;
/*      */               break;
/*      */           } 
/*  151 */           fb = factor();
/*  152 */           f.mul(fb);
/*      */           continue;
/*      */         case 6:
/*  155 */           jj_consume_token(6);
/*  156 */           fb = factor();
/*  157 */           f.div(fb);
/*      */           continue;
/*      */       } 
/*  160 */       jj_la1[6] = jj_gen;
/*  161 */       jj_consume_token(-1);
/*  162 */       throw new ParseException();
/*      */     } 
/*      */     
/*  165 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Units factor() throws ParseException {
/*      */     Token n;
/*  173 */     Units p = primary();
/*  174 */     switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
/*      */     { case 7:
/*  176 */         jj_consume_token(7);
/*  177 */         n = jj_consume_token(11);
/*  178 */         p.pow(Integer.valueOf(n.image).intValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  184 */         return p; }  jj_la1[7] = jj_gen; return p;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final Units primary() throws ParseException {
/*      */     Units e, p;
/*      */     Token n;
/*      */     double d;
/*  192 */     switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
/*      */       case 10:
/*  194 */         n = jj_consume_token(10);
/*  195 */         p = Units.unitsFromName(n.image);
/*  196 */         if (p == null) throw new ParseException("Units /" + n.image + "/ are undefined.");
/*      */         
/*  198 */         return p;
/*      */ 
/*      */       
/*      */       case 11:
/*      */       case 12:
/*  203 */         switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
/*      */           case 12:
/*  205 */             n = jj_consume_token(12);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  215 */             d = (new Double(n.image)).doubleValue();
/*  216 */             return (new Units()).scale(d);case 11: n = jj_consume_token(11); d = (new Double(n.image)).doubleValue(); return (new Units()).scale(d);
/*      */         }  jj_la1[8] = jj_gen; jj_consume_token(-1);
/*      */         throw new ParseException();
/*      */       case 8:
/*  220 */         jj_consume_token(8);
/*  221 */         e = expr();
/*  222 */         jj_consume_token(9);
/*  223 */         return e;
/*      */     } 
/*      */ 
/*      */     
/*  227 */     jj_la1[9] = jj_gen;
/*  228 */     jj_consume_token(-1);
/*  229 */     throw new ParseException();
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean jj_initialized_once = false;
/*      */   public static UnitsParserTokenManager token_source;
/*      */   public static ASCII_CharStream jj_input_stream;
/*      */   public static Token token;
/*      */   public static Token jj_nt;
/*      */   private static int jj_ntk;
/*      */   private static int jj_gen;
/*  240 */   private static final int[] jj_la1 = new int[10];
/*  241 */   private static final int[] jj_la1_0 = new int[] { 6144, 6144, 24, 24, 7520, 32, 7520, 128, 6144, 7424 };
/*      */ 
/*      */   
/*      */   public UnitsParser(InputStream stream) {
/*  245 */     if (jj_initialized_once) {
/*  246 */       System.out.println("ERROR: Second call to constructor of static parser. You must");
/*      */       
/*  248 */       System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
/*      */       
/*  250 */       System.out.println("       during parser generation.");
/*  251 */       throw new Error();
/*      */     } 
/*  253 */     jj_initialized_once = true;
/*  254 */     jj_input_stream = new ASCII_CharStream(stream, 1, 1);
/*  255 */     token_source = new UnitsParserTokenManager(jj_input_stream);
/*  256 */     token = new Token();
/*  257 */     jj_ntk = -1;
/*  258 */     jj_gen = 0;
/*  259 */     for (int i = 0; i < 10; ) { jj_la1[i] = -1; i++; }
/*      */   
/*      */   }
/*      */   public static void ReInit(InputStream stream) {
/*  263 */     ASCII_CharStream.ReInit(stream, 1, 1);
/*  264 */     UnitsParserTokenManager.ReInit(jj_input_stream);
/*  265 */     token = new Token();
/*  266 */     jj_ntk = -1;
/*  267 */     jj_gen = 0;
/*  268 */     for (int i = 0; i < 10; ) { jj_la1[i] = -1; i++; }
/*      */   
/*      */   }
/*      */   public UnitsParser(Reader stream) {
/*  272 */     if (jj_initialized_once) {
/*  273 */       System.out.println("ERROR: Second call to constructor of static parser. You must");
/*      */       
/*  275 */       System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
/*      */       
/*  277 */       System.out.println("       during parser generation.");
/*  278 */       throw new Error();
/*      */     } 
/*  280 */     jj_initialized_once = true;
/*  281 */     jj_input_stream = new ASCII_CharStream(stream, 1, 1);
/*  282 */     token_source = new UnitsParserTokenManager(jj_input_stream);
/*  283 */     token = new Token();
/*  284 */     jj_ntk = -1;
/*  285 */     jj_gen = 0;
/*  286 */     for (int i = 0; i < 10; ) { jj_la1[i] = -1; i++; }
/*      */   
/*      */   }
/*      */   public static void ReInit(Reader stream) {
/*  290 */     ASCII_CharStream.ReInit(stream, 1, 1);
/*  291 */     UnitsParserTokenManager.ReInit(jj_input_stream);
/*  292 */     token = new Token();
/*  293 */     jj_ntk = -1;
/*  294 */     jj_gen = 0;
/*  295 */     for (int i = 0; i < 10; ) { jj_la1[i] = -1; i++; }
/*      */   
/*      */   }
/*      */   public UnitsParser(UnitsParserTokenManager tm) {
/*  299 */     if (jj_initialized_once) {
/*  300 */       System.out.println("ERROR: Second call to constructor of static parser. You must");
/*      */       
/*  302 */       System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
/*      */       
/*  304 */       System.out.println("       during parser generation.");
/*  305 */       throw new Error();
/*      */     } 
/*  307 */     jj_initialized_once = true;
/*  308 */     token_source = tm;
/*  309 */     token = new Token();
/*  310 */     jj_ntk = -1;
/*  311 */     jj_gen = 0;
/*  312 */     for (int i = 0; i < 10; ) { jj_la1[i] = -1; i++; }
/*      */   
/*      */   }
/*      */   public void ReInit(UnitsParserTokenManager tm) {
/*  316 */     token_source = tm;
/*  317 */     token = new Token();
/*  318 */     jj_ntk = -1;
/*  319 */     jj_gen = 0;
/*  320 */     for (int i = 0; i < 10; ) { jj_la1[i] = -1; i++; }
/*      */   
/*      */   }
/*      */   private static final Token jj_consume_token(int kind) throws ParseException {
/*      */     Token oldToken;
/*  325 */     if ((oldToken = token).next != null) { token = token.next; }
/*  326 */     else { token = token.next = UnitsParserTokenManager.getNextToken(); }
/*  327 */      jj_ntk = -1;
/*  328 */     if (token.kind == kind) {
/*  329 */       jj_gen++;
/*  330 */       return token;
/*      */     } 
/*  332 */     token = oldToken;
/*  333 */     jj_kind = kind;
/*  334 */     throw generateParseException();
/*      */   }
/*      */   
/*      */   public static final Token getNextToken() {
/*  338 */     if (token.next != null) { token = token.next; }
/*  339 */     else { token = token.next = UnitsParserTokenManager.getNextToken(); }
/*  340 */      jj_ntk = -1;
/*  341 */     jj_gen++;
/*  342 */     return token;
/*      */   }
/*      */   
/*      */   public static final Token getToken(int index) {
/*  346 */     Token t = token;
/*  347 */     for (int i = 0; i < index; i++) {
/*  348 */       if (t.next != null) { t = t.next; }
/*  349 */       else { t = t.next = UnitsParserTokenManager.getNextToken(); }
/*      */     
/*  351 */     }  return t;
/*      */   }
/*      */   
/*      */   private static final int jj_ntk() {
/*  355 */     if ((jj_nt = token.next) == null) {
/*  356 */       return jj_ntk = (token.next = UnitsParserTokenManager.getNextToken()).kind;
/*      */     }
/*  358 */     return jj_ntk = jj_nt.kind;
/*      */   }
/*      */   
/*  361 */   private static Vector<int[]> jj_expentries = (Vector)new Vector<>();
/*      */   private static int[] jj_expentry;
/*  363 */   private static int jj_kind = -1; private static final int PLUS = 3; private static final int MINUS = 4; private static final int MUL = 5; private static final int DIV = 6; private static final int POW = 7; private static final int LP = 8; private static final int RP = 9; private static final int NAME = 10; private static final int INTEGER = 11; private static final int DOUBLE = 12;
/*      */   
/*      */   public static final ParseException generateParseException() {
/*  366 */     jj_expentries.removeAllElements();
/*  367 */     boolean[] la1tokens = new boolean[17]; int i;
/*  368 */     for (i = 0; i < 17; i++) {
/*  369 */       la1tokens[i] = false;
/*      */     }
/*  371 */     if (jj_kind >= 0) {
/*  372 */       la1tokens[jj_kind] = true;
/*  373 */       jj_kind = -1;
/*      */     } 
/*  375 */     for (i = 0; i < 10; i++) {
/*  376 */       if (jj_la1[i] == jj_gen) {
/*  377 */         for (int k = 0; k < 32; k++) {
/*  378 */           if ((jj_la1_0[i] & 1 << k) != 0) {
/*  379 */             la1tokens[k] = true;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*  384 */     for (i = 0; i < 17; i++) {
/*  385 */       if (la1tokens[i]) {
/*  386 */         jj_expentry = new int[1];
/*  387 */         jj_expentry[0] = i;
/*  388 */         jj_expentries.addElement(jj_expentry);
/*      */       } 
/*      */     } 
/*  391 */     int[][] exptokseq = new int[jj_expentries.size()][];
/*  392 */     for (int j = 0; j < jj_expentries.size(); j++) {
/*  393 */       exptokseq[j] = jj_expentries.elementAt(j);
/*      */     }
/*  395 */     return new ParseException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void enable_tracing() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void disable_tracing() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Token
/*      */   {
/*      */     public int kind;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int beginLine;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int beginColumn;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int endLine;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int endColumn;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String image;
/*      */ 
/*      */ 
/*      */     
/*      */     public Token next;
/*      */ 
/*      */ 
/*      */     
/*      */     public Token specialToken;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Token() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final String toString() {
/*  460 */       return this.image;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final Token newToken(int ofKind) {
/*  477 */       switch (ofKind) {
/*      */       
/*  479 */       }  return new Token();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class UnitsParserTokenManager
/*      */   {
/*      */     private static final int jjStopAtPos(int pos, int kind) {
/*  491 */       jjmatchedKind = kind;
/*  492 */       jjmatchedPos = pos;
/*  493 */       return pos + 1;
/*      */     }
/*      */     
/*      */     private static final int jjStartNfaWithStates_0(int pos, int kind, int state) {
/*  497 */       jjmatchedKind = kind;
/*  498 */       jjmatchedPos = pos; 
/*  499 */       try { curChar = UnitsParser.ASCII_CharStream.readChar(); }
/*  500 */       catch (IOException e) { return pos + 1; }
/*  501 */        return jjMoveNfa_0(state, pos + 1);
/*      */     }
/*      */     
/*      */     private static final int jjMoveStringLiteralDfa0_0() {
/*  505 */       switch (curChar) {
/*      */         
/*      */         case '(':
/*  508 */           return jjStopAtPos(0, 8);
/*      */         case ')':
/*  510 */           return jjStopAtPos(0, 9);
/*      */         case '+':
/*  512 */           return jjStopAtPos(0, 3);
/*      */         case '-':
/*  514 */           return jjStartNfaWithStates_0(0, 4, 30);
/*      */       } 
/*  516 */       return jjMoveNfa_0(0, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     private static final void jjCheckNAdd(int state) {
/*  521 */       if (jjrounds[state] != jjround) {
/*      */         
/*  523 */         jjstateSet[jjnewStateCnt++] = state;
/*  524 */         jjrounds[state] = jjround;
/*      */       } 
/*      */     }
/*      */     
/*      */     private static final void jjAddStates(int start, int end) {
/*      */       do {
/*  530 */         jjstateSet[jjnewStateCnt++] = jjnextStates[start];
/*  531 */       } while (start++ != end);
/*      */     }
/*      */     
/*      */     private static final void jjCheckNAddTwoStates(int state1, int state2) {
/*  535 */       jjCheckNAdd(state1);
/*  536 */       jjCheckNAdd(state2);
/*      */     }
/*      */     
/*      */     private static final void jjCheckNAddStates(int start, int end) {
/*      */       do {
/*  541 */         jjCheckNAdd(jjnextStates[start]);
/*  542 */       } while (start++ != end);
/*      */     }
/*      */ 
/*      */     
/*      */     private static final int jjMoveNfa_0(int startState, int curPos) {
/*  547 */       int startsAt = 0;
/*  548 */       jjnewStateCnt = 33;
/*  549 */       int i = 1;
/*  550 */       jjstateSet[0] = startState;
/*      */       
/*  552 */       int kind = Integer.MAX_VALUE;
/*      */       
/*      */       while (true) {
/*  555 */         if (++jjround == Integer.MAX_VALUE)
/*  556 */           ReInitRounds(); 
/*  557 */         if (curChar < '@') {
/*      */           
/*  559 */           long l = 1L << curChar;
/*      */           
/*      */           do {
/*  562 */             switch (jjstateSet[--i]) {
/*      */               
/*      */               case 0:
/*  565 */                 if ((0x3FF000000000000L & l) != 0L) {
/*      */                   
/*  567 */                   if (kind > 11)
/*  568 */                     kind = 11; 
/*  569 */                   jjCheckNAddStates(0, 4);
/*      */                 }
/*  571 */                 else if ((0x440000000000L & l) != 0L) {
/*      */                   
/*  573 */                   if (kind > 5) {
/*  574 */                     kind = 5;
/*      */                   }
/*  576 */                 } else if (curChar == '-') {
/*  577 */                   jjCheckNAddStates(5, 10);
/*  578 */                 } else if (curChar == '%') {
/*      */                   
/*  580 */                   if (kind > 10) {
/*  581 */                     kind = 10;
/*      */                   }
/*  583 */                 } else if (curChar == '/') {
/*      */                   
/*  585 */                   if (kind > 6)
/*  586 */                     kind = 6; 
/*      */                 } 
/*  588 */                 if (curChar == '.') {
/*  589 */                   jjCheckNAddTwoStates(17, 18); break;
/*  590 */                 }  if (curChar == '*')
/*  591 */                   jjstateSet[jjnewStateCnt++] = 9; 
/*      */                 break;
/*      */               case 30:
/*  594 */                 if ((0x3FF000000000000L & l) != 0L) {
/*      */                   
/*  596 */                   if (kind > 12)
/*  597 */                     kind = 12; 
/*  598 */                   jjCheckNAddStates(11, 14);
/*      */                 }
/*  600 */                 else if (curChar == '-') {
/*  601 */                   jjCheckNAddTwoStates(25, 26);
/*  602 */                 } else if (curChar == '.') {
/*  603 */                   jjCheckNAddTwoStates(17, 18);
/*  604 */                 }  if ((0x3FF000000000000L & l) != 0L) {
/*      */                   
/*  606 */                   if (kind > 12)
/*  607 */                     kind = 12; 
/*  608 */                   jjCheckNAddStates(15, 17);
/*      */                 } 
/*  610 */                 if ((0x3FF000000000000L & l) != 0L) {
/*      */                   
/*  612 */                   if (kind > 12)
/*  613 */                     kind = 12; 
/*  614 */                   jjCheckNAddTwoStates(25, 19);
/*      */                 } 
/*  616 */                 if ((0x3FF000000000000L & l) != 0L) {
/*      */                   
/*  618 */                   if (kind > 11)
/*  619 */                     kind = 11; 
/*  620 */                   jjCheckNAdd(24);
/*      */                 } 
/*      */                 break;
/*      */               case 1:
/*  624 */                 if (curChar == '/')
/*  625 */                   kind = 6; 
/*      */                 break;
/*      */               case 9:
/*  628 */                 if (curChar == '*' && kind > 7)
/*  629 */                   kind = 7; 
/*      */                 break;
/*      */               case 10:
/*  632 */                 if (curChar == '*')
/*  633 */                   jjstateSet[jjnewStateCnt++] = 9; 
/*      */                 break;
/*      */               case 11:
/*  636 */                 if (curChar == '%')
/*  637 */                   kind = 10; 
/*      */                 break;
/*      */               case 14:
/*  640 */                 if ((0x3FF000000000000L & l) != 0L)
/*  641 */                   jjAddStates(18, 19); 
/*      */                 break;
/*      */               case 16:
/*  644 */                 if (curChar == '.')
/*  645 */                   jjCheckNAddTwoStates(17, 18); 
/*      */                 break;
/*      */               case 17:
/*  648 */                 if (curChar == '-')
/*  649 */                   jjCheckNAdd(18); 
/*      */                 break;
/*      */               case 18:
/*  652 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  654 */                 if (kind > 12)
/*  655 */                   kind = 12; 
/*  656 */                 jjCheckNAddTwoStates(18, 19);
/*      */                 break;
/*      */               case 20:
/*  659 */                 if ((0x280000000000L & l) != 0L)
/*  660 */                   jjCheckNAddTwoStates(21, 22); 
/*      */                 break;
/*      */               case 21:
/*  663 */                 if (curChar == '-')
/*  664 */                   jjCheckNAdd(22); 
/*      */                 break;
/*      */               case 22:
/*  667 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  669 */                 if (kind > 12)
/*  670 */                   kind = 12; 
/*  671 */                 jjCheckNAdd(22);
/*      */                 break;
/*      */               case 23:
/*  674 */                 if (curChar == '-')
/*  675 */                   jjCheckNAddStates(5, 10); 
/*      */                 break;
/*      */               case 24:
/*  678 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  680 */                 if (kind > 11)
/*  681 */                   kind = 11; 
/*  682 */                 jjCheckNAdd(24);
/*      */                 break;
/*      */               case 25:
/*  685 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  687 */                 if (kind > 12)
/*  688 */                   kind = 12; 
/*  689 */                 jjCheckNAddTwoStates(25, 19);
/*      */                 break;
/*      */               case 26:
/*  692 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  694 */                 if (kind > 12)
/*  695 */                   kind = 12; 
/*  696 */                 jjCheckNAddStates(15, 17);
/*      */                 break;
/*      */               case 27:
/*  699 */                 if (curChar == '.')
/*  700 */                   jjCheckNAddTwoStates(28, 29); 
/*      */                 break;
/*      */               case 28:
/*  703 */                 if (curChar == '-')
/*  704 */                   jjCheckNAdd(29); 
/*      */                 break;
/*      */               case 29:
/*  707 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  709 */                 if (kind > 12)
/*  710 */                   kind = 12; 
/*  711 */                 jjCheckNAddTwoStates(29, 19);
/*      */                 break;
/*      */               case 31:
/*  714 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  716 */                 if (kind > 12)
/*  717 */                   kind = 12; 
/*  718 */                 jjCheckNAddStates(11, 14);
/*      */                 break;
/*      */               case 32:
/*  721 */                 if ((0x3FF000000000000L & l) == 0L)
/*      */                   break; 
/*  723 */                 if (kind > 11)
/*  724 */                   kind = 11; 
/*  725 */                 jjCheckNAddStates(0, 4);
/*      */                 break;
/*      */             } 
/*      */           
/*  729 */           } while (i != startsAt);
/*      */         }
/*  731 */         else if (curChar < '') {
/*      */           
/*  733 */           long l = 1L << (curChar & 0x3F);
/*      */           
/*      */           do {
/*  736 */             switch (jjstateSet[--i]) {
/*      */               
/*      */               case 0:
/*  739 */                 if ((0x7FFFFFE07FFFFFEL & l) != 0L) {
/*      */                   
/*  741 */                   if (kind > 10)
/*  742 */                     kind = 10; 
/*  743 */                   jjCheckNAddTwoStates(13, 14);
/*      */                 }
/*  745 */                 else if (curChar == '^') {
/*      */                   
/*  747 */                   if (kind > 7)
/*  748 */                     kind = 7; 
/*      */                 } 
/*  750 */                 if (curChar == 'P') {
/*  751 */                   jjstateSet[jjnewStateCnt++] = 6; break;
/*  752 */                 }  if (curChar == 'p')
/*  753 */                   jjstateSet[jjnewStateCnt++] = 3; 
/*      */                 break;
/*      */               case 2:
/*  756 */                 if (curChar == 'r' && kind > 6)
/*  757 */                   kind = 6; 
/*      */                 break;
/*      */               case 3:
/*  760 */                 if (curChar == 'e')
/*  761 */                   jjstateSet[jjnewStateCnt++] = 2; 
/*      */                 break;
/*      */               case 4:
/*  764 */                 if (curChar == 'p')
/*  765 */                   jjstateSet[jjnewStateCnt++] = 3; 
/*      */                 break;
/*      */               case 5:
/*  768 */                 if (curChar == 'R' && kind > 6)
/*  769 */                   kind = 6; 
/*      */                 break;
/*      */               case 6:
/*  772 */                 if (curChar == 'E')
/*  773 */                   jjstateSet[jjnewStateCnt++] = 5; 
/*      */                 break;
/*      */               case 7:
/*  776 */                 if (curChar == 'P')
/*  777 */                   jjstateSet[jjnewStateCnt++] = 6; 
/*      */                 break;
/*      */               case 8:
/*  780 */                 if (curChar == '^')
/*  781 */                   kind = 7; 
/*      */                 break;
/*      */               case 12:
/*  784 */                 if ((0x7FFFFFE07FFFFFEL & l) == 0L)
/*      */                   break; 
/*  786 */                 if (kind > 10)
/*  787 */                   kind = 10; 
/*  788 */                 jjCheckNAddTwoStates(13, 14);
/*      */                 break;
/*      */               case 13:
/*  791 */                 if ((0x7FFFFFE87FFFFFEL & l) == 0L)
/*      */                   break; 
/*  793 */                 if (kind > 10)
/*  794 */                   kind = 10; 
/*  795 */                 jjCheckNAddTwoStates(13, 14);
/*      */                 break;
/*      */               case 15:
/*  798 */                 if ((0x7FFFFFE87FFFFFEL & l) == 0L)
/*      */                   break; 
/*  800 */                 if (kind > 10)
/*  801 */                   kind = 10; 
/*  802 */                 jjCheckNAddTwoStates(14, 15);
/*      */                 break;
/*      */               case 19:
/*  805 */                 if ((0x2000000020L & l) != 0L) {
/*  806 */                   jjAddStates(20, 22);
/*      */                 }
/*      */                 break;
/*      */             } 
/*  810 */           } while (i != startsAt);
/*      */         } else {
/*      */ 
/*      */           
/*      */           do {
/*      */ 
/*      */ 
/*      */             
/*  818 */             switch (jjstateSet[--i]) {
/*      */             
/*      */             } 
/*      */           
/*  822 */           } while (i != startsAt);
/*      */         } 
/*  824 */         if (kind != Integer.MAX_VALUE) {
/*      */           
/*  826 */           jjmatchedKind = kind;
/*  827 */           jjmatchedPos = curPos;
/*  828 */           kind = Integer.MAX_VALUE;
/*      */         } 
/*  830 */         curPos++;
/*  831 */         if ((i = jjnewStateCnt) == (startsAt = 33 - (jjnewStateCnt = startsAt)))
/*  832 */           return curPos;  
/*  833 */         try { curChar = UnitsParser.ASCII_CharStream.readChar(); }
/*  834 */         catch (IOException e) { return curPos; }
/*      */       
/*      */       } 
/*  837 */     } static final int[] jjnextStates = new int[] { 24, 25, 26, 27, 19, 24, 25, 26, 16, 30, 31, 25, 26, 27, 19, 26, 27, 19, 14, 15, 20, 21, 22 };
/*      */ 
/*      */ 
/*      */     
/*  841 */     public static final String[] jjstrLiteralImages = new String[] { "", null, null, "+", "-", null, null, null, "(", ")", null, null, null, null, null, null, null };
/*      */ 
/*      */     
/*  844 */     public static final String[] lexStateNames = new String[] { "DEFAULT" };
/*      */ 
/*      */     
/*  847 */     static final long[] jjtoToken = new long[] { 8185L };
/*      */ 
/*      */     
/*  850 */     static final long[] jjtoSkip = new long[] { 6L };
/*      */     
/*      */     private static UnitsParser.ASCII_CharStream input_stream;
/*      */     
/*  854 */     private static final int[] jjrounds = new int[33];
/*  855 */     private static final int[] jjstateSet = new int[66];
/*      */     protected static char curChar;
/*      */     
/*      */     public UnitsParserTokenManager(UnitsParser.ASCII_CharStream stream) {
/*  859 */       if (input_stream != null)
/*  860 */         throw new UnitsParser.TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", 1); 
/*  861 */       input_stream = stream;
/*      */     }
/*      */     
/*      */     public UnitsParserTokenManager(UnitsParser.ASCII_CharStream stream, int lexState) {
/*  865 */       this(stream);
/*  866 */       SwitchTo(lexState);
/*      */     }
/*      */     
/*      */     public static void ReInit(UnitsParser.ASCII_CharStream stream) {
/*  870 */       jjmatchedPos = jjnewStateCnt = 0;
/*  871 */       curLexState = defaultLexState;
/*  872 */       input_stream = stream;
/*  873 */       ReInitRounds();
/*      */     }
/*      */ 
/*      */     
/*      */     private static final void ReInitRounds() {
/*  878 */       jjround = -2147483647;
/*  879 */       for (int i = 33; i-- > 0;)
/*  880 */         jjrounds[i] = Integer.MIN_VALUE; 
/*      */     }
/*      */     
/*      */     public static void ReInit(UnitsParser.ASCII_CharStream stream, int lexState) {
/*  884 */       ReInit(stream);
/*  885 */       SwitchTo(lexState);
/*      */     }
/*      */     
/*      */     public static void SwitchTo(int lexState) {
/*  889 */       if (lexState >= 1 || lexState < 0) {
/*  890 */         throw new UnitsParser.TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/*      */       }
/*  892 */       curLexState = lexState;
/*      */     }
/*      */ 
/*      */     
/*      */     private static final UnitsParser.Token jjFillToken() {
/*  897 */       UnitsParser.Token t = UnitsParser.Token.newToken(jjmatchedKind);
/*  898 */       t.kind = jjmatchedKind;
/*  899 */       String im = jjstrLiteralImages[jjmatchedKind];
/*  900 */       t.image = (im == null) ? UnitsParser.ASCII_CharStream.GetImage() : im;
/*  901 */       t.beginLine = UnitsParser.ASCII_CharStream.getBeginLine();
/*  902 */       t.beginColumn = UnitsParser.ASCII_CharStream.getBeginColumn();
/*  903 */       t.endLine = UnitsParser.ASCII_CharStream.getEndLine();
/*  904 */       t.endColumn = UnitsParser.ASCII_CharStream.getEndColumn();
/*  905 */       return t;
/*      */     }
/*      */     
/*  908 */     static int curLexState = 0;
/*  909 */     static int defaultLexState = 0;
/*      */     
/*      */     static int jjnewStateCnt;
/*      */     
/*      */     static int jjround;
/*      */     
/*      */     static int jjmatchedPos;
/*      */     
/*      */     static int jjmatchedKind;
/*      */     
/*      */     public static final UnitsParser.Token getNextToken() {
/*  920 */       int curPos = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*      */         try {
/*  927 */           curChar = UnitsParser.ASCII_CharStream.BeginToken();
/*      */         }
/*  929 */         catch (IOException e) {
/*      */           
/*  931 */           jjmatchedKind = 0;
/*  932 */           UnitsParser.Token matchedToken = jjFillToken();
/*  933 */           return matchedToken;
/*      */         } 
/*      */         
/*  936 */         try { UnitsParser.ASCII_CharStream.backup(0);
/*  937 */           while (curChar <= ' ' && (0x100000200L & 1L << curChar) != 0L) {
/*  938 */             curChar = UnitsParser.ASCII_CharStream.BeginToken();
/*      */           } }
/*  940 */         catch (IOException e1) { continue; }
/*  941 */          jjmatchedKind = Integer.MAX_VALUE;
/*  942 */         jjmatchedPos = 0;
/*  943 */         curPos = jjMoveStringLiteralDfa0_0();
/*  944 */         if (jjmatchedKind != Integer.MAX_VALUE) {
/*      */           
/*  946 */           if (jjmatchedPos + 1 < curPos)
/*  947 */             UnitsParser.ASCII_CharStream.backup(curPos - jjmatchedPos - 1); 
/*  948 */           if ((jjtoToken[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 0x3F)) != 0L) {
/*      */             
/*  950 */             UnitsParser.Token matchedToken = jjFillToken();
/*  951 */             return matchedToken;
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*  958 */       int error_line = UnitsParser.ASCII_CharStream.getEndLine();
/*  959 */       int error_column = UnitsParser.ASCII_CharStream.getEndColumn();
/*  960 */       String error_after = null;
/*  961 */       boolean EOFSeen = false; try {
/*  962 */         UnitsParser.ASCII_CharStream.readChar(); UnitsParser.ASCII_CharStream.backup(1);
/*  963 */       } catch (IOException e1) {
/*  964 */         EOFSeen = true;
/*  965 */         error_after = (curPos <= 1) ? "" : UnitsParser.ASCII_CharStream.GetImage();
/*  966 */         if (curChar == '\n' || curChar == '\r') {
/*  967 */           error_line++;
/*  968 */           error_column = 0;
/*      */         } else {
/*      */           
/*  971 */           error_column++;
/*      */         } 
/*  973 */       }  if (!EOFSeen) {
/*  974 */         UnitsParser.ASCII_CharStream.backup(1);
/*  975 */         error_after = (curPos <= 1) ? "" : UnitsParser.ASCII_CharStream.GetImage();
/*      */       } 
/*  977 */       throw new UnitsParser.TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TokenMgrError
/*      */     extends Error
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int LEXICAL_ERROR = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int STATIC_LEXER_ERROR = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int INVALID_LEXICAL_STATE = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int LOOP_DETECTED = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int errorCode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected static final String addEscapes(String str) {
/* 1026 */       StringBuffer retval = new StringBuffer();
/*      */       
/* 1028 */       for (int i = 0; i < str.length(); i++) {
/* 1029 */         char ch; switch (str.charAt(i)) {
/*      */           case '\000':
/*      */             break;
/*      */           
/*      */           case '\b':
/* 1034 */             retval.append("\\b");
/*      */             break;
/*      */           case '\t':
/* 1037 */             retval.append("\\t");
/*      */             break;
/*      */           case '\n':
/* 1040 */             retval.append("\\n");
/*      */             break;
/*      */           case '\f':
/* 1043 */             retval.append("\\f");
/*      */             break;
/*      */           case '\r':
/* 1046 */             retval.append("\\r");
/*      */             break;
/*      */           case '"':
/* 1049 */             retval.append("\\\"");
/*      */             break;
/*      */           case '\'':
/* 1052 */             retval.append("\\'");
/*      */             break;
/*      */           case '\\':
/* 1055 */             retval.append("\\\\");
/*      */             break;
/*      */           default:
/* 1058 */             if ((ch = str.charAt(i)) < ' ' || ch > '~') {
/* 1059 */               String s = "0000" + Integer.toString(ch, 16);
/* 1060 */               retval.append("\\u" + s.substring(s.length() - 4, s.length())); break;
/*      */             } 
/* 1062 */             retval.append(ch);
/*      */             break;
/*      */         } 
/*      */       
/*      */       } 
/* 1067 */       return retval.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar) {
/* 1086 */       return "Lexical error at line " + errorLine + ", column " + errorColumn + ".  Encountered: " + (EOFSeen ? "<EOF> " : ("\"" + 
/*      */         
/* 1088 */         addEscapes(String.valueOf(curChar)) + "\"" + " (" + curChar + "), ")) + "after : \"" + 
/*      */         
/* 1090 */         addEscapes(errorAfter) + "\"";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getMessage() {
/* 1104 */       return super.getMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TokenMgrError() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public TokenMgrError(String message, int reason) {
/* 1115 */       super(message);
/* 1116 */       this.errorCode = reason;
/*      */     }
/*      */ 
/*      */     
/*      */     public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason) {
/* 1121 */       this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ASCII_CharStream
/*      */   {
/*      */     public static final boolean staticFlag = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static int bufsize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static int available;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static int tokenBegin;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     public static int bufpos = -1;
/*      */     
/*      */     private static int[] bufline;
/*      */     private static int[] bufcolumn;
/* 1188 */     private static int column = 0;
/* 1189 */     private static int line = 1;
/*      */     
/*      */     private static boolean prevCharIsCR = false;
/*      */     
/*      */     private static boolean prevCharIsLF = false;
/*      */     
/*      */     private static Reader inputStream;
/*      */     private static char[] buffer;
/* 1197 */     private static int maxNextCharInd = 0;
/* 1198 */     private static int inBuf = 0;
/*      */ 
/*      */     
/*      */     private static final void ExpandBuff(boolean wrapAround) {
/* 1202 */       char[] newbuffer = new char[bufsize + 2048];
/* 1203 */       int[] newbufline = new int[bufsize + 2048];
/* 1204 */       int[] newbufcolumn = new int[bufsize + 2048];
/*      */ 
/*      */       
/*      */       try {
/* 1208 */         if (wrapAround)
/*      */         {
/* 1210 */           System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
/* 1211 */           System.arraycopy(buffer, 0, newbuffer, bufsize - tokenBegin, bufpos);
/*      */           
/* 1213 */           buffer = newbuffer;
/*      */           
/* 1215 */           System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
/* 1216 */           System.arraycopy(bufline, 0, newbufline, bufsize - tokenBegin, bufpos);
/* 1217 */           bufline = newbufline;
/*      */           
/* 1219 */           System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
/* 1220 */           System.arraycopy(bufcolumn, 0, newbufcolumn, bufsize - tokenBegin, bufpos);
/* 1221 */           bufcolumn = newbufcolumn;
/*      */           
/* 1223 */           maxNextCharInd = bufpos += bufsize - tokenBegin;
/*      */         }
/*      */         else
/*      */         {
/* 1227 */           System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
/* 1228 */           buffer = newbuffer;
/*      */           
/* 1230 */           System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
/* 1231 */           bufline = newbufline;
/*      */           
/* 1233 */           System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
/* 1234 */           bufcolumn = newbufcolumn;
/*      */           
/* 1236 */           maxNextCharInd = bufpos -= tokenBegin;
/*      */         }
/*      */       
/* 1239 */       } catch (Throwable t) {
/*      */         
/* 1241 */         throw new Error(t.getMessage());
/*      */       } 
/*      */ 
/*      */       
/* 1245 */       bufsize += 2048;
/* 1246 */       available = bufsize;
/* 1247 */       tokenBegin = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     private static final void FillBuff() throws IOException {
/* 1252 */       if (maxNextCharInd == available)
/*      */       {
/* 1254 */         if (available == bufsize) {
/*      */           
/* 1256 */           if (tokenBegin > 2048) {
/*      */             
/* 1258 */             bufpos = maxNextCharInd = 0;
/* 1259 */             available = tokenBegin;
/*      */           }
/* 1261 */           else if (tokenBegin < 0) {
/* 1262 */             bufpos = maxNextCharInd = 0;
/*      */           } else {
/* 1264 */             ExpandBuff(false);
/*      */           } 
/* 1266 */         } else if (available > tokenBegin) {
/* 1267 */           available = bufsize;
/* 1268 */         } else if (tokenBegin - available < 2048) {
/* 1269 */           ExpandBuff(true);
/*      */         } else {
/* 1271 */           available = tokenBegin;
/*      */         } 
/*      */       }
/*      */       try {
/*      */         int i;
/* 1276 */         if ((i = inputStream.read(buffer, maxNextCharInd, available - maxNextCharInd)) == -1) {
/*      */ 
/*      */           
/* 1279 */           inputStream.close();
/* 1280 */           throw new IOException();
/*      */         } 
/*      */         
/* 1283 */         maxNextCharInd += i;
/*      */         
/*      */         return;
/* 1286 */       } catch (IOException e) {
/* 1287 */         bufpos--;
/* 1288 */         backup(0);
/* 1289 */         if (tokenBegin == -1)
/* 1290 */           tokenBegin = bufpos; 
/* 1291 */         throw e;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public static final char BeginToken() throws IOException {
/* 1297 */       tokenBegin = -1;
/* 1298 */       char c = readChar();
/* 1299 */       tokenBegin = bufpos;
/*      */       
/* 1301 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     private static final void UpdateLineColumn(char c) {
/* 1306 */       column++;
/*      */       
/* 1308 */       if (prevCharIsLF) {
/*      */         
/* 1310 */         prevCharIsLF = false;
/* 1311 */         line += column = 1;
/*      */       }
/* 1313 */       else if (prevCharIsCR) {
/*      */         
/* 1315 */         prevCharIsCR = false;
/* 1316 */         if (c == '\n') {
/*      */           
/* 1318 */           prevCharIsLF = true;
/*      */         } else {
/*      */           
/* 1321 */           line += column = 1;
/*      */         } 
/*      */       } 
/* 1324 */       switch (c) {
/*      */         
/*      */         case '\r':
/* 1327 */           prevCharIsCR = true;
/*      */           break;
/*      */         case '\n':
/* 1330 */           prevCharIsLF = true;
/*      */           break;
/*      */         case '\t':
/* 1333 */           column--;
/* 1334 */           column += 8 - (column & 0x7);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       bufline[bufpos] = line;
/* 1341 */       bufcolumn[bufpos] = column;
/*      */     }
/*      */ 
/*      */     
/*      */     public static final char readChar() throws IOException {
/* 1346 */       if (inBuf > 0) {
/*      */         
/* 1348 */         inBuf--;
/* 1349 */         return (char)(0xFF & buffer[(bufpos == bufsize - 1) ? (bufpos = 0) : ++bufpos]);
/*      */       } 
/*      */       
/* 1352 */       if (++bufpos >= maxNextCharInd) {
/* 1353 */         FillBuff();
/*      */       }
/* 1355 */       char c = (char)(0xFF & buffer[bufpos]);
/*      */       
/* 1357 */       UpdateLineColumn(c);
/* 1358 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int getColumn() {
/* 1367 */       return bufcolumn[bufpos];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int getLine() {
/* 1376 */       return bufline[bufpos];
/*      */     }
/*      */     
/*      */     public static final int getEndColumn() {
/* 1380 */       return bufcolumn[bufpos];
/*      */     }
/*      */     
/*      */     public static final int getEndLine() {
/* 1384 */       return bufline[bufpos];
/*      */     }
/*      */     
/*      */     public static final int getBeginColumn() {
/* 1388 */       return bufcolumn[tokenBegin];
/*      */     }
/*      */     
/*      */     public static final int getBeginLine() {
/* 1392 */       return bufline[tokenBegin];
/*      */     }
/*      */ 
/*      */     
/*      */     public static final void backup(int amount) {
/* 1397 */       inBuf += amount;
/* 1398 */       if ((bufpos -= amount) < 0) {
/* 1399 */         bufpos += bufsize;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public ASCII_CharStream(Reader dstream, int startline, int startcolumn, int buffersize) {
/* 1405 */       if (inputStream != null) {
/* 1406 */         throw new Error("\n   ERROR: Second call to the constructor of a static ASCII_CharStream.  You must\n       either use ReInit() or set the JavaCC option STATIC to false\n       during the generation of this class.");
/*      */       }
/*      */       
/* 1409 */       inputStream = dstream;
/* 1410 */       line = startline;
/* 1411 */       column = startcolumn - 1;
/*      */       
/* 1413 */       available = bufsize = buffersize;
/* 1414 */       buffer = new char[buffersize];
/* 1415 */       bufline = new int[buffersize];
/* 1416 */       bufcolumn = new int[buffersize];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ASCII_CharStream(Reader dstream, int startline, int startcolumn) {
/* 1422 */       this(dstream, startline, startcolumn, 4096);
/*      */     }
/*      */ 
/*      */     
/*      */     public static void ReInit(Reader dstream, int startline, int startcolumn, int buffersize) {
/* 1427 */       inputStream = dstream;
/* 1428 */       line = startline;
/* 1429 */       column = startcolumn - 1;
/*      */       
/* 1431 */       if (buffer == null || buffersize != buffer.length) {
/*      */         
/* 1433 */         available = bufsize = buffersize;
/* 1434 */         buffer = new char[buffersize];
/* 1435 */         bufline = new int[buffersize];
/* 1436 */         bufcolumn = new int[buffersize];
/*      */       } 
/* 1438 */       prevCharIsLF = prevCharIsCR = false;
/* 1439 */       tokenBegin = inBuf = maxNextCharInd = 0;
/* 1440 */       bufpos = -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static void ReInit(Reader dstream, int startline, int startcolumn) {
/* 1446 */       ReInit(dstream, startline, startcolumn, 4096);
/*      */     }
/*      */ 
/*      */     
/*      */     public ASCII_CharStream(InputStream dstream, int startline, int startcolumn, int buffersize) {
/* 1451 */       this(new InputStreamReader(dstream), startline, startcolumn, 4096);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ASCII_CharStream(InputStream dstream, int startline, int startcolumn) {
/* 1457 */       this(dstream, startline, startcolumn, 4096);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static void ReInit(InputStream dstream, int startline, int startcolumn, int buffersize) {
/* 1463 */       ReInit(new InputStreamReader(dstream), startline, startcolumn, 4096);
/*      */     }
/*      */ 
/*      */     
/*      */     public static void ReInit(InputStream dstream, int startline, int startcolumn) {
/* 1468 */       ReInit(dstream, startline, startcolumn, 4096);
/*      */     }
/*      */     
/*      */     public static final String GetImage() {
/* 1472 */       if (bufpos >= tokenBegin) {
/* 1473 */         return new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
/*      */       }
/* 1475 */       return new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos + 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static final char[] GetSuffix(int len) {
/* 1481 */       char[] ret = new char[len];
/*      */       
/* 1483 */       if (bufpos + 1 >= len) {
/* 1484 */         System.arraycopy(buffer, bufpos - len + 1, ret, 0, len);
/*      */       } else {
/*      */         
/* 1487 */         System.arraycopy(buffer, bufsize - len - bufpos - 1, ret, 0, len - bufpos - 1);
/*      */         
/* 1489 */         System.arraycopy(buffer, 0, ret, len - bufpos - 1, bufpos + 1);
/*      */       } 
/*      */       
/* 1492 */       return ret;
/*      */     }
/*      */ 
/*      */     
/*      */     public static void Done() {
/* 1497 */       buffer = null;
/* 1498 */       bufline = null;
/* 1499 */       bufcolumn = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static void adjustBeginLineColumn(int newLine, int newCol) {
/* 1507 */       int len, start = tokenBegin;
/*      */ 
/*      */       
/* 1510 */       if (bufpos >= tokenBegin) {
/*      */         
/* 1512 */         len = bufpos - tokenBegin + inBuf + 1;
/*      */       }
/*      */       else {
/*      */         
/* 1516 */         len = bufsize - tokenBegin + bufpos + 1 + inBuf;
/*      */       } 
/*      */       
/* 1519 */       int i = 0, j = 0, k = 0;
/* 1520 */       int nextColDiff = 0, columnDiff = 0;
/*      */       
/* 1522 */       while (i < len && bufline[j = start % bufsize] == bufline[k = ++start % bufsize]) {
/*      */ 
/*      */         
/* 1525 */         bufline[j] = newLine;
/* 1526 */         nextColDiff = columnDiff + bufcolumn[k] - bufcolumn[j];
/* 1527 */         bufcolumn[j] = newCol + columnDiff;
/* 1528 */         columnDiff = nextColDiff;
/* 1529 */         i++;
/*      */       } 
/*      */       
/* 1532 */       if (i < len) {
/*      */         
/* 1534 */         bufline[j] = newLine++;
/* 1535 */         bufcolumn[j] = newCol + columnDiff;
/*      */         
/* 1537 */         while (i++ < len) {
/*      */           
/* 1539 */           if (bufline[j = start % bufsize] != bufline[++start % bufsize]) {
/* 1540 */             bufline[j] = newLine++; continue;
/*      */           } 
/* 1542 */           bufline[j] = newLine;
/*      */         } 
/*      */       } 
/*      */       
/* 1546 */       line = bufline[j];
/* 1547 */       column = bufcolumn[j];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ParseException
/*      */     extends Exception
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean specialConstructor;
/*      */ 
/*      */ 
/*      */     
/*      */     public UnitsParser.Token currentToken;
/*      */ 
/*      */ 
/*      */     
/*      */     public int[][] expectedTokenSequences;
/*      */ 
/*      */ 
/*      */     
/*      */     public String[] tokenImage;
/*      */ 
/*      */ 
/*      */     
/*      */     protected String eol;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParseException(UnitsParser.Token currentTokenVal, int[][] expectedTokenSequencesVal, String[] tokenImageVal)
/*      */     {
/* 1584 */       super("");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1691 */       this.eol = System.getProperty("line.separator", "\n"); this.specialConstructor = true; this.currentToken = currentTokenVal; this.expectedTokenSequences = expectedTokenSequencesVal; this.tokenImage = tokenImageVal; } public ParseException() { this.eol = System.getProperty("line.separator", "\n"); this.specialConstructor = false; } public ParseException(String message) { super(message); this.eol = System.getProperty("line.separator", "\n");
/*      */       this.specialConstructor = false; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected String add_escapes(String str) {
/* 1699 */       StringBuffer retval = new StringBuffer();
/*      */       
/* 1701 */       for (int i = 0; i < str.length(); i++) {
/* 1702 */         char ch; switch (str.charAt(i)) {
/*      */           case '\000':
/*      */             break;
/*      */           
/*      */           case '\b':
/* 1707 */             retval.append("\\b");
/*      */             break;
/*      */           case '\t':
/* 1710 */             retval.append("\\t");
/*      */             break;
/*      */           case '\n':
/* 1713 */             retval.append("\\n");
/*      */             break;
/*      */           case '\f':
/* 1716 */             retval.append("\\f");
/*      */             break;
/*      */           case '\r':
/* 1719 */             retval.append("\\r");
/*      */             break;
/*      */           case '"':
/* 1722 */             retval.append("\\\"");
/*      */             break;
/*      */           case '\'':
/* 1725 */             retval.append("\\'");
/*      */             break;
/*      */           case '\\':
/* 1728 */             retval.append("\\\\");
/*      */             break;
/*      */           default:
/* 1731 */             if ((ch = str.charAt(i)) < ' ' || ch > '~') {
/* 1732 */               String s = "0000" + Integer.toString(ch, 16);
/* 1733 */               retval.append("\\u" + s.substring(s.length() - 4, s.length())); break;
/*      */             } 
/* 1735 */             retval.append(ch);
/*      */             break;
/*      */         } 
/*      */       
/*      */       } 
/* 1740 */       return retval.toString();
/*      */     }
/*      */     
/*      */     public String getMessage() {
/*      */       if (!this.specialConstructor)
/*      */         return super.getMessage(); 
/*      */       String expected = "";
/*      */       int maxSize = 0;
/*      */       for (int i = 0; i < this.expectedTokenSequences.length; i++) {
/*      */         if (maxSize < (this.expectedTokenSequences[i]).length)
/*      */           maxSize = (this.expectedTokenSequences[i]).length; 
/*      */         for (int k = 0; k < (this.expectedTokenSequences[i]).length; k++)
/*      */           expected = expected + this.tokenImage[this.expectedTokenSequences[i][k]] + " "; 
/*      */         if (this.expectedTokenSequences[i][(this.expectedTokenSequences[i]).length - 1] != 0)
/*      */           expected = expected + "..."; 
/*      */         expected = expected + this.eol + "    ";
/*      */       } 
/*      */       String retval = "Encountered \"";
/*      */       UnitsParser.Token tok = this.currentToken.next;
/*      */       for (int j = 0; j < maxSize; j++) {
/*      */         if (j != 0)
/*      */           retval = retval + " "; 
/*      */         if (tok.kind == 0) {
/*      */           retval = retval + this.tokenImage[0];
/*      */           break;
/*      */         } 
/*      */         retval = retval + add_escapes(tok.image);
/*      */         tok = tok.next;
/*      */       } 
/*      */       retval = retval + "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn + "." + this.eol;
/*      */       if (this.expectedTokenSequences.length == 1) {
/*      */         retval = retval + "Was expecting:" + this.eol + "    ";
/*      */       } else {
/*      */         retval = retval + "Was expecting one of:" + this.eol + "    ";
/*      */       } 
/*      */       retval = retval + expected;
/*      */       return retval;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/UnitsParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */