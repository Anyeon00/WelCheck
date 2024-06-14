package K2LJ.WelCheck_Backend.mail;

import jakarta.mail.Message;
import jakarta.mail.MessageRemovedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MailServiceImpl implements MailService{
    //빈으로 등록해둔거
    private final JavaMailSender emailSender;

    @Value("${naver.id}")
    private String id;

    @Override
    public void sendTempPasswordMail(String to, String name, String password) throws Exception {

        MimeMessage message = createFindPasswordMessage(to, name, password);    // "to"에게 발송하는 메세지
        //예외처리
        try {
            //메일 발송
            emailSender.send(message);
            log.info("sent Mail!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }


    private MimeMessage createFindPasswordMessage(String to, String name, String password) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        //이메일 제목
        message.setSubject("Wel-Check 임시 비밀번호 발급 안내");

        String msgg = "";
        msgg += "<h1>안녕하세요, [" + name + "]님. Wel-Check 입니다.</h1>";
        msgg += "<br>";
        msgg += "임시 비밀번호를 발급해 드립니다. 아래의 임시 비밀번호를 사용하여 로그인해 주시기 바랍니다.";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "임시 비밀번호: " + password;
        msgg += "<br>";
        msgg += "<br>";
        msgg += "로그인 후 반드시 새로운 비밀번호로 변경해 주시기 바랍니다.";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "감사합니다.";
        //메일 내용, charset타입, subtype
        message.setText(msgg, "utf-8", "html");
        //보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(id);

        return message;
    }
}
