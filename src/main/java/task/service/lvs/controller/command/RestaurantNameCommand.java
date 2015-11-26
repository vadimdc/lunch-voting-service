package task.service.lvs.controller.command;

import task.service.lvs.controller.validator.UniqueName;
import task.service.lvs.controller.validator.EntityType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestaurantNameCommand
{
    @NotNull(message = "{validation.error.restaurant.name.null}")
    @Size(min = 1, max = 191, message = "{validation.error.restaurant.name.size}")
    @UniqueName(entityType = EntityType.RESTAURANT)
    private String restaurant;

    public String getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(String restaurant)
    {
        this.restaurant = restaurant;
    }
}