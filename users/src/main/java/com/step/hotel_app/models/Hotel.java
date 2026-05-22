package com.step.hotel_app.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "hotels")
public class Hotel {
    @Id
    private String id;
    private String hotelName;
    private String cityName;
    private Integer rooms;

    public Hotel(String hotelName, String cityName, Integer rooms) {
        this.hotelName = hotelName;
        this.cityName = cityName;
        this.rooms = rooms;
    }
}
