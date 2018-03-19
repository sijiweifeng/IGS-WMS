package com.esquel.wh.delegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class printLabelDelegate implements JavaDelegate {
	private static final Logger LOGGER = LoggerFactory.getLogger(printLabelDelegate.class);
    //生成文件路径
    private static String path = "c:\\Demo\\";
    
    //文件路径+名称
    private static String filenameTemp;
	  @Override
	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("hello {}", execution);
	    Date d = new Date();
	    Random random = new Random();
	    createFile("File1" + Long.toString(d.getTime()), random.nextInt() + "");
	    System.out.println("print label!");
	  }
	  
	  public static boolean createFile(String fileName,String filecontent){
	        Boolean bool = false;
	        filenameTemp = path+fileName+".txt";//文件路径+名称+文件类型
	        File file = new File(filenameTemp);
	        try {
	            //如果文件不存在，则创建新的文件
	            if(!file.exists()){
	                file.createNewFile();
	                bool = true;
	                System.out.println("success create file,the file is "+filenameTemp);
	                //创建文件成功后，写入内容到文件里
	                writeFileContent(filenameTemp, filecontent);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return bool;
	    }
	  
	  public static boolean writeFileContent(String filepath,String newstr) throws IOException{
	        Boolean bool = false;
	        String filein = newstr+"\r\n";//新写入的行，换行
	        String temp  = "";
	        
	        FileInputStream fis = null;
	        InputStreamReader isr = null;
	        BufferedReader br = null;
	        FileOutputStream fos  = null;
	        PrintWriter pw = null;
	        try {
	            File file = new File(filepath);//文件路径(包括文件名称)
	            //将文件读入输入流
	            fis = new FileInputStream(file);
	            isr = new InputStreamReader(fis);
	            br = new BufferedReader(isr);
	            StringBuffer buffer = new StringBuffer();
	            
	            //文件原有内容
	            for(int i=0;(temp =br.readLine())!=null;i++){
	                buffer.append(temp);
	                // 行与行之间的分隔符 相当于“\n”
	                buffer = buffer.append(System.getProperty("line.separator"));
	            }
	            buffer.append(filein);
	            
	            fos = new FileOutputStream(file);
	            pw = new PrintWriter(fos);
	            pw.write(buffer.toString().toCharArray());
	            pw.flush();
	            bool = true;
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            //不要忘记关闭
	            if (pw != null) {
	                pw.close();
	            }
	            if (fos != null) {
	                fos.close();
	            }
	            if (br != null) {
	                br.close();
	            }
	            if (isr != null) {
	                isr.close();
	            }
	            if (fis != null) {
	                fis.close();
	            }
	        }
	        return bool;
	    }
}
