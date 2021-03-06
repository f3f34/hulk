package com.mtl.hulk.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MTLDTActivity {

    String businessDomain() default "";

    String businessActivity() default "";

    String entityId() default "";

    long timeout() default 5;

}
