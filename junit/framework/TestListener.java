package junit.framework;

public interface TestListener {
  void addError(Test paramTest, Throwable paramThrowable);
  
  void addFailure(Test paramTest, AssertionFailedError paramAssertionFailedError);
  
  void endTest(Test paramTest);
  
  void startTest(Test paramTest);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/junit/framework/TestListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */