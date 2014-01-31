/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webform.response;

/**
 *
 * @author evelzen
 */
public class ResponseAddress {

    private String from;
    private String to;
    private String cc;
    private String bcc;

    public ResponseAddress() {
    }

    public ResponseAddress(String from, String to, String cc, String bcc) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
