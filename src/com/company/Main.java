package com.company;
import com.company.model.Aplications;
import com.company.model.Root;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class Main {
private static final String TAG_NAME_MAIN ="name";
private static final String TAG_APLICATIONS ="Aplications";
private static final String TAG_ELEMENT ="Element";
private static final String TAG_NAME ="name";
private static final String TAG_SIZE ="size";
private static final String TAG_COST ="cost";
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, FileNotFoundException {

        Root root= new Root();

        Document document;
        try {
            document = buildDocument();
        } catch (Exception e){
            System.out.println("Open parsing error"+e.toString());
            return;
        }

       Node rootNode = document.getFirstChild();

       NodeList rootChilds = rootNode.getChildNodes();

       String mainName= null;
       Node AplicationsNode=null;
       for(int i=0; i< rootChilds.getLength();  i++){

           if(rootChilds.item(i).getNodeType()!=Node.ELEMENT_NODE){
               continue;
           }
           switch (rootChilds.item(i).getNodeName()){
               case TAG_NAME_MAIN: {
                mainName = rootChilds.item(i).getTextContent();
                break;
               }
               case TAG_APLICATIONS:{
                        AplicationsNode = rootChilds.item(i);
                        break;
               }
           }
       }

       if(AplicationsNode==null) {
           return;
       }
        List<Aplications> AplicationsList =parsAplications(AplicationsNode);
        root.setName(mainName);
        root.setAplications(AplicationsList);

        root.getAplications().stream().forEach(val->System.out.println(val));
        int Sizes=0;
        int Costs=0;
        for(int i=0;i<AplicationsList.toArray().length;i++){
            Sizes+=AplicationsList.get(i).getSize();
        }
        for(int i=0;i<AplicationsList.toArray().length;i++){
            Costs+=AplicationsList.get(i).getCost();
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document new_document = builder.newDocument();
        Element All_Sizes =new_document.createElement("All_Sizes");
        Element All_Costs = new_document.createElement("All_Costs");
        Element All_Aplications = new_document.createElement("Aplications");
        new_document.appendChild(All_Aplications);
        All_Aplications.appendChild(All_Sizes);
        All_Aplications.appendChild(All_Costs);
        All_Sizes.setAttribute("All_size",String.valueOf(Sizes));
        All_Costs.setAttribute("All_Costs",String.valueOf(Costs));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(new DOMSource(new_document), new StreamResult(new FileOutputStream("Export")));

    }
    private static Document buildDocument() throws Exception {
        File file = new File("test.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder().parse(file);


    }
    private static List<Aplications> parsAplications(Node AplicationsNode){
        List<Aplications> AplicationsList = new ArrayList<>();
        NodeList AplicationsChilds = AplicationsNode.getChildNodes();
        for(int i=0; i< AplicationsChilds.getLength();  i++){

            if(AplicationsChilds.item(i).getNodeType()!=Node.ELEMENT_NODE){
                continue;
            }

            if(!AplicationsChilds.item(i).getNodeName().equals(TAG_ELEMENT)){
                continue;
            }
            String name="";
            int size=0;
            int cost=0;
            NodeList elementChilds = AplicationsChilds.item(i).getChildNodes();
            for(int j=0; j< elementChilds.getLength();j++){

                if(elementChilds.item(j).getNodeType()!=Node.ELEMENT_NODE){
                    continue;
                }

                switch (elementChilds.item(j).getNodeName()){
                    case TAG_NAME: {
                        name=elementChilds.item(j).getTextContent();
                        break;
                    }
                    case TAG_SIZE:{

                        size=Integer.valueOf(elementChilds.item(j).getTextContent());
                        break;
                    }
                    case TAG_COST:{
                        cost=Integer.valueOf(elementChilds.item(j).getTextContent());
                        break;
                    }
                }

            }
            Aplications aplications = new Aplications(name,cost,size);
            AplicationsList.add(aplications);
        }
        return AplicationsList;
    }
    }
