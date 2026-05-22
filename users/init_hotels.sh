#!/bin/bash

mongosh "mongodb://localhost:27017/hotel-app" --eval '
  db.hotels.insertMany([
    { hotelName: "Hotel taj", cityName: "Delhi", rooms: 10 },
    { hotelName: "The Oberoi", cityName: "Mumbai", rooms: 15 }
  ]);
'