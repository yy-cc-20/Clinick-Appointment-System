package entity;

public class Branch{
    private String branchId;
    private String branchName;
    private String branchAddress;
    private Receptionist receptionist;
    private String telNo;

    public Branch(String branchId, String branchName, String branchAddress, String receptionistId, String telNo) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.receptionist = findReceptionist(receptionistId);
        this.telNo = telNo;
    }

    public Branch(){}

    private Receptionist findReceptionist(String receptionistId){
        Receptionist receptionist = new Receptionist();
        return receptionist;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
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