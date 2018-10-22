package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.bson.types.ObjectId;

public class UserService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String getUserById(ObjectId objectId) throws JsonProcessingException {
        User user = ManagementService.getUserDAO().getByUserId(objectId);
        if (user == null) {
            return null;
        }
        return mapper.writeValueAsString(ManagementService.getUserDAO().getByUserId(objectId));
    }

    public static String getUserByName(String userName) throws JsonProcessingException {
        User user = ManagementService.getUserDAO().getUserByName(userName);
        if (user == null) {
            return null;
        }
        return mapper.writeValueAsString(ManagementService.getUserDAO().getUserByName(userName));
    }

    public static String getAllUsers() throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getUserDAO().getAllUsers());
    }


}
