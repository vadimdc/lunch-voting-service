package task.service.lvs.repository;

import task.service.lvs.domain.Dish;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends BaseRepository<Dish, Integer>
{
    Dish findByName(String name);
}
