package task.service.lvs.repository;

import task.service.lvs.domain.Vote;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends BaseRepository<Vote, String>
{
}
