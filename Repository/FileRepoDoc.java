package Repository;

import Domain.Doctor;
import Domain.Entity;
import Exception.Invalid_Id;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class FileRepoDoc extends RepoDoc<Integer, Doctor> {
    //This class is repository for saved attributes of Doctor objects in a xml file
    private String filepath;
    public FileRepoDoc(String filepath)
    {
        super();
        this.filepath=filepath;
    }

//    public <T> Element addInDoc(T root)
//    {
//
//    }

    private Element createElem(Document d,Integer id,Integer age,String first_name,String last_name)
    {
        //This method creates an xml element and his children
        //Input:d - document,id - integer, age - integer,first_name - string, last_name - string
        //Output: person - element

        Element person=d.createElement("doctor");
        Attr attr = d.createAttribute("id_doc");
        attr.setValue(id.toString());
        person.setAttributeNode(attr);

        Element firstName=d.createElement("FirstName");
        firstName.appendChild(d.createTextNode(first_name));
        person.appendChild(firstName);

        Element lastName=d.createElement("LastName");
        lastName.appendChild(d.createTextNode(last_name));
        person.appendChild(lastName);

        Element age1=d.createElement("Age");
        age1.appendChild(d.createTextNode(age.toString()));
        person.appendChild(age1);
        return person;
    }

    public void addDoc(Doctor entity,Boolean flag)
    {
        //This method creates a xml file and fills it with doctor's data
        //Input:entity - Doctor object,flag - boolean used for deciding when to start a new document or when to fill the existing one
        //Output:-
        //Exception:ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException, IOException

        DocumentBuilderFactory doc=DocumentBuilderFactory.newInstance();
        String first_name=entity.getFirst_name();
        String last_name=entity.getLast_name();
        Integer age=entity.getAge();
        Integer id=entity.getId();
        try {
            DocumentBuilder document=doc.newDocumentBuilder();
            Document d=null;


            if(flag==false) {
                d=document.newDocument();
                Element root = d.createElement("doctors_list");

                Element person=createElem(d,id,age,first_name,last_name);
                d.appendChild(root);
                root.appendChild(person);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(d);
                StreamResult result = new StreamResult(new File(filepath));
                transformer.transform(source, result);
            } else {
                d = document.parse(filepath);
                Node root=d.getElementsByTagName("doctors_list").item(0);

                Element person=createElem(d,id,age,first_name,last_name);

                root.appendChild(person);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(d);
                StreamResult result = new StreamResult(new File(filepath));
                transformer.transform(source, result);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Doctor saveDoc(Doctor entity,Boolean flag) throws Invalid_Id {
        //This method saves a Doctor object in a map and saves it in a xml file too
        //Input:entity - Doctor object,flag - boolean used for to decide when to start a new document or when to fill the existing one
        //Output: a Doctor object
        //Exception: can generate Invalid_id when it exists another object with the same id

        addDoc(entity,flag);
        return super.save(entity);
    }


}
