package aut.irctc.testscenarios.login;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import aut.framework.common.BaseUITest;
import aut.irctc.objrepo.common.IrctcPageInitialization;
import aut.irctc.objrepo.common.LoginPage;

public class IrctcLogin extends BaseUITest {
	
	  @BeforeMethod
	  public void beforeMethod() {
		  
		  IrctcPageInitialization.initializeAllPages(driver);
		 
		  }
	
	
	@Test
	public void test() throws InterruptedException {
	
		LoginPage.loginIrctc("SM_Santu", "test1233","pnrEnquiry");
		
	}
	

}
