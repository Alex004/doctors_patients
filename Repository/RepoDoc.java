package Repository;

import Domain.Entity;

public class RepoDoc<ID,E extends Entity<ID>> extends Repo<ID,E > {
    //This class is used to allow operations on Doctor object

    public RepoDoc()
    {
        super();
    }
}
