package net.shop2k.blog.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Admin;
import net.shop2k.blog.entitys.Manager;
import net.shop2k.blog.repositorys.AdminRepository;
import net.shop2k.blog.repositorys.ManagerRepository;
import net.shop2k.blog.repositorys.UserRepository;

/*
 * ADMIN Service
 */
@Service
public class AdminService{
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
    //     Admin admin = adminRepository.findByUsername(username);
    //     if(admin == null){
    //         throw new UsernameNotFoundException("Không được để trống");
    //     }
    //     List<GrantedAuthority> authorities = new ArrayList<>();
    //     authorities.add(new SimpleGrantedAuthority(admin.getRole()));

    //     return new org.springframework.security.core.userdetails.User(
    //         admin.getUsername(),
    //         admin.getPassword(),
    //         admin.isSetEnabled(),
    //         true,
    //         true,
    //         true,
    //         authorities
    //     );
    // }
    

    public Admin registerAdmin(Admin admin) {

        //emailは存在するかどうか
        if(adminRepository.findByUsernameAndSetEnabled(admin.getUsername(), true) != null){
            throw new IllegalArgumentException("Admin đã tồn tại");
        }else if(userRepository.findByUsernameAndSetEnabled(admin.getUsername(), true) != null){
            throw new IllegalArgumentException("Tài khoản đã tồn tại");
        }else if(!admin.getPassword().equals(admin.getConfirmedPassword())){
            throw new IllegalArgumentException("Mật khẩu không giống nhau");   
        }
        Manager manager = managerRepository.findByUsername(admin.getUsername());
        if (manager != null) {
            throw new IllegalArgumentException("Tài khoản đã tồn tại");
        }else{
            String encodedPassword = bCryptPasswordEncoder.encode(admin.getPassword());
            String encodedconfirmedPassword = bCryptPasswordEncoder.encode(admin.getConfirmedPassword());
            admin.setPassword(encodedPassword);
            admin.setConfirmedPassword(encodedconfirmedPassword);
            admin.setSetEnabled(false);
            admin.setEmailCode(false);
            admin.setConfirmationCode(UUID.randomUUID().toString().substring(0, 6));
            adminRepository.save(admin);
            return admin;
        }
    }

    /*
     * emailを設定
     */
    public void sendConfirmationEmail(Admin admin){

        //email フォーム
        String recipientEmail = admin.getUsername();
        String subject = "Xác nhận nộp đơn đăng kí trở thành ADMIN";
        String content = "Xin chào " + admin.getNickName() + "\n"
            + "Mã xác nhận của bạn là: " + admin.getConfirmationCode();

        //emailに送信を設定
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        javaMailSender.send(mailMessage);
    }
    
    /*
     * 承認コードをチェック
     */
    public void confirmationCodeAdmin(String confirmationCode){
        
        Admin admin = adminRepository.findByConfirmationCode(confirmationCode);
        //承認コードが違い場合
        if(admin == null){
            throw new IllegalArgumentException("Mã xác nhận không hợp lệ");
        }
        //承認コードがOK場合
        admin.setEmailCode(true);
        // admin.setSetEnabled(true);
        adminRepository.save(admin);
    }

    public List <Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
}
