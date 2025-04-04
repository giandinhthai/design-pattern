package decorator;

interface Notifier {
    public void sendMessage(String message);
}
class BaseNotifier implements  Notifier{
    public void sendMessage(String message){
        System.out.print(message);
        System.out.println();
    }
}
class EmailNotifier extends BaseNotifier{
    @Override
    public void sendMessage(String message){
        super.sendMessage(message);
        System.out.print("this is from email message: "+message);
        System.out.println();
    }
}//without decorator
class PhoneNotifier extends BaseNotifier{
    @Override
    public void sendMessage(String message){
        super.sendMessage(message);
        System.out.print("this is from phone message: "+ message);
        System.out.println();
    }
}//without decorator
class CombinedNotifier implements Notifier {
    private final BaseNotifier baseNotifier = new BaseNotifier();
    private final EmailNotifier emailNotifier = new EmailNotifier();
    private final PhoneNotifier phoneNotifier = new PhoneNotifier();

    @Override
    public void sendMessage(String message) {
        baseNotifier.sendMessage(message);
        emailNotifier.sendMessage(message);
        phoneNotifier.sendMessage(message);
    }
}
class EmailNotifierDecorator implements Notifier{
    Notifier wrapper;
    public EmailNotifierDecorator(Notifier notifier){
        this.wrapper=notifier;
    }
    public void sendMessage(String message){
        System.out.print("this is from email message: "+message);
        System.out.println();
        wrapper.sendMessage(message);


    }
}
class PhoneNotifierDecorator implements Notifier{
    Notifier wrapper;
    public PhoneNotifierDecorator(Notifier notifier){

        this.wrapper=notifier;
    }
    public void sendMessage(String message){
        System.out.print("this is from phone message: "+message);
        System.out.println();
        wrapper.sendMessage(message);
    }
}
class Application{
    Notifier notifier;
    public Application(Notifier notifier){
        this.notifier=notifier;
    }
    public void doLogic(){
        System.out.println("do s.t");
        notifier.sendMessage("done logic");
    }
}

public class Main {
    public static void main(String[] args) {
        Application appEmail = new Application(new EmailNotifier());
        Application appPhone=new Application(new PhoneNotifier());
        System.out.println("app email");
        appEmail.doLogic();
        System.out.println();

        System.out.println("app phone");
        appPhone.doLogic();
        System.out.println();




        //if i want to use phone and email then i need to change that have two notifier in application app or create
        //new class phoneAndEmailNotifier that reimplement phone and email message:
        Application appPhoneAndEmail=new Application(new CombinedNotifier());
        System.out.println("app phone and email");
        appPhoneAndEmail.doLogic();
        System.out.println();

        //instead of it i can create decorater :
        Application appDecorator = new Application(new PhoneNotifierDecorator(new EmailNotifierDecorator(new BaseNotifier())));
        System.out.println("app decorator");
        appDecorator.doLogic();
        System.out.println();

    }
}