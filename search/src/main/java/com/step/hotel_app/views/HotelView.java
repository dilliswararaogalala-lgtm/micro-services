package com.step.hotel_app.views;

import java.io.Serializable;

public record HotelView(String id, String name, String city, Integer rooms)  implements Serializable {
}
