package task.service.lvs.service;

import task.service.lvs.domain.Dish;
import task.service.lvs.repository.DishRepository;

public interface DishService extends BaseEntityService<Dish, Integer, DishRepository>
{
    /**
     * Find dish by name
     *
     * @param name dish name
     * @return {@code Dish} entity or {@literal null} if there is no dish with specified name
     */
    Dish findByName(String name);

    /**
     * Find dish by name. Creates dish if there is no dish with specified name
     *
     * @param name dish name
     * @return {@code Dish} entity
     */
    Dish findOrCreate(String name);
}
