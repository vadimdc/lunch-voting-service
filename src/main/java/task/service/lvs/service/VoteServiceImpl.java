package task.service.lvs.service;

import task.service.lvs.repository.VoteRepository;
import task.service.lvs.domain.Restaurant;
import task.service.lvs.domain.Vote;
import task.service.lvs.dto.RestaurantVoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("voteService")
public class VoteServiceImpl extends AbstractService<Vote, String, VoteRepository> implements VoteService
{
    private static final String TIME_FORMAT_PATTERN = "HH:mm:ss";

    @Value("${application.voting.time-end}")
    private String votingTimeEnd;

    public VoteServiceImpl()
    {
        super(Vote.class);
    }

    @Override
    @Autowired
    public void setRepository(VoteRepository repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false)
    public Vote vote(String username, Restaurant restaurant)
    {
        validateVotingTime();

        Vote vote = findById(username);

        if (null == vote)
        {
            vote = createNew();
            vote.setId(username);
        }

        vote.setRestaurant(restaurant);

        return save(vote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantVoteDto> getResult()
    {
        Map<Restaurant, Long> restaurantVotesMap = findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Vote::getRestaurant,
                                Collectors.counting()
                        )
                );

        return restaurantVotesMap.keySet()
                                 .stream()
                                 .map(restaurant -> new RestaurantVoteDto(restaurant.getName(), restaurantVotesMap.get(restaurant)))
                                 .sorted(Comparator.comparing(RestaurantVoteDto::getVotes).reversed())
                                 .collect(Collectors.toList());
    }

    private void validateVotingTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_PATTERN);

        try
        {
            Date endVotingTime = dateFormat.parse(votingTimeEnd);
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));

            Assert.isTrue(currentTime.before(endVotingTime), String.format("You can't vote after %s", votingTimeEnd));
        }
        catch (ParseException e)
        {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
