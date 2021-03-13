package com.matheusfelixr.scm.util;

public class CepHelper {

    public static Boolean isCep(String cep) {
       if(cep.contains("-") && cep.length() == 9){
           String cepNumber = cep.replace("-", "");
           try {
               Integer.valueOf(cepNumber);
               return true;
           } catch (Exception e) {
              return false;
           }
       }
        return false;
    }
}
