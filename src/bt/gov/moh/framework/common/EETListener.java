package bt.gov.moh.framework.common ;

import java.io.InputStream;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EETListener implements ServletContextListener
{
  
  private static final String PROPERTIES_FILE_PATH = "/WEB-INF/eet.properties";
  
  public void contextInitialized( ServletContextEvent e )
  {
    ServletContext ctx = e.getServletContext() ;
    Properties rmaProps = initRmaProperties( ctx ) ;
    initLogging( rmaProps ) ;
  }


  public void contextDestroyed(ServletContextEvent sce )
  {
    ServletContext ctx = sce.getServletContext() ;
    cleanupLog4j(ctx);

  }

  /* moved to WebMainServlet */
  private Properties initRmaProperties( ServletContext ctx )
  {
    Properties props = loadInternalProperties( ctx ) ;
    return props ;
  }


  /* moved to WebMainServlet */
  private Properties loadInternalProperties( ServletContext sctx )
  {
    Properties props = null ;

    try
    {
      InputStream propStream = sctx.getResourceAsStream( PROPERTIES_FILE_PATH ) ;

      if (propStream != null)
      {
        props = new Properties() ;
        props.load( propStream ) ;
        propStream.close() ;
      }
    } catch (Exception ex)
    {
      props = null ;
      Log.warn( "Exception while loading internal properties:\n", ex ) ;
    }

    return props ;
  }

 
  @SuppressWarnings("deprecation")
private void initLogging( Properties props )
  {

    try
    {
      Context ctx = new InitialContext();
      ctx.rebind("logging-context", "RMALoggingContext");
      
    } catch(Exception e) 
    {
      
    }

    if (null == props)
    {
      org.apache.log4j.BasicConfigurator.configure() ;
      org.apache.log4j.Category.getRoot().debug( "USING BASIC CONFIGURATOR, props IS NULL" ) ;
    }
    else
    {
      
      org.apache.log4j.PropertyConfigurator.configure( props ) ;
    }
  }



  /**
   * Log4j specific cleanup.  Shuts down all loggers and appenders and
   * removes the hierarchy associated with the current classloader.
   *
   * @param context the current servlet context
   */
  private void cleanupLog4j(ServletContext context) {
    //shutdown this webapp's logger repository
    context.log("Cleaning up Log4j resources for context: "
      + context.getServletContextName() + "...");
    //context.log("Shutting down all loggers and appenders...");
    org.apache.log4j.LogManager.shutdown();
    context.log("Log4j cleaned up.");
  }

}
