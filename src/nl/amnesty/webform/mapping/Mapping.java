/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webform.mapping;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.amnesty.webform.config.WebformDef;

/**
 *
 * @author evelzen
 */
public class Mapping {

    private List<Attributelink> attributelinklist;

    public Mapping() {
    }

    public Mapping(List<Attributelink> attributelinklist) {
        this.attributelinklist = attributelinklist;
    }

    public List<Attributelink> getAttributelinklist() {
        return attributelinklist;
    }

    public void setAttributelinklist(List<Attributelink> attributelinklist) {
        this.attributelinklist = attributelinklist;
    }

    public String getFormdefcrmobject(String formdeffieldname) {
        Attributelink attributelink = getAttributelinkViaFormdeffieldname(formdeffieldname);
        if (attributelink != null) {
            return attributelink.getFormdefcrmobject();
        }
        return "";
    }

    public Attributelink getAttributelinkViaFormdefcrmobject(String formdefcrmobject) {
        for (Attributelink attributelink : this.attributelinklist) {

            //DEBUG
            //System.out.println("--> attributelink.getFormdefcrmobject(): " + attributelink.getFormdefcrmobject());

            if (attributelink.getFormdefcrmobject().equals(formdefcrmobject)) {
                return attributelink;
            }
        }
        return null;
    }

    public String getFormdeffieldname(String formdefcrmobject) {
        Attributelink attributelink = getAttributelinkViaFormdefcrmobject(formdefcrmobject);
        if (attributelink != null) {

            //DEBUG
            //System.out.println("--> attributelink.getSource(): " + attributelink.getSource());

            return attributelink.getFormdeffieldname();
        }
        return "";
    }

    public Attributelink getAttributelinkViaFormdeffieldname(String formdeffieldname) {
        for (Attributelink attributelink : this.attributelinklist) {
            if (attributelink.getFormdeffieldname().equals(formdeffieldname)) {
                return attributelink;
            }
        }
        return null;
    }

    /**
     * Reading form fieldvalues into CRM formdefcrmobject object is done in two
     * stages. First of all the form fieldnames and the corresponding values are
     * read into a properties array (named sourcevalues here). Secondly a list
     * is kept that links the form fieldnames to the CRM objects. This list is
     * the attributelinklist of this Mapping object. The attributelinklist is
     * read from the Formdef webform definition that is kept online as a
     * configuration mechanism of processing the webforms thru these
     * webservices.
     *
     * A typical situation is as follows:
     *
     * Webformvalues properties representing the webform data: key "city", value
     * "gouda" Attributelinklist properties linking the webfield to the CRM
     * object: key "city", value "address_city"
     *
     *
     * @param webformdef
     * @param formdefcrmobject
     * @param webformvalues
     * @return
     */
    public String getFormdefcrmobjectValue(WebformDef webformdef, String formdefcrmobject, Properties webformvalues) {
        boolean done;
        String sourcekey;
        String sourcevalue = "";
        try {
            // Get the name of the form field that maps to the formdefcrmobject CRM object
            String formdeffieldname = getFormdeffieldname(formdefcrmobject);
            if (!formdeffieldname.isEmpty()) {
                Enumeration<Object> sourcekeys = webformvalues.keys();
                // Get the value of the webform field with the specified source name
                done = false;
                while (sourcekeys.hasMoreElements() && !done) {
                    sourcekey = (String) sourcekeys.nextElement();
                    if (sourcekey.equals(formdeffieldname)) {
                        // Get webform field value
                        sourcevalue = webformvalues.getProperty(sourcekey);
                        done = true;
                    }
                }
                if (!done) {
                    // We did not find a form source field as specified in the Formdef form definition
                    Logger.getLogger(Mapping.class.getName()).log(Level.SEVERE, "Form field {0} required for form {1} but not passed to webservice.", new Object[]{formdeffieldname, webformdef.getName()});
                }
            }
            // See if we need to apply transformations to the webform field value
            Attributelink attributelink = getAttributelinkViaFormdefcrmobject(formdefcrmobject);
            Properties transformation = attributelink.getFormdeftransformation();
            // Return source value if there are no transformations to be applied
            if (transformation.isEmpty()) {
                return sourcevalue;
            }
            return doTransformation(sourcevalue, transformation);
        } catch (Exception e) {
            Logger.getLogger(Mapping.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return "";
        }
    }

    private String doTransformation(String sourcevalue, Properties transformation) {
        boolean done = false;
        String transformationkey = "";
        String transformationvalue = "";
        try {
            // Traverse the properties to find a matching source value that needs to be translated
            Enumeration<Object> transformationkeys = transformation.keys();
            done = false;
            while (transformationkeys.hasMoreElements() && !done) {
                transformationkey = (String) transformationkeys.nextElement();
                // Return constant value if key is wildcard
                if (transformationkey.equals("*")) {
                    transformationvalue = transformation.getProperty(transformationkey);
                    done = true;
                } else {
                    // Return translated value if key equals source value
                    if (transformationkey.equals(sourcevalue)) {
                        transformationvalue = transformation.getProperty(transformationkey);
                        done = true;
                    }
                }
            }
            if (done) {
                // Return translated value
                return transformationvalue;
            } else {
                // No translation found for source value
                return "";
            }
        } catch (Exception e) {
            Logger.getLogger(Mapping.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return "";
        }
    }
}
