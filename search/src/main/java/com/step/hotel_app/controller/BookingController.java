package com.step.hotel_app.controller;
import com.step.hotel_app.models.AppUser;
import com.step.hotel_app.service.BookingService;
import com.step.hotel_app.views.BookingRequest;
import com.step.hotel_app.views.HotelBookingView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public HotelBookingView bookHotel(@RequestBody BookingRequest bookingRequest, @AuthenticationPrincipal AppUser userDetails) {
        String userId = userDetails.getId();
        return bookingService.bookHotel(userId, bookingRequest.hotelId(), bookingRequest.rooms());
    }

    @GetMapping
    public List<HotelBookingView> getBookings(@AuthenticationPrincipal AppUser userDetails) {
        String userId = userDetails.getId();
        return bookingService.getBookings(userId);
    }
}
