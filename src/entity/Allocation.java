package entity;

import java.util.List;

public class Allocation {
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
