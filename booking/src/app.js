import { Hono } from "hono";
import { logger } from "hono/logger";
import { decode, verify } from "jsonwebtoken";

const secret = "bheemlaNayakParasuramKaruppuGabbarSinghJerseySalaarDragonKGF";

const bookHotel = async (c) => {
  const { hotelId, rooms } = await c.req.json();
  const bookings = c.get("bookings");
  const username = c.get("username");
  const booking = await bookings.insertOne({
    username,
    hotelId,
    rooms,
  });

  const res = await fetch("http://search:3001/api/internal/update-rooms",{
    method :"POST",
    body:JSON.stringify({
      hotelId,
      rooms
    }),
    headers:{"content-type" : "application/json"}
  })
  

  return c.json({
    bookingId: booking.insertedId.toString(),
    username,
    hotelId,
    rooms,
  });
};

const getBookings = async (c) => {
  const bookings = c.get("bookings");
  const allBookings = await bookings.find().toArray();
  return c.json(allBookings);
};

export const createApp = (bookings) => {
  const app = new Hono();
  app.use(logger());
  app.use((c, next) => {
    c.set("bookings", bookings);
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

  return app;
};
