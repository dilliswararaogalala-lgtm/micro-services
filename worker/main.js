import { createClient } from "redis";
import { MongoClient,ObjectId } from "mongodb";


const sleep = (time)=>{
return new Promise((r)=> {
  setTimeout(r,time)
})
}

const redis = createClient({
  url: "redis://redis:6379",
});

const mongoClient = new MongoClient("mongodb://mongo:27017/hotel-app");
  await mongoClient.connect();
  const db = mongoClient.db("hotel-app");
  const bookingCollection = db.collection("bookings");

await redis.connect();

while (true) {
  const data = await redis.blPop("booking_queue", 0);
  console.log(data , "Starting the work");
  const bookingData = JSON.parse(data.element)
  await sleep(10000)
  bookingCollection.updateOne({_id : new ObjectId(bookingData.id)}, {
    $set: {receiptGenerationStatus : "DONE"}
  })
  console.log("Work Completed");
}