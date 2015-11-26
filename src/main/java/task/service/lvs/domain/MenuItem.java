package task.service.lvs.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem extends BaseObject<Integer>
{
    private Integer    id;
    private Restaurant restaurant;
    private Dish       dish;
    private BigDecimal price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id", nullable = false)
    public Integer getId()
    {
        return id;
    }

    protected void setId(Integer id)
    {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    @OneToOne
    @JoinColumn(name = "dish_id", nullable = false)
    public Dish getDish()
    {
        return dish;
    }

    public void setDish(Dish dish)
    {
        this.dish = dish;
    }

    @Column(name = "price", nullable = false, precision = 6, scale = 2)
    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (!(obj instanceof MenuItem))
            return false;

        MenuItem menuItem = (MenuItem)obj;

        return new EqualsBuilder()
                .append(restaurant, menuItem.getRestaurant())
                .append(dish, menuItem.getDish())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(restaurant)
                .append(dish)
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("dish", getDish())
                .append("restaurant", getRestaurant())
                .toString();
    }
}
