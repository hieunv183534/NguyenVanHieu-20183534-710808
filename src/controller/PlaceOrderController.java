package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import controller.shippingfee.ShippingStrategy2;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.order.RushInfo;

/**
 * This class controls the flow of place order usecase in our AIMS project
 *
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     *
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     *
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for(Object object : Cart.getCart().getListMedia()){
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), cartMedia.getQuantity(), cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     *
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order){
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{

    }

    /*sonnh*/
    public boolean validatePhoneNumber(String phoneNumber){
        // TODO: your work
        if(phoneNumber == null){
            return false;
        }

        if(phoneNumber.length() < 10){
            return false;
        }

        try{
            Integer.parseInt(phoneNumber);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /*sonnh*/
    public boolean validateName(String name){
        if(name == null){
            return false;
        }

        // TODO: your work

        return name.matches("[A-Za-z ]*");
    }

    /*sonnh*/
    public boolean validateAddress(String address){
        // TODO: your work
        if(address == null){
            return false;
        }

        return address.matches("/^[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._-]+$/g");
    }


    /**
     * This method calculates the shipping fees of order
     *
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        /*weight include strategy right now*/
        return new ShippingStrategy2().calculateShippingFee(order);
    }

    /**
     * add rush info to order
     *
     * @param order    order to rush
     * @param rushInfo rush info
     */
    public void addRushInfo(Order order, RushInfo rushInfo){
        order.setRushInfo(rushInfo);
    }
}
