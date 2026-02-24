package top.dozy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseManager {

    static class Course {
        String lesson;
        String time;
        String classroom;

        public Course(String lesson, String time, String classroom) {
            this.lesson = lesson;
            this.time = time;
            this.classroom = classroom;
        }

        @Override
        public String toString() {
            return lesson + " - " + time + " - " + classroom;
        }
    }

    public static void main(String[] args) {
        List<Course> courses = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===星火课程管理===");

        while (true) {
            System.out.println("\n--- 菜单 ---");
            System.out.println("1. 添加课程");
            System.out.println("2. 查看所有课程");
            System.out.println("3. 查找课程");
            System.out.println("4. 退出");
            System.out.print("请输入选择: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addCourse(scanner, courses);
                    break;
                case "2":
                    viewAllCourses(courses);
                    break;
                case "3":
                    findCourse(scanner, courses);
                    break;
                case "4":
                    System.out.println("程序已退出。");
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private static void addCourse(Scanner scanner, List<Course> courses) {
        try {
            System.out.print("请输入课程名称: ");
            String lesson = scanner.nextLine();
            
            if (lesson.trim().isEmpty()) {
                throw new Exception("课程名称不能为空！");
            }

            System.out.print("请输入课程时间: ");
            String time = scanner.nextLine();

            System.out.print("请输入课程教室: ");
            String classroom = scanner.nextLine();

            Course newCourse = new Course(lesson, time, classroom);
            courses.add(newCourse);
            System.out.println("成功添加课程！");

        } catch (Exception e) {
            System.out.println("错误：" + e.getMessage());
            System.out.println("请重新添加课程。");
        }
    }

    private static void viewAllCourses(List<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("暂无课程。");
        } else {
            System.out.println("--- 课程列表 ---");
            for (Course c : courses) {
                System.out.println(c.toString());
            }
        }
    }

    private static void findCourse(Scanner scanner, List<Course> courses) {
        System.out.print("请输入要查找的课程名称(搜索关键字): ");
        String target = scanner.nextLine();
        
        boolean found = false;
        for (Course c : courses) {
            if (c.lesson.contains(target)) { 
                System.out.println("找到课程: " + c.toString());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("未找到该课程。");
        }
    }
}
