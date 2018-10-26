package dao;

import model.BaseMongoDO;
import service.EncryptionServices;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;


public class MongoDBConnection {
    private static MongoDBConnection instance = new MongoDBConnection();

    private MongoClient mongo = null;
    private Datastore dataStore = null;
    private Morphia morphia = null;

    private MongoDBConnection() {}
    
    private static boolean useLocalDB = false;

    public MongoClient getMongo() throws RuntimeException {
        if (mongo == null) {
            System.out.println("Starting Mongo");
            MongoClientOptions.Builder options = MongoClientOptions.builder()
                    .connectionsPerHost(4)
                    .maxConnectionIdleTime((60 * 1_000))
                    .maxConnectionLifeTime((120 * 1_000));


            MongoClientURI uri;
            if(useLocalDB) {
            	 uri = new MongoClientURI("mongodb://localhost:27017", options);
            }
            else
            {
                String mongoUser = "iktBnbHMqwUsStkwgYpBmw==";
                String mongoPassword = "axo0aFqgpeH6YdBZhwNcpTL9v6ihSaQQdHASkS+B1UU=";
                
                //MongoClientURI uri = new MongoClientURI("mongodb://heroku_7h0pgzxc:n2ch01otcr2dsb2p8vta9hn09h@ds145325.mlab.com:45325/heroku_7h0pgzxc", options);
                uri = new MongoClientURI("mongodb://" + 
                										EncryptionServices.decrypt(mongoUser) + ":" + 
                										EncryptionServices.decrypt(mongoPassword) +
                										"@ds145325.mlab.com:45325/heroku_7h0pgzxc", options);
            }
                     
            
            // remote connection
            

            System.out.println("About to connect to MongoDB @ " + uri.toString());

            try {
                mongo = new MongoClient(uri);
                mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
            } catch (MongoException ex) {
                System.out.println("An error occoured when connecting to MongoDB" + ex);
            } catch (Exception ex) {
                System.out.println("An error occoured when connecting to MongoDB" + ex);
            }

            // To be able to wait for confirmation after writing on the DB
            mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        }

        return mongo;
    }

    public Morphia getMorphia() {
        if (morphia == null) {
            System.out.println("Starting Morphia");
            morphia = new Morphia();

            System.out.println("Mapping packages for clases within %s" + BaseMongoDO.class.getName());
            morphia.mapPackageFromClass(BaseMongoDO.class);
        }

        return morphia;
    }

    public Datastore getDatastore() {
        //TODO: Why do we have this Horrible DBName? :)
    	
    	if (dataStore == null) {
            String dbName = "heroku_7h0pgzxc";
            System.out.println("Starting DataStore on DB: %s" + dbName);
            dataStore = getMorphia().createDatastore(getMongo(), dbName);
        }

        return dataStore;
    }

    public void init() {
        System.out.println("Bootstraping");
        getMongo();
        getMorphia();
        getDatastore();
    }

    public void close() {
        System.out.println("Closing MongoDB connection");
        if (mongo != null) {
            try {
                mongo.close();
                System.out.println("Nulling the connection dependency objects");
                mongo = null;
                morphia = null;
                dataStore = null;
            } catch (Exception e) {
                System.out.println("An error occurred when closing the MongoDB connection\n%s" + e.getMessage());
            }
        } else {
            System.out.println("mongo object was null, wouldn't close connection");
        }
    }

    public static MongoDBConnection getInstance() {
        return instance;
    }
}