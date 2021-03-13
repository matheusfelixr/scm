package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.dto.MessageDTO;
import com.matheusfelixr.scm.util.CepHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CaptureMailingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureMailingService.class);


    public MessageDTO captureMailingByExample(String example) throws Exception {

        String url = "https://www.google.com/search?tbs=lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk01pgIMS0wVa2kMx6-wP9kYIrLLG0Q:1615496772648&q=empresas#rlfi=hd:;si:;mv:[[-18.8735391,-48.2425554],[-18.9434743,-48.341009400000004]];tbs:lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!2m1!1e2!2m1!1e3,lf:1,lf_ui:2";
        String uriDriver = "driver/chromedriver.exe";

        //Abre navegador
        WebDriver driver = this.openBrowser(url, uriDriver);

        //realiza busca no google
        this.searchGoogle(example, driver);

        //pega tags de pagina
        List<WebElement> tagPages = this.getPages(driver);


        String dateFormat = new SimpleDateFormat("yyyyMMdd").format(new Date());
        FileWriter arq = new FileWriter("csv/" + dateFormat + "_RETORNO_PESQUISA_" + example + ".csv");
        PrintWriter printWriter = new PrintWriter(arq);
        printWriter.println("EMPRESA|TELEFONE|ENDEREÇO");

        //pega o numero de paginas fazendo a regra de calculo
        int numberOfPages = this.getNumberOfPages(tagPages);

        LOGGER.info("Possui "+ numberOfPages + " paginas");

        for (int i = 0; i <= numberOfPages; i++) {
            try {
                if (i != 0) {
                    tagPages = driver.findElements(By.className("SJajHc"));
                    tagPages.get(tagPages.size() - 1).click();
                    Thread.sleep(4000);
                }
                String company = "";
                String phone = "";
                String fullAddress = "";
                String road = "";

                String city = "";
                String cep = "";
                List<WebElement> companyBlocks = driver.findElements(By.className("rllt__link"));
                for (WebElement companyBlock : companyBlocks) {
                    try {
                        Thread.sleep(1000);
                        companyBlock.click();
                        Thread.sleep(1000);
                        company = driver.findElement(By.className("kno-ecr-pt")).getText();

                        LOGGER.info("\n" + company);

                        String info = driver.findElement(By.className("SALvLe")).getText();

                        try {
                            phone = this.getItemContainerInfoByType(info, "Telefone: ");
                        } catch (Exception e) {
                            LOGGER.error("Erro ao capturar telefone para as informações: " + info);
                            e.printStackTrace();
                        }

                        try {
                            fullAddress = this.getItemContainerInfoByType(info, "Endereço: ");
                        } catch (Exception e) {
                            LOGGER.error("Erro ao capturar telefone para as informações: " + info);
                            e.printStackTrace();
                        }

                        try {
                            road = this.getRoadByFullAddress(fullAddress);
                        } catch (Exception e) {
                            LOGGER.error("Erro ao capturar logradouro para as informações: " + info);
                        }

                        try {
                            cep = this.getCepByFullAddress(fullAddress);
                        } catch (Exception e) {
                            LOGGER.error("Erro ao capturar cep para as informações: " + info);
                        }

                        try {
                            city = this.getCityByFullAddressAndCepAndRoad(fullAddress, cep);
                        } catch (Exception e) {
                            LOGGER.error("Erro ao capturar cidade para as informações: " + info);
                        }



                            System.out.println("\n----------------------------------------------");
                    } catch (Exception e) {
                        LOGGER.error("Erro ao capturar empresa");
                        e.printStackTrace();
                    }
                    if (!phone.equals("")) {
                        printWriter.println(company + ";" + phone + ";" + fullAddress);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        arq.close();
        Thread.sleep(8000);
        driver.close();
        return new MessageDTO("Sucesso ao realizar import");
    }

    private String getRoadByFullAddress(String fullAddress) {
        String ret = "";
        int index = fullAddress.indexOf(",");
        if(index != -1){
            ret = fullAddress.substring(0, index);
            LOGGER.info(ret);
            return ret;
        }
        return "";
    }

    private String getCityByFullAddressAndCepAndRoad(String fullAddress, String cep) {
        String ret = "";

        String cepRemove = ", " + cep;
        //verifica se existe cep
        boolean containsCep = fullAddress.contains(cepRemove);
        if(containsCep){
            //remove cep da string
            ret = fullAddress.replace(cepRemove, "");
        }
        int i = 0;
        while (i <= 1){
            String remove = "";
            int index = ret.indexOf(",");
            if(index == -1){
                i = 2;
            }else{
                remove = ret.substring(0, index + 2);
                ret = ret.replace(remove, "");
            }
        }



        LOGGER.info(ret);
        return "";
    }

    private String getCepByFullAddress(String fullAddress) {
        String ret = "";
        ret = fullAddress.substring(fullAddress.length()-9, fullAddress.length());

        if(CepHelper.isCep(ret)){
            LOGGER.info(ret);
            return ret;
        }

        return "";
    }

    private int getNumberOfPages(List<WebElement> tagPages) {
        int numberOfPages;
        int numberTagPages = tagPages.size();

        if (numberTagPages == 0) {
            numberOfPages = 1;
        } else {
            //remove a pagina atual
            numberOfPages = numberTagPages - 3;
        }
        return numberOfPages;
    }

    private List<WebElement> getPages(WebDriver driver) throws Exception {
        try {
            Thread.sleep(1000);
            List<WebElement> pages = driver.findElements(By.className("SJajHc"));
            return pages;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro ao encontrar paginas");
        }
    }

    private WebDriver openBrowser(String url, String uriDriver) throws Exception {
        try {
            System.setProperty("webdriver.chrome.driver", uriDriver);
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get(url);
            Thread.sleep(1000);
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro fatal ao abrir navegador. Contate o desenvolvedor.");
        }
    }

    private void searchGoogle(String example, WebDriver driver) throws ValidationException {
        try {
            driver.findElement(By.id("lst-ib")).click();
            driver.findElement(By.id("lst-ib")).clear();
            driver.findElement(By.id("lst-ib")).sendKeys(example);
            driver.findElement(By.className("sbico-c")).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro fatal ao realizar busca no google. Contate o desenvolvedor.");
        }
    }

    private String getItemContainerInfoByType(String info, String type) {
        String ret = "";
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
