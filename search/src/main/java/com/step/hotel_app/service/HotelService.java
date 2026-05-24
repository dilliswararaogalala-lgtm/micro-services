package com.step.hotel_app.service;

import com.step.hotel_app.models.Hotel;
import com.step.hotel_app.repository.HotelRepository;
import com.step.hotel_app.views.HotelView;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private  final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Cacheable(value = "hotelSearch", key = "#cityName")
    public List<HotelView> searchHotels(String cityName){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Hotel> hotels = hotelRepository.findHotelByCityName(cityName);
        System.out.println(hotels);
        return hotels
                .stream()
                .map(hotel -> new HotelView(hotel.getId(), hotel.getHotelName(), hotel.getCityName(), hotel.getRooms()))
                .toList();
    }
}


