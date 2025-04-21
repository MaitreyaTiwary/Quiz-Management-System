import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.DefaultCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class Teacher_homepage1 extends JFrame {
    Connection con = null;
    JButton b1, b2, b3, b4, b5, b6, b7;
    JTextField Que_no, Question, opt1, opt2, opt3, opt4, ans, Que_num, quiz_name, quiz_id;
    JPanel p1, p2, p3, p4, p5, p6, p7;
    private JComboBox<String> difficultyCombo;
    private JComboBox<String> subjectCombo;

    Teacher_homepage1(int tea_id, String tea_name) {
        con = (Connection) Db.dbconnect();
        setTitle("Teacher Homepage");
        setLocation(280, 120);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.lightGray);
        Font fo = new Font("Times New Roman", Font.ITALIC, 40);
        Font fo1 = new Font("Times New Roman", Font.PLAIN, 30);
        Font fo2 = new Font("Times New Roman", Font.PLAIN, 18);
        Font fo3 = new Font("Sitka Display", Font.BOLD, 15);

        // Profile Panel
        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 0, 250, 560);
        panel1.setLayout(null);
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel1.setBackground(new Color(127, 255, 212));
        add(panel1);

        JLabel prof_img = new JLabel();
        prof_img.setIcon(new ImageIcon("Images/teacher.png"));
        prof_img.setBounds(10, 10, 250, 100);
        prof_img.setHorizontalAlignment(JLabel.CENTER);
        panel1.add(prof_img);

        JLabel pan_name = new JLabel("Name   :");
        pan_name.setFont(fo3);
        pan_name.setBounds(5, 120, 80, 30);
        panel1.add(pan_name);

        JLabel prof_name = new JLabel(tea_name);
        prof_name.setFont(fo3);
        prof_name.setBounds(75, 120, 215, 30);
        panel1.add(prof_name);

        JLabel pan_roll = new JLabel("ID Num:");
        pan_roll.setFont(fo3);
        pan_roll.setBounds(5, 160, 80, 30);
        panel1.add(pan_roll);

        JLabel prof_roll = new JLabel(String.valueOf(tea_id));
        prof_roll.setFont(fo3);
        prof_roll.setBounds(75, 160, 215, 30);
        panel1.add(prof_roll);

        // Initialize panels for tabs
        p1 = new JPanel(); // Available Quizzes
        p1.setLayout(null);
        p1.setBackground(new Color(255, 182, 193));

        p2 = new JPanel(); // Create Quiz
        p2.setLayout(null);
        p2.setBackground(new Color(238, 232, 170));

        p3 = new JPanel(); // Create Questions
        p3.setBackground(new Color(152, 251, 152));
        p3.setLayout(null);
        p3.setPreferredSize(new Dimension(500, 600));

        p4 = new JPanel(); // Delete Quiz
        p4.setBackground(new Color(255, 228, 225));
        p4.setLayout(null);

        p5 = new JPanel(); // Delete Questions
        p5.setBackground(new Color(230, 230, 250));
        p5.setLayout(null);

        p6 = new JPanel(); // Student Information
        p6.setBackground(new Color(255, 204, 229));
        p6.setLayout(null);

        p7 = new JPanel(); // Question Bank
        p7.setBackground(new Color(230, 230, 250));
        p7.setLayout(null);

        // Tabbed Pane
        JTabbedPane tp = new JTabbedPane();
        tp.setBounds(251, 0, 530, 560);
        tp.add("Available Quizzes", p1);
        tp.add("Create Quiz", p2);
        tp.add("Create Questions", p3);
        tp.add("Student Information", p6);
        tp.add("Question Bank", p7);
        add(tp);

        // Available Quizzes Tab
        b1 = new JButton("Show Available Quizzes");
        b1.setBounds(30, 30, 200, 40);
        p1.add(b1);

        // Create Quiz Tab
        JLabel quizNameLabel = new JLabel("Quiz Name:");
        quizNameLabel.setBounds(50, 40, 120, 40);
        p2.add(quizNameLabel);

        quiz_name = new JTextField();
        quiz_name.setBounds(190, 40, 180, 40);
        p2.add(quiz_name);

        b2 = new JButton("Create Quiz");
        b2.setBounds(190, 100, 120, 40);
        p2.add(b2);

        // Create Questions Tab
        JLabel l1 = new JLabel("Question:");
        l1.setBounds(50, 40, 120, 40);
        p3.add(l1);

        Question = new JTextField();
        Question.setBounds(190, 40, 180, 40);
        p3.add(Question);

        JLabel o1 = new JLabel("Option 1:");
        o1.setBounds(50, 100, 120, 40);
        p3.add(o1);

        opt1 = new JTextField();
        opt1.setBounds(190, 100, 180, 40);
        p3.add(opt1);

        JLabel o2 = new JLabel("Option 2:");
        o2.setBounds(50, 160, 120, 40);
        p3.add(o2);

        opt2 = new JTextField();
        opt2.setBounds(190, 160, 180, 40);
        p3.add(opt2);

        JLabel o3 = new JLabel("Option 3:");
        o3.setBounds(50, 220, 120, 40);
        p3.add(o3);

        opt3 = new JTextField();
        opt3.setBounds(190, 220, 180, 40);
        p3.add(opt3);

        JLabel o4 = new JLabel("Option 4:");
        o4.setBounds(50, 280, 120, 40);
        p3.add(o4);

        opt4 = new JTextField();
        opt4.setBounds(190, 280, 180, 40);
        p3.add(opt4);

        JLabel Answer = new JLabel("Correct Answer:");
        Answer.setBounds(50, 340, 120, 40);
        p3.add(Answer);

        ans = new JTextField();
        ans.setBounds(190, 340, 180, 40);
        p3.add(ans);

        // Add Difficulty and Subject fields
        JLabel diffLabel = new JLabel("Difficulty Level:");
        diffLabel.setBounds(50, 400, 120, 40);
        p3.add(diffLabel);

        difficultyCombo = new JComboBox<>(new String[]{"easy", "medium", "hard"});
        difficultyCombo.setBounds(190, 400, 180, 40);
        p3.add(difficultyCombo);

        JLabel subjLabel = new JLabel("Subject:");
        subjLabel.setBounds(50, 460, 120, 40);
        p3.add(subjLabel);

        subjectCombo = new JComboBox<>(new String[]{"english", "maths", "science"});
        subjectCombo.setBounds(190, 460, 180, 40);
        p3.add(subjectCombo);

        // Add a save button with updated position
        JButton saveButton = new JButton("Save Question");
        saveButton.setBounds(190, 520, 180, 40);
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        p3.add(saveButton);

        // Add scroll pane to handle the increased size
        JScrollPane scrollPane = new JScrollPane(p3);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tp.add("Create Questions", scrollPane);

        // Student Information Tab
        b6 = new JButton("Show Student Info");
        b6.setBounds(30, 30, 160, 40);
        p6.add(b6);

        // Question Bank Tab
        JButton showQuestionsButton = new JButton("Show Question Bank");
        showQuestionsButton.setBounds(30, 30, 200, 40);
        p7.add(showQuestionsButton);

        // Action Listeners
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Remove any existing table and scroll pane
                    Component[] components = p1.getComponents();
                    for (Component component : components) {
                        if (component instanceof ScrollPane) {
                            p1.remove(component);
                        }
                    }
                    p1.revalidate();
                    p1.repaint();

                    String[] ColName = {"Quiz ID", "Quiz Name", "Created By", "Created At", "Questions Count", "Actions"};
                    final DefaultTableModel model = new DefaultTableModel(ColName, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return column == 5; // Only allow editing of the Actions column
                        }
                    };
                    final JTable table = new JTable(model);
                    table.setBounds(30, 100, 400, 200);
                    table.setRowHeight(30);
                    table.setOpaque(true);
                    JTableHeader header = table.getTableHeader();
                    header.setOpaque(true);
                    header.setBackground(Color.gray);
                    table.setBorder((BorderFactory.createLineBorder(Color.BLACK, 1)));

                    // Set up the delete button column
                    table.getColumnModel().getColumn(5).setCellRenderer(new TeacherButtonRenderer());
                    table.getColumnModel().getColumn(5).setCellEditor(
                        new TeacherButtonEditor(new JCheckBox(), table, model, con, tea_id) {
                            @Override
                            public Object getCellEditorValue() {
                                if (isPushed) {
                                    int row = table.getSelectedRow();
                                    int quizId = Integer.parseInt(table.getValueAt(row, 0).toString());
                                    
                                    try {
                                        // Check if this quiz belongs to the current teacher
                                        PreparedStatement checkStmt = con.prepareStatement(
                                            "SELECT created_by FROM quizzes WHERE quiz_id = ?");
                                        checkStmt.setInt(1, quizId);
                                        ResultSet rs = checkStmt.executeQuery();
                                        
                                        if (rs.next()) {
                                            int createdBy = rs.getInt("created_by");
                                            if (createdBy != teacherId) {
                                                JOptionPane.showMessageDialog(button, 
                                                    "You can only delete quizzes that you created!", 
                                                    "Permission Denied", 
                                                    JOptionPane.WARNING_MESSAGE);
                                                return label;
                                            }
                                        }

                                        // Check if any students have taken this quiz
                                        PreparedStatement resultCheckStmt = con.prepareStatement(
                                            "SELECT COUNT(*) as count FROM student_quiz_results WHERE quiz_id = ?");
                                        resultCheckStmt.setInt(1, quizId);
                                        rs = resultCheckStmt.executeQuery();
                                        
                                        if (rs.next() && rs.getInt("count") > 0) {
                                            JOptionPane.showMessageDialog(button, 
                                                "This quiz cannot be deleted as it has been attempted by students!", 
                                                "Cannot Delete", 
                                                JOptionPane.WARNING_MESSAGE);
                                            return label;
                                        }

                                        // If all checks pass, confirm deletion
                                        int confirm = JOptionPane.showConfirmDialog(button, 
                                            "Are you sure you want to delete this quiz? The questions will remain in the question bank.", 
                                            "Confirm Deletion", 
                                            JOptionPane.YES_NO_OPTION);
                                        
                                        if (confirm == JOptionPane.YES_OPTION) {
                                            // First delete the quiz-question associations
                                            PreparedStatement deleteAssocStmt = con.prepareStatement(
                                                "DELETE FROM quiz_questions WHERE quiz_id = ?");
                                            deleteAssocStmt.setInt(1, quizId);
                                            deleteAssocStmt.executeUpdate();

                                            // Then delete the quiz
                                            PreparedStatement deleteStmt = con.prepareStatement(
                                                "DELETE FROM quizzes WHERE quiz_id = ?");
                                            deleteStmt.setInt(1, quizId);
                                            deleteStmt.executeUpdate();
                                            
                                            model.removeRow(row);
                                            JOptionPane.showMessageDialog(button, "Quiz deleted successfully!");
                                        }
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(button, 
                                            "Error deleting quiz: " + ex.getMessage(), 
                                            "Error", 
                                            JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                isPushed = false;
                                return label;
                            }
                        });

                    ScrollPane s = new ScrollPane();
                    s.setBounds(10, 100, 500, 300);
                    s.add(table);
                    p1.add(s);

                    // Get quizzes with question counts
                    String sql = "SELECT q.quiz_id, q.quiz_name, t.teach_name, q.created_at, " +
                               "COUNT(qq.question_id) as question_count, q.created_by " +
                               "FROM quizzes q " +
                               "LEFT JOIN teacher_login t ON q.created_by = t.teach_id " +
                               "LEFT JOIN quiz_questions qq ON q.quiz_id = qq.quiz_id " +
                               "GROUP BY q.quiz_id, q.quiz_name, t.teach_name, q.created_at";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    ResultSet r = pst.executeQuery();

                    while (r.next()) {
                        String id = String.valueOf(r.getInt("quiz_id"));
                        String name = r.getString("quiz_name");
                        String teacher = r.getString("teach_name");
                        String createdAt = r.getTimestamp("created_at").toString();
                        String count = String.valueOf(r.getInt("question_count"));
                        String deleteButton = "Delete";
                        String tbData[] = {id, name, teacher, createdAt, count, deleteButton};
                        model.addRow(tbData);
                    }

                } catch (Exception d) {
                    System.out.println(d);
                    JOptionPane.showMessageDialog(null, "Error displaying quizzes: " + d.getMessage());
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quiz_name.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a quiz name!");
                    return;
                }
                
                QuizDetailsDialog dialog = new QuizDetailsDialog(Teacher_homepage1.this, 
                    quiz_name.getText(), tea_id, con);
                dialog.setVisible(true);
                quiz_name.setText("");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this question?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == 0) {
                    try {
                        // Validate question fields
                        if (Question.getText().trim().isEmpty() || 
                            opt1.getText().trim().isEmpty() || 
                            opt2.getText().trim().isEmpty() || 
                            opt3.getText().trim().isEmpty() || 
                            opt4.getText().trim().isEmpty() || 
                            ans.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all question fields!");
                            return;
                        }

                        String sql = "INSERT INTO questions (question, opt1, opt2, opt3, opt4, answer, difficulty, subject, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                        pst.setString(1, Question.getText());
                        pst.setString(2, opt1.getText());
                        pst.setString(3, opt2.getText());
                        pst.setString(4, opt3.getText());
                        pst.setString(5, opt4.getText());
                        pst.setString(6, ans.getText());
                        pst.setString(7, (String) difficultyCombo.getSelectedItem());
                        pst.setString(8, (String) subjectCombo.getSelectedItem());
                        pst.setInt(9, tea_id);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Question added to question bank successfully!");
                        clearFields();
                    } catch (Exception e1) {
                        System.out.println(e1);
                        JOptionPane.showMessageDialog(null, "Error adding question: " + e1.getMessage());
                    }
                }
            }
        });

        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Remove any existing table and scroll pane
                    Component[] components = p6.getComponents();
                    for (Component component : components) {
                        if (component instanceof ScrollPane) {
                            p6.remove(component);
                        }
                    }
                    p6.revalidate();
                    p6.repaint();

                    String[] ColName = {"S.No.", "Roll No.", "Student Name", "Tests Attempted", "Average Score"};
                    final DefaultTableModel model = new DefaultTableModel(ColName, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false; // Make all cells non-editable
                        }
                    };
                    final JTable table = new JTable(model);
                    table.setBounds(30, 100, 400, 200);
                    table.setRowHeight(30);
                    
                    // Set column widths
                    table.getColumnModel().getColumn(0).setPreferredWidth(60);  // S.No.
                    table.getColumnModel().getColumn(1).setPreferredWidth(80);  // Roll No.
                    table.getColumnModel().getColumn(2).setPreferredWidth(150); // Name
                    table.getColumnModel().getColumn(3).setPreferredWidth(100); // Tests Attempted
                    table.getColumnModel().getColumn(4).setPreferredWidth(100); // Average Score
                    
                    table.setOpaque(true);
                    JTableHeader header = table.getTableHeader();
                    header.setOpaque(true);
                    header.setBackground(Color.gray);
                    table.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                    // Query to get student information with test statistics
                    String sql = 
                        "SELECT s.stu_rollno, s.stu_name, " +
                        "COALESCE(stats.tests_attempted, 0) as tests_attempted, " +
                        "COALESCE(stats.avg_score, 0) as avg_score " +
                        "FROM student_login s " +
                        "LEFT JOIN (" +
                        "    SELECT student_id, " +
                        "           COUNT(DISTINCT quiz_id) as tests_attempted, " +
                        "           ROUND(AVG(CAST(score AS DECIMAL(10,2))), 2) as avg_score " +
                        "    FROM student_quiz_results " +
                        "    GROUP BY student_id" +
                        ") stats ON s.stu_id = stats.student_id " +
                        "ORDER BY s.stu_rollno";

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    
                    int serialNo = 1;
                    while (rs.next()) {
                        String rollNo = String.valueOf(rs.getInt("stu_rollno"));
                        String name = rs.getString("stu_name");
                        String testsAttempted = String.valueOf(rs.getInt("tests_attempted"));
                        String avgScore = String.format("%.2f", rs.getDouble("avg_score"));
                        
                        model.addRow(new Object[]{
                            serialNo++,
                            rollNo,
                            name,
                            testsAttempted,
                            avgScore
                        });
                    }

                    // Create scroll pane with proper size
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setBounds(30, 100, 450, 300);
                    p6.add(scrollPane);
                    p6.revalidate();
                    p6.repaint();

                } catch (Exception d) {
                    System.out.println(d);
                    JOptionPane.showMessageDialog(null, "Error displaying student information: " + d.getMessage());
                }
            }
        });

        showQuestionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Remove any existing table and scroll pane
                    Component[] components = p7.getComponents();
                    for (Component component : components) {
                        p7.remove(component);
                    }
                    p7.revalidate();
                    p7.repaint();

                    String[] ColName = {"Question ID", "Subject", "Difficulty", "Question", "Actions"};
                    final DefaultTableModel model = new DefaultTableModel(ColName, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return column == 4; // Only allow editing of the Actions column
                        }
                    };
                    final JTable table = new JTable(model);
                    table.setRowHeight(30);
                    table.setOpaque(true);
                    JTableHeader header = table.getTableHeader();
                    header.setOpaque(true);
                    header.setBackground(Color.gray);
                    table.setBorder((BorderFactory.createLineBorder(Color.BLACK, 1)));

                    // Set column widths for better readability
                    TableColumnModel columnModel = table.getColumnModel();
                    columnModel.getColumn(0).setPreferredWidth(80);  // Question ID
                    columnModel.getColumn(1).setPreferredWidth(100); // Subject
                    columnModel.getColumn(2).setPreferredWidth(100); // Difficulty
                    columnModel.getColumn(3).setPreferredWidth(300); // Question
                    columnModel.getColumn(4).setPreferredWidth(80);  // Actions

                    // Set up the delete button column
                    table.getColumnModel().getColumn(4).setCellRenderer(new TeacherButtonRenderer());
                    table.getColumnModel().getColumn(4).setCellEditor(new TeacherButtonEditor(new JCheckBox(), table, model, con, tea_id));

                    // Create a scroll pane with proper settings
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setBounds(10, 100, 500, 300);
                    p7.add(scrollPane);

                    // Add a panel to hold the button and table
                    JPanel mainPanel = new JPanel();
                    mainPanel.setLayout(new BorderLayout());
                    mainPanel.add(showQuestionsButton, BorderLayout.NORTH);
                    mainPanel.add(scrollPane, BorderLayout.CENTER);
                    mainPanel.setBounds(0, 0, 520, 400);
                    p7.add(mainPanel);

                    String sql = "SELECT question_id, subject, difficulty, question, created_by " +
                               "FROM questions ORDER BY subject, difficulty, question_id";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    ResultSet r = pst.executeQuery();

                    while (r.next()) {
                        String id = String.valueOf(r.getInt("question_id"));
                        String subject = r.getString("subject");
                        String difficulty = r.getString("difficulty");
                        String question = r.getString("question");
                        String deleteButton = "Delete";
                        String tbData[] = {id, subject, difficulty, question, deleteButton};
                        model.addRow(tbData);
                    }

                    // Force the panel to update its layout
                    p7.revalidate();
                    p7.repaint();

                } catch (Exception d) {
                    System.out.println(d);
                    JOptionPane.showMessageDialog(null, "Error displaying questions: " + d.getMessage());
                }
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void clearFields() {
        Question.setText("");
        opt1.setText("");
        opt2.setText("");
        opt3.setText("");
        opt4.setText("");
        ans.setText("");
        difficultyCombo.setSelectedIndex(0);
        subjectCombo.setSelectedIndex(0);
    }
}

class QuizDetailsDialog extends JDialog {
    private JTextField timeLimitField;
    private JTextField totalMarksField;
    private JTable questionBankTable;
    private DefaultTableModel questionModel;
    private List<Integer> selectedQuestionIds;
    private int quizId;
    private int teacherId;
    private Connection con;

    public QuizDetailsDialog(JFrame parent, String quizName, int teacherId, Connection con) {
        super(parent, "Quiz Details: " + quizName, true);
        this.con = con;
        this.teacherId = teacherId;
        this.selectedQuestionIds = new ArrayList<>();
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Create the quiz first
        try {
            String sql = "insert into quizzes (quiz_name, created_by) values (?, ?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, quizName);
            pst.setInt(2, teacherId);
            pst.executeUpdate();
            
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                this.quizId = rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating quiz: " + e.getMessage());
            dispose();
            return;
        }

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel for basic quiz details
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.add(new JLabel("Time Limit (minutes):"));
        timeLimitField = new JTextField();
        topPanel.add(timeLimitField);
        topPanel.add(new JLabel("Total Marks:"));
        totalMarksField = new JTextField();
        topPanel.add(totalMarksField);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for question bank
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JLabel("Available Questions:"), BorderLayout.NORTH);
        
        String[] columnNames = {"Select", "Question ID", "Question", "Options"};
        questionModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
        };
        
        questionBankTable = new JTable(questionModel);
        questionBankTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        questionBankTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        questionBankTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        questionBankTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        
        JScrollPane scrollPane = new JScrollPane(questionBankTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addNewQuestionButton = new JButton("Add New Question");
        JButton saveButton = new JButton("Save Quiz");
        bottomPanel.add(addNewQuestionButton);
        bottomPanel.add(saveButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load existing questions
        loadQuestions();

        // Add action listeners
        addNewQuestionButton.addActionListener(e -> {
            new QuestionDialog(this, quizId, teacherId, con).setVisible(true);
            loadQuestions(); // Refresh the question list
        });

        saveButton.addActionListener(e -> {
            try {
                // Get selected questions
                List<Integer> selectedQuestionIds = new ArrayList<>();
                for (int i = 0; i < questionModel.getRowCount(); i++) {
                    if ((Boolean) questionModel.getValueAt(i, 0)) {
                        selectedQuestionIds.add(Integer.parseInt(questionModel.getValueAt(i, 1).toString()));
                    }
                }

                if (selectedQuestionIds.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select at least one question for the quiz!");
                    return;
                }

                // Update quiz with time limit and total marks
                String updateSql = "UPDATE quizzes SET time_limit = ?, total_marks = ? WHERE quiz_id = ?";
                PreparedStatement pst = con.prepareStatement(updateSql);
                pst.setInt(1, Integer.parseInt(timeLimitField.getText()));
                pst.setInt(2, Integer.parseInt(totalMarksField.getText()));
                pst.setInt(3, quizId);
                pst.executeUpdate();

                // Add selected questions to the quiz
                String insertQuizQuestionSql = "INSERT INTO quiz_questions (quiz_id, question_id, que_no) VALUES (?, ?, ?)";
                pst = con.prepareStatement(insertQuizQuestionSql);
                
                int queNo = 1;
                for (int questionId : selectedQuestionIds) {
                    pst.setInt(1, quizId);
                    pst.setInt(2, questionId);
                    pst.setInt(3, queNo++);
                    pst.addBatch();
                }
                pst.executeBatch();

                JOptionPane.showMessageDialog(this, "Quiz saved successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving quiz: " + ex.getMessage());
            }
        });
    }

    private void loadQuestions() {
        try {
            questionModel.setRowCount(0); // Clear existing rows
            
            String sql = "SELECT q.question_id, q.question, q.opt1, q.opt2, q.opt3, q.opt4, q.answer " +
                       "FROM questions q " +
                       "WHERE NOT EXISTS (SELECT 1 FROM quiz_questions qq WHERE qq.question_id = q.question_id AND qq.quiz_id = ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, quizId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String question = rs.getString("question");
                String options = rs.getString("opt1") + ", " + rs.getString("opt2") + ", " + 
                               rs.getString("opt3") + ", " + rs.getString("opt4");
                questionModel.addRow(new Object[]{false, rs.getInt("question_id"), question, options});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage());
        }
    }
}

class QuestionDialog extends JDialog {
    private JTextField questionField;
    private JTextField[] optionFields;
    private JTextField answerField;
    private JComboBox<String> difficultyComboBox;
    private JComboBox<String> subjectComboBox;
    private int quizId;
    private int teacherId;
    private Connection con;

    public QuestionDialog(JDialog parent, int quizId, int teacherId, Connection con) {
        super(parent, "Add New Question", true);
        this.quizId = quizId;
        this.teacherId = teacherId;
        this.con = con;
        
        setSize(500, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(10, 2, 10, 10));

        // Add components
        add(new JLabel("Question:"));
        questionField = new JTextField();
        add(questionField);

        optionFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            add(new JLabel("Option " + (i + 1) + ":"));
            optionFields[i] = new JTextField();
            add(optionFields[i]);
        }

        add(new JLabel("Correct Answer:"));
        answerField = new JTextField();
        add(answerField);

        add(new JLabel("Difficulty:"));
        difficultyComboBox = new JComboBox<>(new String[]{"easy", "medium", "hard"});
        add(difficultyComboBox);

        add(new JLabel("Subject:"));
        subjectComboBox = new JComboBox<>(new String[]{"english", "maths", "science"});
        add(subjectComboBox);

        JButton saveButton = new JButton("Save Question");
        add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                String sql = "INSERT INTO questions (question, opt1, opt2, opt3, opt4, answer, difficulty, subject, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, questionField.getText());
                pst.setString(2, optionFields[0].getText());
                pst.setString(3, optionFields[1].getText());
                pst.setString(4, optionFields[2].getText());
                pst.setString(5, optionFields[3].getText());
                pst.setString(6, answerField.getText());
                pst.setString(7, (String) difficultyComboBox.getSelectedItem());
                pst.setString(8, (String) subjectComboBox.getSelectedItem());
                pst.setInt(9, teacherId);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Question added successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding question: " + ex.getMessage());
            }
        });
    }
}

// Add these new classes for the delete button functionality
class TeacherButtonRenderer extends JButton implements TableCellRenderer {
    public TeacherButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class TeacherButtonEditor extends DefaultCellEditor {
    protected JButton button;
    protected String label;
    protected boolean isPushed;
    protected JTable table;
    protected DefaultTableModel model;
    protected Connection con;
    protected int teacherId;

    public TeacherButtonEditor(JCheckBox checkBox, JTable table, DefaultTableModel model, Connection con, int teacherId) {
        super(checkBox);
        this.table = table;
        this.model = model;
        this.con = con;
        this.teacherId = teacherId;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();
            if (row < 0 || row >= model.getRowCount()) {
                return label;
            }

            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            
            try {
                // First check if this item belongs to the current teacher
                PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT created_by FROM " + (isQuizTable() ? "quizzes" : "questions") + " WHERE " + 
                    (isQuizTable() ? "quiz_id" : "question_id") + " = ?");
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next()) {
                    int createdBy = rs.getInt("created_by");
                    if (createdBy != teacherId) {
                        JOptionPane.showMessageDialog(button, 
                            "You can only delete items that you created!", 
                            "Permission Denied", 
                            JOptionPane.WARNING_MESSAGE);
                        return label;
                    }
                }

                // Additional checks based on type
                if (isQuizTable()) {
                    // Check if any students have taken this quiz
                    PreparedStatement resultCheckStmt = con.prepareStatement(
                        "SELECT COUNT(*) as count FROM student_quiz_results WHERE quiz_id = ?");
                    resultCheckStmt.setInt(1, id);
                    rs = resultCheckStmt.executeQuery();
                    
                    if (rs.next() && rs.getInt("count") > 0) {
                        JOptionPane.showMessageDialog(button, 
                            "This quiz cannot be deleted as it has been attempted by students!", 
                            "Cannot Delete", 
                            JOptionPane.WARNING_MESSAGE);
                        return label;
                    }
                } else {
                    // Check if the question is used in any quiz
                    PreparedStatement quizCheckStmt = con.prepareStatement(
                        "SELECT COUNT(*) as count FROM quiz_questions WHERE question_id = ?");
                    quizCheckStmt.setInt(1, id);
                    rs = quizCheckStmt.executeQuery();
                    
                    if (rs.next() && rs.getInt("count") > 0) {
                        JOptionPane.showMessageDialog(button, 
                            "This question cannot be deleted as it is being used in one or more quizzes!", 
                            "Cannot Delete", 
                            JOptionPane.WARNING_MESSAGE);
                        return label;
                    }
                }

                // If all checks pass, confirm deletion
                String confirmMessage = isQuizTable() ? 
                    "Are you sure you want to delete this quiz? The questions will remain in the question bank." :
                    "Are you sure you want to delete this question?";
                
                int confirm = JOptionPane.showConfirmDialog(button, 
                    confirmMessage, 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (isQuizTable()) {
                        // First delete the quiz-question associations
                        PreparedStatement deleteAssocStmt = con.prepareStatement(
                            "DELETE FROM quiz_questions WHERE quiz_id = ?");
                        deleteAssocStmt.setInt(1, id);
                        deleteAssocStmt.executeUpdate();
                    }

                    // Delete the item
                    PreparedStatement deleteStmt = con.prepareStatement(
                        "DELETE FROM " + (isQuizTable() ? "quizzes" : "questions") + 
                        " WHERE " + (isQuizTable() ? "quiz_id" : "question_id") + " = ?");
                    deleteStmt.setInt(1, id);
                    deleteStmt.executeUpdate();

                    // Remove the row and force table refresh
                    SwingUtilities.invokeLater(() -> {
                        model.removeRow(row);
                        table.clearSelection();
                        table.repaint();
                    });
                    
                    JOptionPane.showMessageDialog(button, 
                        (isQuizTable() ? "Quiz" : "Question") + " deleted successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(button, 
                    "Error deleting " + (isQuizTable() ? "quiz" : "question") + ": " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        isPushed = false;
        return label;
    }

    private boolean isQuizTable() {
        return table.getColumnCount() == 6; // Quiz table has 6 columns
    }
}

public class Teacher_homepage {
    public static void main(String[] args) {
        int tea_id = 10;
        String tea_name = "";
        Teacher_homepage1 t1 = new Teacher_homepage1(tea_id, tea_name);
        t1.setSize(800, 600);
    }
}