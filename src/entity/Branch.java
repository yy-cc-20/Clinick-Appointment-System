package entity;

public class Branch {
<<<<<<< HEAD
    //private static final List<Receptionist> receptionists = DataList.getInstance().getReceptionistList();
=======
>>>>>>> 7e97ca07d6a18380e990e62afe8ee0d0233d2ad0
    private int branchId;
    private String branchName;
    private String branchAddress;
    private final Receptionist receptionist;
    private String telNo;

    public Branch(int branchId, String branchName, String branchAddress, int receptionistId, String telNo) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.receptionist = DataList2.createReceptionistObject(receptionistId);
        this.telNo = telNo;
    }

<<<<<<< HEAD
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
=======
    private Receptionist findReceptionist(int receptionistId) {
        List<Receptionist> receptionists = DataList.getInstance().getReceptionistList();
        for (Receptionist value : receptionists) {
            if (value.getUserId() == receptionistId) {
                return value;
            }
        }
        return null;
>>>>>>> 7e97ca07d6a18380e990e62afe8ee0d0233d2ad0
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
<<<<<<< HEAD
    	return receptionist;
=======
        return receptionist;
>>>>>>> 7e97ca07d6a18380e990e62afe8ee0d0233d2ad0
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

}
