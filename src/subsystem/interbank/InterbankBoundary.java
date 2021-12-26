package subsystem.interbank;

import common.exception.UnrecognizedException;
import utils.API;

public class InterbankBoundary{

    String query(String url, String data){
        String response = null;
        String token = "";
        try{
            response = API.post(url, data, token);
        }catch(Exception e){
            // TODO Auto-generated catch block
            throw new UnrecognizedException();
        }
        return response;
    }

}
