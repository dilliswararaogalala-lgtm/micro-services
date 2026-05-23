import { createApp } from "./src/app.js";
import { MongoClient } from "mongodb";

const main = async() => {
  const mongoClient = new MongoClient("mongodb://mongo:27017/hotel-app");
  await mongoClient.connect();
  const db = mongoClient.db("hotel-app");
  const bookingCollection = db.collection("bookings");
  const app = createApp(bookingCollection);
  Deno.serve({port: 3002}, app.fetch)
}

main();