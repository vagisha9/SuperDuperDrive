package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HomeController {

    FileService fileService;
    UserService userService;
    NoteService noteService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
    }

    @RequestMapping("/home")
    public  String getHomePage(Authentication auth, Model model) {
        String username = auth.getName();
        User user = userService.getUser(username);
        model.addAttribute("files", this.fileService.getFiles(user.getUserid()));
        return "home";
    }



    @GetMapping("/file/view/{fileId}")
    public String getFile(@PathVariable long fileId, Authentication authentication, Model model){
        model.addAttribute("files",this.fileService.getFileById(fileId));
        return "file";
    }

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication auth, Model model) throws IOException {
        String username = auth.getName();
        User user = userService.getUser(username);
        File file = new File();
        file.setFilename(fileUpload.getOriginalFilename());
        file.setFilesize(fileUpload.getSize());
        file.setContenttype(fileUpload.getContentType());
        file.setFiledata(fileUpload.getBytes());
        file.setUserid(user.getUserid());
        fileService.uploadFile(file);
        model.addAttribute("files",this.fileService.getFiles(user.getUserid()));
        return "file";
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable long fileId, Authentication authentication,Model model) {
        this.fileService.deleteFile(fileId);
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("files",this.fileService.getFiles(user.getUserid()));
        return "file";
    }

    @GetMapping("/notes/delete/{noteid}")
    public String deleteNote(Authentication authentication, @PathVariable("noteid") Integer noteid, Model model){
        this.noteService.deleteNote(noteid);
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "notes";
    }

    @GetMapping("/notes/update/{noteid}")
    public String editNote(Authentication authentication, @PathVariable("noteid") Integer noteid, Note note, Model model){
        this.noteService.updateNote(new Note(note.getNoteid(),note.getNotetitle(),note.getNotedescription(),userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "notes";
    }

    @PostMapping("/notes")
    public String addNote(Authentication authentication, Note note, Model model){
        Integer userid= userService.getUser(authentication.getName()).getUserid();
        note.setUserid(userid);
        Note noteObj = new Note(null,note.getNotetitle(),note.getNotedescription(), note.getUserid());
        this.noteService.addNewNote(note);
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "notes";
    }
}
