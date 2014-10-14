package webService;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.junit.Assert;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SoapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String location = "Kent";
			  SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		      SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		        // Send SOAP Message to SOAP Server
		        String url = "http://www.webservicex.net/uklocation.asmx?WSDL";
		        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(location), url);

		        // print SOAP Response
		       // soapResponse.writeTo(System.out);

		        //Verify the expected location is in the results
		        verifyMessage(soapResponse, location);
		        
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	private static void verifyMessage(SOAPMessage soapResponse, String location) throws SOAPException, DOMException, SAXException, IOException, ParserConfigurationException {
		//Parse soapmessage into xml
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(new InputSource(new StringReader(soapResponse.getSOAPBody().getTextContent())));
	    
	    //Go through xml and check county
	    for(int i=0; i<doc.getElementsByTagName("County").getLength();i++){
	    	Assert.assertEquals("County matched", location, doc.getElementsByTagName("County").item(i).getTextContent());
	    }
	 
	}
	/**
	 * <?xml version="1.0" encoding="UTF-8"?> 
	 *    <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.webserviceX.NET"> 
	 *      <SOAP-ENV:Body>
	 *        <ns1:GetUKLocationByCounty> 
	 *          <ns1:County>Kent</ns1:County>
	 *        </ns1:GetUKLocationByCounty> 
	 *      </SOAP-ENV:Body>
	 *    </SOAP-ENV:Envelope>	
	 * @return soapMessage
	 * @throws Exception
	 * 
	 * Build Soap message
	 */
	 private static SOAPMessage createSOAPRequest(String location) throws Exception {
	        MessageFactory messageFactory = MessageFactory.newInstance();
	        SOAPMessage soapMessage = messageFactory.createMessage();
	        
	        MimeHeaders mimeHeaders = new MimeHeaders();
	        mimeHeaders.addHeader("Content-Type","text/xml; charset=UTF-8");
	        
	        SOAPPart soapPart = soapMessage.getSOAPPart();

	        String serverURI = "http://www.webserviceX.NET";
	        
	        // SOAP Envelope
	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration("ns1", serverURI);
	        // SOAP Body
	       SOAPBody soapBody = envelope.getBody();
	       SOAPElement GetUKLocation = soapBody.addChildElement("GetUKLocationByCounty", "ns1");
	       SOAPElement County = GetUKLocation.addChildElement("County", "ns1").addTextNode(location);	       
	       
	        soapMessage.saveChanges();

	        return soapMessage;
	    }
}
