package com.zhuofengyuan.wechat.shop.admin.mail;

import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public RestResponseBo testmail() throws MessagingException {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //发件人
        helper.setFrom("zhuofengyuan@zsmls.com");
        //收件人
        helper.setTo("jiaxing.feng@zsmls.cn");
        //标题
        helper.setSubject("新年快乐");
        //文本
        helper.setText("message text");
        //附件
        helper.addAttachment("downFile.jpg", new File("F:\\fengtoos\\ABannerPC2019.jpg"));
        this.mailSender.send(mimeMessage);
        return RestResponseBo.ok();
    }
}
