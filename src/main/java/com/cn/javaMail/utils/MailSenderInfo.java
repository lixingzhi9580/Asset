/**
 * Copyright 2012 suixingpay, Inc. All rights reserved.
 */
package com.cn.javaMail.utils;

import java.util.Properties;
/**
 * 邮件
 * @author Administrator
 *
 */
public class MailSenderInfo {
    // 发送邮件的服务器的IP和端口
    private String mailServerHost;
    private String mailServerPort = "25";
    // 邮件发送者的地址
    private String fromAddress;
    // 邮件接收者的地址
    private String toAddress;
    // 登陆邮件发送服务器的用户名和密码
    private String userName;
    private String password;
    // 是否需要身份验证
    private boolean validate = false;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private String[] attachFileNames;

    // 邮件的接收者，可以有多个
    private String[] receivers;
    // 邮件的抄送者，可以有多个
    private String[] ccs;

    /**
     * 获得邮件会话属性
     * @return Properties
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }
    /**
     * 获取发送邮件的服务器的IP
     * @return String
     */
    public String getMailServerHost() {
        return mailServerHost;
    }

    /**
     * 设置发送邮件的服务器的IP
     * @param mailServerHost
     */
    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    /**
     * 获取发送邮件的服务器的端口
     * @return String
     */
    public String getMailServerPort() {
        return mailServerPort;
    }

    /**
     * 设置发送邮件的服务器的端口
     * @param mailServerPort
     */
    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    /**
     * 是否需要身份验证
     * @return boolean
     */
    public boolean isValidate() {
        return validate;
    }

    /**
     * 设置是否需要身份验证
     * @param validate
     */
    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    /**
     * 获取邮件附件的文件名
     * @return String[]
     */
    public String[] getAttachFileNames() {
         return attachFileNames;
    }

    /**
     * 设置邮件附件的文件名
     * @param fileNames
     */
    public void setAttachFileNames(String[] fileNames) {
        this.attachFileNames = fileNames;
    }

    /**
     * 获取邮件发送者的地址
     * @return String
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * 设置邮件发送者的地址
     * @param fromAddress
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * 获取邮件发送者的密码
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置邮件发送者的密码
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取邮件接收者的地址
     * @return String
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * 设置邮件接收者的地址
     * @param toAddress
     */
    public void setToAddress(String toAddress) {
       this.toAddress = toAddress;
    }

    /**
     * 获取登陆邮件发送服务器的用户名
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置登陆邮件发送服务器的用户名
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取邮件主题
     * @return String
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置邮件主题
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 获取邮件的文本内容
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置邮件的文本内容
     * @param textContent
     */
    public void setContent(String textContent) {
        this.content = textContent;
    }

    /**
     * 获得抄送人数组
     * @return  String[]
     */
    public String[] getCcs() {
        return ccs;
    }

    /**
     * 设置抄送人数组
     * @param ccs
     */
    public void setCcs(String[] ccs) {
        this.ccs = ccs;
    }

    /**
     * 获得收件人数组
     * @return String[]
     */
    public String[] getReceivers() {
        return receivers;
    }

    /**
     * 设置收件人数组
     * @param receivers
     */
    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }
}
