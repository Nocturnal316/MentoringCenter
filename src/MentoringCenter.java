import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

public class MentoringCenter extends Thread {
    private String TA;
    private int gamingTime;
    private int helpingTime;
    private int students;
    private LinkedBlockingDeque<Student> waitingLine;
    private int maxCapacity;
    private static Semaphore taThinking;
    private static Semaphore  taHelping;
    private boolean helping;
    private Object waitForHelp;



    public MentoringCenter(int numStudents){
        TA = "Kenny";
        gamingTime = 3000;
        helpingTime = 6000;
        maxCapacity = 4;
        students = numStudents;
        waitingLine = new LinkedBlockingDeque();
        taThinking = new Semaphore(1);
        taHelping =  new Semaphore(1);
        waitForHelp = new Object();
        helping = false;
    }

    public MentoringCenter(String taName,int gameTime,int helpTime, int maxCap, int numStudents){
        TA = taName;
        gamingTime = gameTime;
        helpingTime = helpTime;
        maxCapacity = maxCap;
        students = numStudents;
        waitingLine = new LinkedBlockingDeque();
        taThinking = new Semaphore(1);
        taHelping =  new Semaphore(1);
        waitForHelp = new Object();
        helping = false;

    }


    synchronized public boolean getHelp(Student currentStudent){
        System.out.println(currentStudent+" is getting Help");
        if(waitingLine.size() >= maxCapacity){
            return false;
        }else{
            synchronized(waitingLine){
                waitingLine.add(currentStudent);
            }

            try{

                while(!(waitingLine.peekFirst().getID() == currentStudent.getID()) || helping){
                    wait();
                }
                System.out.println(currentStudent+" Waking up");
                if(taThinking.hasQueuedThreads()){
                    taThinking.release();
                }

                taHelping.acquire();
                currentStudent.sleep(helpingTime);


            }catch(Exception e){
                System.out.println(e);
            }
        }
        return true;
    }
    synchronized public void decrementStu(){
        students--;
        if(students == 0){
            taThinking.release();
        }
    }

    public void run(){
        System.out.println("Now Opening Mentoring Center");
        System.out.println(TA+" is playing a game");
        try{
            Thread.sleep(gamingTime);
            while(students > 0){
                System.out.println("TA is Working on Thesis");
                //Let TA idle until student taps him.

                taThinking.acquire();

                while(!waitingLine.isEmpty()){


                    helping = true;

                    Student currentStudent = waitingLine.peek();
                    System.out.println(TA+" is helping " + currentStudent);
                    this.sleep(helpingTime);
                    taHelping.release();

                    helping = false;
                    waitingLine.poll();
                    System.out.println(waitingLine.peekFirst());
                    synchronized(waitForHelp){
                        waitForHelp.notifyAll();
                    }



                }

                if(students > 0 && waitingLine.isEmpty()){
                    System.out.println(TA+" is playing a game");
                    this.sleep(gamingTime);
                }

            }
        }catch(Exception e){
            System.out.println(e);
        }

        System.out.println(students);
        System.out.println("Closing Center");
    }
}
