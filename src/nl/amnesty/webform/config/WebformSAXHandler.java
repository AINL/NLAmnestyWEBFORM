package nl.amnesty.webform.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import nl.amnesty.webform.config.WebformDef.operation;
import nl.amnesty.webform.http.FormHTTP;
import nl.amnesty.webform.mapping.Attributelink;
import nl.amnesty.webform.mapping.Mapping;
import nl.amnesty.webform.response.Response;
import nl.amnesty.webform.response.ResponseAddress;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author evelzen
 */
public class WebformSAXHandler extends DefaultHandler {

    private List<String> qnamelist;
    private int index;
    private String value;
    private boolean inelement = false;
    //
    public static List<WebformDef> webformdeflist;
    private int webform_node;
    private String webform_name;
    private SimpleDateFormat webform_simpledateformat;
    private String webform_notification;
    private Mapping webform_mapping;
    private List<operation> webform_operationlist;
    private String network_name;
    private String network_source;
    private Response webform_response;
    private ResponseAddress webform_responseaddress;
    //
    private List<Attributelink> attributelinklist;
    private Properties transformation;
    private String entitymapping;
    private String fieldmapping;
    private String keycase;
    private String valuecase;
    //
    private String response_address_from;
    private String response_address_to;
    private String response_address_cc;
    private String response_address_bcc;
    private String response_subject;
    private String response_url;

    public WebformSAXHandler() throws org.xml.sax.SAXException {
        super();
    }

    /**
     * Receive notification of the start of the document.
     */
    @Override
    public void startDocument() {
        webformdeflist = new ArrayList();
        value = "";
        qnamelist = new ArrayList();
        index = 0;
    }

    /**
     * Receive notification of the end of the document.
     */
    @Override
    public void endDocument() {
        FormHTTP.webformdeflist = webformdeflist;
    }

    /**
     * Receive notification of the start of an element.
     *
     * @param uri
     * @param localname
     * @param qname
     * @param attributes
     */
    @Override
    public void startElement(String uri, String localname, String qname, Attributes attributes) {
        if (qname.equals("form")) {
            webform_mapping = new Mapping();
            webform_operationlist = new ArrayList();
            attributelinklist = new ArrayList();
            //transformation = new Properties();
        }
        if (qname.equals("mapping")) {
            entitymapping = "";
            fieldmapping = "";
            transformation = new Properties();
        }
        if (qname.equals("case")) {
            keycase = "";
            valuecase = "";
        }
        if (qname.equals("response")) {
            webform_response = new Response();
        }
        if (qname.equals("address")) {
            webform_responseaddress = new ResponseAddress();
        }

        qnamelist.add(qname);
        index++;
        inelement = true;
    }

    /**
     * Receive notification of the end of an element.
     *
     * @param uri
     * @param localname
     * @param qname
     */
    @Override
    public void endElement(String uri, String localname, String qname) {
        if (qname.equals("address")) {
            webform_responseaddress.setFrom(response_address_from);
            webform_responseaddress.setFrom(response_address_to);
            webform_responseaddress.setFrom(response_address_cc);
            webform_responseaddress.setFrom(response_address_bcc);
        }
        if (qname.equals("response")) {
            webform_response.setResponseaddress(webform_responseaddress);
            webform_response.setSubject(response_subject);
            webform_response.setUrl(response_url);
        }
        if (qname.equals("form")) {
            if (qnameParent().equals("webform")) {
                WebformDef webformdef = new WebformDef();
                webformdef.setNode(webform_node);
                webformdef.setName(webform_name);
                webformdef.setSimpledateformat(webform_simpledateformat);
                webformdef.setNotification(webform_notification);
                webform_mapping.setAttributelinklist(attributelinklist);
                webformdef.setMapping(webform_mapping);
                webformdef.setOperationlist(webform_operationlist);
                webformdef.setNetworkname(network_name);
                webformdef.setNetworksource(network_source);
                webformdef.setResponse(webform_response);
                webformdeflist.add(webformdef);
            }
        }


        // Form node and name
        if (qname.equals("node")) {
            if (qnameParent().equals("form")) {
                if (isInteger(value)) {
                    webform_node = Integer.valueOf(value);
                }
            }
        }
        if (qname.equals("name")) {
            if (qnameParent().equals("form")) {
                webform_name = value;
            }
        }
        // Date format
        if (qname.equals("dateformat")) {
            if (value.toLowerCase().contains("yyyy") && value.toLowerCase().contains("mm") && value.toLowerCase().contains("dd")) {
                webform_simpledateformat = new SimpleDateFormat(value);
            }
        }
        // Notification via email
        if (qname.equals("notification")) {
            if (qnameParent().equals("form")) {
                webform_notification = value;
            }
        }
        // Object mapping
        if (qname.equals("entity")) {
            if (qnameParent().equals("mapping")) {
                entitymapping = value;
            }
        }
        if (qname.equals("field")) {
            if (qnameParent().equals("mapping")) {
                fieldmapping = value;
            }
        }
        // Transformations
        if (qname.equals("value")) {
            if (qnameParent().equals("case")) {
                valuecase = value;
            }
        }
        if (qname.equals("key")) {
            if (qnameParent().equals("case")) {
                keycase = value;
            }
        }
        // Object transformation case
        if (qname.equals("case")) {
            if (qnameParent().equals("mapping")) {
                transformation.setProperty(keycase, valuecase);
            }
        }
        // Object mapping
        if (qname.equals("mapping")) {
            Attributelink mapping = new Attributelink(fieldmapping, entitymapping, transformation);
            attributelinklist.add(mapping);
        }
        // Action
        if (qname.equals("action")) {
            if (qnameParent().equals("operation")) {
                if (value.equals("addaddress")) {
                    webform_operationlist.add(operation.ADDADDRESS);
                }
                if (value.equals("addbankaccount")) {
                    webform_operationlist.add(operation.ADDBANKACCOUNT);
                }
                if (value.equals("addchannel")) {
                    webform_operationlist.add(operation.ADDCHANNEL);
                }
                if (value.equals("addcommitment")) {
                    webform_operationlist.add(operation.ADDCOMMITMENT);
                }
                if (value.equals("addfinance")) {
                    webform_operationlist.add(operation.ADDFINANCE);
                }
                if (value.equals("addcontact")) {
                    webform_operationlist.add(operation.ADDCONTACT);
                }
                if (value.equals("addcountry")) {
                    webform_operationlist.add(operation.ADDCOUNTRY);
                }
                if (value.equals("adddocument")) {
                    webform_operationlist.add(operation.ADDDOCUMENT);
                }
                if (value.equals("addflag")) {
                    webform_operationlist.add(operation.ADDFLAG);
                }
                if (value.equals("addgroup")) {
                    webform_operationlist.add(operation.ADDGROUP);
                }
                if (value.equals("addinvolvement")) {
                    webform_operationlist.add(operation.ADDINVOLVEMENT);
                }
                if (value.equals("addnetwork")) {
                    webform_operationlist.add(operation.ADDNETWORK);
                }
                if (value.equals("addperson")) {
                    webform_operationlist.add(operation.ADDPERSON);
                }
                if (value.equals("addphone")) {
                    webform_operationlist.add(operation.ADDPHONE);
                }
                if (value.equals("addproduct")) {
                    webform_operationlist.add(operation.ADDPRODUCT);
                }
                if (value.equals("addrelation")) {
                    webform_operationlist.add(operation.ADDRELATION);
                }
                if (value.equals("addrole")) {
                    webform_operationlist.add(operation.ADDROLE);
                }
                if (value.equals("addsubscription")) {
                    webform_operationlist.add(operation.ADDSUBSCRIPTION);
                }
                if (value.equals("addurl")) {
                    webform_operationlist.add(operation.ADDURL);
                }
                if (value.equals("changeaddress")) {
                    webform_operationlist.add(operation.CHANGEADDRESS);
                }
                if (value.equals("changebankaccount")) {
                    webform_operationlist.add(operation.CHANGEBANKACCOUNT);
                }
                if (value.equals("changechannel")) {
                    webform_operationlist.add(operation.CHANGECHANNEL);
                }
                if (value.equals("changecommitment")) {
                    webform_operationlist.add(operation.CHANGECOMMITMENT);
                }
                if (value.equals("changecontact")) {
                    webform_operationlist.add(operation.CHANGECONTACT);
                }
                if (value.equals("changecountry")) {
                    webform_operationlist.add(operation.CHANGECOUNTRY);
                }
                if (value.equals("changedocument")) {
                    webform_operationlist.add(operation.CHANGEDOCUMENT);
                }
                if (value.equals("changeflag")) {
                    webform_operationlist.add(operation.CHANGEFLAG);
                }
                if (value.equals("changegroup")) {
                    webform_operationlist.add(operation.CHANGEGROUP);
                }
                if (value.equals("changeinvolvement")) {
                    webform_operationlist.add(operation.CHANGEINVOLVEMENT);
                }
                if (value.equals("changenetwork")) {
                    webform_operationlist.add(operation.CHANGENETWORK);
                }
                if (value.equals("changeperson")) {
                    webform_operationlist.add(operation.CHANGEPERSON);
                }
                if (value.equals("changephone")) {
                    webform_operationlist.add(operation.CHANGEPHONE);
                }
                if (value.equals("changeproduct")) {
                    webform_operationlist.add(operation.CHANGEPRODUCT);
                }
                if (value.equals("changerelation")) {
                    webform_operationlist.add(operation.CHANGERELATION);
                }
                if (value.equals("changerole")) {
                    webform_operationlist.add(operation.CHANGEROLE);
                }
                if (value.equals("changesubscription")) {
                    webform_operationlist.add(operation.CHANGESUBSCRIPTION);
                }
                if (value.equals("changeurl")) {
                    webform_operationlist.add(operation.CHANGEURL);
                }
                if (value.equals("removeaddress")) {
                    webform_operationlist.add(operation.REMOVEADDRESS);
                }
                if (value.equals("removebankaccount")) {
                    webform_operationlist.add(operation.REMOVEBANKACCOUNT);
                }
                if (value.equals("removechannel")) {
                    webform_operationlist.add(operation.REMOVECHANNEL);
                }
                if (value.equals("removecommitment")) {
                    webform_operationlist.add(operation.REMOVECOMMITMENT);
                }
                if (value.equals("removecontact")) {
                    webform_operationlist.add(operation.REMOVECONTACT);
                }
                if (value.equals("removecountry")) {
                    webform_operationlist.add(operation.REMOVECOUNTRY);
                }
                if (value.equals("removedocument")) {
                    webform_operationlist.add(operation.REMOVEDOCUMENT);
                }
                if (value.equals("removeflag")) {
                    webform_operationlist.add(operation.REMOVEFLAG);
                }
                if (value.equals("removegroup")) {
                    webform_operationlist.add(operation.REMOVEGROUP);
                }
                if (value.equals("removeinvolvement")) {
                    webform_operationlist.add(operation.REMOVEINVOLVEMENT);
                }
                if (value.equals("removenetwork")) {
                    webform_operationlist.add(operation.REMOVENETWORK);
                }
                if (value.equals("removeperson")) {
                    webform_operationlist.add(operation.REMOVEPERSON);
                }
                if (value.equals("removephone")) {
                    webform_operationlist.add(operation.REMOVEPHONE);
                }
                if (value.equals("removeproduct")) {
                    webform_operationlist.add(operation.REMOVEPRODUCT);
                }
                if (value.equals("removerelation")) {
                    webform_operationlist.add(operation.REMOVERELATION);
                }
                if (value.equals("removerole")) {
                    webform_operationlist.add(operation.REMOVEROLE);
                }
                if (value.equals("removesubscription")) {
                    webform_operationlist.add(operation.REMOVESUBSCRIPTION);
                }
                if (value.equals("removeurl")) {
                    webform_operationlist.add(operation.REMOVEURL);
                }
                if (value.equals("sendresponse")) {
                    webform_operationlist.add(operation.SENDRESPONSE);
                }
            }
        }
        // Network
        if (qname.equals("name")) {
            if (qnameParent().equals("network")) {
                network_name = value;
            }
        }
        if (qname.equals("source")) {
            if (qnameParent().equals("network")) {
                network_source = value;
            }
        }
        // Response
        if (qname.equals("from")) {
            if (qnameParent().equals("address")) {
                response_address_from = value;
            }
        }
        if (qname.equals("to")) {
            if (qnameParent().equals("address")) {
                response_address_to = value;
            }
        }
        if (qname.equals("cc")) {
            if (qnameParent().equals("address")) {
                response_address_cc = value;
            }
        }
        if (qname.equals("bcc")) {
            if (qnameParent().equals("address")) {
                response_address_bcc = value;
            }
        }
        if (qname.equals("subject")) {
            if (qnameParent().equals("response")) {
                response_subject = value;
            }
        }
        if (qname.equals("url")) {
            if (qnameParent().equals("response")) {
                response_url = value;
            }
        }

        qnamelist.remove(index - 1);
        index--;
        value = "";
        inelement = false;
    }

    /**
     * Receive notification of character data inside an element.
     *
     * @param ch
     * @param start
     * @param length
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        if (inelement) {
            String charactervalue = "";
            for (int i = 0; i < length; i++) {
                char c = ch[start + i];
                if (c != '\r' && c != '\t' && c != '\f' && c != '\n') {
                    charactervalue = charactervalue.concat(String.valueOf(c));
                }
            }
            value = value.concat(charactervalue).trim();
        }
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String qnameParent() {
        return qnamelist.get(index - 2);
    }
}
