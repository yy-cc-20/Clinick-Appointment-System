package entity;

public class Service {
    private int serviceId;
    private String serviceName;
    private double price;
    private String description;
    private int timeSlotRequired;

    public Service(int serviceId, String serviceName, double price, String description, int timeSlotRequired) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.description = description;
        this.timeSlotRequired = timeSlotRequired;
    }

    public Service() {
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeSlotRequired() {
        return timeSlotRequired;
    }

    public void setTimeSlotRequired(int timeSlotRequired) {
        this.timeSlotRequired = timeSlotRequired;
    }
}
