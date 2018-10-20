package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;

public class UserService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String getUserById(ObjectId objectId) throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getUserDAO().getByUserId(objectId));
    }

    public static String getAllUsers() throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getUserDAO().getAllUsers());
    }


}
