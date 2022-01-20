package com.gunt.security.controllers;


import com.gunt.security.dao.RoleDAO;
import com.gunt.security.dao.RoleDAOImpl;
import com.gunt.security.entity.Role;
import com.gunt.security.entity.User;
import com.gunt.security.service.UserService;
import com.gunt.security.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {


    private UserService userService;
    private final RoleDAO roleDAO;

    @Autowired
    public AdminController(UserService userService, RoleDAO roleDAO) {
        this.userService = userService;
        this.roleDAO = roleDAO;
    }

    @RequestMapping("/admin")
    public String showAllUsers(Model model) {

        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all-users";
    }

    @RequestMapping("/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-info";

    }

    @PostMapping(value = "/updateInfo")
    public String updateInfo(
            @ModelAttribute("id") long id,
            @ModelAttribute("name") String name,
            @ModelAttribute("password") String password,
            @ModelAttribute("lastName") String lastName,
            @ModelAttribute("age") byte age,
            @RequestParam("roles") String[] roles
    ) {
        User user = userService.getById(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        if (!password.isEmpty()) {
            user.setPassword(password);
        }
        Set<Role> setroles = new HashSet<>();
        for (String st : roles) {
            if (st.equals("ADMIN")) {
                Role role_admin = roleDAO.createRoleIfNotFound("ADMIN", 1L);
                setroles.add(role_admin);
            }
            if (st.equals("USER")) {
                Role role_user = roleDAO.createRoleIfNotFound("USER", 2L);
                setroles.add(role_user);
            }
        }
        user.setRoles(setroles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/updateinfo")
    public ModelAndView updateUser(@RequestParam("usId") Long id) {
        User user = userService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("updateInfo");
        modelAndView.addObject("user", user);
        Set<Role> setroles = new HashSet<>();
        Role role_admin = roleDAO.createRoleIfNotFound("ADMIN", 1L);
        Role role_user = roleDAO.createRoleIfNotFound("USER", 2L);
        setroles.add(role_admin);
        setroles.add(role_user);
        modelAndView.addObject("rolelist", setroles);
        return modelAndView;
    }


    @RequestMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam("usId") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
