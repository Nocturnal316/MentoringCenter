/**
 * Created by Nocturnal on 6/12/2016.
 */
public class Student extends Thread{
    String name;
    int cycles;
    int codingTime;
    int id;

    MentoringCenter TA;


    public Student(String s, int cyc, int codeTime, int i,MentoringCenter t){
        name = s;
        cycles = cyc;
        codingTime = codeTime;
        id = i;
        TA = t;
    }

    public String toString(){
        return name;
    }
    public int getID(){
        return id;
    }

    public void run(){

        int i = 0;
        boolean gotHelped = false;
        try{
            while( i < cycles){
                System.out.println(name +" is coding like crazy");
                Thread.sleep(codingTime);

                if(!TA.getHelp(this)){
                    System.out.println(name+" did not get help, Waiting Room was full");
                }
                i+=1;
                Thread.sleep(3000);

            }

            System.out.println(name + " done coding for day");
        }catch(Exception e){

        }
        TA.decrementStu();

    }
}
