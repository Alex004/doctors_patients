package UI;

import Check.Check;
import Service.ServCons;
import Service.ServDoc;
import Service.ServPatient;

public class UI {
    //This class is the user interface

    private ServDoc servDoc=ServDoc.getInstance();
    private ServPatient servPatient=ServPatient.getInstance();
    private ServCons servCons=ServCons.getInstance();
    private Read read=new Read();
    private Check check=new Check();

    private static UI instance;
    private UI() {

    }


    public  static UI getInstance() {
        if (instance == null) {
            instance = new UI();
        }
        return instance;
    }

    private Integer setNrOfDoc()
    {
        //This function checks if the user wants to select a number of doctors. If he or she won't select the number of doctors,the number
        // will be 8
        //Input:-
        //Output:number of doctors
        Integer nr=8;
        System.out.println("Do you want to select a number of doctors?(it should be at least 8)");
        String answerQ=read.readQuestionDoc();
        while(check.checkAnsDoc(answerQ).compareTo("y")!=0)
        {
            System.out.println("You should introduce only 'y' if you want to give a number of doctors or 'n' otherwise");
            answerQ=read.readQuestionDoc();
        }
        if(answerQ.compareTo("y")==0)
        {
            Integer newNrDoc=(Integer) read.readNumberOfDoc();
            while(newNrDoc<8)
            {
                System.out.println("You should introduce a number bigger than 8");
                newNrDoc=(Integer)read.readNumberOfDoc();
            }
            return newNrDoc;
        }
        else
            return nr;

    }

    public void start()
    {
        //This method calls all important methods
        //Input:-
        //Output:-
        Integer nr_of_doc=setNrOfDoc();
        servDoc.addDoctors(nr_of_doc);
        servPatient.addPatients(100);
        servCons.setServ(servDoc,servPatient);
        System.out.println("List of doctors:");
        servDoc.printDoctors();
        System.out.println("\nList of patients:");
        servPatient.printPatients();
        System.out.println("\nSummary of patients:");
        servPatient.printStatistics();
        System.out.println("\nList of consults:");
        servCons.addConsult();
        servCons.prindConsult();
        System.out.println("\nSummary of doctors:");
        servCons.creatDtoConsult();
        servCons.printDtoConsult();
        System.out.println("\nList of patients that weren't consulted:");
        servPatient.printPatients();

    }


}
