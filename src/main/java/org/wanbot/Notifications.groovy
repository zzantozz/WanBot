package org.wanbotimport javax.xml.transform.stream.StreamResultimport javax.xml.transform.stream.StreamSourceimport javax.xml.transform.TransformerFactoryimport java.security.Securityimport javax.mail.*;import javax.mail.internet.*
class WanBotAuthenticator extends Authenticator {    protected PasswordAuthentication getPasswordAuthentication() {        def props = new Properties()        props.load(getClass().getResourceAsStream("/wanbot.properties"))        return [ user:props.getProperty('user'), password:props.getProperty('password') ]    }}
class Notifications {    def notify(sender, converter, xml, Object[] extraConverterArgs) {        def argList = []        argList << xml        extraConverterArgs.each{ argList << it }        def crlf = System.getProperty("line.separator")        def report = new StringBuffer()        report << converter(argList)        report << crlf*2        report << '<image src="' + googleChartsUrlFromDatastore(xml) + '" />'         sender(report)    }        def emailer = {
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
        message.setSubject("WanBot point summary");        message.setContent(body, "text/plain");
        Transport.send(message);    }        def stdout = { println it }        def xslConverter = {            String xml, String stylesheet ->        def factory = TransformerFactory.newInstance()        def transformer = factory.newTransformer(new StreamSource(new StringReader(stylesheet)))        def writer = new StringWriter()        transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(writer))        writer.toString()    }        def identityConverter = { String s -> return s }        def googleChartsUrlFromPointsList(points) {        def chartData = new StringBuffer()        def chartLabels = new StringBuffer()        points.each{            chartLabels << it[0] + '|'            chartData << it[1] + ','        }        chartData.setLength(chartData.size() - 1)        chartLabels.setLength(chartLabels.size() - 1)        'http://chart.apis.google.com/chart?cht=p3&chs=500x220&chco=FF0000,FF8040,FFFF00,00FF00,00FFFF,0000FF,800080&chd=t:' + chartData + '&chl=' + chartLabels    }        def googleChartsUrlFromDatastore(xml) {        xslConverter(xml, new File('wanbot-charts-url.xsl').getText())    }}