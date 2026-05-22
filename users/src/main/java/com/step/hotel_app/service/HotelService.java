package com.step.hotel_app.service;

import com.step.hotel_app.models.Hotel;
import com.step.hotel_app.repository.HotelRepository;
import com.step.hotel_app.views.HotelView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private  final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelView> searchHotels(String cityName){
        System.out.println(cityName);
        List<Hotel> hotels = hotelRepository.findHotelByCityName(cityName);
        System.out.println(hotels);
        return hotels
                .stream()
                .map(hotel -> new HotelView(hotel.getId(), hotel.getHotelName(), hotel.getCityName(), hotel.getRooms()))
                .toList();
    }
}
