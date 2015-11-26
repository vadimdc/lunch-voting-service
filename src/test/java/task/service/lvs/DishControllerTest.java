package task.service.lvs;

import com.google.common.collect.Lists;
import task.service.lvs.controller.command.DishNameCommand;
import task.service.lvs.domain.Dish;
import task.service.lvs.dto.ResponseErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishControllerTest extends AbstractApiControllerTest
{
    @Test
    public void testListDishes() throws Exception
    {
        List<Dish> dishes = Lists.newArrayList(createDish(), createDish(), createDish(), createDish());

        mockMvc.perform(MockMvcRequestBuilders.get(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(dishes.size())))
               .andExpect(jsonPath("$[*].dishId", containsInAnyOrder(dishes.stream().map(Dish::getId).toArray())))
               .andExpect(jsonPath("$[*].dish", containsInAnyOrder(dishes.stream().map(Dish::getName).collect(Collectors.toList()).toArray())));

    }

    @Test
    public void testGetDish() throws Exception
    {
        Dish dish = createDish();

        mockMvc.perform(get(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), dish.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.dishId").value(dish.getId()))
               .andExpect(jsonPath("$.dish").value(dish.getName()));
    }

    @Test
    public void testGetDishWrongId() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), WRONG_ID))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong dishId")));
    }

    @Test
    public void testCreateDish() throws Exception
    {
        String dishName = randomString();
        DishNameCommand command = new DishNameCommand();
        command.setDish(dishName);

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.dishId").exists())
               .andExpect(jsonPath("$.dish", is(dishName)));
    }

    @Test
    public void testCreateDishDuplicateName() throws Exception
    {
        Dish dish = createDish();

        DishNameCommand command = new DishNameCommand();
        command.setDish(dish.getName());

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command))
        )
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Specified name already exists")));
    }

    @Test
    public void testCreateDishNullName() throws Exception
    {
        DishNameCommand command = new DishNameCommand();
        command.setDish(null);

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Dish name should be set")));
    }

    @Test
    public void testCreateDishEmptyName() throws Exception
    {
        DishNameCommand command = new DishNameCommand();
        command.setDish(StringUtils.EMPTY);

        mockMvc.perform(MockMvcRequestBuilders.post(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Dish name length should be between 1 and 191 characters")));
    }

    @Test
    public void testUpdateDish() throws Exception
    {
        Dish dish = createDish();
        String dishName = randomString();
        DishNameCommand command = new DishNameCommand();
        command.setDish(dishName);

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), dish.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.dishId", is(dish.getId())))
               .andExpect(jsonPath("$.dish", is(dishName)));
    }

    @Test
    public void testUpdateDishWrongId() throws Exception
    {
        String dishName = randomString();
        DishNameCommand command = new DishNameCommand();
        command.setDish(dishName);

        mockMvc.perform(MockMvcRequestBuilders.put(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), WRONG_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong dishId")));

    }

    @Test
    public void testUpdateDishDuplicateName() throws Exception
    {
        Dish dish = createDish();

        DishNameCommand command = new DishNameCommand();
        command.setDish(dish.getName());

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), dish.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Specified name already exists")));
    }

    @Test
    public void testUpdateDishNullName() throws Exception
    {
        Dish dish = createDish();

        DishNameCommand command = new DishNameCommand();
        command.setDish(null);

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), dish.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Dish name should be set")));
    }

    @Test
    public void testUpdateDishEmptyName() throws Exception
    {
        Dish dish = createDish();

        DishNameCommand command = new DishNameCommand();
        command.setDish(StringUtils.EMPTY);

        mockMvc.perform(put(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), dish.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent(command)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Dish name length should be between 1 and 191 characters")));
    }

    @Test
    public void testDeleteDish() throws Exception
    {
        Dish dish = createDish();

        mockMvc.perform(delete(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), dish.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is(String.format("Dish '%s' was successfully deleted", dish.getName()))));
    }

    @Test
    public void testDeleteDishWrongId() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES_DISH), WRONG_ID))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorCode", is(ResponseErrorCode.VALIDATION_ERROR.name())))
               .andExpect(jsonPath("$.message", is("Wrong dishId")));
    }

    @Test
    public void testDeleteAllDish() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete(ApiEndpoints.apiEndpoint(ApiEndpoints.DISHES)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is("All dishes were successfully deleted")));
    }
}