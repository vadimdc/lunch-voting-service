package task.service.lvs.service;

import task.service.lvs.repository.MenuItemRepository;
import task.service.lvs.domain.Dish;
import task.service.lvs.domain.MenuItem;
import task.service.lvs.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("menuItemService")
public class MenuItemServiceImpl extends AbstractService<MenuItem, Integer, MenuItemRepository> implements MenuItemService
{
    protected MenuItemServiceImpl()
    {
        super(MenuItem.class);
    }

    @Override
    @Autowired
    public void setRepository(MenuItemRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public MenuItem findByRestaurantAndDish(Restaurant restaurant, Dish dish)
    {
        return repository.findByRestaurantAndDish(restaurant, dish);
    }

    @Override
    public MenuItem createOrUpdate(Restaurant restaurant, Dish dish, BigDecimal price)
    {
        MenuItem menuItem = findByRestaurantAndDish(restaurant, dish);

        if (null == menuItem)
        {
            menuItem = createNew();
            menuItem.setRestaurant(restaurant);
            menuItem.setDish(dish);
        }

        menuItem.setPrice(price);

        return save(menuItem);
    }
}
