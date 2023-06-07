package net.shop2k.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showLoginUser() {
        return "html/users/login.html";
    }

    /*
     * ユーザーを登録画面表示
     */
    @GetMapping("/register")
    public String showRegisterUser() {
        return "html/users/register.html";
    }

    /*
     * パスワードを取得画面表示
     */
    @GetMapping("/forgot-password")
    public String showForgotUser() {
        return "html/users/forgotpassword.html";
    }

    /*
     * リセットパスワード
     */
    @GetMapping("/forgot-password-reset")
    public String showForgotPasswordReset() {
        return "html/users/code-update-password.html";
    }

    /*
     * ユーザーを登録機能
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("confirmedPassword") String confirmedPassword,
            @RequestParam("nickName") String nickName, Model model, RedirectAttributes redirectAttributes) {

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
            // return "redirect:html/users/confiramationcode.html";
        } catch (IllegalArgumentException e) {
            /*
             * エラー
             */
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            log.info("エラー：このemailは存在してる");
            return "redirect:/blog/register";
        } catch (UsernameNotFoundException e) {
            /*
             * エラー
             */
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            log.info("空白でした");
            return "redirect:/blog/register";
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
            return "redirect:/blog/login";
        } catch (IllegalArgumentException e) {
            /*
             * エラー
             */
            model.addAttribute("errorMessage", e.getMessage());
            log.info("Emailで承認できなかった");
            return "html/users/confiramationcode.html";
        }
    }

    /*
     * パスワードを取得機能
     */
    // EmailにCodeを送信する
    @PostMapping("/forgot-password")
    public String getForgot(@RequestParam("username") String username, RedirectAttributes redirectAttributes,
            Model model) {
        try {
            //Emailが存在してる場合
            userService.sendConfirmationCode(username);
            log.info("codeを送信した");
            redirectAttributes.addFlashAttribute("successMessageForgot", "Mã xác nhận đã được gửi đến Email của bạn");
            return "redirect:/blog/forgot-password-reset";
        } catch (IllegalArgumentException e) {
            //Emailが存在しなかった場合
            redirectAttributes.addFlashAttribute("errorMessageForgot", e.getMessage());
            log.info("エラー：このEmailは存在しなかった");
            return "redirect:/blog/forgot-password";
        }
    }

    /*
     * 新しいパスワードを取得する
     * codeを入力
     * 新しいパスワードを入力
     */
    @PostMapping("/forgot-password-reset")
    public String getForgotReset(@RequestParam("confirmationCode") String confirmationCode,@RequestParam("newPassword") String newPassword,
            @RequestParam("newConfirmedPassword") String newConfirmedPassword, RedirectAttributes redirectAttributes, Model model) {
        try{
            //正しく入力した場合
            userService.confirmForgotPass(confirmationCode, newPassword, newConfirmedPassword);
            log.info("パスワードを取得できた");
            redirectAttributes.addFlashAttribute("successMessageReset", "Lấy lại mật khẩu thành công");
            return "redirect:/blog/login";
        }catch(IllegalArgumentException e){
            //間違った場合
            log.info("エラー：" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessageReset", e.getMessage());
            return "redirect:/blog/forgot-password-reset";
        }
    }

}
