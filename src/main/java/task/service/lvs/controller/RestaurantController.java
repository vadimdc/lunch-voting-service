package task.service.lvs.controller;

import task.service.lvs.ApiEndpoints;
import task.service.lvs.controller.command.RestaurantNameCommand;
import task.service.lvs.dto.MessageDto;
import task.service.lvs.dto.RestaurantDto;
import task.service.lvs.service.RestaurantService;
import task.service.lvs.domain.Restaurant;
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
public class RestaurantController extends ApiController
{
    private static final String RESTAURANT_DELETED_CODE     = "restaurant.deleted.message.key";
    private static final String RESTAURANT_DELETED_ALL_CODE = "restaurant.deleted-all.message.key";

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = ApiEndpoints.RESTAURANTS_RESTAURANT, method = RequestMethod.GET)
    public RestaurantDto get(@PathVariable Integer restaurantId)
    {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        Assert.notNull(restaurant, "Wrong restaurantId");

        return new RestaurantDto(restaurant.getId(), restaurant.getName());
    }

    @RequestMapping(value = ApiEndpoints.RESTAURANTS, method = RequestMethod.GET)
    public List<RestaurantDto> list()
    {
        return restaurantService.findAll()
                                .stream()
                                .map(restaurant -> new RestaurantDto(restaurant.getId(), restaurant.getName()))
                                .collect(Collectors.toList());
    }

    @RequestMapping(value = ApiEndpoints.RESTAURANTS, method = RequestMethod.POST)
    public RestaurantDto create(@Valid @RequestBody RestaurantNameCommand command)
    {
        Restaurant restaurant = restaurantService.createNew();

        restaurant.setName(command.getRestaurant());
        restaurantService.save(restaurant);

        return new RestaurantDto(restaurant.getId(), restaurant.getName());
    }

    @RequestMapping(value = ApiEndpoints.RESTAURANTS_RESTAURANT, method = RequestMethod.PUT)
    public RestaurantDto update(@PathVariable Integer restaurantId, @Valid @RequestBody RestaurantNameCommand command)
    {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        Assert.notNull(restaurant, "Wrong restaurantId");

        restaurant.setName(command.getRestaurant());
        restaurantService.save(restaurant);

        return new RestaurantDto(restaurant.getId(), restaurant.getName());
    }

    @RequestMapping(value = ApiEndpoints.RESTAURANTS_RESTAURANT, method = RequestMethod.DELETE)
    public MessageDto delete(@PathVariable Integer restaurantId)
    {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        Assert.notNull(restaurant, "Wrong restaurantId");

        restaurantService.delete(restaurant);

        return message(RESTAURANT_DELETED_CODE, restaurant.getName());
    }

    @RequestMapping(value = ApiEndpoints.RESTAURANTS, method = RequestMethod.DELETE)
    public MessageDto deleteAll()
    {
        restaurantService.deleteAll();
        return message(RESTAURANT_DELETED_ALL_CODE);
    }
}
