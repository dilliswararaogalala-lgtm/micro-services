import { createApp } from "./src/app.js";
import { MongoClient } from "mongodb";
import {createClient} from "redis"

const main = async() => {
  console.log(Deno.env.get("MONGO_URL"));
  console.log(Deno.env.get("REDIS_URL"));
  const mongoClient = new MongoClient("mongodb://mongo:27017/hotel-app");
  const redis = createClient({
  url: "redis://redis:6379",
});
  await mongoClient.connect();
  await redis.connect()
  const db = mongoClient.db("hotel-app");
  const bookingCollection = db.collection("bookings");
  const app = createApp(bookingCollection,redis);
  Deno.serve({port: 3002}, app.fetch)
}

main();