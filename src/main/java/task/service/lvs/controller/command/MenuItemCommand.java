package task.service.lvs.controller.command;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class MenuItemCommand
{
    @NotNull(message = "{validation.error.dish.name.null}")
    @Size(min = 1, max = 191, message = "{validation.error.dish.name.size}")
    private String dish;

    @NotNull(message = "{validation.error.price}")
    @Digits(integer = 6, fraction = 2, message = "{validation.error.price}")
    private BigDecimal price;

    public String getDish()
    {
        return dish;
    }

    public void setDish(String dish)
    {
        this.dish = dish;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
}
