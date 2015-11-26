package task.service.lvs.controller.validator;

import task.service.lvs.service.DishService;
import task.service.lvs.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String>
{
    @Autowired
    private DishService dishService;

    @Autowired
    private RestaurantService restaurantService;

    private EntityType entityType;

    @Override
    public void initialize(UniqueName constraintAnnotation)
    {
        this.entityType = constraintAnnotation.entityType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        switch (entityType)
        {
            case DISH:
                return null == dishService.findByName(value);
            case RESTAURANT:
                return null == restaurantService.findByName(value);
        }
        return false;
    }
}
