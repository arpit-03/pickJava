package org.hamcrest;

public interface Matcher<T> extends SelfDescribing {
  boolean matches(Object paramObject);
  
  void describeMismatch(Object paramObject, Description paramDescription);
  
  @Deprecated
  void _dont_implement_Matcher___instead_extend_BaseMatcher_();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/hamcrest/Matcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */