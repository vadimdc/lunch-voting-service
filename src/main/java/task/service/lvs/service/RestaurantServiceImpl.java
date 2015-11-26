package task.service.lvs.service;

import task.service.lvs.domain.Restaurant;
import task.service.lvs.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("restaurantService")
public class RestaurantServiceImpl extends AbstractService<Restaurant, Integer, RestaurantRepository> implements RestaurantService
{
    public RestaurantServiceImpl()
    {
        super(Restaurant.class);
    }

    @Override
    @Autowired
    public void setRepository(RestaurantRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public Restaurant findByName(String name)
    {
        return repository.findByName(name);
    }
}
