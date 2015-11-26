package task.service.lvs.controller;

import task.service.lvs.ApiEndpoints;
import task.service.lvs.controller.command.VoteCommand;
import task.service.lvs.domain.Vote;
import task.service.lvs.dto.MessageDto;
import task.service.lvs.service.VoteService;
import task.service.lvs.domain.Restaurant;
import task.service.lvs.dto.RestaurantVoteDto;
import task.service.lvs.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.API_ROOT)
public class VoteController extends ApiController
{
    private static final String VOTE_MISSING_CODE     = "vote.missing.message.key";
    private static final String VOTE_ACCEPTED_CODE    = "vote.accepted.message.key";
    private static final String VOTE_DELETED_ALL_CODE = "vote.deleted-all.message.key";

    @Autowired
    private VoteService       voteService;
    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = ApiEndpoints.VOTING_RESULTS, method = RequestMethod.GET)
    public List<RestaurantVoteDto> results()
    {
        return voteService.getResult();
    }

    @RequestMapping(value = ApiEndpoints.VOTING, method = RequestMethod.GET)
    public MessageDto get(@ModelAttribute("username") String username)
    {
        Assert.notNull(username, "Current user is required");

        Vote vote = voteService.findById(username);

        return (null != vote)
                ? message(VOTE_ACCEPTED_CODE, vote.getRestaurant().getName())
                : message(VOTE_MISSING_CODE);

    }

    @RequestMapping(value = ApiEndpoints.VOTING, method = RequestMethod.POST)
    public MessageDto vote(@RequestBody VoteCommand command, @ModelAttribute("username") String username)
    {
        Assert.notNull(username, "User is required");

        Restaurant restaurant = restaurantService.findByName(command.getRestaurant());
        Assert.notNull(restaurant, String.format("Restaurant '%s' not found", command.getRestaurant()));

        Vote vote = voteService.vote(username, restaurant);

        return message(VOTE_ACCEPTED_CODE, vote.getRestaurant().getName());
    }

    @RequestMapping(value = ApiEndpoints.VOTING, method = RequestMethod.DELETE)
    public MessageDto delete()
    {
        voteService.deleteAll();

        return message(VOTE_DELETED_ALL_CODE);
    }

}
