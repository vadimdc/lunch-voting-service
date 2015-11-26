package task.service.lvs.controller.command;

import task.service.lvs.controller.validator.EntityType;
import task.service.lvs.controller.validator.UniqueName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DishNameCommand
{
    @NotNull(message = "{validation.error.dish.name.null}")
    @Size(min = 1, max = 191, message = "{validation.error.dish.name.size}")
    @UniqueName(entityType = EntityType.DISH)
    private String dish;

    public String getDish()
    {
        return dish;
    }

    public void setDish(String dish)
    {
        this.dish = dish;
    }
}