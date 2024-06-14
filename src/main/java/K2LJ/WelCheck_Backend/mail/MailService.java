package K2LJ.WelCheck_Backend.mail;

public interface MailService {
    //비밀번호 찾기 기능 - 임시 랜덤 비밀번호 메일 발송
    public void sendTempPasswordMail(String to, String name, String password) throws Exception;

}
