package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.model.dto.MessageDTO;
import com.matheusfelixr.scm.model.dto.security.AuthenticateResponseDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaptureMailingService {


    public MessageDTO captureMailingByNameCity(String nameCity, UserAuthentication currentUser) throws Exception {


        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/search?tbs=lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk01pgIMS0wVa2kMx6-wP9kYIrLLG0Q:1615496772648&q=empresas#rlfi=hd:;si:;mv:[[-18.8735391,-48.2425554],[-18.9434743,-48.341009400000004]];tbs:lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2");
        Thread.sleep(3000);

        //realiza busca
        driver.findElement(By.id("lst-ib")).click();
        driver.findElement(By.id("lst-ib")).sendKeys(" - uberlandia");
        driver.findElement(By.className("sbico-c")).click();

        //pega elementos
        Thread.sleep(1000);
        List<WebElement> companyBlocks = driver.findElements(By.className("rllt__link"));

        for (WebElement companyBlock : companyBlocks) {

            companyBlock.click();
            Thread.sleep(1000);
            String company = driver.findElement(By.className("kno-ecr-pt")).getText();
            System.out.println("Empresa: " + company);

            String info = driver.findElement(By.className("SALvLe")).getText();

            // pega endereço
            String address = "";
            boolean containsAddress = info.contains("Endereço: ");
            if(containsAddress){
                int pos = info.indexOf("\n");
                if(pos != -1){
                    address = info.substring(0, pos);
                    address = address.replace("Endereço: ", "");
                    System.out.println(address);
                }else{
                    address = info;
                    address = address.replace("Endereço: ", "");
                    System.out.println(address);
                }
            }



            System.out.println( info.contains("\n"));

            Thread.sleep(1000);
            System.out.println("\n \n \n \n----------------------------------------------");
        }


        Thread.sleep(20000);
        driver.close();
        return new MessageDTO("Deu certo");
    }
}
