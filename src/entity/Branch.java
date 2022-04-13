package entity;

public class Branch {
    //private static final List<Receptionist> receptionists = DataList.getInstance().getReceptionistList();
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
        this.receptionist = DataList2.createReceptionistObject(receptionistId);
        this.telNo = telNo;
    }

    public Branch(int branchId, String branchName, String branchAddress, Receptionist rec, String telNo) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.receptionist = new Receptionist(rec);
        this.telNo = telNo;
    }
    
    // Copy constructor: create a new object with exactly the same properties
    public Branch(Branch b) {
    	this.branchId = b.branchId;
        this.branchName = b.branchName;
        this.branchAddress = b.branchAddress;
        this.receptionist = new Receptionist(b.receptionist);
        this.telNo = b.telNo;
    }
    
    public Branch() {
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
