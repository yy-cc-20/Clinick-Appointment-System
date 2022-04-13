package entity;

import java.util.List;

public class Allocation {
	//private final static List<Service> services = DataList.getInstance().getServiceList();
    //private final static List<Branch> branches = DataList.getInstance().getBranchList(null, "","");
    //private final static List<Doctor> doctors = DataList.getInstance().getDoctorList();
    private int allocationId;
    private Service service;
    private Branch branch;
    private Doctor doctor;

    // Copy constructor: create a new object with exactly the same properties
    public Allocation(Allocation a) {
    	allocationId = a.allocationId;
    	service = new Service(a.service);
    	branch = new Branch(a.branch);
    	doctor = new Doctor(a.doctor);
    }
    
    public Allocation(int id, Service s, Branch b, Doctor d) {
    	allocationId = id;
    	service = new Service(s);
    	branch = new Branch(b);
    	doctor = new Doctor(d);
    }

    public Allocation(int linkId, int branchId, int serviceId, int doctorId) {
        this.allocationId = linkId;
        this.branch = DataList2.createBranchObject(branchId);
        this.service = DataList2.createServiceObject(serviceId);
        this.doctor = DataList2.createDoctorObject(doctorId);
    }

    public Allocation() {
    	
    }

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
