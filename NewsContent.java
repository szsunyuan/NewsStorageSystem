/**
 * Write a description of class NewsContent here.
 * 
 * @author Yuan Sun
 * @version 1.0
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class NewsContent
{
    private Document dom;
    private NodeList nl;
    private Element eElement;
    private Node nNode;
    /**
     * Constructor for objects of class NewsContent
     */
    public NewsContent()
    {
        ParseNews();
    }
    
    public void ParseNews()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("database.xml");
            Element docEle = dom.getDocumentElement();
            nl = docEle.getElementsByTagName("NewsList");
            nNode = nl.item(0);
            eElement = (Element) nNode;
        }catch(ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        }
    }
    
    public String getTitle(int id)
    {
        if(id > 0)
            id--;
        else
            return null;
        return eElement.getElementsByTagName("title").item(id).getTextContent();
    }
    
    public String getContent(int id)
    {
        if(id > 0)
            id--;
        else
            return null;
        return eElement.getElementsByTagName("body").item(id).getTextContent();
    }
    
    public String getTime(int id)
    {
        if(id > 0)
            id--;
        else
            return null;
        return eElement.getElementsByTagName("month").item(id).getTextContent() + "/" + 
            eElement.getElementsByTagName("day").item(id).getTextContent() + "/" +
            eElement.getElementsByTagName("year").item(id).getTextContent() + " " +
            eElement.getElementsByTagName("time").item(id).getTextContent();
    }
    
    public int getCurrentID()
    {
        return eElement.getElementsByTagName("news").getLength();
    }
}
