package task.service.lvs.dto;

import java.io.Serializable;

public class RestaurantDto implements Serializable
{
    private Integer restaurantId;
    private String  restaurant;

    public RestaurantDto(Integer restaurantId, String restaurant)
    {
        this.restaurantId = restaurantId;
        this.restaurant = restaurant;
    }

    public Integer getRestaurantId()
    {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId)
    {
        this.restaurantId = restaurantId;
    }

    public String getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(String restaurant)
    {
        this.restaurant = restaurant;
    }
}
