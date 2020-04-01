package Repository;

import Domain.Entity;
import Exception.Invalid_Id;

import java.util.HashMap;
import java.util.Map;

public class RepoPatient<ID,E extends Entity<ID>> extends Repo<ID,E> {
    //This class is the repo for patients

    private Map<Integer,Integer> summary=null;


    public RepoPatient()
    {
        super();
        summary=new HashMap<>();

        summary.put(1,0);
        summary.put(2,0);
        summary.put(3,0);
        summary.put(4,0);
    }


    public E save_summary(E entity,int key) throws Invalid_Id {
        //This method call save() from parent class and add a patient to the right place
        //Input: entity - generic type(in this will be Patient),key - integer the right category for a patient
        //Output: a Patient
        //Exception: is throw Invalid_Id


        E saved=save(entity);
        int value;

        summary.replace(key,summary.get(key),summary.get(key)+1);

        return saved;

    }
    public Map<Integer,Integer> map()
    {
        //This method returns a Map used for putting a patient in a category
        //Input:-
        //Output: summary - map

        return summary;
    }
    public Iterable<Integer> findAll_summary()
    {
        //This method returns the values of a map
        //Input:-
        //Output:Iterable with value
        return summary.values();
    }
}
