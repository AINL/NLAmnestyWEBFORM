/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webform.http;

import java.util.List;
import nl.amnesty.webform.config.WebformDef;
import nl.amnesty.webform.config.XMLParser;
import nl.amnesty.webform.exception.WEBFORMWebformException;

/**
 *
 * @author evelzen
 */
public class FormHTTP {

    public static List<WebformDef> webformdeflist;

    public WebformDef getWebformDef(java.net.URL urlconfigwebform, int node) throws WEBFORMWebformException {
        if (urlconfigwebform == null) {
            throw new WEBFORMWebformException("Config url is null, cannot search webform definition for node " + node);
        }
        if (urlconfigwebform.getHost().isEmpty()) {
            throw new WEBFORMWebformException("Config url hostname is empty, cannot search webform definition for node " + node);
        }
        if (node == 0) {
            throw new WEBFORMWebformException("Node is 0");
        }
        // Parse webformconfig
        XMLParser xmlparser = new XMLParser();
        xmlparser.parseWebformConfig(urlconfigwebform);

        // Get webform definition for node
        for (WebformDef webformdef : webformdeflist) {
            if (webformdef.getNode() == node) {
                return webformdef;
            }
        }
        throw new WEBFORMWebformException("No webform definition found for node " + node);
    }
}
