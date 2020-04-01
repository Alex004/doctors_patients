package Repository;

import Domain.Entity;

import java.util.HashMap;
import java.util.Map;
import Exception.Invalid_Id;


public class Repo<ID,E extends Entity<ID>> implements CrudRepo<ID,E> {
    //This class is the Repository

    protected Map<ID,E> entities=null;


    public Repo() {
      //  this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public E save(E entity)throws Invalid_Id {
        //This method saves an entity in a map
        //Input: entity has a generic type
        //Output: entity
        //Exception: IllegalArgumentException if entity is null, Invalid_Id if exists already another object with the same id


        if(entity == null)
        {
            throw new IllegalArgumentException("Entity is null.");
        }
       // validator.validate(entity,"s");
        E oldEntity=entities.get(entity.getId());
        if(oldEntity==null)
        {

            entities.put(entity.getId(),entity);
        }
        else
        {
            throw new Invalid_Id("This id is already exist.");
        }
        return oldEntity;
    }

    @Override
    public E delete(ID id) {
        //This method deletes an object
        //Input: id generic
        //Output: the deleted entity
        //Exception: IllegalArgumentException when id is not exist
        if(id==null)
        {
            throw new IllegalArgumentException("Key is not exist");
        }
        return entities.remove(id);

    }


    @Override
    public Iterable<E> findAll() {
        //This method returns an iterable with the objects saved in map
        //Input:-
        //Output: an iterable

        return entities.values();
    }


}
