package com.step.hotel_app.controller;

import com.step.hotel_app.service.HotelService;
import com.step.hotel_app.views.HotelView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/search/hotels")
    public List<HotelView> searchHotels(@RequestParam String city){

        return hotelService.searchHotels(city);
    }
}
