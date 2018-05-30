/**
 * Copyright 2012 suixingpay, Inc. All rights reserved.
 */
package com.cn.javaMail.demo;

import com.cn.javaMail.utils.MailSenderInfo;
import com.cn.javaMail.utils.SimpleMailSender;


/**
 * 发送邮件
 * @author Administrator
 *
 */
public class SendMailService {

    /**
     * 发邮件的方法
     * @param json
     * @param oarvwopr
     */
      public final  void sendmail(String title, String value, String[] receivers, String[] ccs, String[] fileNames) {
         //这个类主要是设置邮件
         MailSenderInfo mailInfo = new MailSenderInfo();
         mailInfo.setMailServerHost("smtp.suixingpay.com");//smtp.163.com    smtp.suixingpay.com
         mailInfo.setMailServerPort("25");
         mailInfo.setValidate(true);
         mailInfo.setUserName("li_xz1@suixingpay.com");
         mailInfo.setPassword("30&NUigrUR");
         mailInfo.setFromAddress("li_xz1@suixingpay.com");
         //mailInfo.setToAddress("li_xz1@suixingpay.com");
         //String[] receivers = new String[]{"wang_xue@suixingpay.com"+,
         //"chen_xue@suixingpay.com", "li_xz1@suixingpay.com"};
         mailInfo.setReceivers(receivers);
         //String[] ccs = new String[]{"lin_fj@suixingpay.com", "yu_wenzhen@suixingpay.com",+
         //"li_wen@suixingpay.com", "it@suixingpay.com"};
         //mailInfo.setCcs(ccs);
         mailInfo.setSubject(title);
         mailInfo.setContent(value);

/*         String[] fileNames = new String[1];
         fileNames[0] = "C:/数据.xlsx";*/
//         mailInfo.setAttachFileNames(fileNames);

         //这个类主要来发送邮件
         SimpleMailSender sms = new SimpleMailSender();
         sms.sendHtmlMail(mailInfo); //发送html格式
         //sms.sendTextMail(mailInfo); //发送文体格式
     }
      
     public static void main(String[] args) {
    	 SendMailService s=new SendMailService();
    	 String[] receivers = new String[]{"li_xz1@suixingpay.com"};
    	 s.sendmail("456","456",receivers,null,null);
	}
}
