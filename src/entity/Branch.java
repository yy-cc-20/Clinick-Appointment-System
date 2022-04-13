package entity;

import java.util.List;

public class Branch {
    private final List<Receptionist> receptionists = DataList.getInstance().getReceptionistList();
    private int branchId;
    private String branchName;
    private String branchAddress;
    private Receptionist receptionist;
    private String telNo;

    // todo id string to int
    public Branch(int branchId, String branchName, String branchAddress, int receptionistId, String telNo) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.receptionist = findReceptionist(receptionistId);
        this.telNo = telNo;
    }

    public Branch() {
    }

    private Receptionist findReceptionist(int receptionistId) {
        for (Receptionist value : receptionists) {
            if (value.getUserId() == receptionistId) {
                return value;
            }
        }
        return null;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }


    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

}
