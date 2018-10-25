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
	
	public boolean create(User newUser) {
	
		int idUsuario = getListadoUsuarios().size() + 1;
		newUser.setUserId(idUsuario);
		
		List<User> results = getByUsername(newUser.getUserName());
		if(results.size() != 0)
        	return false;
		
		getListadoUsuarios().add(newUser);
		
		results = getByUsername(newUser.getUserName());
		if(results.size() != 1)
        	return false;
		
		return true;
		
	}
	
    public List<User> getByUsername(String username) {
    	
    	List<User> results = new ArrayList<User>();
        results = listadoUsuarios.stream().filter(elem -> elem.getUserName().equals(username)).collect(Collectors.toList());
        
        if(results.size() != 1)
        	return null;

        return results;
    }
    
    public int getUserIdFromUsername(String username) {
    	
    	List<User> results = new ArrayList<User>();
        results = listadoUsuarios.stream().filter(elem -> elem.getUserName().equals(username)).collect(Collectors.toList());
        if(results.size() != 1)
        	return -1;
        
        return results.get(0).getUserId();
    }

}