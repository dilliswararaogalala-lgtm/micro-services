package com.step.hotel_app.controller;

import com.step.hotel_app.models.Hotel;
import com.step.hotel_app.repository.HotelRepository;
import com.step.hotel_app.views.HotelView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.AutoConfigureDataMongo;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureRestTestClient
@AutoConfigureDataMongo
class HotelControllerTest {

    @Autowired
    RestTestClient client;

    @Autowired
    HotelRepository repo;

    @Test
    void searchHotels() {

        Hotel tajHotel = repo.save(new Hotel("Hotel Taj", "Delhi", 50));
        repo.save(new Hotel("International Hotel", "Abu Dhabi", 1000));

        List<?> responseBody = client
                .get()
                .uri("/api/search/hotels?city=Delhi")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class).returnResult().getResponseBody();

        ObjectMapper objectMapper = new ObjectMapper();
        List<HotelView> expectedHotelView = List.of(new HotelView(tajHotel.getId(), tajHotel.getHotelName(), tajHotel.getCityName(), tajHotel.getRooms()));

        assertEquals(objectMapper.convertValue(expectedHotelView, List.class), responseBody);
    }
}