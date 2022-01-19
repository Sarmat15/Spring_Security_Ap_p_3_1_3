package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private  UserService userService;
    private  RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping
    public String findAll(Model model, Principal principal){
        model.addAttribute("admin", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("newUser", new User());
        return "/admin";
    }



    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) { return "/admin/create"; }
        else {
            userService.saveUser(user);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }



    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user,@RequestParam(value = "role") String role, BindingResult bindingResult){
        user.setRoles(roleService.findRolesByName(role));
        if (bindingResult.hasErrors()) { return "/admin/update/{id}"; }
        else userService.saveUser(user);
        return "redirect:/admin";
    }
}
