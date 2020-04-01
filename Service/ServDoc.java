package Service;

import Domain.Doctor;
import Repository.FileRepoDoc;
import Repository.Repo;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import Exception.Invalid_Id;
import Repository.RepoDoc;

public class ServDoc {
    //This class is service class for Doctor

    private String filepath;

    private FileRepoDoc repDoc;

    private ServDoc()
    {

    }

    public static ServDoc getInstance() {
        return ServDoc.ServDoc_singleton.INSTANCE;
    }

    private static class ServDoc_singleton {

        private static final ServDoc INSTANCE = new ServDoc();
    }

    public FileRepoDoc getRepDoc() {
        //This method returns the repository for Doctor class
        //Input:-
        //Output:-
        return repDoc;
    }

    public void setFilepath(String filepath)
    {
        //This method sets file's path for saving data
        //Input:- filepath - string
        //Output:-
        this.filepath=filepath;
        repDoc=new FileRepoDoc(filepath);

    }

    private void addOneDoctor(Boolean flag)
    {
        //This method generates one doctor
        //Input:flag - boolean help the repo to create a new file or to append an existing one
        //Output:-
        //Exception:  catches an exception when it is duplicate
        String first_name=generate_name(3);
        String last_name=generate_name(2);
        int age=generate_age(30,65);

        boolean ok=false;

        while(ok!=true)
        {
            try{
                int id=generate_id(4);
                Doctor doc=new Doctor(first_name,last_name,age,id);
                repDoc.saveDoc(doc,flag);

                ok=true;
            }catch (Invalid_Id ex )
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void addDoctors(Integer n)
    {
        //This method generates the doctors
        //Input: n - integer (number of doctors that should be generated)
        //Output:-
        setFilepath("Doc.xml");
        verifFile();

        boolean flag= false;

        while(n!=0)
        {
            addOneDoctor(flag);
            flag=true;
            n--;
        }

    }

    private void verifFile()
    {
        //This method creates a file
        //Input:-
        //Output:-
        //Exception: IOException


        try
        {
            File temp=new File(filepath);
            temp.createNewFile();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void printDoctors()
    {
        //This method print all the doctors
        //Input:-
        //Output:-
        repDoc.findAll().forEach(System.out::println);
    }

    private Integer generate_id(int i) {
        //This method generates the id a doctor
        //Input: i -integer (number of digits that the id should have)
        //Output: random number
        int num=1;
        while(i>0)
        {
            num*=10;
            i--;
        }
        return new Random().nextInt(num-num/10+1)+num/10;
    }

    private Integer generate_age(int i, int i1) {
        //This method generates the age of one doctor
        //Input:i,i1 integer(interval of value)
        //Output: a random number
        if(i>i1)
        {
            int aux=i;
            i=i1;
            i1=aux;
        }
        return new Random().nextInt(i1-i+1)+i;
    }

    private String generate_name(int i) {
        //This method generates the name of the patient
        //Input: i -integer (number of digits that the name should have)
        //Output: name -string;
        Random rand=new Random();
        String name="";
        int generate=rand.nextInt((int)'Z'-(int)'A'+1)+(int)'A';
        name=name+(char)generate;
        i--;
        while (i>0)
        {
            generate=rand.nextInt((int)'z'-(int)'a'+1)+(int)'a';
            name=name+(char)generate;
            i--;
        }

        return name;
    }

}
