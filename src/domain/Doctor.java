package domain;

public class Doctor extends User {
    public Doctor(int userid, String username, String password) {
        super(userid, username, password);
    }

    public Doctor() {
    }

    // Copy constructor: create a new object with exactly the same properties
    public Doctor(Doctor d) {
        super(d.userId, d.username, d.password);
    }
}
