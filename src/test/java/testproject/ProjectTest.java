package testproject;

import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.util.Random;



public class ProjectTest {
    static WebDriver driver;
    @BeforeClass
    public static void setWindow(){

        ExtendReportBase.createReport();

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://tms.pisystindia.com/admin/login");
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void login() throws InterruptedException {

        WebElement email = driver.findElement(By.id("admin_email"));
        WebElement password = driver.findElement(By.id("admin_password"));
        email.sendKeys("sales.infinitycorp@gmail.com");
        password.sendKeys("123456");
        WebElement submit = driver.findElement(By.xpath("(//button)[1]"));
        submit.click();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebElement projects = driver.findElement(By.xpath("(//li//a//i)[23]"));
        projects.click();
        Thread.sleep(1000);
    }

    @AfterMethod
    public void logout() throws InterruptedException {
        WebElement infinity = driver.findElement(By.xpath("//span[@class=\"user-avatar\"]"));
        infinity.click();
        WebElement logout = driver.findElement(By.xpath("(//a//span)[9]"));
        logout.click();
        Thread.sleep(3000);
        ExtendReportBase.test.log(LogStatus.INFO, "Logging out");
    }

    @Test
    public void changeListOrder() throws CustomException {
        ExtendReportBase.test = ExtendReportBase.reports.startTest("Change List Order","Checking if List order is reversed when the arrow is clicked in table header");
        ExtendReportBase.test.log(LogStatus.INFO, "Logging in to the test page");
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on Project button ");

        WebElement ini_count = driver.findElement(By.xpath("(//td)[1]"));
        String initial_count = String.valueOf(ini_count.getText());
        ExtendReportBase.test.log(LogStatus.INFO, "Saved serial number of first row");

        WebElement reverseNum = driver.findElement(By.xpath("(//th)[1]"));
        reverseNum.click();
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on reverse List Arrow ");

        WebElement fin_count = driver.findElement(By.xpath("(//td)[1]"));
        String final_count = String.valueOf(fin_count.getText());
        ExtendReportBase.test.log(LogStatus.INFO, "Saved serial number of first row after clicking button");

        if(!initial_count.equals(final_count)){
            System.out.println("The list is reversed");
            ExtendReportBase.test.log(LogStatus.PASS, "The list is reversed");
        }
        else{
            ExtendReportBase.test.log(LogStatus.FAIL, "The list is not reversed");
            throw new CustomException("The list is not reversed");
        }
    }

    @Test
    public void addProject() throws InterruptedException, CustomException {
        ExtendReportBase.test = ExtendReportBase.reports.startTest("Add new project","Checking if new project adds successfully on valid data");
        ExtendReportBase.test.log(LogStatus.INFO, "Logging in to the test page");
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on Project button ");

        WebElement addButton = driver.findElement(By.xpath("(//a[@type=\"submit\"])[1]"));
        addButton.click();
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on Add Project button ");

        WebElement projectName = driver.findElement(By.id("project_name"));
        WebElement projectCode = driver.findElement(By.id("project_code"));
        WebElement projectDescription = driver.findElement(By.id("project_description"));
        WebElement startDate = driver.findElement(By.id("start_date"));
        WebElement endDate = driver.findElement(By.id("end_date"));
        projectName.sendKeys("Automation Selenium");
        String projectCustomCode = "AUTOSEL" + (new Random().nextInt(800000) + 100);
        projectCode.sendKeys(projectCustomCode);
        projectDescription.sendKeys("This test is being run using Selenium");
        startDate.sendKeys("06122022");
        endDate.sendKeys("06282022");
        ExtendReportBase.test.log(LogStatus.INFO, "Added details for the project");
        WebElement add = driver.findElement(By.xpath("//button"));
        add.click();
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on Add button ");
        Thread.sleep(3000);
        ExtendReportBase.test.log(LogStatus.INFO, "Saving the code to verify addition");
        WebElement read = driver.findElement(By.xpath("(//td)[3]"));
        String readCode = String.valueOf(read.getText());

        if(readCode.equals(projectCustomCode)){
            System.out.println("Project Added successfully");
            ExtendReportBase.test.log(LogStatus.PASS, "Project Added successfully");
        }
        else{
            ExtendReportBase.test.log(LogStatus.FAIL, "Project addition failed");
            throw new CustomException("Project Adding failed");
        }
    }

    @Test
    public void updateProject() throws InterruptedException, CustomException {
        ExtendReportBase.test = ExtendReportBase.reports.startTest("Update project","Checking if project update works with valid data");
        ExtendReportBase.test.log(LogStatus.INFO, "Logging in to the test page");
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on Project Button");

        ExtendReportBase.test.log(LogStatus.INFO, "Reading project code to verify later");
        WebElement read = driver.findElement(By.xpath("(//td)[11]"));
        String readCode = String.valueOf(read.getText());
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on Update Link");
        WebElement updateButton = driver.findElement(By.xpath("(//td//a)[2]"));
        updateButton.click();

        WebElement projectName = driver.findElement(By.id("project_name"));
        WebElement projectCode = driver.findElement(By.id("project_code"));
        WebElement projectDescription = driver.findElement(By.id("project_description"));
        projectName.clear();
        projectCode.clear();
        projectDescription.clear();
        int num = (new Random().nextInt(9000) + 100);
        projectName.sendKeys("NewUsingSelenium" + num);
        String projectCustomCode = "UPDATED" + num;
        projectCode.sendKeys(projectCustomCode);
        projectDescription.sendKeys("This test is being run using Selenium and it is updated later using Id " + projectCustomCode);
        ExtendReportBase.test.log(LogStatus.INFO, "Updating details of project");

        WebElement update = driver.findElement(By.xpath("//button"));
        update.click();
        ExtendReportBase.test.log(LogStatus.INFO, "Clicked on update button");
        Thread.sleep(3000);
        WebElement readUpdated = driver.findElement(By.xpath("(//td)[11]"));
        String readCodeUpdated = String.valueOf(readUpdated.getText());
        ExtendReportBase.test.log(LogStatus.INFO, "Reading current project code to verify");

        if(!readCodeUpdated.equals(readCode)){
            ExtendReportBase.test.log(LogStatus.PASS, "Project Updated successfully");
            System.out.println("Project Updated successfully");
        }
        else{
            ExtendReportBase.test.log(LogStatus.FAIL, "Project updating Test Case Failed");
            throw new CustomException("Project updating Test Case Failed");
        }
    }

    @AfterClass
    public static void shut(){
        ExtendReportBase.test.log(LogStatus.INFO, "Closing web driver");
        driver.quit();
        ExtendReportBase.reports.flush();
    }
}
