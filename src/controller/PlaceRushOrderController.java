package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class PlaceRushOrderController extends BaseController{
    /**
     * For logging
     */
    private static final Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * Date formatter
     */
    public static DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * Validate provided rush info
     *
     * @param rushInfo rush info that customer provided, must not be null
     * @return whether rush info is valid
     */
    public static boolean validateInstruction(String rushInfo){
        LOGGER.info("Validating rush info: " + rushInfo);
        return rushInfo != null;
    }

    /**
     * validate rush time (at least after 2h from checked out time for preparation)
     *
     * @param date
     * @return
     */
    public static boolean validateTime(Date date){
        Date now = new Date();

        LOGGER.info("Validating rush time: " + DATE_FORMATTER.format(date) + " at " + DATE_FORMATTER.format(now));
        // if rush date is more than 2 hour from now
        return (date.getTime() - now.getTime()) > 2 * 60 * 60 * 1000;
    }

    /**
     * validate rush time (at least after 2h from checked out time for preparation)
     *
     * @param date date in format yyyy/MM/dd HH:mm:ss
     * @exception ParseException if parsing failed
     * @return
     */
    public static boolean validateTime(String date) throws ParseException{
        return validateTime(DATE_FORMATTER.parse(date));
    }

}
