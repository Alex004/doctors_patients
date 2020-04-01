package Service;

import Domain.Doctor;
import Domain.Patient;
import Domain.Reason;
import Repository.FileRepoDoc;
import Repository.FileRepoPatient;
import Repository.Repo;
import Exception.Invalid_Id;
import Repository.RepoPatient;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static Domain.Reason.*;

public class ServPatient {
    //This class is the service for Patient

    private String filepath;
    private FileRepoPatient repPat;

    private ServPatient() {
    }

    public static ServPatient getInstance() {
        return ServPatient.ServPatient_singleton.INSTANCE;
    }



    private static class ServPatient_singleton {

        private static final ServPatient INSTANCE = new ServPatient();
    }


    public FileRepoPatient getRepPat() {
        //This method returns the Repository of Patient
        //Input:-
        //Output:-
        return repPat;
    }

    public void setFilepath(String filepath)
    {
        //This method sets file's path for saving data
        //Input:- filepath - string
        //Output:-
        this.filepath=filepath;
        repPat=new FileRepoPatient(filepath);

    }


    private int verifType(int age)
    {
        //This method categorizes a patient according to his age
        //Input:- age int
        //Output:-
        if(age<=1)
            return 1;
        if(age>1 && age<=7)
            return 2;
        if(age>7 && age<=18)
            return 3;
       return 4;
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

    private void sureReason(Reason[] reasons,Boolean flag)
    {
        //This method helps to be sure that exists minimum one patient with every reason
        //Input:- reasons - array of Reason enum, flag - boolean helps the repo to create a new file or to append an existing one
        //Output:-
        //Exception: catches an exception when id is duplicated

        for(int i=0;i<reasons.length;i++)
        {
            boolean ok=false;
            while(ok!=true)
            {
                try{
                    Patient pat=new Patient(generate_name( 5),generate_name( 4),generate_age(0,85),reasons[i]);

                    repPat.savePat(pat,verifType(pat.getAge()),flag);
                    flag=true;
                    ok=true;
                }catch(Invalid_Id ex)
                {
                    System.out.println(ex.getMessage());
                }
            }

        }
    }

    private void restOfPatients(int n,Reason[] reasons,Boolean flag)
    {
        //This method generates the rest of patients
        //Input: n - integer (number of patients that should be generated),reasons - array of Reason enum,
        // flag - boolean helps the repo to create a new file or to append an existing one
        //Output:-
        //Exception: catches an exception when id is duplicated

        while(n>0)
        {
            boolean ok=false;
            while(ok!=true)
            {
                try{
                    String first_name=generate_name( 5);
                    String last_name=generate_name(4);
                    int age=generate_age(0,85);
                    Reason reason=(Reason) generate_reson(reasons);
                    Patient patient=new Patient(first_name,last_name,age,reason);
                    repPat.savePat(patient,verifType(patient.getAge()),flag);
                    ok=true;
                }catch(Invalid_Id ex)
                {
                    System.out.println(ex.getMessage());
                }
                n--;
            }

        }
    }

    public void addPatients(int n)
    {
        //This method creates patients
        //Input:-number of patients that should be created
        //Output:-
        Reason[] reasons=Reason.values();

        //saves file's path and creates the file
        setFilepath("Pat.xml");
        verifFile();
        Boolean flag=false;

        sureReason(reasons,flag);
        n=n-reasons.length;
        flag=true;
        restOfPatients(n,reasons,flag);
    }

    public void printStatistics()
    {
        //This method prints a statistic according with the patient's age
        //Input:-
        //Output:-
        Map<Integer,Integer> map=repPat.map();
        for(Map.Entry<Integer, Integer> i:map.entrySet())
        {
            switch (i.getKey())
            {
                case 1:
                {
                    System.out.println("Children (0-1): "+i.getValue()+" patients");
                    break;
                }
                case 2:
                {
                    System.out.println("Pupil (1-7): "+i.getValue()+" patients");
                    break;
                }
                case 3:
                {
                    System.out.println("Student (7-18): "+i.getValue()+" patients");
                    break;
                }
                case 4:
                {
                    System.out.println("Adults (>18): "+i.getValue()+" patients");
                    break;
                }
            }
        }
    }

    public void printPatients()
    {
        //This method prints all the patients
        //Input:-
        //Output:-
        repPat.findAll().forEach(System.out::println);

    }

    private Reason generate_reson(Reason[] reasons) {

        //This method generates the reason for one patient
        //Input: reasons - array of Reason enum
        //Output:- a reason

        return reasons[new Random().nextInt(reasons.length)];
    }

    private Integer generate_age(int i, int i1) {
        //This method generate the age of one patient
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
        //This method generate the name of the patient
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
