package Domain;

public class DtoConsulting extends Entity<Integer> {
    private Integer id_doc,nr_pat,sum,time;
    private String firstNameDoc,lastNameDoc;

    public DtoConsulting(Integer id_doc, Integer nr_pat, Integer sum, Integer time, String firstNameDoc, String lastNameDoc) {
        this.id_doc = id_doc;
        this.nr_pat = nr_pat;
        this.sum = sum;
        this.time = time;
        this.firstNameDoc = firstNameDoc;
        this.lastNameDoc = lastNameDoc;
        setId(id_doc);
    }

    public Integer getId_doc() {
        return id_doc;
    }

    public Integer getNr_pat() {
        return nr_pat;
    }

    public Integer getSum() {
        return sum;
    }

    public Integer getTime() {
        return time;
    }

    public String getFirstNameDoc() {
        return firstNameDoc;
    }

    public String getLastNameDoc() {
        return lastNameDoc;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==this)
            return true;
        if(!(obj instanceof DtoConsulting))
            return false;
        DtoConsulting cons=(DtoConsulting) obj;

        return Integer.compare(id_doc,cons.getId_doc())==0;
    }

    @Override
    public String toString() {
        return  firstNameDoc+", "+lastNameDoc+" - "+id_doc+": "+nr_pat+" patients, "+time+" minutes, "+sum+" RON";

    }
}
