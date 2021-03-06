/**
 * Copyright 2011 suixingpay, Inc. All rights reserved.
 */
package com.cn.javaMail.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件
 * @author Administrator
 *
 */
public class MyAuthenticator extends Authenticator {
      String userName = null;
      String password = null;

      public MyAuthenticator() {
      }
      public MyAuthenticator(String username, String password) {
             this.userName = username;
             this.password = password;
      }
      protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(userName, password);
      }
}
