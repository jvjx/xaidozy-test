package top.dozy.test;

public class Duotai {
	static class Person {
		void speak() {
			System.out.println("大家好，我是一个人。");
		}
	}

		void sayHello() {
			System.out.println("你好，我是人。");
		}
	}

	static class Student extends Person {
		@Override
		void sayHello() {
			System.out.println("你好，我是学生。");
		}
	}

	static class Teacher extends Person {
		@Override
		void sayHello() {
			System.out.println("你好，我是教师。");
		}
	}

	static class Worker extends Person {
		@Override
		void sayHello() {
			System.out.println("你好，我是工人。");
		}
	}

	public static void main(String[] args) {
		Person p1 = new Student();
		Person p2 = new Teacher();
		Person p3 = new Worker();

		p1.sayHello();
		p2.sayHello();
		p3.sayHello();
	}
}
