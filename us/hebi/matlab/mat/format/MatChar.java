/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.CharBuffer;
/*    */ import java.util.Arrays;
/*    */ import us.hebi.matlab.mat.types.AbstractCharBase;
/*    */ import us.hebi.matlab.mat.types.Array;
/*    */ import us.hebi.matlab.mat.types.Sink;
/*    */ import us.hebi.matlab.mat.util.Preconditions;
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
/*    */ class MatChar
/*    */   extends AbstractCharBase
/*    */   implements Mat5Serializable
/*    */ {
/*    */   protected final CharBuffer buffer;
/*    */   protected final CharEncoding encoding;
/*    */   
/*    */   MatChar(int[] dims, CharEncoding encoding) {
/* 42 */     this(dims, encoding, CharBuffer.allocate(getNumElements(dims)));
/* 43 */     Arrays.fill(this.buffer.array(), ' ');
/*    */   }
/*    */   
/*    */   MatChar(int[] dims, CharEncoding encoding, CharBuffer buffer) {
/* 47 */     super(dims);
/* 48 */     Preconditions.checkArgument((buffer.remaining() == getNumElements()), "Unexpected number of elements");
/* 49 */     this.buffer = (CharBuffer)Preconditions.checkNotNull(buffer);
/* 50 */     this.encoding = (CharEncoding)Preconditions.checkNotNull(encoding);
/*    */   }
/*    */ 
/*    */   
/*    */   public CharSequence asCharSequence() {
/* 55 */     return this.buffer.slice();
/*    */   }
/*    */ 
/*    */   
/*    */   public char getChar(int index) {
/* 60 */     return this.buffer.get(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setChar(int index, char value) {
/* 65 */     this.buffer.put(index, value);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */   
/*    */   public int getMat5Size(String name) {
/* 74 */     this.buffer.rewind();
/* 75 */     return 8 + 
/* 76 */       Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this) + 
/* 77 */       Mat5WriteUtil.computeCharBufferSize(this.encoding, this.buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 82 */     this.buffer.rewind();
/* 83 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/* 84 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/* 85 */     Mat5WriteUtil.writeCharBufferWithTag(this.encoding, this.buffer, sink);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int subHashCode() {
/* 93 */     return Compat.hash(new Object[] { this.buffer, this.encoding });
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 98 */     MatChar other = (MatChar)otherGuaranteedSameClass;
/* 99 */     return other.buffer.equals(this.buffer);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatChar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */