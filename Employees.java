import java.util.Scanner;
public class Employees {
    protected String name;
    protected float salary;
    protected int pfNo;

    Scanner reader = new Scanner(System.in);
    
    public Employees(){
        System.out.println("Creating a new employee");
        System.out.println("Enter your name: ");
        name=reader.next();
        System.out.println("Enter your salary: ");
        salary=reader.nextFloat();
        System.out.println("Enter your pfNo: ");
        pfNo=reader.nextInt();

    }

    public Employees(String name,float salary,int pfNo){

        this.name=name;
        this.salary=salary;
        this.pfNo=pfNo;
    }
    
    public void getTax(){
        float tax;
        tax= 0.3f * salary;
        System.out.println(name + ", your tax is: "+ tax);

    }

    public void getName(){
        System.out.println("Your name is "+name);
    }

    public String setName(){
        System.out.println("Enter your new name");
        name=reader.nextLine();
        return name;
    }

    public void getSalary(){
        System.out.println("Your salary is "+ salary);
    }

    public double setSalary(){
        System.out.println("Enter new salary: ");
        salary=reader.nextFloat();
        return salary;
    }

    public void getpfNo(){
        System.out.println("Your pfNo is "+ pfNo);
    }

    public int setpfNo(){
        System.out.println("Enter new pfNo: ");
        pfNo=reader.nextInt();
        return pfNo;
    }

    public static void main(String[] args) throws Exception {
        Employees e1 = new Employees();
        Employees e2 = new Employees("joe", 2345, 30);
        e1.getTax();
        e2.getTax();
    }
}
