package top.dozy.test;

interface  A{
    double f(double x,double y);
}
class B implements A{
    @Override
    public double f(double x, double y) {
        return x*y;
    }
}
class C implements A{
    @Override
    public double f(double x, double y) {
        return Math.pow((x+y),2);
    }
}
public class Asd {
    public static void main(String args[]){
        A a1=new B();
        System.out.println(a1.f(3,5));
        A a2=new C();
        System.out.println(a2.f(4,6));

    }
}