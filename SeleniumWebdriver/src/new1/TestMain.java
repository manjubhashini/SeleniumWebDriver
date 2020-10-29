package new1;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestMain {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(junitFunction.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println("Result Status ="+result.wasSuccessful());
   }
} 