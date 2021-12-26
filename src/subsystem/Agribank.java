package subsystem;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentCard;
import entity.payment.PaymentTransaction;

public class Agribank implements InterbankInterface{
    @Override
    public PaymentTransaction payOrder(PaymentCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException{
        return null;
    }

    @Override
    public PaymentTransaction refund(PaymentCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException{
        return null;
    }
}
