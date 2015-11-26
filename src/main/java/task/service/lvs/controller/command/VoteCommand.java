package task.service.lvs.controller.command;

import javax.validation.constraints.NotNull;

public class VoteCommand
{
    @NotNull(message = "{validation.error.restaurant.name.null}")
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
