package task.service.lvs.controller;

import task.service.lvs.ApiEndpoints;
import task.service.lvs.controller.command.DishNameCommand;
import task.service.lvs.dto.MessageDto;
import task.service.lvs.domain.Dish;
import task.service.lvs.dto.DishDto;
import task.service.lvs.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiEndpoints.API_ROOT)
public class DishController extends ApiController
{
    private static final String DISH_DELETED_CODE     = "dish.deleted.message.key";
    private static final String DISH_DELETED_ALL_CODE = "dish.deleted-all.message.key";

    @Autowired
    private DishService dishService;

    @RequestMapping(value = ApiEndpoints.DISHES_DISH, method = RequestMethod.GET)
    public DishDto get(@PathVariable Integer dishId)
    {
        Dish dish = dishService.findById(dishId);
        Assert.notNull(dish, "Wrong dishId");

        return new DishDto(dish.getId(), dish.getName());
    }

    @RequestMapping(value = ApiEndpoints.DISHES, method = RequestMethod.GET)
    public List<DishDto> list()
    {
        return dishService.findAll()
                          .stream()
                          .map(dish -> new DishDto(dish.getId(), dish.getName()))
                          .collect(Collectors.toList());
    }

    @RequestMapping(value = ApiEndpoints.DISHES, method = RequestMethod.POST)
    public DishDto create(@Valid @RequestBody DishNameCommand command)
    {
        Dish dish = dishService.createNew();
        dish.setName(command.getDish());
        dishService.save(dish);

        return new DishDto(dish.getId(), dish.getName());
    }

    @RequestMapping(value = ApiEndpoints.DISHES_DISH, method = RequestMethod.PUT)
    public DishDto update(@PathVariable Integer dishId, @Valid @RequestBody DishNameCommand command)
    {
        Dish dish = dishService.findById(dishId);
        Assert.notNull(dish, "Wrong dishId");

        dish.setName(command.getDish());
        dishService.save(dish);

        return new DishDto(dish.getId(), dish.getName());
    }

    @RequestMapping(value = ApiEndpoints.DISHES_DISH, method = RequestMethod.DELETE)
    public MessageDto delete(@PathVariable Integer dishId)
    {
        Dish dish = dishService.findById(dishId);
        Assert.notNull(dish, "Wrong dishId");

        dishService.delete(dish);

        return message(DISH_DELETED_CODE, dish.getName());
    }

    @RequestMapping(value = ApiEndpoints.DISHES, method = RequestMethod.DELETE)
    public MessageDto deleteAll()
    {
        dishService.deleteAll();

        return message(DISH_DELETED_ALL_CODE);
    }
}
