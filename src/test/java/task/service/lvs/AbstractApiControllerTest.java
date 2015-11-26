package task.service.lvs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import task.service.lvs.domain.Dish;
import task.service.lvs.domain.MenuItem;
import task.service.lvs.domain.Restaurant;
import task.service.lvs.service.DishService;
import task.service.lvs.service.MenuItemService;
import task.service.lvs.service.RestaurantService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
@Ignore
public class AbstractApiControllerTest extends AbstractTransactionalJUnit4SpringContextTests
{
    protected static final Integer WRONG_ID = 0;

    protected MockMvc mockMvc;

    @Autowired
    protected DishService dishService;
    @Autowired
    protected RestaurantService restaurantService;
    @Autowired
    protected MenuItemService menuItemService;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected WebApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    protected String jsonContent(Object o) throws JsonProcessingException
    {
        return objectMapper.writeValueAsString(o);
    }

    protected Restaurant createRestaurant(String name)
    {
        Restaurant restaurant = restaurantService.createNew();
        restaurant.setName(name);

        return restaurantService.save(restaurant);
    }

    protected Restaurant createRestaurant()
    {
        return createRestaurant(randomString());
    }

    protected Dish createDish()
    {
        return createDish(randomString());
    }

    protected Dish createDish(String name)
    {
        Dish dish = dishService.createNew();
        dish.setName(name);

        return dishService.save(dish);
    }

    protected MenuItem createMenuItem(Restaurant restaurant, Dish dish, BigDecimal price)
    {
        MenuItem menuItem = menuItemService.createNew();
        menuItem.setRestaurant(restaurant);
        menuItem.setDish(dish);
        menuItem.setPrice(price);

        return menuItemService.save(menuItem);
    }

    protected String randomString()
    {
        return RandomStringUtils.random(10, true, true);
    }
}