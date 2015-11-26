package task.service.lvs.dto;

public class RestaurantVoteDto
{
    private String restaurant;
    private Long   votes;

    public RestaurantVoteDto(String restaurant, Long votes)
    {
        this.restaurant = restaurant;
        this.votes = votes;
    }

    public String getRestaurant()
    {
        return restaurant;
    }

    public Long getVotes()
    {
        return votes;
    }
}
