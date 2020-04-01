package Domain;

import java.sql.Connection;

public class Consulting extends Entity<Integer> {
    //This class is the Consulting class

    private static final Integer clinicOpen=720;
    private Room room;
    private String firstNameDoc,lastNameDoc;
    private Integer idDoc,idPatient;
    private Integer reasonSuma,reasonTimp;
    private static Integer id=1;

    public Consulting(Room room, String firstNameDoc, String lastNameDoc, Integer idDoc, Integer idPatient, Integer reasonSuma, Integer reasonTimp) {
        this.room = room;
        this.firstNameDoc = firstNameDoc;
        this.lastNameDoc = lastNameDoc;
        this.idDoc = idDoc;
        this.idPatient = idPatient;

        this.reasonSuma = reasonSuma;
        this.reasonTimp = reasonTimp;
        setId(id);
        id++;
    }

    public static Integer getClinicOpen() {
        return clinicOpen;
    }


    public Room getRoom() {
        return room;
    }

    public String getFirstNameDoc() {
        return firstNameDoc;
    }

    public String getLastNameDoc() {
        return lastNameDoc;
    }

    public Integer getIdDoc() {
        return idDoc;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public Integer getReasonSuma() {
        return reasonSuma;
    }

    public Integer getReasonTimp() {
        return reasonTimp;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==this)
            return true;
        if(!(obj instanceof Consulting))
            return false;
        Consulting cons=(Consulting) obj;

        return Integer.compare(idDoc,cons.getIdDoc())==0&&
                Integer.compare(idPatient,cons.getIdPatient())==0&&
                Integer.compare(reasonSuma,cons.getReasonSuma())==0;
    }

    @Override
    public String toString() {
        return "ID_Consult: "+getId()+
                "; Room: "+room+
                "; ID_Doc: "+idDoc+
                "; First Name: "+firstNameDoc+
                "; Last Name: "+lastNameDoc+
                "; ID_Pat: "+idPatient+
                "; Sum: "+reasonSuma+
                "; Time: "+reasonTimp;
    }
}
