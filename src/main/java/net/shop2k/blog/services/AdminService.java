// package net.shop2k.blog.services;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// import net.shop2k.blog.entitys.Admin;
// import net.shop2k.blog.repositorys.AdminRepository;

// /*
//  * ADMIN Service
//  */
// @Service
// public class AdminService  implements UserDetailsService{
    
//     @Autowired
//     private AdminRepository adminRepository;

//     @Autowired
//     private BCryptPasswordEncoder bCryptPasswordEncoder;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         Admin admin = adminRepository.findByUsername(username);
//         if(admin == null){
//             throw new UsernameNotFoundException("Loix");
//         }
//         List<GrantedAuthority> authorities = new ArrayList<>();
//         authorities.add(new SimpleGrantedAuthority(admin.getRole()));

//         return new org.springframework.security.core.userdetails.User(
//             admin.getUsername(),
//             admin.getPassword(),
//             admin.isSetEnabled(),
//             true,
//             true,
//             true,
//             authorities
//         );
//     }

//     public void registerAdmin(Admin admin){
//         if(adminRepository.findByUsername(admin.getUsername()) != null){
//             throw new IllegalArgumentException("Admim đã tồn tại");
//         }
//         String encodedPassword = bCryptPasswordEncoder.encode(admin.getPassword());
//         admin.setPassword(encodedPassword);
//         adminRepository.save(admin);
//     }
// }
