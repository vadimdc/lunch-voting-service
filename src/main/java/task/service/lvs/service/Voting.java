package task.service.lvs.service;

public interface Voting<R, V>
{
    /**
     * Add vote from username for restaurant or change it if exists
     *
     * @param username user who votes
     * @param voteFor entity user votes for
     *
     * @return V voting entity
     */
    V vote(String username, R voteFor);
}
