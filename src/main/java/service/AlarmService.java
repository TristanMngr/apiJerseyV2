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
        boolean nameTaken = ManagementService.getAlarmDAO().nameIsPresent(user, name);
        boolean categoryTaken = ManagementService.getAlarmDAO().categoryIsPresent(user, categoryId);
        String message = "";
        int nbrError = 0;

        if (!nameTaken && !categoryTaken && !name.equals("")) {
            Alarm alarm = new Alarm(user.getId(), name, categoryId);
            ManagementService.getAlarmDAO().save(alarm);
            return "{\"error\":0,\"alarm\":" + mapper.writeValueAsString(alarm) + ",\"user\":" + mapper.writeValueAsString(user) + ", \"message\": \"<span class='block'>Alerta creada !</span>\"}";
        }

        if (nameTaken) {
            nbrError ++;
            message += "<span class='block'><span class='font-weight-bold'>Nombre</span> ya utilizado</span>";
        }

        if (categoryTaken) {
            nbrError ++;
            message += "<span class='block'><span class='font-weight-bold'>Category</span> ya utilizado</span>";
        }

        if (name.equals("")) {
            message += "<span class='block'><span class='font-weight-bold'>Name</span> not present</span>";
        }
        return "{\"error\": "+ nbrError + ", \"message\": \""+ message +"\"}";

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
