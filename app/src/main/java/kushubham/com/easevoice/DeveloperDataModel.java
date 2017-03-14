package kushubham.com.easevoice;

/**
 * Created by admin on 6/16/2016.
 */
public class DeveloperDataModel
{
    String name;
    String email;
    String phone;
    int image;
    public DeveloperDataModel(String name, String email, String phone, int image)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.image=image;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getImage() {
        return image;
    }
    public String getPhone() {
        return phone;
    }
}