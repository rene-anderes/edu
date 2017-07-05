package org.anderes.edu.soap;

import javax.jws.WebService;

import org.anderes.wsdl.CalculatorWs;

@WebService(name = "CalculatorWs", targetNamespace = "http://www.anderes.org/wsdl")
public class CalculatorWebService implements CalculatorWs {

    @Override
    public int multiply(int arg0, int arg1) {
        return arg0 * arg1;
    }

    @Override
    public int sum(int arg0, int arg1) {
        return arg0 + arg1;
    }

}
