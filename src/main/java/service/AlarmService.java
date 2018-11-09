package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

public class AlarmService {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String getAlarmsFromUser(User user) throws JsonProcessingException {
        return mapper.writeValueAsString(user.getAlarms());
    }
}
