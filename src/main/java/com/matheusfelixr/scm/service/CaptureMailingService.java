package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.controller.SecurityController;
import com.matheusfelixr.scm.model.dto.MessageDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CaptureMailingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureMailingService.class);


    public MessageDTO captureMailingByExample(String example) throws Exception {

        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/search?tbs=lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk01pgIMS0wVa2kMx6-wP9kYIrLLG0Q:1615496772648&q=empresas#rlfi=hd:;si:;mv:[[-18.8735391,-48.2425554],[-18.9434743,-48.341009400000004]];tbs:lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2");
        Thread.sleep(1000);

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
        FileWriter arq = new FileWriter("csv/" + dateFormat + "_RETORNO_PESQUISA_" + example + ".csv");
        PrintWriter printWriter = new PrintWriter(arq);
        printWriter.println("EMPRESA|TELEFONE|ENDEREÇO");

        int qtyPage = pages.size();
        int indexPage = 0;
        if(qtyPage == 0){
            indexPage = 1;
        } else{
            indexPage =  qtyPage - 3;
        }


        for (int i = 0; i <= indexPage ; i++) {
            try {
                if (i != 0) {
                    pages = driver.findElements(By.className("SJajHc"));
                    pages.get(pages.size() - 1).click();
                    Thread.sleep(4000);
                }
                String company = "";
                String phone = "";
                String address = "";
                List<WebElement> companyBlocks = driver.findElements(By.className("rllt__link"));
                for (WebElement companyBlock : companyBlocks) {
                    try {
                        Thread.sleep(1000);
                        companyBlock.click();
                        company = driver.findElement(By.className("kno-ecr-pt")).getText();

                        LOGGER.info("\n"+company);

                        String info = driver.findElement(By.className("SALvLe")).getText();

                        try {
                            phone = getItemContainerInfoByType(info, "Telefone: ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            address = getItemContainerInfoByType(info, "Endereço: ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("\n----------------------------------------------");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!phone.equals("")) {
                        printWriter.println(company + ";" + phone + ";" + address);
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

    private String getItemContainerInfoByType(String info, String type) {
        String ret = "" ;
        boolean containsByType = info.contains(type);

        if (containsByType) {
            int init = info.indexOf(type);
            //pega do inicio to telefone ate o final da string
            String initString = info.substring(init, info.length());

            int initCut = initString.indexOf(type);
            int end = initString.indexOf("\n");
            if (end != -1) {
                ret = initString.substring(initCut, end);
                ret = ret.replace(type, "");
                LOGGER.info(ret);
            } else {
                ret = initString.replace(type, "");
                LOGGER.info(ret);
            }
        }
        return ret;
    }
}
