package task.service.lvs.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "voting")
public class Vote extends BaseObject<String>
{
    private String username;
    private Restaurant restaurant;

    @Id
    @Override
    @Column(name = "username", nullable = false)
    public String getId()
    {
        return username;
    }

    public void setId(String username)
    {
        this.username = username;
    }

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (!(obj instanceof Vote))
            return false;

        Vote vote = (Vote)obj;

        return new EqualsBuilder()
                .append(getId(), vote.getId())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("username", getId())
                .append("restaurant", getRestaurant())
                .toString();
    }


}
