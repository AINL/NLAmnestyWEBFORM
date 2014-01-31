/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webform.config;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * @author evelzen
 */
public class XMLParser {

    public void parseWebformConfig(URL url) {
        try {
            if (url == null) {
                url = new URL("http://zeebaars.amnesty.nl:8080/nlamnestyconfig/webform.xml");
            }

            //DEBUG
            //java.util.logging.Logger.getLogger(XMLParser.class.getName()).log(Level.INFO, "about to open {0}", url.toString());

            InputStream in = url.openStream();
            SAXParserFactory factorySAX = SAXParserFactory.newInstance();
            SAXParser sax = factorySAX.newSAXParser();
            WebformSAXHandler saxhandler = new WebformSAXHandler();
            sax.parse(in, saxhandler);
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
