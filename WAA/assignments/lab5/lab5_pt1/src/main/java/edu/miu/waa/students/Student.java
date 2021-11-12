package edu.miu.waa.students;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
	@Id
	private int studentNumber;
	private String name;
	private String phone;
	private String email;
	private Address address;

	public Student(int studentNumber, String name, String phone, String email) {
		this.studentNumber = studentNumber;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
}