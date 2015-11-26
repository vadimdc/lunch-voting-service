package task.service.lvs;

import task.service.lvs.domain.Restaurant;
import task.service.lvs.domain.Vote;
import task.service.lvs.service.VoteService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static task.service.lvs.ApiEndpoints.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractApiControllerTest
{
    @Autowired
    private VoteService voteService;

    @Test
    public void testVoteDelete() throws Exception
    {
        mockMvc.perform(delete(apiEndpoint(VOTING)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message", is("All voting results were successfully deleted")));
    }

    @Test
    public void testVoteResults() throws Exception
    {
        String     username1   = randomString();
        String     username2   = randomString();
        String     username3   = randomString();
        Restaurant restaurant1 = createRestaurant();
        Restaurant restaurant2 = createRestaurant();

        createVote(username1, restaurant1);
        createVote(username2, restaurant2);
        createVote(username3, restaurant2);

        mockMvc.perform(get(apiEndpoint(VOTING_RESULTS)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[*].restaurant", contains(restaurant2.getName(), restaurant1.getName())))
               .andExpect(jsonPath("$[*].votes", contains(2, 1)));
    }

    private Vote createVote(String username, Restaurant restaurant)
    {
        Vote vote = voteService.createNew();
        vote.setId(username);
        vote.setRestaurant(restaurant);

        return voteService.save(vote);
    }
}