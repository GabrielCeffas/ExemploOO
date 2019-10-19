package br.edu.unifei.ecoi09.course.main;


public class Person {

    private String CPF ;
    private String name ;
    protected String birthDate ;


    public Person (String CPF, String name) {
        this.CPF = CPF;
        this.name = name;
    }
    public String getCPF () {
        return CPF ;
    }
    public void setCPF (String CPF) {
        this.CPF = CPF;
    }
    public String getName () {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }
    public void setBirthDate (String birthDate) {
        this.birthDate = birthDate;
    }
    public String getBirthDate () {
        return this . birthDate ;
    }

    @Override
    public String toString () {
        return new String( this.CPF );
    }

}