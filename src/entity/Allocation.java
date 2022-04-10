package entity;

public class Allocation {
    private String linkId;
    private Branch branch;
    private Service service;
    private Doctor doctor;

    public Allocation(String linkId, String branchId, String serviceId, String doctorId) {
        this.linkId = linkId;
        this.branch = findBranch(branchId);
        this.service = findService(serviceId);
        this.doctor = findDoctor(doctorId);
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

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
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
