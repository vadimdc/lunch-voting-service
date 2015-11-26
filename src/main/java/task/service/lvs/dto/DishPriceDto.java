package task.service.lvs.dto;

import task.service.lvs.domain.MenuItem;

import java.io.Serializable;
import java.math.BigDecimal;

public class DishPriceDto implements Serializable
{
    private String     dish;
    private BigDecimal price;

    public DishPriceDto(MenuItem menuItem)
    {
        this.dish = menuItem.getDish().getName();
        this.price = menuItem.getPrice();
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
