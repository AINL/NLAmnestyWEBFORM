/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.webform.config;

import java.text.SimpleDateFormat;
import java.util.List;
import nl.amnesty.webform.mapping.Mapping;
import nl.amnesty.webform.response.Response;

/**
 *
 * @author evelzen
 */
public class WebformDef {

    public final static String URLCONFIGWEBFORM = "http://zeebaars.amnesty.nl:8080/nlamnestyconfig/webform.xml";

    /*
     * public static enum crmobject {
     *
     * ADDRESS, BANKACCOUNT, CHANNEL, COMMITMENT, CONTACT, COUNTRY, DOCUMENT,
     * FLAG, GROUP, INVOLVEMENT, NETWORK, PERSON, PHONE, PRODUCT, RELATION,
     * ROLE, SUBSCRIPTION, URL }
     *
     */
    public static enum operation {

        ADDADDRESS, ADDBANKACCOUNT, ADDCHANNEL, ADDCOMMITMENT, ADDCONTACT, ADDCOUNTRY, ADDDOCUMENT, ADDFINANCE, ADDFLAG, ADDGROUP, ADDINVOLVEMENT, ADDNETWORK, ADDPERSON, ADDPHONE, ADDPRODUCT, ADDRELATION, ADDROLE, ADDSUBSCRIPTION, ADDURL,
        CHANGEADDRESS, CHANGEBANKACCOUNT, CHANGECHANNEL, CHANGECOMMITMENT, CHANGECONTACT, CHANGECOUNTRY, CHANGEDOCUMENT, CHANGEFLAG, CHANGEGROUP, CHANGEINVOLVEMENT, CHANGENETWORK, CHANGEPERSON, CHANGEPHONE, CHANGEPRODUCT, CHANGERELATION, CHANGEROLE, CHANGESUBSCRIPTION, CHANGEURL,
        REMOVEADDRESS, REMOVEBANKACCOUNT, REMOVECHANNEL, REMOVECOMMITMENT, REMOVECONTACT, REMOVECOUNTRY, REMOVEDOCUMENT, REMOVEFLAG, REMOVEGROUP, REMOVEINVOLVEMENT, REMOVENETWORK, REMOVEPERSON, REMOVEPHONE, REMOVEPRODUCT, REMOVERELATION, REMOVEROLE, REMOVESUBSCRIPTION, REMOVEURL,        
        SENDRESPONSE
    }
    private int node;
    private String name;
    private SimpleDateFormat simpledateformat;
    private String notification;
    private Mapping mapping;
    private List<operation> operationlist;
    private String networkname;
    private String networksource;
    private Response response;

    public WebformDef() {
    }

    public WebformDef(int node, String name, SimpleDateFormat simpledateformat, String notification, Mapping mapping, List<operation> operationlist, String networkname, String networksource, Response response) {
        this.node = node;
        this.name = name;
        this.simpledateformat = simpledateformat;
        this.notification = notification;
        this.mapping = mapping;
        this.operationlist = operationlist;
        this.networkname = networkname;
        this.networksource = networksource;
        this.response = response;
    }

    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworksource() {
        return networksource;
    }

    public void setNetworksource(String networksource) {
        this.networksource = networksource;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public List<operation> getOperationlist() {
        return operationlist;
    }

    public void setOperationlist(List<operation> operationlist) {
        this.operationlist = operationlist;
    }

    public SimpleDateFormat getSimpledateformat() {
        return simpledateformat;
    }

    public void setSimpledateformat(SimpleDateFormat simpledateformat) {
        this.simpledateformat = simpledateformat;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNetworkname() {
        return networkname;
    }

    public void setNetworkname(String networkname) {
        this.networkname = networkname;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
