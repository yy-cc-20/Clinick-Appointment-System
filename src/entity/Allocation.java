package entity;

public class Allocation {
    private int linkId;
    private Branch branch;
    private Service service;
    private Doctor doctor;
    private TimeSlot timeSlotList;

    public Allocation(int linkId, String branchId, String serviceId, String doctorId) {
        this.linkId = linkId;
        this.branch = findBranch(branchId);
        this.service = findService(serviceId);
        this.doctor = findDoctor(doctorId);
        this.timeSlotList =
    }

    public Allocation(){

    }

    private Branch findBranch(String branchId){
        Branch branch = new Branch();
        return branch;
    }

    private Service findService(String branchId){
        Service service = new Service();
        return service;
    }

    private Doctor findDoctor(String doctorId){
        Doctor doctor = new Doctor();
        return doctor;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
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
