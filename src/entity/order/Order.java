package entity.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import utils.Configs;

public class Order {
    
    private int shippingFees;
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;
    /**
     * if this is rush order
     */
    private boolean isRush;
    public boolean isRush(){
        return isRush;
    }

    public void setRush(boolean rush){
        isRush = rush;
    }

    /**
     * rush info if this is rush order
     */
    private RushInfo rushInfo;

    public RushInfo getRushInfo(){
        return rushInfo;
    }

    public void setRushInfo(RushInfo rushInfo){
        this.rushInfo = rushInfo;
    }



    public Order(){
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
    }

}
