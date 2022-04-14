package entity;

public class Allocation {
    private int allocationId;
    private Branch branch;
    private Service service;
    private Doctor doctor;
    
    // Copy constructor: create a new object with exactly the same properties
    public Allocation(int linkId, Service service, Branch branch, Doctor doctor){
        this.allocationId = linkId;
        this.branch = branch;
        this.service = service;
        this.doctor = doctor;
    }

    public Allocation(Allocation a){
        this.allocationId = a.allocationId;
        this.branch = a.branch;
        this.service = a.service;
        this.doctor = a.doctor;
    }
    
    public Allocation(){}
/*
    public Allocation(int linkId, int branchId, int serviceId, int doctorId) {
        this.linkId = linkId;
        this.branch = findBranch(Integer.toString(branchId));
        this.service = findService(Integer.toString(serviceId));
        this.doctor = findDoctor(Integer.toString(doctorId));
    }

    

    private Branch findBranch(String branchId) {
        List<Branch> branches = DataList2.getBranchList();
        for (Branch value : branches) {
            if (value.getBranchId() == Integer.parseInt(branchId)) {
                return value;
            }
        }
        return null;
    }

    private Service findService(String serviceId) {
        List<Service> services = DataList.getInstance().getServiceList();
        for (Service value : services) {
            if (value.getServiceId() == Integer.parseInt(serviceId)) {
                return value;
            }
        }
        return null;
    }

    private Doctor findDoctor(String doctorId) {
        List<Doctor> doctors = DataList2.getDoctorList();
        for (Doctor value : doctors) {
            if (value.getUserId() == Integer.parseInt(doctorId)) {
                return value;
            }
        }
        return null;
    }
*/
    public int getLinkId() {
        return allocationId;
    }

    public void setLinkId(int linkId) {
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
