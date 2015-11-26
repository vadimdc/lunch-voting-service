package task.service.lvs;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import task.service.lvs.controller.command.MenuItemCommand;
import task.service.lvs.domain.Dish;
import task.service.lvs.domain.MenuItem;
import task.service.lvs.domain.Restaurant;
import task.service.lvs.dto.ResponseErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuControllerTest extends AbstractApiControllerTest
{
    @Test
    public void testGetRestaurantMenuItems() throws Exception
    {
        String     price      = "1.00";
        Dish       dish       = createDish();
        Restaurant restaurant = createRestaurant();

        MenuItem menuItem = createMenuItem(restaurant, dish, new BigDecimal(price));
        restaurant.setMenuItems(Sets.newHashSet(menuItem));
        restaurantService.save(restaurant);

        mockMvc.perform(get(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_RESTAURANT), restaurant.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].menuItemId", is(menuItem.getId())))
               .andExpect(jsonPath("$[0].restaurant", is(restaurant.getName())))
               .andExpect(jsonPath("$[0].dish", is(dish.getName())))
               .andExpect(jsonPath("$[0].price", is(new Double(price))));
    }

    @Test
    public void testGetRestaurantMenuItemWrongId() throws Exception
    {
        mockMvc.perform(get(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_RESTAURANT), WRONG_ID))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong restaurantId")));
    }

    @Test
    public void testCreateRestaurantMenuItems() throws Exception
    {
        String     price      = "1.00";
        Restaurant restaurant = createRestaurant();

        MenuItemCommand command = new MenuItemCommand();
        command.setDish(randomString());
        command.setPrice(new BigDecimal(price));

        mockMvc.perform(post(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command))
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.menuItemId").exists())
               .andExpect(jsonPath("$.restaurant", is(restaurant.getName())))
               .andExpect(jsonPath("$.dish", is(command.getDish())))
               .andExpect(jsonPath("$.price", is(new Double(price))));
    }

    @Test
    public void testCreateRestaurantMenuItemNullDish() throws Exception
    {
        Restaurant restaurant = createRestaurant();
        BigDecimal price      = BigDecimal.ONE;

        MenuItemCommand command = new MenuItemCommand();
        command.setDish(null);
        command.setPrice(price);

        mockMvc.perform(post(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command))
        )
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Dish name should be set")));
    }

    @Test
    public void testCreateRestaurantMenuItemEmptyDish() throws Exception
    {
        Restaurant restaurant = createRestaurant();
        BigDecimal price      = BigDecimal.ONE;

        MenuItemCommand command = new MenuItemCommand();
        command.setDish(StringUtils.EMPTY);
        command.setPrice(price);

        mockMvc.perform(post(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command))
        )
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Dish name length should be between 1 and 191 characters")));
    }

    @Test
    public void testCreateRestaurantMenuItemNullPrice() throws Exception
    {
        Restaurant restaurant = createRestaurant();

        MenuItemCommand command = new MenuItemCommand();
        command.setDish(randomString());
        command.setPrice(null);

        mockMvc.perform(post(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command))
        )
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Price should be set")));
    }

    @Test
    public void testCreateRestaurantMenuItemWrongId() throws Exception
    {
        BigDecimal price = BigDecimal.ONE;

        MenuItemCommand command = new MenuItemCommand();
        command.setDish(randomString());
        command.setPrice(price);

        mockMvc.perform(post(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_RESTAURANT), WRONG_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command))
        )
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong restaurantId")));
    }

    @Test
    public void testGetAllMenuItems() throws Exception
    {
        String     price1     = "1.00";
        String     price10    = "10.00";
        Restaurant restaurant = createRestaurant();

        List<MenuItem> menuItems = Lists
                .newArrayList(createMenuItem(restaurant, createDish(), new BigDecimal(price1)), createMenuItem(restaurant, createDish(), new BigDecimal(price10)));

        mockMvc.perform(MockMvcRequestBuilders.get(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[*].menuItemId").exists())
               .andExpect(jsonPath("$[*].restaurant", hasItem(restaurant.getName())))
               .andExpect(jsonPath("$[*].dish", containsInAnyOrder(menuItems.stream().map(item -> item.getDish().getName()).toArray())))
               .andExpect(jsonPath("$[*].price", containsInAnyOrder(new Double(price1), new Double(price10))));
    }

    @Test
    public void testDeleteAllMenuItems() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is("All menu items were successfully deleted")));
    }

    @Test
    public void testGetMenuItem() throws Exception
    {
        String     price      = "1.00";
        Dish       dish       = createDish();
        Restaurant restaurant = createRestaurant();

        MenuItem menuItem = createMenuItem(restaurant, dish, new BigDecimal(price));

        mockMvc.perform(get(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_ITEM), menuItem.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.menuItemId", is(menuItem.getId())))
               .andExpect(jsonPath("$.restaurant", is(restaurant.getName())))
               .andExpect(jsonPath("$.dish", is(dish.getName())))
               .andExpect(jsonPath("$.price", is(new Double(price))));
    }

    @Test
    public void testGetMenuItemWrongId() throws Exception
    {
        mockMvc.perform(get(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_ITEM), WRONG_ID))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong menuItemId")));
    }

    @Test
    public void testDeleteMenuItem() throws Exception
    {
        Dish       dish       = createDish();
        Restaurant restaurant = createRestaurant();

        MenuItem menuItem = createMenuItem(restaurant, dish, BigDecimal.ONE);

        mockMvc.perform(delete(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_ITEM), menuItem.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is(String.format("Menu item with id=%s was successfully deleted", menuItem.getId()))));
    }

    @Test
    public void testDeleteMenuItemWrongId() throws Exception
    {
        mockMvc.perform(delete(ApiEndpoints.apiEndpoint(ApiEndpoints.MENU_ITEMS_ITEM), WRONG_ID))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong menuItemId")));
    }

    @Test
    public void testGetLunchMenu() throws Exception
    {
        String     price1      = "1.00";
        String     price2      = "2.00";
        String     price5      = "5.00";
        Dish       dish1       = createDish();
        Dish       dish2       = createDish("Dish 2");
        Dish       dish3       = createDish("Dish 3");
        Restaurant restaurant1 = createRestaurant("Restaurant 1");
        Restaurant restaurant2 = createRestaurant("Restaurant 2");

        Set<MenuItem> menuItems1 = Sets.newHashSet(createMenuItem(restaurant1, dish1, new BigDecimal(price1)));
        Set<MenuItem> menuItems2 = Sets.newHashSet(createMenuItem(restaurant2, dish2, new BigDecimal(price2)), createMenuItem(restaurant2, dish3, new BigDecimal(price5)));
        restaurant1.setMenuItems(menuItems1);
        restaurantService.save(restaurant1);
        restaurant2.setMenuItems(menuItems2);
        restaurantService.save(restaurant2);

        mockMvc.perform(MockMvcRequestBuilders.get(ApiEndpoints.apiEndpoint(ApiEndpoints.LUNCH_MENU)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[*].restaurant", contains(restaurant1.getName(), restaurant2.getName())))
               .andExpect(jsonPath("$[0].dishes", hasSize(1)))
               .andExpect(jsonPath("$[0].dishes[0].dish", is(dish1.getName())))
               .andExpect(jsonPath("$[0].dishes[0].price", is(new Double(price1))))
               .andExpect(jsonPath("$[1].dishes", hasSize(2)))
               .andExpect(jsonPath("$[1].dishes[*].dish", contains(dish2.getName(), dish3.getName())))
               .andExpect(jsonPath("$[1].dishes[*].price", contains(new Double(price2), new Double(price5))));
    }
}