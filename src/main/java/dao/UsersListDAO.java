package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
        // TODO: Validar que un unico usuario exista.
		
		int idUsuario = getListadoUsuarios().size() + 1;
		newUser.setUserId(idUsuario);
		getListadoUsuarios().add(newUser);
	}
	
    public List<User> getByUsername(String username) {
        // TODO: Validar que un unico usuario exista.
    	
    	List<User> results = new ArrayList<User>();
        results = listadoUsuarios.stream().filter(elem -> elem.getUserName().equals(username)).collect(Collectors.toList());

        return results;
    }
    
    public int getUserIdFromUsername(String username) {
        // TODO: Validar que un unico usuario exista.
    	
    	List<User> results = new ArrayList<User>();
        results = listadoUsuarios.stream().filter(elem -> elem.getUserName().equals(username)).collect(Collectors.toList());
        if(results.size() != 1)
        	return -1;
        
        return results.get(0).getUserId();
    }

}