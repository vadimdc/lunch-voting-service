package task.service.lvs.repository;

import task.service.lvs.domain.Restaurant;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant, Integer>
{
    Restaurant findByName(String name);
}
