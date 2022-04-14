package entity;

public class Receptionist extends User {
	/*
    public Receptionist(int userid, String username, String password) {
        super(userid, username, password);
    }*/

    // Copy constructor: create a new object with exactly the same properties
    public Receptionist(Receptionist r) {
    	super(r.userId, r.username, r.password);
    }
    
    public Receptionist() {
    }
}
