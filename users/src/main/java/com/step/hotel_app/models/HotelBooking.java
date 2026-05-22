package com.step.hotel_app.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "bookings")
public class HotelBooking {
    @Id
    private String id;
    private String userId;
    private String hotelId;
    private int rooms;

    public HotelBooking(String userId, String hotelId, int rooms) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.rooms = rooms;
    }
}
