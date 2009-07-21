import javax.xml.transform.stream.StreamResultimport javax.xml.transform.stream.StreamSourceimport javax.xml.transform.TransformerFactoryimport java.security.Securityimport javax.mail.*;import javax.mail.internet.*
class WanBotAuthenticator extends Authenticator {    protected PasswordAuthentication getPasswordAuthentication() {        return new PasswordAuthentication("thewanbot@gmail.com", "FOObar1337")    }}
def notify(sender, converter, xml, Object[] extraConverterArgs) {    def argList = []    argList << xml    extraConverterArgs.each{ argList << it }    sender(converter(argList))}def emailer = {        body ->
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
    message.setContent("test", "text/plain");    Transport.send(message);
}def stdout = { println it }def xslConverter = {        String xml, String stylesheet ->    def factory = TransformerFactory.newInstance()    def transformer = factory.newTransformer(new StreamSource(new StringReader(stylesheet)))    def writer = new StringWriter()    transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(writer))    writer.toString()}def identityConverter = { String s -> return s }xml = new File('datastore.xml').getText()def stylesheet = new File('wanbot-basic.xsl').getText()notify(emailer, xslConverter, xml, stylesheet)