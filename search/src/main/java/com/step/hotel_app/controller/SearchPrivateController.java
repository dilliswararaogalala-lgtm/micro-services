package com.step.hotel_app.controller;

import com.step.hotel_app.models.Hotel;
import com.step.hotel_app.repository.HotelRepository;
import com.step.hotel_app.views.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal")
public class SearchPrivateController {
    @Autowired
    HotelRepository hotelRepository;
    @PostMapping("/update-rooms")
    public ResponseEntity<?> updateRooms(@RequestBody BookingRequest body) {
        Hotel hotel = hotelRepository.findById(body.hotelId()).orElseThrow(() -> new RuntimeException("hotel not found"));

        hotelRepository.updateRooms(body.hotelId(), hotel.getRooms() - body.rooms());
        return ResponseEntity.ok().build();
    }
}
