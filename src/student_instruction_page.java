import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class student_instruction_page1 extends JFrame {

    public int roll_no,student_id;
    public String stu_name;
    private int quiz_id;

    student_instruction_page1(int stuid,int rollno,String stuname, int quiz_id)
    {
        roll_no=rollno;
        student_id=stuid;
        stu_name=stuname;
        this.quiz_id = quiz_id;

        setTitle("Instruction Page");
        setLocation(380,120);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(255,204,229));
        Font fo = new Font("Times New Roman", Font.ITALIC, 40);
        Font fo1 = new Font("Times New Roman", Font.PLAIN, 30);
        Font fo2 = new Font("Times New Roman", Font.PLAIN, 18);


        ImageIcon i1 = new ImageIcon("Images/logo.png");
        setIconImage(i1.getImage());
        JLabel wl = new JLabel("Welcome ");
        wl.setBounds(210, 1, 160, 50);
        wl.setFont(fo);
        add(wl);

        JLabel stu_nm = new JLabel(stuname);
        stu_nm.setBounds(380, 1, 500, 50);
        stu_nm.setFont(fo);
        add(stu_nm);

        JLabel j2 = new JLabel("Instructions: ");
        j2.setBounds(20, 55, 200, 50);
        j2.setForeground(Color.RED);
        j2.setFont(fo1);
        add(j2);

        JLabel inst = new JLabel("");
        inst.setText(
                "<html>" +
                        "       1. All questions are compulsory." +"<br>"+"<br>"+
                        "       2. Try to submit the paper in 30 minutes." + "<br>"+"<br>" +
                        "       3. You are allowed to submit only once, make sure that you have correctly attempted all the questions before submission." + "<br>"+ "<br>" +
                        "       4. Make sure you clicked on submit button to successfully complete the test." + "<br>"+"<br>"+
                        "       5. New students must contact their class teacher for the admission number." + "<br>"+"<br>" +
                        "       6. or any difficulties while appearing the test please contact respective faculties."+"</html>"
        );
        inst.setFont(fo2);
        inst.setBounds(20, 55, 950, 450);
        add(inst);


        JButton back = new JButton("Back");
        back.setFont(fo2);
        back.setBackground(new Color(128,0,128));
        back.setForeground(Color.WHITE);
        back.setBounds(50, 500, 80, 25);
        add(back);

        JButton next = new JButton("Next");
        next.setFont(fo2);
        next.setBackground(new Color(128,0,128));
        next.setForeground(Color.WHITE);
        next.setBounds(850, 500, 80, 25);
        add(next);


        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Student_exampage1 h1 = new Student_exampage1(student_id, roll_no, stu_name, quiz_id);
                h1.setSize(1000, 600);
                h1.setVisible(true);
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Welcome_page1 w3 = null;
                try {
                    w3 = new Welcome_page1();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                w3.setSize(800, 600);
            }
        });


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

public class student_instruction_page {

    public static void main(String[] args) {

        int stuid=0,rollno=10;
        String stuname="";
        int quiz_id = 0;
        student_instruction_page1 i1 = new student_instruction_page1(stuid,rollno,stuname, quiz_id);
        i1.setSize(1000, 600);
    }
}



