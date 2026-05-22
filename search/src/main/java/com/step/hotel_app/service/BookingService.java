package com.step.hotel_app.service;

import com.step.hotel_app.models.Hotel;
import com.step.hotel_app.models.HotelBooking;
import com.step.hotel_app.repository.BookingRepository;
import com.step.hotel_app.repository.HotelRepository;
import com.step.hotel_app.views.HotelBookingView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    public BookingService(BookingRepository bookingRepository, HotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
    }

    public HotelBookingView bookHotel(String userId, String hotelId, int rooms) {
        HotelBooking booking = bookingRepository.insert(new HotelBooking(userId, hotelId, rooms));
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("hotel not found"));
        hotelRepository.updateRooms(hotelId, hotel.getRooms() - rooms);
        return new HotelBookingView(booking.getId(), booking.getHotelId(), booking.getRooms());
    }

    public List<HotelBookingView> getBookings(String userId) {
        return bookingRepository.findHotelBookingsByUserId(userId).stream().map(booking -> new HotelBookingView(booking.getId(), booking.getHotelId(), booking.getRooms())).toList();
    }
}
