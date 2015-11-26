package task.service.lvs.service;

import task.service.lvs.repository.RestaurantRepository;
import task.service.lvs.domain.Restaurant;

public interface RestaurantService extends BaseEntityService<Restaurant, Integer, RestaurantRepository>
{
    /**
     * Find restaurant by name
     *
     * @param name restaurant name
     * @return {@code Restaurant} entity or {@literal null} if there is no restaurant with specified name
     */
    Restaurant findByName(String name);
}
