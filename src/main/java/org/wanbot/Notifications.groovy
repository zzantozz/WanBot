package org.wanbot

class Notifications {
            body ->
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        Session session = Session.getDefaultInstance(props, new WanBotAuthenticator());
        
        Message message = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress("thewanbot@gmail.com");
        message.setFrom(addressFrom);
        
        InternetAddress addressTo = new InternetAddress("white_and_nerdy@googlegroups.com");
        message.setRecipient(Message.RecipientType.TO, addressTo);
        message.setSubject("WanBot point summary");
        Transport.send(message);