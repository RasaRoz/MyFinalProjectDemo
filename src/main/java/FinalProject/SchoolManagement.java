package FinalProject;

import java.sql.*;
import java.util.Scanner;


public class SchoolManagement {

    public static void main(String[] args) {
        try
        {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/finaldbs", "root", "L4tvija2021");



//            System.out.println("Enter student's first name!");
//            Scanner myScanner = new Scanner(System.in);
//            String f_name = myScanner.nextLine();
//
//            System.out.println("Enter student's last name!");
//            Scanner myScanner1 = new Scanner(System.in);
//            String l_name = myScanner1.nextLine();
//
//            System.out.println("Enter student's birth year!");
//            Scanner myScanner2 = new Scanner(System.in);
//            int birthYear = myScanner2.nextInt();
//
//            System.out.println("Enter student's score (1-10)!");
//            Scanner myScanner3 = new Scanner(System.in);
//            int student_score = myScanner3.nextInt();
//
//            System.out.println("Enter the ID of a class the student is going to attend!");
//            Scanner myScanner4 = new Scanner(System.in);
//            int classesID = myScanner4.nextInt();
//
//            addNewStudent(con, f_name, l_name, birthYear, student_score, classesID);




//            System.out.println("Enter teacher's first name!");
//            Scanner myScanner6 = new Scanner(System.in);
//            String f_name = myScanner6.nextLine();
//
//            System.out.println("Enter teacher's last name!");
//            Scanner myScanner7 = new Scanner(System.in);
//            String l_name = myScanner7.nextLine();
//
//            addNewTeacher(con,f_name, l_name);





//            System.out.println("Enter the title of the class!");
//            Scanner myScanner8 = new Scanner(System.in);
//            String c_title = myScanner8.nextLine();
//
//            System.out.println("Enter teacher ID assigned to the class!");
//            Scanner myScanner9 = new Scanner(System.in);
//            int teacherID = myScanner9.nextInt();
//
//            addNewClass(con, c_title, teacherID);





//            System.out.println("Enter the ID of a student who you want to assign to a new class!");
//            Scanner myScanner10 = new Scanner(System.in);
//            int studentID = myScanner10.nextInt();
//
//            System.out.println("Enter the new class ID!");
//            Scanner myScanner11 = new Scanner(System.in);
//            int classesID = myScanner11.nextInt();
//
//            updateStudentClass(con, classesID, studentID);





//            System.out.println("Enter the ID of a class you want to change the teacher!");
//            Scanner myScanner12 = new Scanner(System.in);
//            int classesID = myScanner12.nextInt();
//
//            System.out.println("Enter the ID of the teacher who is going to teach that subject!");
//            Scanner myScanner13 = new Scanner(System.in);
//            int teacherID = myScanner13.nextInt();
//
//            updateTeacherClass(con, classesID, teacherID);



            System.out.println("Enter class ID to see the average score!");
            Scanner myScanner14 = new Scanner(System.in);
            int classesID = myScanner14.nextInt();
            getAverageScore(con, classesID);





//            System.out.println("Enter teacher ID to see all his/her students!");
//            Scanner myScanner5 = new Scanner(System.in);
//            int teacherID = myScanner5.nextInt();
//
//            getAllStudentsByTeacher(con, teacherID);

        }

        catch(Exception e)
        {
            System.out.println(e);
        }

    }

    public static void addNewStudent(Connection connection, String f_name, String l_name, int birthYear, int student_score, int classesID){
        String insertStudent = "INSERT INTO finaldbs.student(first_name, last_name, yob, score, classes_id) VALUES(?,?,?,?,?)";
        try(PreparedStatement insertQuery = connection.prepareStatement(insertStudent)){

            insertQuery.setString(1, f_name);
            insertQuery.setString(2, l_name);
            insertQuery.setInt(3, birthYear);
            insertQuery.setInt(4, student_score);
            insertQuery.setInt(5, classesID);

            insertQuery.executeUpdate();

        }catch(SQLException err){
            err.printStackTrace();
        }
    }

    public static void addNewTeacher(Connection connection, String f_name, String l_name){
        String insertTeacher = "INSERT INTO finaldbs.teacher(first_name, last_name) VALUES(?,?)";
        try(PreparedStatement insertQuery = connection.prepareStatement(insertTeacher)){

            insertQuery.setString(1, f_name);
            insertQuery.setString(2, l_name);


            insertQuery.executeUpdate();

        }catch(SQLException err){
            err.printStackTrace();
        }
    }

    public static void addNewClass(Connection connection, String c_title, int teacherID){
        String insertClass = "INSERT INTO finaldbs.classes(title, teacher_id) VALUES(?,?)";
        try(PreparedStatement insertQuery = connection.prepareStatement(insertClass)){

            insertQuery.setString(1, c_title);
            insertQuery.setInt(2, teacherID);


            insertQuery.executeUpdate();

        }catch(SQLException err){
            err.printStackTrace();
        }
    }


    public static void updateStudentClass(Connection connection, int classesID, int studentID){
        String updateClass = "update finaldbs.student SET classes_id = ? where id = ?";

        try(PreparedStatement assignClass = connection.prepareStatement(updateClass)){
            assignClass.setInt(1,classesID);
            assignClass.setInt(2,studentID);

            assignClass.executeUpdate();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    public static void updateTeacherClass(Connection connection, int classesID, int teacherID){
        String updateTeachClass = "update finaldbs.classes SET teacher_id = ? where id = ?";

        try(PreparedStatement assignClass = connection.prepareStatement(updateTeachClass)){
            assignClass.setInt(1,teacherID);
            assignClass.setInt(2,classesID);

            assignClass.executeUpdate();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    public static void getAllStudentsByTeacher(Connection connection, int teacherID){
        CallableStatement studentStatement = null;
        try{
            String SQLStudents = "{call getAllStudentsUnderTeacher(?)}";
            studentStatement = connection.prepareCall(SQLStudents);
            studentStatement.setInt(1, teacherID);
            ResultSet result = studentStatement.executeQuery();
            while (result.next())
                System.out.println(result.getInt(1) + " " +result.getString(2)+ " " +result.getString(3)+ " " +result.getInt(4)+ " " +result.getInt(5));


        } catch (SQLException err){
            err.printStackTrace();
        }
    }

    public static void getAverageScore(Connection connection, int classesID){
        CallableStatement scoreStatement = null;
        try{
            String SQLScore = "{call getAvgScore(?)}";
            scoreStatement = connection.prepareCall(SQLScore);
            scoreStatement.setInt(1, classesID);
            ResultSet result = scoreStatement.executeQuery();
            while (result.next())
                System.out.println(result.getFloat(1));


        } catch (SQLException err){
            err.printStackTrace();
        }
    }

}
