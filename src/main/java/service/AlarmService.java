package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Alarm;
import model.User;

import java.util.Iterator;

public class AlarmService {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String getAlarmsFromUser(User user) throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getAlarmDAO().getAlarmFromUser(user));
    }

    public static String createAlarm(User user, String name, String categoryId) throws JsonProcessingException {
        Alarm alarm = new Alarm(user.getId(), name, categoryId);
        ManagementService.getAlarmDAO().save(alarm);
        return "{\"error\":0,\"alarm\":" + mapper.writeValueAsString(alarm) + ",\"user\":" + mapper.writeValueAsString(user) + "}";
    }

    /*public static String removeAlarm(User user, String name) throws JsonProcessingException {
        for (Iterator<Alarm> iterator = user.getAlarms().iterator(); iterator.hasNext(); ) {
            Alarm alarm = iterator.next();
            if (alarm.getName() != null && alarm.getName().equals(name)) {
                iterator.remove();
            }
        }

        ManagementService.getUserDAO().save(user);
        return "{\"error\":0,\"user\":" + mapper.writeValueAsString(user) + "}";
    }*/
}
