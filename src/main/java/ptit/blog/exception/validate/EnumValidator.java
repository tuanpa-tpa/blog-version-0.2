package ptit.blog.exception.validate;
/* Created by hunghust97 on 03/11/2021 - 2:04 PM
 *@project csign
 *@author Lab Security - CIST
 *@web http://cist.cmc.com.vn/
 */

import javax.validation.*;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Documented
@Constraint(validatedBy = EnumValidator.EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface EnumValidator {

    Class<? extends Enum<?>> enumClazz();

    String message() default "Value is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

        List<String> valueList = null;

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return valueList.contains(value.toUpperCase());
        }

        @Override
        public void initialize(EnumValidator constraintAnnotation) {
            valueList = new ArrayList<String>();
            Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

            @SuppressWarnings("rawtypes")
            Enum[] enumValArr = enumClass.getEnumConstants();

            for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
                valueList.add(enumVal.toString().toUpperCase());
            }
        }
    }

}