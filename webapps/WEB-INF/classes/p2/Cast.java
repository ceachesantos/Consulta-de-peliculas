package p2;

public class Cast implements Comparable<Cast>{

    String name;
    String ID;
    String papel;
    String contacto;

    public Cast(String name, String ID, String papel, String contacto){
        this.name = name;
        this.ID = ID;
        this.papel = papel;
        this.contacto = contacto;
    }

    public void setName(String name){
        this.name = name;
        return;
    }

    public void setID(String ID){
        this.ID = ID;
        return;
    }

    public void setPapel(String papel){
        this.papel = papel;
        return;
    }

    public void setContacto(String contacto){
        this.contacto = contacto;
        return;
    }

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.ID;
    }

    public String getPapel(){
        return this.papel;
    }

    public String getContacto(){
        return this.contacto;
    }

    public int compareTo(Cast actores){

        if(this.papel.equals(actores.papel)) return this.ID.compareTo(actores.ID);

        if(this.papel.equals("Supporting") && actores.papel.equals("Extra")) return -1;
        if(this.papel.equals("Supporting") && actores.papel.equals("Main")) return -1;
        if(this.papel.equals("Main") && actores.papel.equals("Extra")) return -1;

        else return 1;
        
    }

    public String toString(){
        return "Nombre = '"+name+"' --- ID = '"+ID+"' --- Papel= '"+papel+"' --- Contacto= '"+contacto+"'";
    }
}