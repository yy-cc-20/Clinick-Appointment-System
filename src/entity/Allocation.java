package entity;

public class Allocation {
    private int allocationId;
    private Branch branch;
    private Service service;
    private Doctor doctor;

    // Copy constructor: create a new object with exactly the same properties
    public Allocation(int linkId, Service service, Branch branch, Doctor doctor) {
        this.allocationId = linkId;
        this.branch = branch;
        this.service = service;
        this.doctor = doctor;
    }

    public Allocation(Allocation a) {
        this.allocationId = a.allocationId;
        this.branch = a.branch;
        this.service = a.service;
        this.doctor = a.doctor;
    }

    public Allocation() {
    }

    public int getId() {
        return allocationId;
    }

    public void setId(int linkId) {
        this.allocationId = linkId;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
