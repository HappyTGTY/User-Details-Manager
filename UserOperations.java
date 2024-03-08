import java.io.*;
public class UserOperations {
    User getUserInput() {
        User user = new User();
        try {
            InputStreamReader read = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(read);

            System.out.print("Enter Phone Number : ");
            user.phoneNo = Long.parseLong(in.readLine());

            System.out.print("Enter Email : ");
            user.email = in.readLine();

            System.out.print("Enter Name : ");
            user.name = in.readLine();

            System.out.print("Enter Age : ");
            user.age = Byte.parseByte(in.readLine());

            System.out.print("Enter Gender : ");
            user.gender = in.readLine().toUpperCase().charAt(0);

            System.out.print("Enter Address : ");
            user.address = in.readLine();

            return user;
        } catch (Exception e) {
            System.out.println("Error Occurred");
            return null;
        }
    }
    void printUserDetails(User user) {
        System.out.println("Phone Number : "+user.phoneNo);
        System.out.println("Email        : "+user.email);
        System.out.println("Name         : "+user.name);
        System.out.println("Age          : "+user.age);
        System.out.println("Gender       : "+user.gender);
        System.out.println("Address      : "+user.address);
    }
}
