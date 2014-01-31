/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webform.mapping;

import java.util.Properties;

/**
 *
 * @author evelzen
 */
public class Attributelink {

    private String formdeffieldname;
    private String formdefcrmobject;
    private Properties formdeftransformation;

    public Attributelink() {
    }

    public Attributelink(String formdeffieldname, String formdefcrmobject, Properties formdeftransformation) {
        this.formdeffieldname = formdeffieldname;
        this.formdefcrmobject = formdefcrmobject;
        this.formdeftransformation = formdeftransformation;
    }

    public String getFormdeffieldname() {
        return formdeffieldname;
    }

    public void setFormdeffieldname(String formdeffieldname) {
        this.formdeffieldname = formdeffieldname;
    }

    public String getFormdefcrmobject() {
        return formdefcrmobject;
    }

    public void setFormdefcrmobject(String formdefcrmobject) {
        this.formdefcrmobject = formdefcrmobject;
    }

    public Properties getFormdeftransformation() {
        return formdeftransformation;
    }

    public void setFormdeftransformation(Properties formdeftransformation) {
        this.formdeftransformation = formdeftransformation;
    }
}
