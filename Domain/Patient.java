package Domain;

public class Patient extends Entity<Integer> {
    private String first_name,last_name;
    private Integer age=null;
    private Reason reason;
    private static int  id=1;

    public Patient(String first_name, String last_name, Integer age, Reason reason) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.reason = reason;
        setId(id);
        id++;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Integer getAge() {
        return age;
    }

    public Reason getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==this)
            return true;
        if(!(obj instanceof Patient))
            return false;
        Patient pat=(Patient) obj;

        return first_name.equals(pat.getFirst_name())&&last_name.equals(pat.getLast_name())&&reason==pat.getReason();
    }

    @Override
    public String toString() {
        return "ID: "+getId()+
                "; First Name: "+first_name+
                "; Last Name: "+last_name+
                "; Age: "+age+
                "; Reason: "+reason;
    }

}
