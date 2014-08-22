/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author olivier
 */
public class Token {

    public enum tokenType {
        O_PAR, C_PAR, NUMBER, MULT,DIV,ADD, SUB, POW
    }
    
    private tokenType token;
    private double value;
    private String sValue;

    public tokenType getToken() {
        return token;
    }

    public double getValue() {
        return value;
    }

    public void setToken(tokenType token) {
        this.token = token;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getsValue() {
        return sValue;
    }

    public void setsValue(String sValue) {
        this.sValue = sValue;
    }

}
