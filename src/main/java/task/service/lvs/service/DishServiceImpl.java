package task.service.lvs.service;

import task.service.lvs.domain.Dish;
import task.service.lvs.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dishService")
public class DishServiceImpl extends AbstractService<Dish, Integer, DishRepository> implements DishService
{
    protected DishServiceImpl()
    {
        super(Dish.class);
    }

    @Override
    @Autowired
    public void setRepository(DishRepository repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Dish findByName(String name)
    {
        return repository.findByName(name);
    }

    @Override
    @Transactional(readOnly = false)
    public Dish findOrCreate(String name)
    {
        Dish dish = repository.findByName(name);

        if (null == dish)
        {
            dish = createNew();
            dish.setName(name);
            save(dish);
        }

        return dish;
    }
}
