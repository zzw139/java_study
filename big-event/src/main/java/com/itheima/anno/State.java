package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 定义了一个状态验证的注解，用于确保字段的值符合指定的状态。
 * 可以应用于字段级别，用于验证字段是否是发布状态或草稿状态。
 *
 * @Documented 注解表明这个注解将被包含在文档中。
 * @Target 注解标明这个注解可以应用于字段（FIELD）上。
 * @Retention 注解标明这个注解的保留策略是运行时（RUNTIME）。
 * @Constraint 注解标明这个注解是一个验证约束，并指定StateValidation类作为验证逻辑的实现。
 */
@Documented
@Target({ FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = { StateValidation.class})
public @interface State {
    /**
     * 验证失败时的错误消息，默认值为"state参数的值只能是已发布或者草稿"。
     * 可以通过这个字段自定义验证失败时的错误消息。
     *
     * @return 验证失败时的错误消息
     */
    String message() default "state参数的值只能是已发布或者草稿";

    /**
     * 定义了验证的分组，默认为空组。
     * 可以通过这个字段指定字段在特定验证分组下的验证规则。
     *
     * @return 验证分组数组
     */
    Class<?>[] groups() default { };

    /**
     * 定义了Payload类型数组，默认为空数组。
     * 可以通过这个字段携带额外的验证信息。
     *
     * @return Payload类型数组
     */
    Class<? extends Payload>[] payload() default { };
}
