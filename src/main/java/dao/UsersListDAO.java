package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import model.EventsList;
import model.User;

public class UsersListDAO {
    private final AtomicLong counter = new AtomicLong();

    private List<User> listadoUsuarios;

    {
    	listadoUsuarios = new ArrayList<User>();
    }


	public List<User> getListadoUsuarios() {
		return listadoUsuarios;
	}
	
	public void create(User newUser) {
		int idUsuario = getListadoUsuarios().size() + 1;
		newUser.setUserId(idUsuario);
		getListadoUsuarios().add(newUser);
	}
	
    public List<User> getByUsername(String username) {
        List<User> results = new ArrayList<User>();
        results = listadoUsuarios.stream().filter(elem -> elem.getUserName().equals(username)).collect(Collectors.toList());

        return results;
    }

}