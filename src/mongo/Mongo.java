package mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.IOException;
import java.util.Properties;

/**
 * This class demonstrates connecting to a MongoDB database, performing CRUD (Create, Read, Update, Delete) operations,
 * and handling potential exceptions.
 */
public class Mongo {

    /**
     * Main program entry point.
     *
     * @param args Command-line arguments (not currently used)
     * @throws IOException If an error occurs while reading the connection string from the properties file.
     */
    public static void main(String[] args) throws IOException {

        // Read connection string from a properties file
        Properties properties = new Properties();
        properties.load(Mongo.class.getClassLoader().getResourceAsStream("dbconfig/credentials.properties"));
        String connectionString = properties.getProperty("MONGODB_URI");

        // Check if connection string is available
        if (connectionString == null) {
            System.err.println("Missing 'MONGODB_URI' property in credentials.properties");
            return;
        }

        // Create a MongoClient instance using the connection string
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            // Get the "newdb" database
            MongoDatabase database = mongoClient.getDatabase("newdb");

            // =========== Create Operation ===========

            // Create sample documents
            Document document1 = new Document()
                    .append("name", "Sam dari Pakistan")
                    .append("age", 250)
                    .append("city", "Kuala Lumpur");
            Document document2 = new Document()
                    .append("name", "Mike McDonalds")
                    .append("age", 20)
                    .append("city", "Kuala Lumpur");

            // Insert documents into the "users" collection
            database.getCollection("users").insertOne(document1);
            System.out.println("Document inserted successfully!");
            database.getCollection("users").insertOne(document2);
            System.out.println("Document inserted successfully!");

            // =========== Read Operation ===========

            // Read all documents from the "users" collection and print them as JSON
            for (Document user : database.getCollection("users").find()) {
                System.out.println(user.toJson());
            }

            // =========== Update Operation ===========

            // Update the age of the document with "name" as "Sam anak Pakistan"
            database.getCollection("users")
                    .updateOne(new Document("name", "Sam dari Pakistan"),
                            new Document("$set", new Document("age", 30)));
            System.out.println("Document updated successfully!");

            // =========== Delete Operation ===========

            // Delete the document with "name" as "Sam anak Pakistan"
            database.getCollection("users").deleteOne(new Document("name", "Sam dari Pakistan"));
            System.out.println("Document deleted successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
