package lib.order;

public class Users {
    private int id;
    private String name;
    private String phone;

    public Users(){
    }
    public Users(int id, String name, String phone){
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}