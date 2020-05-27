package model;

import java.util.Date;
import java.util.Optional;

public class bookingDetails {
    private int ticketId;
    private int userId;
    private Date date;

    public bookingDetails(int ticketId, Optional<User> u , Date date) {
        this.ticketId = ticketId;
        u.ifPresent(user -> this.userId = user.getId());
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }


    public String getDate() {
        return date.toString();
    }
}
