package task.service.lvs.controller;

import task.service.lvs.ApiEndpoints;
import task.service.lvs.domain.Dish;
import task.service.lvs.domain.MenuItem;
import task.service.lvs.dto.MenuItemDto;
import task.service.lvs.dto.MessageDto;
import task.service.lvs.dto.RestaurantLunchMenuDto;
import task.service.lvs.controller.command.MenuItemCommand;
import task.service.lvs.domain.Restaurant;
import task.service.lvs.service.DishService;
import task.service.lvs.service.MenuItemService;
import task.service.lvs.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiEndpoints.API_ROOT)
public class MenuController extends ApiController
{
    private static final String MENU_ITEM_DELETED_CODE     = "menu-item.deleted.message.key";
    private static final String MENU_ITEM_DELETED_ALL_CODE = "menu-item.deleted-all.message.key";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Autowired
    private MenuItemService menuItemService;

    @RequestMapping(value = ApiEndpoints.LUNCH_MENU, method = RequestMethod.GET)
    public List<RestaurantLunchMenuDto> getMenu()
    {
        List<Restaurant> restaurants = restaurantService.findAll();
        restaurants.removeIf(restaurant -> restaurant.getMenuItems().size() == 0);

        return restaurants
                .stream()
                .map(RestaurantLunchMenuDto::new)
                .sorted(Comparator.comparing(RestaurantLunchMenuDto::getRestaurant))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = ApiEndpoints.MENU_ITEMS_RESTAURANT, method = RequestMethod.GET)
    public Set<MenuItemDto> getMenuItems(@PathVariable Integer restaurantId)
    {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        Assert.notNull(restaurant, "Wrong restaurantId");

        return restaurant.getMenuItems()
                         .stream()
                         .map(MenuItemDto::new).collect(Collectors.toSet());
    }

    @RequestMapping(value = ApiEndpoints.MENU_ITEMS_RESTAURANT, method = RequestMethod.POST)
    public MenuItemDto addMenuItem(@PathVariable Integer restaurantId, @Valid @RequestBody MenuItemCommand command)
    {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        Assert.notNull(restaurant, "Wrong restaurantId");

        Dish     dish = dishService.findOrCreate(command.getDish());
        MenuItem menuItem = menuItemService.createOrUpdate(restaurant, dish, command.getPrice());

        return new MenuItemDto(menuItem);
    }

    @RequestMapping(value = ApiEndpoints.MENU_ITEMS, method = RequestMethod.GET)
    public List<MenuItemDto> listMenuItems()
    {
        return menuItemService.findAll()
                              .stream().map(MenuItemDto::new)
                              .collect(Collectors.toList());
    }

    @RequestMapping(value = ApiEndpoints.MENU_ITEMS_ITEM, method = RequestMethod.GET)
    public MenuItemDto getMenuItem(@PathVariable Integer itemId)
    {
        MenuItem menuItem = menuItemService.findById(itemId);
        Assert.notNull(menuItem, "Wrong menuItemId");

        return new MenuItemDto(menuItem);
    }

    @RequestMapping(value = ApiEndpoints.MENU_ITEMS_ITEM, method = RequestMethod.DELETE)
    public MessageDto deleteMenuItem(@PathVariable Integer itemId)
    {
        MenuItem menuItem = menuItemService.findById(itemId);
        Assert.notNull(menuItem, "Wrong menuItemId");

        menuItemService.delete(menuItem);

        return message(MENU_ITEM_DELETED_CODE, itemId);
    }

    @RequestMapping(value = ApiEndpoints.MENU_ITEMS, method = RequestMethod.DELETE)
    public MessageDto deleteAllMenuItems()
    {
        menuItemService.deleteAll();

        return message(MENU_ITEM_DELETED_ALL_CODE);
    }
}
