package ua.nure.itkn179.kushnarenko;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = -3415538824579544066L;
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateofBirth;
	
	public User() {
    }
	
	public User(Long id, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateofBirth = dateOfBirth;
    }
	
	public User(String firstName, String lastName, Date now) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateofBirth = now;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getFullName() {
		return getLastName()+", "+getFirstName();
	}
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(getDateofBirth());
		int birthYear = calendar.get(Calendar.YEAR);
		int birthMonth = calendar.get(Calendar.MONTH);
        int birthDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        int age = currentYear - birthYear;
        if((currentMonth<birthMonth)||(currentMonth==birthMonth&&currentDay<birthDay)) {
        	age--;
        }
		
		return age;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getId() == null && ((User) obj).getId() == null) {
			return true;
		}

		return this.getId().equals(((User) obj).getId());
	}

	public int hashCode() {
		if (this.getId() == null) {
			return 0;
		}
		return this.getId().hashCode();
	}
}
