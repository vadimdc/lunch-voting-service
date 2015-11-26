package task.service.lvs.service;

import task.service.lvs.domain.Dish;
import task.service.lvs.domain.MenuItem;
import task.service.lvs.repository.MenuItemRepository;
import task.service.lvs.domain.Restaurant;

import java.math.BigDecimal;

public interface MenuItemService extends BaseEntityService<MenuItem, Integer, MenuItemRepository>
{
    /**
     * Find menu item by restaurant and dish
     *
     * @param restaurant {@link Restaurant} entity
     * @param dish {@link Dish} entity
     * @return {@link MenuItem} entity if exists or {@literal null} otherwise
     */
    MenuItem findByRestaurantAndDish(Restaurant restaurant, Dish dish);

    /**
     * Create menu item by restaurant, dish and price. If menu item exists for
     * specified restaurant and dish just update price
     *
     * @param restaurant {@link Restaurant} entity
     * @param dish {@link Dish} entity
     * @return {@link MenuItem} entity
     */
    MenuItem createOrUpdate(Restaurant restaurant, Dish dish, BigDecimal price);
}
