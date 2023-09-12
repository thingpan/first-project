package com.game.team1.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.team1.service.UserInfoService;
import com.game.team1.vo.MsgVO;
import com.game.team1.vo.UserInfoVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserInfoController {
    @Autowired
    private UserInfoService userService;
    
	@GetMapping("/user-infos")
	public List<UserInfoVO> getUserInfos(UserInfoVO user){

		return userService.getUserInfos(user);
	}
	@GetMapping("/user-infos/{uiNum}")
	public UserInfoVO getUserInfo(@PathVariable("uiNum") int uiNum) {
		log.info("단일값 조회=>{}", userService.getUserInfo(uiNum));
		return userService.getUserInfo(uiNum);
	}
    @PostMapping("/login")
    public MsgVO login(@RequestBody UserInfoVO user, MsgVO msg, HttpSession session){
        log.info("user=>{}", user);
        UserInfoVO loginUser = userService.login(user);
        msg.setMsg("아이디나 비밀번호를 확인하세요");
        if(loginUser!=null){
            session.setAttribute("user", loginUser);
            msg.setMsg("로그인이 성공하였습니다.");
            msg.setUrl("/");
            msg.setSuccess(true);
        }
        return msg;
    }

    @PostMapping("/insert")
    public MsgVO join(@RequestBody UserInfoVO user, MsgVO msg, HttpSession session){
        int joinResult = userService.insertUserInfo(user);
        msg.setMsg("Join Failed");
        if(joinResult!= 0){
            msg.setMsg("Join Succed. plz Login");
            msg.setUrl("/");
            msg.setSuccess(true);
        }
        return msg;
    }

    @DeleteMapping("/delete/{uiNum}")
    public MsgVO delete(@PathVariable int uiNum, MsgVO msg){
        int joinResult = userService.deleteUserInfo(uiNum);
        msg.setMsg("Join Failed");
        if(joinResult!= 0){
            msg.setMsg("Delete Succed.");
            msg.setUrl("/");
            msg.setSuccess(true);
        }
        return msg;
    }
}
