package UI;

import java.util.Scanner;

public class Read {
    //This class is used for reading data from console

    public String readQuestionDoc()
    {
        //This method is used when it is asked the number of doctors
        //Input:-
        //Output: string

        System.out.println("\n[y/n]\n");
        Scanner x=new Scanner(System.in);
        return x.nextLine();
    }
    public Integer readNumberOfDoc()
    {
        //This method is used for reading the number of doctors from console
        //Input:-
        //Output: integer
        System.out.println("Introduce the number:");
        Scanner x=new Scanner(System.in);
        return x.nextInt();
    }

}
