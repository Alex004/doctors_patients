package Repository;

import Domain.Entity;

public interface CrudRepo<ID,E extends Entity<ID>> {




        E save(E entity);
        E delete(ID id);
        Iterable<E> findAll();




}
