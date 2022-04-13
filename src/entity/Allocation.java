package entity;

import java.util.List;

public class Allocation {
<<<<<<< HEAD
	//private final static List<Service> services = DataList.getInstance().getServiceList();
    //private final static List<Branch> branches = DataList.getInstance().getBranchList(null, "","");
    //private final static List<Doctor> doctors = DataList.getInstance().getDoctorList();
    private int allocationId;
=======
    private int linkId;
    private Branch branch;
>>>>>>> 7e97ca07d6a18380e990e62afe8ee0d0233d2ad0
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
<<<<<<< HEAD
    
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
    	
=======

    private Branch findBranch(String branchId) {
        List<Branch> branches = DataList.getInstance().getBranchList(null, "", "");
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
        List<Doctor> doctors = DataList.getInstance().getDoctorList();
        for (Doctor value : doctors) {
            if (value.getUserId() == Integer.parseInt(doctorId)) {
                return value;
            }
        }
        return null;
>>>>>>> 7e97ca07d6a18380e990e62afe8ee0d0233d2ad0
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
