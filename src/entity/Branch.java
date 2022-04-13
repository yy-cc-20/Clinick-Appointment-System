package entity;

import java.util.List;

public class Branch {
    private int branchId;
    private String branchName;
    private String branchAddress;
    private final Receptionist receptionist;
    private String telNo;

    public Branch(int branchId, String branchName, String branchAddress, int receptionistId, String telNo) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.receptionist = findReceptionist(receptionistId);
        this.telNo = telNo;
    }

    private Receptionist findReceptionist(int receptionistId) {
        List<Receptionist> receptionists = DataList.getInstance().getReceptionistList();
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

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

}
