import {  Hono } from "hono";
import { logger } from "hono/logger";
import { decode, verify } from "jsonwebtoken";
import { ObjectId } from "mongodb";

const secret = "bheemlaNayakParasuramKaruppuGabbarSinghJerseySalaarDragonKGF";

const bookHotel = async (c) => {
  const { hotelId, rooms } = await c.req.json();
  const bookings = c.get("bookings");
  const username = c.get("username");
  const redis = c.get("redis");
  const booking = await bookings.insertOne({
    username,
    hotelId,
    rooms,
    receiptGenerationStatus: "PENDING",
  });

  await fetch("http://search:3001/api/internal/update-rooms", {
    method: "POST",
    body: JSON.stringify({
      hotelId,
      rooms,
    }),
    headers: { "content-type": "application/json" },
  });
  await redis.rPush(
    "booking_queue",
    JSON.stringify({ id: booking.insertedId.toString() }),
  );

  return c.json({
    bookingId: booking.insertedId.toString(),
    username,
    hotelId,
    rooms,
    receiptGenerationStatus: "PENDING",
  });
};

const getBookings = async (c) => {
  const bookings = c.get("bookings");
  const allBookings = await bookings.find().toArray();
  return c.json(allBookings);
};

const generateReceipt = async (c) => {
  const id = c.req.param("id");
  const bookings = c.get("bookings");
  const bookingData = await bookings.findOne({ _id: new ObjectId(id) });
  if (bookingData.receiptGenerationStatus === "PENDING") {
    return c.json({ msg: "Receipt is not yet generated" });
  }
  return c.json({
    bookingId: bookingData._id.toString(),
    username: bookingData.username,
    hotelId: bookingData.hotelId,
    rooms: bookingData.rooms,
  });
};

export const createApp = (bookings, redis) => {
  const app = new Hono();
  app.use(logger());
  app.use((c, next) => {
    c.set("bookings", bookings);
    c.set("redis", redis);
    return next();
  });

  app.use(async (c, next) => {
    const token = await c.req.header("authorization");
    if (!token || !token.startsWith("Bearer")) {
      return c.json({ msg: "Invalid token" });
    }
    const jwtToken = token.split(" ")[1];

    const jwt = decode(jwtToken);
    try {
      verify(jwtToken, secret);
      c.set("username", jwt.sub);
      return next();
    } catch {
      return c.json({ msg: "token expired" });
    }
  });

  app.get("/api/bookings", getBookings);

  app.post("/api/bookings", bookHotel);

  app.get("/api/bookings/:id/receipt", generateReceipt);

  return app;
};
