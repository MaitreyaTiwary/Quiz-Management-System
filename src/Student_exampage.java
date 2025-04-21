import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import java.io.IOException;


class Student_exampage1 extends JFrame {

    JPanel p1;
    JLabel j1,j2,prof_name,totaltime_lbl,totaltime_lbl1,time_lbl,min_lbl,sec_lbl,colon_lbl,date_lbl,date_lbl1,pan_name,pan_roll,prof_roll,prof_img;
    JRadioButton b1,b2,b3,b4;
    ButtonGroup g1;
    JButton save_next,sbt,clr;
    Connection con = null;
    String answer;

    private int quizId;  // Add quiz ID field
    private int totalQuestions;
    private int timeLimit;
    private int student_id;  // Add student_id as class field
    public int que_id = 1;
    public int mark = 0;
    public int min =0;
    public int sec =0;
    Timer time;
    String stu_ans ="";

    Student_exampage1(final int student_id, int roll_no, String stu_name, int quizId)
    {
        this.student_id = student_id;
        this.quizId = quizId;
        mark = 0;  // Initialize mark to 0
        con = (Connection) Db.dbconnect();
        
        // Initialize fonts and date formatter
        SimpleDateFormat d = new SimpleDateFormat("dd-M-yyyy");
        Date date = new Date();
        Font fo1 = new Font("Times New Roman", Font.BOLD, 18);
        Font fo2 = new Font("Times New Roman", Font.PLAIN, 18);
        Font fo3 = new Font("Sitka Display", Font.BOLD, 15);

        // Initialize UI components first
        setTitle("Student Exam Page");
        setLocation(280,120);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(255,255,224));
        setSize(1000, 600);

        ImageIcon i1 = new ImageIcon("Images/logo.png");
        setIconImage(i1.getImage());

        // Initialize all UI components
        p1 = new JPanel();
        p1.setBounds(0,0,300,564);
        p1.setLayout(null);
        p1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        p1.setBackground(Color.cyan);
        add(p1);

        prof_img = new JLabel();
        prof_img.setIcon(new ImageIcon("D:\\Desktop\\Quiz_Management_System - Copy\\Images\\student.png"));
        prof_img.setBounds(10, 10, 300, 100);
        prof_img.setHorizontalAlignment(JLabel.CENTER);
        p1.add(prof_img);

        pan_name = new JLabel("Name   :");
        pan_name.setFont(fo3);
        pan_name.setBounds(5,120,70,30);
        p1.add(pan_name);

        prof_name = new JLabel(stu_name);
        prof_name.setFont(fo3);
        prof_name.setBounds(75,120,225,30);
        p1.add(prof_name);

        pan_roll = new JLabel("Roll no:");
        pan_roll.setFont(fo3);
        pan_roll.setBounds(5,160,70,30);
        p1.add(pan_roll);

        prof_roll = new JLabel(String.valueOf(roll_no));
        prof_roll .setFont(fo3);
        prof_roll .setBounds(75,160,225,30);
        p1.add(prof_roll );

        date_lbl= new JLabel("Date    :");
        date_lbl.setBounds(5,200,70,30);
        date_lbl.setFont(fo3);
        p1.add(date_lbl);

        date_lbl1= new JLabel();
        date_lbl1.setText(d.format(date));
        date_lbl1.setBounds(75,200,225,30);
        date_lbl1.setFont(fo3);
        p1.add(date_lbl1);

        totaltime_lbl= new JLabel("Time   :");
        totaltime_lbl.setBounds(5,240,70,30);
        totaltime_lbl.setFont(fo3);
        p1.add(totaltime_lbl);

        totaltime_lbl1 = new JLabel("Loading...");
        totaltime_lbl1.setBounds(75,240,225,30);
        totaltime_lbl1.setFont(fo3);
        p1.add(totaltime_lbl1);

        j1=new JLabel("Question ?");
        j1.setFont(fo1);
        j1.setBounds(340,100,600,60);
        add(j1);

        j2=new JLabel("Q.1");
        j2.setBounds(340,70,100,30);
        j2.setFont(fo1);
        j2.setBackground(Color.blue);
        add(j2);

        time_lbl = new JLabel("Time Taken:");
        time_lbl.setBounds(700,10,110,50);
        time_lbl.setForeground(Color.BLACK);
        time_lbl.setFont(fo1);
        add(time_lbl);


        min_lbl = new JLabel();                                   //min
        min_lbl.setBounds(810,10,20,50);
        min_lbl.setForeground(Color.RED);
        min_lbl.setFont(fo1);
        add(min_lbl);

        colon_lbl = new JLabel(":");                                  //colon
        colon_lbl.setBounds(831,10,10,50);
        colon_lbl.setForeground(Color.RED);
        colon_lbl.setFont(fo1);
        add(colon_lbl);

        sec_lbl = new JLabel();                                  //Sec
        sec_lbl.setBounds(841,10,20,50);
        sec_lbl.setForeground(Color.RED);
        sec_lbl.setFont(fo1);
        add(sec_lbl);

        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                min_lbl.setText(String.valueOf(min));
                sec_lbl.setText(String.valueOf(sec));
                if(sec==59)
                {
                    sec =0;
                    min++;
                    if(min==timeLimit) {
                        time.stop();
                        submitQuiz(student_id);
                    }
                }
                sec++;
            }
        });

        g1=new ButtonGroup();


        b1=new JRadioButton("Option1");
        b1.setBackground(new Color(255,255,224));
        b1.setFont(fo2);
        b1.setBounds(340,170,500,30);
        add(b1);

        b2=new JRadioButton("Option2");
        b2.setBackground(new Color(255,255,224));
        b2.setFont(fo2);
        b2.setBounds(340,220,500,30);
        add(b2);

        b3=new JRadioButton("Option3");
        b3.setBackground(new Color(255,255,224));
        b3.setFont(fo2);
        b3.setBounds(340,270,500,30);
        add(b3);

        b4=new JRadioButton("Option4");
        b4.setBackground(new Color(255,255,224));
        b4.setFont(fo2);
        b4.setBounds(340,320,500,30);
        add(b4);

        g1.add(b1);
        g1.add(b2);
        g1.add(b3);
        g1.add(b4);

        clr=new JButton("Clear selection");
        clr.setBounds(570,500,120,30);

        add(clr);

        save_next=new JButton("Save & next");
        save_next.setBounds(710,500,120,30);
        add(save_next);


        sbt=new JButton("Submit");
        sbt.setBounds(850,500,100,30);
        add(sbt);

        // Now load quiz details after UI is set up
        initializeQuizDetails();

        // Start the quiz
        loadQuestion(1);
        time.start();

        save_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    answercheck(que_id, answer);
                    que_id++;
                    
                    if (que_id <= totalQuestions) {
                        loadQuestion(que_id);
                    } else {
                        // This was the last question
                        save_next.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "This was the last question. Please click Submit to finish the quiz.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        sbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                    "Do you really want to submit the quiz?",
                    "Confirm Submission",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        // Check the last answer if not already checked
                        if (que_id == totalQuestions) {
                            answercheck(que_id, answer);
                        }
                        
                        // Get student information
                        PreparedStatement studentStmt = con.prepareStatement(
                            "SELECT stu_rollno, stu_name FROM student_login WHERE stu_id = ?"
                        );
                        studentStmt.setInt(1, student_id);
                        ResultSet studentRs = studentStmt.executeQuery();
                        
                        if (studentRs.next()) {
                            int roll_no = studentRs.getInt("stu_rollno");
                            String stu_name = studentRs.getString("stu_name");
                            
                            // Insert quiz result
                            PreparedStatement pst = con.prepareStatement(
                                "INSERT INTO student_quiz_results (student_id, quiz_id, score) " +
                                "VALUES (?, ?, ?)"
                            );
                            pst.setInt(1, student_id);
                            pst.setInt(2, quizId);
                            pst.setInt(3, mark);
                            pst.executeUpdate();

                            // Update student's marks in student_login table
                            PreparedStatement updateStmt = con.prepareStatement(
                                "UPDATE student_login SET marks = ? WHERE stu_id = ?"
                            );
                            updateStmt.setInt(1, mark);
                            updateStmt.setInt(2, student_id);
                            updateStmt.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Quiz completed! Your score: " + mark + " out of " + totalQuestions);
                            dispose();
                            new StudentDashboard(student_id, roll_no, stu_name).setVisible(true);
                        } else {
                            throw new SQLException("Could not find student information");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error submitting quiz: " + ex.getMessage());
                    }
                }
            }
        });

        clr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g1.clearSelection();
            }
        });

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializeQuizDetails() {
        try {
            // Get time limit from quizzes table
            PreparedStatement pst = con.prepareStatement("SELECT time_limit FROM quizzes WHERE quiz_id = ?");
            pst.setInt(1, quizId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                timeLimit = rs.getInt("time_limit");
                if (totaltime_lbl1 != null) {
                    totaltime_lbl1.setText(timeLimit + " Min");
                }
            }

            // Calculate total questions from quiz_questions table
            PreparedStatement countPst = con.prepareStatement(
                "SELECT COUNT(*) as total FROM quiz_questions WHERE quiz_id = ?"
            );
            countPst.setInt(1, quizId);
            ResultSet countRs = countPst.executeQuery();
            if (countRs.next()) {
                totalQuestions = countRs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading quiz details: " + e.getMessage());
        }
    }

    private void loadQuestion(int questionNumber) {
        try {
            PreparedStatement pst = con.prepareStatement(
                "SELECT q.* FROM questions q " +
                "JOIN quiz_questions qq ON q.question_id = qq.question_id " +
                "WHERE qq.quiz_id = ? AND qq.que_no = ?"
            );
            pst.setInt(1, quizId);
            pst.setInt(2, questionNumber);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                j1.setText(rs.getString("question"));
                j2.setText("Q." + questionNumber);
                b1.setText(rs.getString("opt1"));
                b2.setText(rs.getString("opt2"));
                b3.setText(rs.getString("opt3"));
                b4.setText(rs.getString("opt4"));
                answer = rs.getString("answer");
                g1.clearSelection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading question: " + e.getMessage());
        }
    }

    public void submit(final int student_id)
    {

        sbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p1 = JOptionPane.showConfirmDialog(null,
                    "Do you really want to submit the exam?",
                    "Confirm Submission",
                    JOptionPane.YES_NO_OPTION);
                
                if(p1 == 0) {
                    try {
                        answercheck(que_id, answer);
                        Statement st = con.createStatement();
                        st.executeUpdate("update student_login set marks='"+mark+"' where stu_id="+student_id);
                        JOptionPane.showMessageDialog(null, "Your score: " + mark + " out of " + totalQuestions);
                        dispose();
                        new Welcome_page1().setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error submitting exam: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void submitQuiz(int student_id) {
        try {
            answercheck(que_id, answer);
            
            // First get student information
            PreparedStatement studentStmt = con.prepareStatement(
                "SELECT stu_rollno, stu_name FROM student_login WHERE stu_id = ?"
            );
            studentStmt.setInt(1, student_id);
            ResultSet studentRs = studentStmt.executeQuery();
            
            if (studentRs.next()) {
                int roll_no = studentRs.getInt("stu_rollno");
                String stu_name = studentRs.getString("stu_name");
                
                // Insert quiz result
                PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO student_quiz_results (student_id, quiz_id, score) " +
                    "VALUES (?, ?, ?)"
                );
                pst.setInt(1, student_id);
                pst.setInt(2, quizId);
                pst.setInt(3, mark);
                pst.executeUpdate();

                // Update student's marks in student_login table
                PreparedStatement updateStmt = con.prepareStatement(
                    "UPDATE student_login SET marks = ? WHERE stu_id = ?"
                );
                updateStmt.setInt(1, mark);
                updateStmt.setInt(2, student_id);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Quiz completed! Your score: " + mark + " out of " + totalQuestions);
                dispose();
                new StudentDashboard(student_id, roll_no, stu_name).setVisible(true);
            } else {
                throw new SQLException("Could not find student information");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting quiz: " + ex.getMessage());
        }
    }

    public void answercheck(int q_num, String ans)
    {
        String selected = "";
        if(b1.isSelected()) selected = b1.getText();
        else if(b2.isSelected()) selected = b2.getText();
        else if(b3.isSelected()) selected = b3.getText();
        else if(b4.isSelected()) selected = b4.getText();
        
        if(!selected.isEmpty() && selected.equals(ans)) {
            mark++;
        }

        if(q_num == totalQuestions)
            save_next.setVisible(false);
    }

}

public class Student_exampage {

    public static void main(String[] args) {

        int student_id=0,roll_no=10;
        String stu_name="";
        Student_exampage1 h1 = new Student_exampage1(student_id,roll_no,stu_name,0);
        h1.setSize(1000, 600);

    }
}