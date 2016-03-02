package it.polito.po.test;
import junit.framework.TestCase;
import applications.*;

import java.util.*;

public class TestReq5 extends TestCase {
	HandleApplications ha ;
	protected void setUp() throws Exception {
		super.setUp();
		ha = new HandleApplications();
		try{
			ha.addSkills("java", "pascal", "javascript", "sql", "html", "css", "xml", "sw design");
			ha.addPosition("developer", "pascal", "css");
			ha.addPosition("team leader", "pascal", "css");
			ha.addPosition("database expert", "pascal", "sql");
			ha.addPosition("junior developer", "pascal", "css");
			ha.addPosition("senior developer", "pascal", "css");
			
			ha.addApplicant("albert", "pascal:9,sql:7,css:5");
			ha.addApplicant("bob", "pascal:9,sql:7,xml:5");
			ha.addApplicant("robert", "pascal:9,sql:7,xml:5");
			ha.addApplicant("alfred", "pascal:9,sql:7,css:5");
			ha.addApplicant("ted", "pascal:5,sql:7,css:5");
			ha.addApplicant("mary", "pascal:9,sql:7,css:5");
			
			ha.enterApplication("albert", "developer"); 
			ha.enterApplication("bob", "database expert");
			ha.enterApplication("robert", "database expert");
			ha.enterApplication("ted", "junior developer");
			ha.enterApplication("mary", "team leader");
			
		}catch(Exception e){
			fail("no exception must be thrown");
		}
	}
	
	public void testSkill_nApplicants() {
		Map<String,Long> map = ha.skill_nApplicants();
		assertNotNull(map );
		System.out.println(map); //{css=4, pascal=6, sql=6, xml=2} or the one with 0s
        assertTrue("The map should contain 4 or 8 elements",map.size() == 4 || map.size()==8);
	}	
	public void testSkill_nApplicantsT2() {
		SortedMap<String,Long> map = ha.skill_nApplicants();
		assertNotNull(map );
        assertTrue("The map should contain 4 or 8 elements",map.size() == 4 || map.size()==8);
		assertEquals("css",map.firstKey());
	}
	public void testSkill_nApplicantsT3() {
		SortedMap<String,Long> map = ha.skill_nApplicants();
		assertNotNull(map );
		assertTrue("The map should contain 4 or 8 elements",map.size() == 4 || map.size()==8);
		assertEquals(new Long(6),map.get("pascal"));
	}
	
	public void testMaxPosition() {
		String pos = ha.maxPosition();
		assertNotNull(pos);
		System.out.println(pos);
		assertEquals("database expert",pos );
	}
	
}
