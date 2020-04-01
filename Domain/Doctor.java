package Domain;

public class Doctor extends Entity<Integer> {
    //This class is the Doctor class

    private String first_name,last_name;
    private Integer age=null;
    private Integer workTime=420;

    public Doctor(String first_name, String last_name, Integer age,Integer id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        setId(id);
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

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==this)
            return true;
        if(!(obj instanceof Doctor))
            return false;
        Doctor doc=(Doctor) obj;

        return Integer.compare(getId(),doc.getId())==0;
    }

    @Override
    public String toString() {
        return "ID: "+getId()+
                "; First Name: "+first_name+
                "; Last Name: "+last_name+
                "; Age: "+age;
    }
}
