public class manager extends Employees{
    private String section;
    private double Allowance=0;

    public manager(){
        super();
    }
    public manager(String v,float w,int x,String y,double z){
        super(v,w,x);
        section=y;
        Allowance=z;

    }

    public void getAllowance(){
        Allowance =salary*0.1;
        System.out.println("Your allowance is " + Allowance);
    }

    public void viewSalary(){
        double total;
        total = salary + Allowance;
        System.out.println("Your Basic salary is " + salary);
        System.out.println("Your allowance is " + Allowance);
        System.out.println("Your net salary is " + total);

    }

    public void getTax(){
        System.out.println("no tax");
    }

    public static void main(String [] args){
    
        manager m1;
        m1=new manager();
        
        
        
    }
    
}
