package task.service.lvs.dto;

import task.service.lvs.domain.Restaurant;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantLunchMenuDto implements Serializable
{
    private String             restaurant;
    private List<DishPriceDto> dishes;

    public RestaurantLunchMenuDto(Restaurant restaurant)
    {
        this.restaurant = restaurant.getName();
        this.dishes = restaurant.getMenuItems()
                                .stream()
                                .map(DishPriceDto::new)
                                .sorted(Comparator.comparing(DishPriceDto::getDish))
                                .collect(Collectors.toList());
    }

    public String getRestaurant()
    {
        return restaurant;
    }

    public List<DishPriceDto> getDishes()
    {
        return dishes;
    }
}
