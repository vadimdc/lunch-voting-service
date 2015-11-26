package task.service.lvs.service;

import task.service.lvs.repository.VoteRepository;
import task.service.lvs.domain.Restaurant;
import task.service.lvs.domain.Vote;
import task.service.lvs.dto.RestaurantVoteDto;

public interface VoteService extends Voting<Restaurant, Vote>, Result<RestaurantVoteDto>, BaseEntityService<Vote, String, VoteRepository>
{
}
