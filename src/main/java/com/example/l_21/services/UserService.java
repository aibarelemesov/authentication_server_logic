package com.example.l_21.services;

import com.example.l_21.entities.Otp;
import com.example.l_21.entities.Users;
import com.example.l_21.repositories.OtpRepository;
import com.example.l_21.repositories.UsersRepository;
import com.example.l_21.utils.GenerateCodeUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OtpRepository otpRepository;

    public void addUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public void auth(Users user){
        Optional<Users> o = usersRepository.findUsersByUsername(user.getUsername());

        if (o.isPresent()){
            Users u = o.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())){
                renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private void renewOtp(Users u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> o = otpRepository.findOtpByUsername(u.getUsername());
        if (o.isPresent()){
            Otp otp = o.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

    public boolean check(Otp otpToValidate){
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());

        if (userOtp.isPresent()){
            Otp otp = userOtp.get();
            return otpToValidate.getCode().equals(otp.getCode());
        }

        return false;
    }
}
