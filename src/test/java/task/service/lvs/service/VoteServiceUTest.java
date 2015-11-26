package task.service.lvs.service;

import task.service.lvs.repository.VoteRepository;
import task.service.lvs.domain.Vote;

public class VoteServiceUTest extends BaseServiceUTest<Vote, String, VoteRepository, VoteService>
{
    public VoteServiceUTest()
    {
        super(new VoteServiceImpl(), VoteRepository.class, Vote.class);
    }
}