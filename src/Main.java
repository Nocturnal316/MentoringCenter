public class Main {

    public static void main(String[] args) {


        Student[] stu = new Student[2];
        String[] names =  {"Tom","Tim","Kacey","Yuting","Betty"};

        MentoringCenter center = new MentoringCenter("Earl",2000,1200,4,stu.length);



        center.start();
        for(int i = 0; i < stu.length;i++){
            stu[i] = new Student(names[i],1,1200,i,center);
            stu[i].start();
        }


        for(int i = 0; i < stu.length;i++){
            try{
                stu[i].join();
            }catch( Exception e){
               System.out.println(e);
            }
        }

        try{
            center.join();
        }catch (Exception e){

        }


    }
}
