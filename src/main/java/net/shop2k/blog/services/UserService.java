package net.shop2k.blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.User;
import net.shop2k.blog.repositorys.UserRepository;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không được để trống");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
    
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isSetEnabled(),
            true,
            true,
            true,
            authorities
        );
    }

    public User registerUser(User user){
        /*
         * このemailは存在してるかどうかを確認する
         */
        if(userRepository.findByUsernameAndSetEnabled(user.getUsername(), true) != null){
            throw new IllegalArgumentException("Tài khoản đã tồn tại");
        }else if(!user.getPassword().equals(user.getConfirmedPassword())){
            throw new IllegalArgumentException("Mật khẩu xác nhận không chính xác");
        }
        else{
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword()); //パスワードのセキュリティー
            String encodedconfirmedPassword = bCryptPasswordEncoder.encode(user.getConfirmedPassword());
            user.setPassword(encodedPassword);
            user.setConfirmedPassword(encodedconfirmedPassword);
            user.setSetEnabled(false);
            user.setConfirmationCode(UUID.randomUUID().toString().substring(0, 6)); //randomで6文字
            userRepository.save(user);
            return user;
        }
    }

    public void sendConfirmationEmail(User user){
        String recipientEmail = user.getUsername();
        String subject = "Xác nhận đăng kí";
        String content = "Xin chào " + user.getNickName() + "\n" 
            // + "Vui lòng xác nhận đăng kí bằng cách nhấp vào liên kết sau: \n"
            + "Mã xác nhận: " + user.getConfirmationCode();
        /*
         * Email承認を設定
         */
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        /*
         * ユーザーに送信
         */
        javaMailSender.send(mailMessage);
    }

    /*
     * 承認コードをチェックする
     */
    public void confirmRegistration(String code){
        User user = userRepository.findByConfirmationCode(code);
        /*
         * エラー：メッセージが表示
         */
        if(user == null){
            throw new IllegalArgumentException("Mã xác nhận không hợp lệ");
        }
        /*
         * 承認できたら、
         */
        user.setSetEnabled(true);
        userRepository.save(user);
    }
}
