package cs326.hw1p2;

import cs326.hw1p2.Commands.UpdateStatus;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        UpdateStatus cmd = new UpdateStatus();
        cmd.Execute("testing twitter4j");
        
        
    }
}
