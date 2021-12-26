package views.screen.rush;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.RushInfo;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.shipping.ShippingScreenHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * @author HieuNV183534
 */
public class RushScreenHandler extends BaseScreenHandler{
    /**
     * Logger
     */
    private static Logger LOGGER = Utils.getLogger(RushScreenHandler.class.getName());
    /**
     * Date formatter
     */
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * set invoice to add rush info
     *
     * @param invoice invoice from InvoiceScreen
     */
    public void setInvoice(Invoice invoice){
        this.invoice = invoice;
    }

    /**
     * Invoice
     */
    private Invoice invoice;


    @FXML
    private TextArea instructionInput;

    @FXML
    private DatePicker timePicker;

    public RushScreenHandler(Stage stage, String screenPath) throws IOException{
        super(stage, screenPath);
    }


    @FXML
    public void goToPayment(MouseEvent event){
        try{
            String instruction = instructionInput.getText();
            String time = timePicker.getValue().format(DATE_FORMATTER).concat(" 08:00:00");

            if(PlaceRushOrderController.validateInstruction(instruction) && PlaceRushOrderController.validateTime(
                    time)){
                //set rush info to order
                ((PlaceOrderController) this.getPreviousScreen().getBController()).addRushInfo(invoice.getOrder(),
                        new RushInfo(instruction, time));

                //recalculate shipping fee
                invoice.getOrder().setShippingFees(
                        ((PlaceOrderController) this.getPreviousScreen().getBController()).calculateShippingFee(
                                invoice.getOrder()));
                // continue to payment
                ((ShippingScreenHandler) this.getPreviousScreen()).goToInvoice(invoice);
            }else{
                PopupScreen.error("Not valid instruction or time. Please try again.");
            }
        }catch(ParseException|IOException e){
            e.printStackTrace();
            try{
                PopupScreen.error("Error parsing date");
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

}
