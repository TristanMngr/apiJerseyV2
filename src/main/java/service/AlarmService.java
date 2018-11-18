package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AlarmDAO;
import model.Alarm;
import model.User;
import model.Event;

import java.awt.*;
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

    public static String getAlarmByName(User user, String name) throws JsonProcessingException {
        Alarm alarm = ManagementService.getAlarmDAO().getAlarmByName(user, name);

        return "{\"error\":0,\"events\":" + mapper.writeValueAsString(alarm.getEvent()) + "}";
    }


    public static String removeAlarm(User user, String name) throws JsonProcessingException {
        for (Iterator<Alarm> iterator = ManagementService.getAlarmDAO().getAlarmFromUser(user).iterator(); iterator.hasNext(); ) {
            Alarm alarm = iterator.next();
            if (alarm.getName() != null && alarm.getName().equals(name)) {
                ManagementService.getAlarmDAO().delete(alarm);
            }
        }


        return "{\"error\":0,\"user\":" + mapper.writeValueAsString(user) + "}";
    }
}
