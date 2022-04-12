package entity;

import java.util.List;

public class Allocation {
    private final List<Branch> branches = DataList.getInstance().getBranchList(null, "","");
    private final List<Service> services = DataList.getInstance().getServiceList();
    private final List<Doctor> doctors = DataList.getInstance().getDoctorList();
    private int linkId;
    private Branch branch;
    private Service service;
    private Doctor doctor;

    public Allocation(int linkId, String branchId, String serviceId, String doctorId) {
        this.linkId = linkId;
        this.branch = findBranch(branchId);
        this.service = findService(serviceId);
        this.doctor = findDoctor(doctorId);
    }

    public Allocation(int linkId, int branchId, int serviceId, int doctorId) {
        this.linkId = linkId;
        this.branch = findBranch(Integer.toString(branchId));
        this.service = findService(Integer.toString(serviceId));
        this.doctor = findDoctor(Integer.toString(doctorId));
    }


    // todo connect to database
    private Branch findBranch(String branchId){
        for (Branch value : branches) {
            if (value.getBranchId() == Integer.parseInt(branchId)) {
                return value;
            }
        }
        return null;
    }

    private Service findService(String serviceId){
        for (Service value : services) {
            if (value.getServiceId() == Integer.parseInt(serviceId)) {
                return value;
            }
        }
        return null;
    }

    private Doctor findDoctor(String doctorId){
        for (Doctor value : doctors) {
            if (value.getUserId() == Integer.parseInt(doctorId)) {
                return value;
            }
        }
        return null;
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
