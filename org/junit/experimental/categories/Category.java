package org.junit.experimental.categories;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.validator.ValidateWith;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ValidateWith(CategoryValidator.class)
public @interface Category {
  Class<?>[] value();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/experimental/categories/Category.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */