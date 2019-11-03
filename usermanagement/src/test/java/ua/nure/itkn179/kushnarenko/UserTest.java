package ua.nure.itkn179.kushnarenko;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	
	private static final int YEAR_OF_BIRTH = 2000;
	private static final int CURRENT_YEAR = 2019;
	
	private static final int ETALONE_AGE1 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int MONTH_OF_BIRTH1 = Calendar.MAY;
	private static final int DAY_OF_BIRTH1 = 9;
	
	private static final int ETALONE_AGE2 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int MONTH_OF_BIRTH2 = Calendar.OCTOBER;
	private static final int DAY_OF_BIRTH2 = 1;
	
	private static final int ETALONE_AGE3 = CURRENT_YEAR - YEAR_OF_BIRTH -1;
	private static final int MONTH_OF_BIRTH3 = Calendar.NOVEMBER;
	private static final int DAY_OF_BIRTH3 = 30;
	
	private static final int ETALONE_AGE4 = CURRENT_YEAR - YEAR_OF_BIRTH -1;
	private static final int MONTH_OF_BIRTH4 = Calendar.DECEMBER;
	private static final int DAY_OF_BIRTH4 = 31;
	
	private static final int ETALONE_AGE5 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int MONTH_OF_BIRTH5 = Calendar.OCTOBER;
	private static final int DAY_OF_BIRTH5 = 8;
	
	
	private User user;
	private Date dateofBirth;

	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		user = new User();
	}
	
	public void testGetFullName() {
		  user.setFirstName("Den");
		  user.setLastName("Petrov");
		  assertEquals("Petrov, Den",user.getFullName());  
	}
	public void testGetAge1() { // Тест1 - день рождения прошел
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH1, DAY_OF_BIRTH1);
		dateofBirth = calendar.getTime();
		user.setDateofBirth(dateofBirth);
		assertEquals(ETALONE_AGE1,user.getAge());
	}
	public void testGetAge2() { // Тест2 - день рождения прошел, но еще месяц дня рождения
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH2, DAY_OF_BIRTH2);
		dateofBirth = calendar.getTime();
		user.setDateofBirth(dateofBirth);
		assertEquals(ETALONE_AGE2,user.getAge());
	}
	public void testGetAge3() { // Тест3 - день рождения будет в этом месяце
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH3, DAY_OF_BIRTH3);
		dateofBirth = calendar.getTime();
		user.setDateofBirth(dateofBirth);
		assertEquals(ETALONE_AGE3,user.getAge());
	}
	public void testGetAge4() { // Тест4 - день рождения будет не в этом месяце
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH4, DAY_OF_BIRTH4);
		dateofBirth = calendar.getTime();
		user.setDateofBirth(dateofBirth);
		assertEquals(ETALONE_AGE4,user.getAge());
	}
	public void testGetAge5() { // Тест5 - день рождения сегодня(08.10.2019)
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH5, DAY_OF_BIRTH5);
		dateofBirth = calendar.getTime();
		user.setDateofBirth(dateofBirth);
		assertEquals(ETALONE_AGE5,user.getAge());
	}

}

