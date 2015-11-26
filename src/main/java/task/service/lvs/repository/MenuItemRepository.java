package task.service.lvs.repository;

import task.service.lvs.domain.Dish;
import task.service.lvs.domain.MenuItem;
import task.service.lvs.domain.Restaurant;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends BaseRepository<MenuItem, Integer>
{
    MenuItem findByRestaurantAndDish(Restaurant restaurant, Dish dish);
}
