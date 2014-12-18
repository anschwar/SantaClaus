package de.hska.project;

/**
 * Execution and measurement of the different version
 */
public class JavaMain {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Santa Test Case 1");
        long duration = 0L;
        int elfGroupsHelped = 0;
        SantaClausTC1 app1;
        for (int i = 0; i < 20; i++) {
            app1 = new SantaClausTC1();
            duration += app1.getDuration();
            elfGroupsHelped += app1.getElvesHelped();
        }
        System.out.println(elfGroupsHelped + " elf groups helped");
        System.out.println(duration / 20 + " ms");

        System.out.println("Starting Santa Test Case 2");
        duration = 0L;
        elfGroupsHelped = 0;
        SantaClausTC2 app2;
        for (int i = 0; i < 20; i++) {
            app2 = new SantaClausTC2();
            duration += app2.getDuration();
            elfGroupsHelped += app2.getElvesHelped();
        }
        System.out.println(elfGroupsHelped + " elf groups helped");
        System.out.println(duration / 20 + " ms");

        System.out.println("Starting Santa Test Case 3");
        duration = 0L;
        elfGroupsHelped = 0;
        SantaClausTC3 app3;
        for (int i = 0; i < 20; i++) {
            app3 = new SantaClausTC3();
            duration += app3.getDuration();
            elfGroupsHelped += app3.getElvesHelped();
        }
        System.out.println(elfGroupsHelped + " elf groups helped");
        System.out.println(duration / 20 + " ms");

        System.out.println("Starting Santa Test Case 4");
        duration = 0L;
        elfGroupsHelped = 0;
        SantaClausTC4 app4;
        for (int i = 0; i < 20; i++) {
            app4 = new SantaClausTC4();
            duration += app4.getDuration();
            elfGroupsHelped += app4.getElvesHelped();
        }
        System.out.println(elfGroupsHelped + " elf groups helped");
        System.out.println(duration / 20 + " ms");

        System.out.println("Starting Santa Test Case 5");
        duration = 0L;
        elfGroupsHelped = 0;
        SantaClausTC5 app5;
        for (int i = 0; i < 20; i++) {
            app5 = new SantaClausTC5();
            duration += app5.getDuration();
            elfGroupsHelped += app5.getElvesHelped();
        }
        System.out.println(elfGroupsHelped + " elf groups helped");
        System.out.println(duration / 20 + " ms");
    }
}


