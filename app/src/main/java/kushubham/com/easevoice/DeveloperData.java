package kushubham.com.easevoice;

/**
 * Created by admin on 6/16/2016.
 */
public class DeveloperData
{
    public static String names[]={"Kushagra Saxena","Shubham Saxena"};
    public static String email[]={"Email: kushagrasaxena03\n@gmail.com","Email: \nshubh3695@gmail.com"};
    public static String phone[]={"Contact: \n+91-9761194180","Contact: \n+91-8765513361"};
    public static int images[]={R.drawable.kushagra,R.drawable.shubham};
    public static String getNames(int i)
    {
        return names[i];
    }
    public static String getEmail(int i)
    {
        return email[i];
    }
    public static String getPhone(int i)
    {
        return phone[i];
    }
    public static int getImage(int i)
    {
        return images[i];
    }
}
