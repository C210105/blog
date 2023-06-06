package net.shop2k.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import net.shop2k.blog.entitys.User;
import net.shop2k.blog.services.UserService;

@Log4j2
@Controller
@RequestMapping(path = "/blog")
public class UsersController {

    @Autowired
    UserService userService;

    /*
     * ログイン画面表示
     */
    @GetMapping("/login")
    public String loginUser() {
        return "html/users/login.html";
    }

    /*
     * ユーザーを登録画面表示
     */
    @GetMapping("/register")
    public String registerUser() {
        return "html/users/register.html";
    }

    /*
     * ユーザーを登録機能
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("confirmedPassword") String confirmedPassword,
            @RequestParam("nickName") String nickName, Model model) {

        // 登録情報からUserオブジェクトを作成
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setConfirmedPassword(confirmedPassword);
        user.setNickName(nickName);
        // user.setSetEnabled(true);
        user.setRole("ROLE_ADMIN");

        // 登録した情報を処理
        try {
            /*
             * 問題ない場合はEmailコードで承認ページを表示
             */
            User registeredUser = userService.registerUser(user);
            userService.sendConfirmationEmail(registeredUser);
            model.addAttribute("susccessMessage", "Nhập mã xác nhận đã được gửi đến trong email");
            log.info("このEmailは使える");
            return "html/users/confiramationcode.html";
        } catch (IllegalArgumentException e) {
            /*
             * エラー
             */
            model.addAttribute("error", e.getMessage());
            log.info("エラー：このemailは存在してる");
            return "html/users/register.html";
        } catch (UsernameNotFoundException e) {
            /*
             * エラー
             */
            model.addAttribute("errorMessage", e.getMessage());
            log.info("空白でした");
            return "html/users/register.html";
        }
    }

    /*
     * Emailコードを承認機能
     */
    @PostMapping("/register/confirm")
    public String confirmRegistration(@RequestParam("code") String confirmationCode, Model model) {
        try {
            /*
             * 問題ない場合は承認でき、ログインできるようになる
             */
            userService.confirmRegistration(confirmationCode);
            model.addAttribute("successMessage", "Xác nhận đăng kí thành công");
            log.info("Emailで承認できた");
            return "html/users/login.html";
        } catch (IllegalArgumentException e) {
            /*
             * エラー
             */
            model.addAttribute("errorMessage", e.getMessage());
            log.info("Emailで承認できなかった");
            return "html/users/confiramationcode.html";
        }
    }

}
