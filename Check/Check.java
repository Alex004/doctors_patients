package Check;

public class Check {
    //This class is used for checking attributes
    public String checkAnsDoc(String answer)
    {
        //This method checks if answer is 'y' or 'n'
        //Input: answer - String (read from console)
        //Output: "y" or "n" string

        if(answer.compareTo("y")==0||answer.compareTo("n")==0)
            return "y";
        return "n";
    }

}
