package task.service.lvs.dto;

import java.io.Serializable;

public class DishDto implements Serializable
{
    private Integer dishId;
    private String  dish;

    public DishDto(Integer dishId, String dish)
    {
        this.dishId = dishId;
        this.dish = dish;
    }

    public Integer getDishId()
    {
        return dishId;
    }

    public void setDishId(Integer dishId)
    {
        this.dishId = dishId;
    }

    public String getDish()
    {
        return dish;
    }

    public void setDish(String dish)
    {
        this.dish = dish;
    }
}
