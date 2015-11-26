package task.service.lvs.dto;

import task.service.lvs.domain.MenuItem;

import java.io.Serializable;
import java.math.BigDecimal;

public class MenuItemDto implements Serializable
{
    private Integer    menuItemId;
    private String     restaurant;
    private String     dish;
    private BigDecimal price;

    public MenuItemDto(MenuItem menuItem)
    {
        this.menuItemId = menuItem.getId();
        this.restaurant = menuItem.getRestaurant().getName();
        this.dish = menuItem.getDish().getName();
        this.price = menuItem.getPrice();
    }

    public Integer getMenuItemId()
    {
        return menuItemId;
    }

    public String getRestaurant()
    {
        return restaurant;
    }

    public String getDish()
    {
        return dish;
    }

    public BigDecimal getPrice()
    {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
