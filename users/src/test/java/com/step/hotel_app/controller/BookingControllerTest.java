package com.step.hotel_app.controller;

import com.step.hotel_app.models.Hotel;
import com.step.hotel_app.models.HotelBooking;
import com.step.hotel_app.repository.BookingRepository;
import com.step.hotel_app.repository.HotelRepository;
import com.step.hotel_app.views.BookingRequest;
import com.step.hotel_app.views.HotelBookingView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.AutoConfigureDataMongo;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureRestTestClient
class BookingControllerTest {

    @Autowired
    RestTestClient client;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Test
    void shouldBookAHotel() {
        Hotel hotel = hotelRepository.save(new Hotel("Hotel Taj", "Delhi", 50));

        BookingRequest hotelBooking = new BookingRequest(hotel.getId(), 1);
        HotelBookingView responseBody = client.post().uri("/api/bookings").body(hotelBooking).exchange().expectStatus().isOk().expectBody(HotelBookingView.class).returnResult().getResponseBody();

        assertNotNull(bookingRepository.findById(Objects.requireNonNull(responseBody).bookingId()));

        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).orElseThrow(() -> new RuntimeException("hotel not found"));

        assertEquals(updatedHotel.getRooms(), 49);
    }

    @Test
    void getBookings() {
        bookingRepository.save(new HotelBooking("12234", "1", 1));
        List responseBody = client.get().uri("/api/bookings").exchange().expectStatus().isOk().expectBody(List.class).returnResult().getResponseBody();

        assertEquals(responseBody.size(), 1);
    }
}