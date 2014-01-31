/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webform.response;

/**
 *
 * @author evelzen
 */
public class Response {

    private ResponseAddress responseaddress;
    private String subject;
    private String url;

    public Response() {
    }

    public Response(ResponseAddress responseaddress, String subject, String url) {
        this.responseaddress = responseaddress;
        this.subject = subject;
        this.url = url;
    }

    public ResponseAddress getResponseaddress() {
        return responseaddress;
    }

    public void setResponseaddress(ResponseAddress responseaddress) {
        this.responseaddress = responseaddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
