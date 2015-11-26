package task.service.lvs;

import com.google.common.collect.Lists;
import task.service.lvs.controller.command.RestaurantNameCommand;
import task.service.lvs.domain.Restaurant;
import task.service.lvs.dto.ResponseErrorCode;
import task.service.lvs.service.RestaurantService;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantControllerTest extends AbstractApiControllerTest
{
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testListRestaurants() throws Exception
    {
        List<Restaurant> restaurants = Lists.newArrayList(createRestaurant(), createRestaurant(), createRestaurant());

        mockMvc.perform(MockMvcRequestBuilders.get(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(restaurants.size())))
               .andExpect(jsonPath("$[*].restaurantId", containsInAnyOrder(restaurants.stream().map(Restaurant::getId).toArray())))
               .andExpect(jsonPath("$[*].restaurant", containsInAnyOrder(restaurants.stream().map(Restaurant::getName).collect(Collectors.toList()).toArray())));
    }

    @Test
    public void testGetRestaurant() throws Exception
    {
        Restaurant restaurant = createRestaurant();

        mockMvc.perform(get(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), restaurant.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.restaurantId").value(restaurant.getId()))
               .andExpect(jsonPath("$.restaurant").value(restaurant.getName()));
    }

    @Test
    public void testGetRestaurantWrongId() throws Exception
    {
        mockMvc.perform(get(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), WRONG_ID))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", Matchers.is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong restaurantId")));
    }

    @Test
    public void testCreateRestaurant() throws Exception
    {
        String restaurantName = randomString();
        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(restaurantName);

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.restaurantId").exists())
               .andExpect(jsonPath("$.restaurant", is(restaurantName)));
    }

    @Test
    public void testCreateRestaurantDuplicateName() throws Exception
    {
        Restaurant restaurant = createRestaurant();

        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(restaurant.getName());

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command))
        )
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Specified name already exists")));
    }

    @Test
    public void testCreateRestaurantNullName() throws Exception
    {
        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(null);

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Restaurant name should be set")));
    }

    @Test
    public void testCreateRestaurantEmptyName() throws Exception
    {
        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(StringUtils.EMPTY);

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Restaurant name length should be between 1 and 191 characters")));
    }

    @Test
    public void testUpdateRestaurant() throws Exception
    {
        Restaurant restaurant = createRestaurant();
        String restaurantName = randomString();
        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(restaurantName);

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.restaurantId", is(restaurant.getId())))
               .andExpect(jsonPath("$.restaurant", is(restaurantName)));
    }

    @Test
    public void testUpdateRestaurantWrongId() throws Exception
    {
        String restaurantName = randomString();
        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(restaurantName);

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), WRONG_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong restaurantId")));

    }

    @Test
    public void testUpdateRestaurantDuplicateName() throws Exception
    {
        Restaurant restaurant = createRestaurant();

        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(restaurant.getName());

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Specified name already exists")));
    }

    @Test
    public void testUpdateRestaurantNullName() throws Exception
    {
        Restaurant restaurant = createRestaurant();

        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(null);

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Restaurant name should be set")));
    }

    @Test
    public void testUpdateRestaurantEmptyName() throws Exception
    {
        Restaurant restaurant = createRestaurant();

        RestaurantNameCommand command = new RestaurantNameCommand();
        command.setRestaurant(StringUtils.EMPTY);

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Restaurant name length should be between 1 and 191 characters")));
    }

    @Test
    public void testDeleteRestaurant() throws Exception
    {
        Restaurant restaurant = createRestaurant();

        mockMvc.perform(delete(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), restaurant.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is(String.format("Restaurant '%s' was successfully deleted", restaurant.getName()))));
    }

    @Test
    public void testDeleteRestaurantWrongId() throws Exception
    {
        mockMvc.perform(delete(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS_RESTAURANT), WRONG_ID))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong restaurantId")));
    }

    @Test
    public void testDeleteAllRestaurant() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiEndpoints.apiEndpoint(ApiEndpoints.RESTAURANTS)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is("All restaurants were successfully deleted")));
    }
}