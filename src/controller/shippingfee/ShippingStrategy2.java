package controller.shippingfee;

import entity.order.Order;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author HieuNV
 */
public class ShippingStrategy2 implements ShippingFeeCalculator {

    @Override
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int weight = (int) (((rand.nextFloat() * 10) / 100) * order.getAmount());
        int length = (int) (((rand.nextFloat() * 10) / 100) * order.getAmount());
        int width = (int) (((rand.nextFloat() * 10) / 100) * order.getAmount());
        int height = (int) (((rand.nextFloat() * 10) / 100) * order.getAmount());
        int alt = (length * width * height) / 6000;
        int fees = alt + weight;
        return fees;
    }
}
