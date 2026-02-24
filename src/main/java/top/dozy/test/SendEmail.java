package top.dozy.test;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class SendEmail {

    public static void sendEmail(String to, String subject, String content, List<File> attachments) throws Exception {
        Properties props = new Properties();
        try (InputStream inputStream = SendEmail.class.getClassLoader().getResourceAsStream("email.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("email.properties not found");
            }
            props.load(inputStream);
        }

        String username = props.getProperty("mail.username");
        String password = props.getProperty("mail.password");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(content, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            if (attachments != null) {
                for (File file : attachments) {
                    if (!file.exists()) continue;
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                }
            }

            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateHtmlContent(String title, String content) throws Exception {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String encodedContent = URLEncoder.encode(content, StandardCharsets.UTF_8);
        String fullUrl = "https://api.dozy.eu.org/html-gen?title=" + encodedTitle + "&content=" + encodedContent;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .header("Accept", "text/plain")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws Exception {
        String to = "@163.com";
        String title = "Java邮件发送测试";
        List<String> attachmentPaths = Arrays.asList("a.jpg", "b.png");

        String template = """
            {%drb1^你好,我是来自
                xxxx班的}{{a^y^b^g^c^a}^MMt}
                    {%drb1^我的学号是}{{%rb^xxxx}^MMMMt}
            """.trim();

        String content = generateHtmlContent(title, template);

        List<File> attachments = attachmentPaths.stream()
                .map(File::new)
                .collect(Collectors.toList());

        sendEmail(to, title, content, attachments);
    }
}
