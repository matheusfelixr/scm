package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.model.dto.MessageDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CaptureMailingService {


    public MessageDTO captureMailingByExample(String example) throws Exception {

        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/search?tbs=lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk01pgIMS0wVa2kMx6-wP9kYIrLLG0Q:1615496772648&q=empresas#rlfi=hd:;si:;mv:[[-18.8735391,-48.2425554],[-18.9434743,-48.341009400000004]];tbs:lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2");
        Thread.sleep(3000);

        //realiza busca
        driver.findElement(By.id("lst-ib")).click();
        driver.findElement(By.id("lst-ib")).clear();
        driver.findElement(By.id("lst-ib")).sendKeys(example);
        driver.findElement(By.className("sbico-c")).click();

        //pega elementos
        Thread.sleep(1000);
        List<WebElement> pages = driver.findElements(By.className("SJajHc"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateFormat = sdf.format(new Date());
        FileWriter arq = new FileWriter("csv/"+dateFormat + "_RETORNO_PESQUISA_" + example + ".csv");
        PrintWriter printWriter = new PrintWriter(arq);
        printWriter.println("EMPRESA|TELEFONE|ENDEREÇO");

        for (WebElement page : pages) {
            try {
                if (pages.get(0) != page) {
                    pages = driver.findElements(By.className("SJajHc"));
                    pages.get(pages.size() - 1).click();
                    Thread.sleep(6000);
                }
                String company ="";
                String phone = "";
                String address = "";
                List<WebElement> companyBlocks = driver.findElements(By.className("rllt__link"));
                for (WebElement companyBlock : companyBlocks) {
                    try {
                        Thread.sleep(1000);
                        companyBlock.click();
                        Thread.sleep(1000);
                        company = driver.findElement(By.className("kno-ecr-pt")).getText();
                        System.out.println(company);

                        String info = driver.findElement(By.className("SALvLe")).getText();

                        boolean containsAddress = info.contains("Endereço: ");
                        boolean containsPhone = info.contains("Telefone: ");

                        if (containsPhone) {
                            int init = info.indexOf("Telefone: ");
                            //pega do inicio to telefone ate o final da string
                            String initPhone = info.substring(init, info.length());
                            // seta posição final caso seja -1 pq e o ultimo
                            int pos = initPhone.indexOf("\n");
                            if (pos != -1) {
                                phone = info.substring(init, pos);
                                phone = phone.replace("Telefone: ", "");
                                System.out.println(phone);
                            } else {
                                phone = initPhone;
                                phone = phone.replace("Telefone: ", "");
                                System.out.println(phone);
                            }
                        }

                        try {
                            if (containsAddress) {
                                int init = info.indexOf("Endereço: ");
                                String initAddress = info.substring(init, info.length());
                                int pos = initAddress.indexOf("\n");
                                if (pos != -1) {
                                    address = info.substring(init, pos);
                                    address = address.replace("Endereço: ", "");
                                    System.out.println(address);
                                } else {
                                    address = initAddress;
                                    address = address.replace("Endereço: ", "");
                                    System.out.println(address);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("\n----------------------------------------------");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(!phone.equals("")) {
                        printWriter.println(company + "|" + phone + "|" + address);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        arq.close();
        Thread.sleep(8000);
        driver.close();
        return new MessageDTO("Deu certo");
    }
}
