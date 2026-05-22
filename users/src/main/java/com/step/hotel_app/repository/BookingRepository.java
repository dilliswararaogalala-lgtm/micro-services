package com.step.hotel_app.repository;

import com.step.hotel_app.models.HotelBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<HotelBooking, String> {
//    Arrays findHotelBookingsById(String id);

    List<HotelBooking> findHotelBookingsByUserId(String userId);
}
